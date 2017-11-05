package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.MessageRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class MessageRepository extends AbstractRepository<MessageRecord> {

  public MessageRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  public MessageRecord add(final MessageRecord entity) {
    try {
      return sql().insert("INSERT INTO messages (msg_id, date, text, is_edited, chat_id, user_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final MessageRecord dbRecord = toRecord(resultSet);
                log.info(dbRecord);
                return dbRecord;
              },
              entity.getMessageId(), entity.getDate(), entity.getText(),
              entity.getIsEdited(), entity.getChatId(), entity.getUserId());
    } catch (SQLException e) {
      log.error("Unable to add message", e);
    }
    return null;
  }

  @Override
  public MessageRecord fetchOne(final MessageRecord entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int update(final MessageRecord entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(final MessageRecord entity) {
    throw new UnsupportedOperationException();
  }

  public List<MessageRecord> fetchAll(final Long chatId) {
    try {
      return sql().query("SELECT * FROM messages WHERE chat_id=?",
              resultSet -> {
                final List<MessageRecord> records = new ArrayList<>();
                while (resultSet.next()) {
                  records.add(toRecord(resultSet));
                }
                return records;
              },
              chatId);
    } catch (SQLException e) {
      log.error("Unable to fetch messages", e);
    }
    return Collections.emptyList();
  }

  private MessageRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final Long messageId = resultSet.getLong(2);
    final Timestamp date = resultSet.getTimestamp(3);
    final String text = resultSet.getString(4);
    final Boolean isEdited = resultSet.getBoolean(5);
    final Long chatId = resultSet.getLong(6);
    final Long userId = resultSet.getLong(7);
    return new MessageRecord(id, messageId, date, text, isEdited,
            chatId, userId);
  }
}
