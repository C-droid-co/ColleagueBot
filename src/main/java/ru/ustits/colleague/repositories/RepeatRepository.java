package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class RepeatRepository extends AbstractRepository<RepeatRecord> {

  public RepeatRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  public RepeatRecord innerAdd(final RepeatRecord record) throws SQLException {
    return sql().insert("INSERT INTO repeats (message, chat_id, user_id, cron) VALUES (?, ?, ?, ?)",
            this::addRecord,
            record.getMessage(), record.getChatId(), record.getUserId(), record.getCron());
  }

  @Override
  public RepeatRecord innerFetchOne(final RepeatRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public int innerUpdate(final RepeatRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public List<RepeatRecord> fetchAll(final Long chatId) {
    try {
      return sql().query("SELECT * FROM repeats WHERE chat_id=?",
              this::fetchAllRecords,
              chatId);
    } catch (SQLException e) {
      log.error("Unable to fetch repeats", e);
    }
    return Collections.emptyList();
  }

  @Override
  public void innerDelete(final RepeatRecord record) throws SQLException {
    sql().update("DELETE FROM repeats WHERE id=?", record.getId());
  }

  @Override
  protected RepeatRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String message = resultSet.getString(2);
    final Long chatId = resultSet.getLong(3);
    final Long userId = resultSet.getLong(4);
    final String cron = resultSet.getString(5);
    return new RepeatRecord(id, message, cron, chatId, userId);
  }
}