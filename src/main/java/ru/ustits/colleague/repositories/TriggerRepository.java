package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class TriggerRepository extends AbstractRepository<TriggerRecord> {

  public TriggerRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  public TriggerRecord innerFetchOne(final TriggerRecord record) throws SQLException {
    return sql().query("SELECT * FROM triggers WHERE chat_id=? AND user_id=? AND trigger=?",
            this::fetchOneRecord,
            record.getChatId(), record.getUserId(), record.getTrigger());
  }

  public List<TriggerRecord> fetchAll(final Long chatId) {
    try {
      return sql().query("SELECT * FROM triggers WHERE chat_id=?",
              this::fetchAllRecords,
              chatId);
    } catch (SQLException e) {
      log.error(e);
    }
    return Collections.emptyList();
  }

  @Override
  public TriggerRecord innerAdd(final TriggerRecord record) throws SQLException {
    return sql().insert("INSERT INTO triggers (trigger, message, chat_id, user_id) VALUES (?, ?, ?, ?)",
            this::addRecord,
            record.getTrigger(), record.getMessage(),
            record.getChatId(), record.getUserId());
  }

  @Override
  protected int innerUpdate(final TriggerRecord entity) throws SQLException {
    return sql().update("UPDATE triggers SET message=? WHERE trigger=? AND chat_id=? AND user_id=?",
            entity.getMessage(), entity.getTrigger(), entity.getChatId(), entity.getUserId());
  }

  @Override
  public void innerDelete(final TriggerRecord record) throws SQLException {
    sql().update("DELETE FROM triggers WHERE trigger=? AND chat_id=? AND user_id=?",
            record.getTrigger(), record.getChatId(), record.getUserId());
  }

  @Override
  public TriggerRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String trigger = resultSet.getString(2);
    final String message = resultSet.getString(3);
    final Long chatId = resultSet.getLong(4);
    final Long userId = resultSet.getLong(5);
    return new TriggerRecord(id, trigger, message, chatId, userId);
  }

}
