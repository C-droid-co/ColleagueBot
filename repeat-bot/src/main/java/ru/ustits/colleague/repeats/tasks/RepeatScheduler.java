package ru.ustits.colleague.repeats.tasks;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.tools.cron.CronRestriction;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public class RepeatScheduler {

  static final String SENDER_KEY = "sender";
  static final String MESSAGE_KEY = "message";
  static final String CHAT_KEY = "chat";

  private final Scheduler scheduler;

  public void scheduleTasks(final List<RepeatRecord> repeatRecords, @NonNull final AbsSender sender) {
    repeatRecords.forEach(record -> scheduleTask(record, sender));
  }

  public boolean scheduleTask(final RepeatRecord repeat, @NonNull final AbsSender sender) {
    return scheduleTask(repeat.getCron(), repeat.getMessage(), repeat.getChatId(), sender);
  }

  public boolean scheduleTask(final String cron, final String message, final Long chatId,
                              @NonNull final AbsSender sender) {
    final Optional<String> text = Optional.ofNullable(message);
    final Optional<CronExpression> cronExpression = parseCron(cron);
    return text.isPresent() && cronExpression.isPresent() &&
            scheduleTask(text.get(), cronExpression.get(), chatId, sender);
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

  Optional<CronExpression> parseCron(final String expression) {
    if (isValidExpression(expression)) {
      try {
        log.info("Parsed repeat task cron expression [{}]", expression);
        return new CronRestriction(
                new CronExpression(expression)).restrictToMinutes();
      } catch (ParseException e) {
        throw new IllegalStateException("Error occurred, though the expression was validated", e);
      }
    } else {
      log.info("[{}] is not valid cron expression", expression);
      return Optional.empty();
    }
  }
}
