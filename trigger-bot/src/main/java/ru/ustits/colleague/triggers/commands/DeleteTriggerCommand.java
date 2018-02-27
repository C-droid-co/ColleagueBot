package ru.ustits.colleague.triggers.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class DeleteTriggerCommand extends AbstractTriggerCommand {

  public DeleteTriggerCommand(final String commandIdentifier, final String description,
                              final TriggerRepository repository, final Parser<TriggerRecord> parser) {
    super(commandIdentifier, description, repository, parser);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                                       final String[] arguments) {
    final TriggerRecord record = getParser().buildRecord(toUnsignedLong(user.getId()), chat.getId(), arguments);
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

  private boolean deleteTrigger(final TriggerRecord record) {
    if (record == null) {
      return false;
    }
    final boolean exists = getRepository().existsByTriggerAndChatIdAndUserId(record.getTrigger(), record.getChatId(),
            record.getUserId());
    if (exists) {
      getRepository().deleteByTriggerAndChatIdAndUserId(record.getTrigger(), record.getChatId(),
              record.getUserId());
      return true;
    } else {
      return false;
    }
  }

}
