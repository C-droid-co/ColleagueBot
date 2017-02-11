package ru.ustits.colleague;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.tables.records.MessagesRecord;

import java.sql.Timestamp;

import static ru.ustits.colleague.tables.Messages.MESSAGES;

/**
 * @author ustits
 */
public class MessageRepository {

  @Autowired
  private DSLContext dsl;

  public void add(final Update update) {
    if (update.getEditedMessage() != null) {
      add(update.getEditedMessage(), true);
    } else {
      add(update.getMessage(), false);
    }
  }

  private void add(final Message message, final boolean isEdited) {
    final MessagesRecord record = dsl.newRecord(MESSAGES);
    record.setMsgId(new Long(message.getMessageId()));
    record.setDate(new Timestamp((long)message.getDate() * 1000));
    record.setText(message.getText());
    record.setIsEdited(isEdited);
    record.setUserId(new Long(message.getFrom().getId()));
    record.setChatId(message.getChatId());
    record.store();
  }
}
