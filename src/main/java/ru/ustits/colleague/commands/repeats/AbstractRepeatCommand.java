package ru.ustits.colleague.commands.repeats;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.ArgsAwareCommand;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

import java.util.Arrays;
import java.util.Optional;

import static ru.ustits.colleague.tools.StringUtils.asString;

/**
 * @author ustits
 */
@Log4j2
public abstract class AbstractRepeatCommand extends ArgsAwareCommand {

  private final RepeatScheduler scheduler;
  private final RepeatService service;

  public AbstractRepeatCommand(final String commandIdentifier, final String description,
                               final int minArgsLen, final RepeatScheduler scheduler,
                               final RepeatService service) {
    super(commandIdentifier, description, minArgsLen);
    this.scheduler = scheduler;
    this.service = service;
  }

  @Override
  protected void executeInternal(final AbsSender absSender, final User user, final Chat chat,
                                 final String[] arguments) {
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
    final Optional<String> message = parseMessage(arguments);
    final Optional<String> cron = parseCron(arguments);
    if (message.isPresent() && cron.isPresent()) {
      final RepeatRecord record = service.addRepeat(message.get(), transformCron(cron.get()), chat, user);
      final boolean isScheduled = scheduler.scheduleTask(record, sender);
      if (!isScheduled) {
        service.deleteRepeat(record);
        return false;
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

  protected abstract String transformCron(final String cron);

  Optional<String> parseMessage(final String[] arguments) {
    final String text = asString(arguments, getMinArgsLen() - 1);
    log.info("Parsed repeat task text: {}", text);
    return Optional.of(text);
  }

  Optional<String> parseCron(final String[] arguments) {
    return Optional.of(
            asString(arguments, 0, getMinArgsLen() - 1));
  }
}
