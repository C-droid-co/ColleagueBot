package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.api.objects.Chat;
import ru.ustits.colleague.repositories.records.ChatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    try {
      final ChatRecord record = sql().insert("INSERT INTO chats (id, title) VALUES (?, ?)",
              resultSet -> {
                resultSet.next();
                final Long id = resultSet.getLong(1);
                final String title = resultSet.getString(3);
                return new ChatRecord(id, null, title);
              },
              entity.getId(),
              entity.getTitle());
      log.info(record);
      return record;
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }
}
