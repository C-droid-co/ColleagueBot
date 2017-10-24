package ru.ustits.colleague.commands;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.tasks.RepeatTask;
import ru.ustits.colleague.tools.CronRestriction;
import ru.ustits.colleague.tools.StringUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author ustits
 */
@Log4j2
public final class RepeatCommand extends BotCommand {

  public static final String SENDER_KEY = "sender";
  public static final String MESSAGE_KEY = "message";
  public static final String CHAT_KEY = "chat";
  private static final Integer PARAMETERS_COUNT = 7;

  private final Scheduler scheduler;

  public RepeatCommand(final String commandIdentifier, final Scheduler scheduler) {
    super(commandIdentifier, "command for adding repeatable messages");
    this.scheduler = scheduler;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    try {
      final String message = scheduleTask(arguments, chat.getId(), absSender) ?
              "Job scheduled" : "Failed to schedule job";
      absSender.execute(new SendMessage(chat.getId(), message));
    } catch (TelegramApiException e) {
      log.error("Unable to inform about job", e);
    }
  }

  boolean scheduleTask(final String[] arguments, final Long chatId, @NonNull final AbsSender sender) {
    log.info("Got arguments {} for repeat task", Arrays.toString(arguments));
    if (arguments == null || arguments.length < PARAMETERS_COUNT) {
      return false;
    } else {
      final Optional<String> text = parseMessage(arguments);
      final Optional<CronExpression> cron = parseCron(arguments);
      return text.isPresent() && cron.isPresent()
              && scheduleTask(text.get(), cron.get(), chatId, sender);
    }
  }

  private boolean scheduleTask(final String text, final CronExpression cron,
                               final Long chatId, @NonNull final AbsSender sender) {
    final JobDetail job = buildJob(text, chatId, sender);
    final Trigger trigger = buildTrigger(cron, job);
    return scheduleTask(job, trigger);
  }

  private boolean scheduleTask(final JobDetail job, final Trigger trigger) {
    try {
      scheduler.scheduleJob(job, trigger);
      log.info("Scheduled new job");
    } catch (SchedulerException e) {
      log.error("Unable to start job", e);
      return false;
    }
    return true;
  }

  JobDetail buildJob(final String text, final Long chatId, @NonNull final AbsSender sender) {
    final JobDataMap data = new JobDataMap();
    data.put(SENDER_KEY, sender);
    data.put(MESSAGE_KEY, text);
    data.put(CHAT_KEY, chatId);
    return newJob(RepeatTask.class)
            .usingJobData(data)
            .build();
  }

  private Trigger buildTrigger(final CronExpression cronExpression, final JobDetail job) {
    return newTrigger()
            .forJob(job)
            .startNow()
            .withSchedule(cronSchedule(cronExpression))
            .build();
  }

  Optional<String> parseMessage(final String[] arguments) {
    final String text = StringUtils.asString(arguments, PARAMETERS_COUNT - 1);
    log.info("Parsed repeat task text: {}", text);
    return Optional.of(text);
  }

  Optional<CronExpression> parseCron(final String[] arguments) {
    final String expression = StringUtils.asString(arguments, 0, PARAMETERS_COUNT - 1);
    if (isValidExpression(expression)) {
      try {
        log.info("Parsed repeat task cron expression [{}]", expression);
        return new CronRestriction(
                new CronExpression(expression)).restrictToHours();
      } catch (ParseException e) {
        throw new IllegalStateException("Error occurred, though the expression was validated", e);
      }
    } else {
      log.info("[{}] is not valid cron expression", expression);
      return Optional.empty();
    }
  }
}
