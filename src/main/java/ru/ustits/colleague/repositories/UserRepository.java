package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class UserRepository extends AbstractRepository<UserRecord> {

  public boolean exists(final User entity) {
    try {
      return sql().query("SELECT * FROM users WHERE id=?", ResultSet::next, entity.getId());
    } catch (SQLException e) {
      log.error(e);
    }
    return false;
  }

  @Override
  public UserRecord add(final UserRecord entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public UserRecord fetchOne(final UserRecord entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int update(final UserRecord entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(final UserRecord entity) {
    throw new UnsupportedOperationException();
  }

  public UserRecord add(final User entity) {
    try {
      return sql().insert("INSERT INTO users (id, first_name, last_name, user_name) VALUES (?, ?, ?, ?)",
              resultSet -> {
                resultSet.next();
                final Long id = resultSet.getLong(1);
                final String firstName = resultSet.getString(2);
                final String lastName = resultSet.getString(3);
                final String userName = resultSet.getString(4);
                final UserRecord record = new UserRecord(id, firstName, lastName, userName);
                log.info(record);
                return record;
              },
              entity.getId(),
              entity.getFirstName(),
              entity.getLastName(),
              entity.getUserName());
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  public List<UserRecord> fetchAll() {
    try {
      return sql().query("SELECT * FROM users",
              resultSet -> {
                final List<UserRecord> records = new ArrayList<>();
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

  private UserRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Long id = resultSet.getLong(1);
    final String firstName = resultSet.getString(2);
    final String lastName = resultSet.getString(3);
    final String userName = resultSet.getString(4);
    return new UserRecord(id, firstName, lastName, userName);
  }
}
