package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.ChatRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class ChatsRepository extends AbstractRepository<ChatRecord> {

  public ChatsRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  public ChatRecord innerFetchOne(final ChatRecord entity) throws SQLException {
    return sql().query("SELECT * FROM chats WHERE id=?",
            this::fetchOneRecord,
            entity.getId());
  }

  @Override
  public int innerUpdate(final ChatRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void innerDelete(final ChatRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ChatRecord innerAdd(final ChatRecord record) throws SQLException {
    return sql().insert("INSERT INTO chats (id, title) VALUES (?, ?)",
            this::addRecord,
            record.getId(),
            record.getTitle());
  }

  public List<ChatRecord> fetchAll() {
    try {
      return sql().query("SELECT * FROM chats",
              this::fetchAllRecords);
    } catch (SQLException e) {
      log.error("Unable to fetch messages", e);
    }
    return Collections.emptyList();
  }

  @Override
  protected ChatRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Long id = resultSet.getLong(1);
    final String title = resultSet.getString(3);
    return new ChatRecord(id, null, title);
  }
}
