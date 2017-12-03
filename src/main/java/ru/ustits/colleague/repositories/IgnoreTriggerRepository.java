package ru.ustits.colleague.repositories;

import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.IgnoreTriggerRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ustits
 */
public class IgnoreTriggerRepository extends AbstractRepository<IgnoreTriggerRecord> {

  public IgnoreTriggerRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  protected IgnoreTriggerRecord innerAdd(final IgnoreTriggerRecord entity) throws SQLException {
    return sql().insert("INSERT INTO ignore_triggers (chat_id, user_id) VALUES (?, ?)",
            this::addRecord,
            entity.getChatId(), entity.getUserId());
  }

  @Override
  protected IgnoreTriggerRecord innerFetchOne(final IgnoreTriggerRecord entity) throws SQLException {
    return sql().query("SELECT * FROM ignore_triggers WHERE chat_id=? AND user_id=?",
            this::fetchOneRecord,
            entity.getChatId(), entity.getUserId());
  }

  @Override
  protected int innerUpdate(final IgnoreTriggerRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void innerDelete(final IgnoreTriggerRecord entity) throws SQLException {
    sql().update("DELETE FROM ignore_triggers WHERE chat_id=? AND user_id=?",
            entity.getChatId(), entity.getUserId());
  }

  @Override
  public IgnoreTriggerRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final Long chatId = resultSet.getLong(2);
    final Long userId = resultSet.getLong(3);
    return new IgnoreTriggerRecord(id, chatId, userId);
  }
}
