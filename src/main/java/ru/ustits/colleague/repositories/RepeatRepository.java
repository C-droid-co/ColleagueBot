package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @author ustits
 */
@Log4j2
public class RepeatRepository extends BotRepository<String, RepeatRecord> {

  @Override
  public RepeatRecord add(final String entity) {
    return null;
  }

  public RepeatRecord add(final String text, final Long chatId, final Long userId, final Time time) {
    try {
      return sql().insert("INSERT INTO repeaters (message, chat_id, user_id, time) VALUES (?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final RepeatRecord record = toRecord(resultSet);
                log.info(record);
                return record;
              },
              text, chatId, userId, time);
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  @Override
  public boolean exists(final String entity) {
    return false;
  }

  private RepeatRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String message = resultSet.getString(2);
    final Long chatId = resultSet.getLong(3);
    final Long userId = resultSet.getLong(4);
    final Time time = resultSet.getTime(5);
    final LocalTime localTime = time.toLocalTime();
    return new RepeatRecord(id, message, chatId, userId, localTime);
  }
}