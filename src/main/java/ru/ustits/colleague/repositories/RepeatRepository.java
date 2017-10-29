package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class RepeatRepository extends BotRepository<String, RepeatRecord> {

  @Override
  public RepeatRecord add(final String entity) {
    return null;
  }

  public RepeatRecord add(final RepeatRecord record) {
    try {
      return sql().insert("INSERT INTO repeats (message, chat_id, user_id, cron) VALUES (?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final RepeatRecord result = toRecord(resultSet);
                log.info(result);
                return result;
              },
              record.getMessage(), record.getChatId(), record.getUserId(), record.getCron());
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  public List<RepeatRecord> fetchAll(final Long chatId) {
    try {
      return sql().query("SELECT * FROM repeats WHERE chat_id=?",
              resultSet -> {
                final List<RepeatRecord> records = new ArrayList<>();
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

  @Override
  public boolean exists(final String entity) {
    return false;
  }

  private RepeatRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String message = resultSet.getString(2);
    final Long chatId = resultSet.getLong(3);
    final Long userId = resultSet.getLong(4);
    final String cron = resultSet.getString(5);
    return RepeatRecord.builder()
            .id(id)
            .message(message)
            .chatId(chatId)
            .userId(userId)
            .cron(cron)
            .build();
  }
}