package ru.ustits.colleague.commands;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;
import ru.ustits.colleague.tools.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
public final class RepeatCommand extends BotCommand {

  private static final Integer PARAMETERS_COUNT = 7;

  private final RepeatScheduler scheduler;
  private final RepeatService service;

  public RepeatCommand(final String commandIdentifier, final RepeatScheduler scheduler,
                       final RepeatService service) {
    super(commandIdentifier, "command for adding repeatable messages");
    this.scheduler = scheduler;
    this.service = service;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    try {
      final String message = scheduleTask(arguments, chat, user, absSender) ?
              "Job scheduled" : "Failed to schedule job";
      absSender.execute(new SendMessage(chat.getId(), message));
    } catch (TelegramApiException e) {
      log.error("Unable to inform about job", e);
    }
  }

  boolean scheduleTask(final String[] arguments, final Chat chat,
                       final User user, @NonNull final AbsSender sender) {
    log.info("Got arguments {} for repeat task", Arrays.toString(arguments));
    if (arguments == null || arguments.length < PARAMETERS_COUNT) {
      return false;
    } else {
      final Optional<String> message = parseMessage(arguments);
      final Optional<String> cron = parseCron(arguments);
      if (message.isPresent() && cron.isPresent()) {
        final RepeatRecord record = service.addRepeat(message.get(), cron.get(), chat, user);
        return record != null && scheduler.scheduleTask(record, sender);
      } else {
        return false;
      }
    }
  }

  Optional<String> parseMessage(final String[] arguments) {
    final String text = StringUtils.asString(arguments, PARAMETERS_COUNT - 1);
    log.info("Parsed repeat task text: {}", text);
    return Optional.of(text);
  }

  Optional<String> parseCron(final String[] arguments) {
    return Optional.of(
            StringUtils.asString(arguments, 0, PARAMETERS_COUNT - 1));
  }
}
