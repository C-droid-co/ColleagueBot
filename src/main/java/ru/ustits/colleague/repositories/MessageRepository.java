package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.objects.Message;
import ru.ustits.colleague.repositories.records.MessageRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author ustits
 */
@Log4j2
public class MessageRepository extends BotRepository<Message, MessageRecord> {

  @Override
  public boolean exists(final Message entity) {
    try {
      return sql().query("SELECT * FROM messages WHERE text=?",
              ResultSet::next, entity.getText());
    } catch (SQLException e) {
      log.error(e);
    }
    return false;
  }

  @Override
  public MessageRecord add(final Message entity) {
    try {
      return sql().insert("INSERT INTO messages (msg_id, date, text, is_edited, chat_id, user_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final Integer id = resultSet.getInt(1);
                final Long messageId = resultSet.getLong(2);
                final Timestamp date = resultSet.getTimestamp(3);
                final String text = resultSet.getString(4);
                final Boolean isEdited = resultSet.getBoolean(5);
                final Long chatId = resultSet.getLong(6);
                final Long userId = resultSet.getLong(7);
                final MessageRecord record = new MessageRecord(id, messageId, date, text, isEdited,
                        chatId, userId);
                log.info(record);
                return record;
              },
              new Long(entity.getMessageId()),
              new Timestamp((long) entity.getDate() * 1000),
              entity.getText(),
              isEdited(entity),
              entity.getChat().getId(),
              new Long(entity.getFrom().getId())
              );
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  private boolean isEdited(final Message entity) {
    return entity.getEditDate() != null;
  }
}
