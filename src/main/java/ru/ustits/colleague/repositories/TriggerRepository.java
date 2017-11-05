package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class TriggerRepository extends AbstractRepository<TriggerRecord> {

  @Override
  public boolean exists(final TriggerRecord record) {
    return fetchOne(record) != null;
  }

  @Override
  public TriggerRecord fetchOne(final TriggerRecord record) {
    try {
      return sql().query("SELECT * FROM triggers WHERE chat_id=? AND user_id=? AND trigger=?",
              resultSet -> {
                if (resultSet.next()) {
                  return toRecord(resultSet);
                }
                return null;
              },
              record.getChatId(), record.getUserId(), record.getTrigger());
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  public List<TriggerRecord> fetchAll(final Long chatId) {
    try {
      return sql().query("SELECT * FROM triggers WHERE chat_id=?",
              resultSet -> {
                final List<TriggerRecord> records = new ArrayList<>();
                while (resultSet.next()) {
                  records.add(toRecord(resultSet));
                }
                return records;
              },
              chatId);
    } catch (SQLException e) {
      log.error(e);
    }
    return Collections.emptyList();
  }

  @Override
  public TriggerRecord add(final TriggerRecord record) {
    try {
      return sql().insert("INSERT INTO triggers (trigger, message, chat_id, user_id) VALUES (?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final TriggerRecord dbRecord = toRecord(resultSet);
                log.info(dbRecord);
                return dbRecord;
              },
              record.getTrigger(), record.getMessage(),
              record.getChatId(), record.getUserId());
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  @Override
  public int update(final TriggerRecord record) {
    try {
      final int rows = sql().update("UPDATE triggers SET message=? WHERE trigger=? AND chat_id=? AND user_id=?",
              record.getMessage(), record.getTrigger(), record.getChatId(), record.getUserId());
      log.info("Updated trigger: " + record.getTrigger());
      return rows;
    } catch (SQLException e) {
      log.error(e);
    }
    return 0;
  }

  @Override
  public void delete(final TriggerRecord record) {
    try {
      sql().update("DELETE FROM triggers WHERE trigger=? AND chat_id=? AND user_id=?",
              record.getTrigger(), record.getChatId(), record.getUserId());
      log.info("Deleted: {}", record);
    } catch (SQLException e) {
      log.error("Unable to delete trigger", e);
    }
  }

  private TriggerRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String trigger = resultSet.getString(2);
    final String message = resultSet.getString(3);
    final Long chatId = resultSet.getLong(4);
    final Long userId = resultSet.getLong(5);
    return new TriggerRecord(id, trigger, message, chatId, userId);
  }

}
