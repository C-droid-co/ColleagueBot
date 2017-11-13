package ru.ustits.colleague.commands.repeats;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

import java.util.Arrays;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class RepeatCommand extends BotCommand {

  private final Parser<RepeatRecord> parser;
  private final RepeatScheduler scheduler;
  private final RepeatService service;

  public RepeatCommand(final String commandIdentifier, final String description,
                       final Parser<RepeatRecord> parser, final RepeatScheduler scheduler,
                       final RepeatService service) {
    super(commandIdentifier, description);
    this.parser = parser;
    this.scheduler = scheduler;
    this.service = service;
  }

  @Override
  public final void execute(final AbsSender absSender, final User user, final Chat chat,
                                 final String[] arguments) {
    try {
      final String message = scheduleTask(arguments, chat, user, absSender) ?
              "Job scheduled" : "Failed to schedule job";
      absSender.execute(new SendMessage(chat.getId(), message));
    } catch (TelegramApiException e) {
      log.error("Unable to inform about job", e);
    }
  }

  final boolean scheduleTask(final String[] arguments, final Chat chat,
                       final User user, @NonNull final AbsSender sender) {
    log.info("Got arguments {} for repeat task", Arrays.toString(arguments));
    final RepeatRecord record = parser.buildRecord(
            toUnsignedLong(user.getId()), chat.getId(), arguments);
    if (record != null) {
      final RepeatRecord dbRecord = service.addRepeat(record, chat, user);
      final boolean isScheduled = scheduler.scheduleTask(dbRecord, sender);
      if (!isScheduled) {
        service.deleteRepeat(dbRecord);
        return false;
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

}
