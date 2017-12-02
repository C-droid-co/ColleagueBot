package ru.ustits.colleague.repositories.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public final class MessageService {

  private final QueryRunner sql;

  public Map<String, Long> count(final Long chatId, final boolean isEdited) {
    try {
      final Map<String, Long> counts = new LinkedHashMap<>();
      return sql.query("SELECT users.first_name, count(*) as messages_count " +
                      "FROM messages " +
                      "INNER JOIN users ON (messages.user_id = users.id) " +
                      "WHERE messages.chat_id=? AND messages.is_edited=? " +
                      "GROUP BY users.first_name " +
                      "ORDER BY messages_count DESC",
              resultSet -> {
                while (resultSet.next()) {
                  final String name = resultSet.getString(1);
                  final long count = resultSet.getLong(2);
                  counts.put(name, count);
                }
                log.info("Fetched: {}", counts);
                return counts;
              }, chatId, isEdited);
    } catch (SQLException e) {
      log.error("Unable to count messages", e);
    }
    return Collections.emptyMap();
  }

}
