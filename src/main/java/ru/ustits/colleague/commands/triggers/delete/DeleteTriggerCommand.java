package ru.ustits.colleague.commands.triggers.delete;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.triggers.AbstractTriggerCommand;
import ru.ustits.colleague.commands.triggers.TriggerStrategy;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class DeleteTriggerCommand extends AbstractTriggerCommand {

  public DeleteTriggerCommand(final String commandIdentifier, final TriggerRepository repository,
                              final TriggerStrategy commandStrategy) {
    super(commandIdentifier, "delete trigger", repository, commandStrategy);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                                       final String[] arguments) {
    final TriggerRecord record = getCommandStrategy().buildRecord(toUnsignedLong(user.getId()), chat.getId(), arguments);
    final boolean isDeleted = deleteTrigger(record);
    final SendMessage answer;
    if (isDeleted) {
      answer = new SendMessage(chat.getId(), String.format("Deleted trigger [%s]", record.getTrigger()));
    } else {
      answer = new SendMessage(chat.getId(), "Unable to delete trigger");
    }
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error("Unable to send delete message", e);
    }
  }

  boolean deleteTrigger(final TriggerRecord record) {
    if (record == null) {
      return false;
    }
    final boolean exists = getRepository().exists(record);
    if (exists) {
      getRepository().delete(record);
      return true;
    } else {
      return false;
    }
  }

}
