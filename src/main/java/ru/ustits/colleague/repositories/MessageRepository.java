package ru.ustits.colleague.repositories;

import org.telegram.telegrambots.api.objects.Message;
import ru.ustits.colleague.tables.records.MessagesRecord;

import java.sql.Timestamp;

import static ru.ustits.colleague.tables.Messages.MESSAGES;

/**
 * @author ustits
 */
public class MessageRepository extends BotRepository<Message, MessagesRecord> {

  @Override
  public boolean exists(final Message entity) {
    final MessagesRecord record = dsl().fetchOne(MESSAGES, MESSAGES.TEXT.equal(entity.getText()));
    return record != null;
  }

  @Override
  public MessagesRecord add(final Message entity) {
    final MessagesRecord record = dsl().newRecord(MESSAGES);
    record.setIsEdited(isEdited(entity));
    record.setMsgId(new Long(entity.getMessageId()));
    record.setDate(new Timestamp((long) entity.getDate() * 1000));
    record.setText(entity.getText());
    record.setUserId(new Long(entity.getFrom().getId()));
    record.setChatId(entity.getChat().getId());
    record.store();
    return record;
  }

  private boolean isEdited(final Message entity) {
    return entity.getEditDate() != null;
  }
}
