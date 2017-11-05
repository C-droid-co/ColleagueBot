package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class DeleteTriggerCommand extends AbstractTriggerCommand {

  private static final Integer MIN_ARGS = 1;

  public DeleteTriggerCommand(final String commandIdentifier, final TriggerRepository repository) {
    super(commandIdentifier, "delete trigger", repository, MIN_ARGS);
  }

  @Override
  protected void executeInternal(final AbsSender absSender, final User user, final Chat chat,
                                       final String[] arguments) {
    final String trigger = resolveTrigger(arguments);
    final boolean isDeleted = deleteTrigger(trigger, chat.getId(), toUnsignedLong(user.getId()));
    final SendMessage answer;
    if (isDeleted) {
      answer = new SendMessage(chat.getId(), String.format("Deleted trigger [%s]", trigger));
    } else {
      answer = new SendMessage(chat.getId(), String.format("Unable to delete trigger [%s]", trigger));
    }
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error("Unable to send delete message", e);
    }
  }

  boolean deleteTrigger(final String trigger, final Long chatId, final Long userId) {
    final TriggerRecord toDelete = new TriggerRecord(trigger, chatId, userId);
    final boolean exists = getRepository().exists(toDelete);
    if (exists) {
      getRepository().delete(toDelete);
      return true;
    } else {
      return false;
    }
  }

}
