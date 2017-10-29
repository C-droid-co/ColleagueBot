package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.api.objects.Chat;
import ru.ustits.colleague.repositories.records.ChatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
@Repository
public class ChatsRepository extends BotRepository<Chat, ChatRecord> {

  @Override
  public boolean exists(final Chat entity) {
    try {
      return sql().query("SELECT * FROM chats WHERE id=?", ResultSet::next, entity.getId());
    } catch (SQLException e) {
      log.error(e);
    }
    return false;
  }

  @Override
  public ChatRecord add(final Chat entity) {
    return add(new ChatRecord(entity.getId(), null, entity.getTitle()));
  }

  public ChatRecord add(final ChatRecord record) {
    try {
      final ChatRecord result = sql().insert("INSERT INTO chats (id, title) VALUES (?, ?)",
              resultSet -> {
                resultSet.next();
                final Long id = resultSet.getLong(1);
                final String title = resultSet.getString(3);
                return new ChatRecord(id, null, title);
              },
              record.getId(),
              record.getTitle());
      log.info(result);
      return result;
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  public List<ChatRecord> fetchAll() {
    try {
      return sql().query("SELECT * FROM chats",
              resultSet -> {
                final List<ChatRecord> records = new ArrayList<>();
                while (resultSet.next()) {
                  records.add(toRecord(resultSet));
                }
                return records;
              });
    } catch (SQLException e) {
      log.error("Unable to fetch messages", e);
    }
    return Collections.emptyList();
  }

  private ChatRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Long id = resultSet.getLong(1);
    final String title = resultSet.getString(3);
    return new ChatRecord(id, null, title);
  }
}
