package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class UserRepository extends AbstractRepository<UserRecord> {

  public UserRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  public UserRecord innerAdd(final UserRecord entity) throws SQLException {
    return sql().insert("INSERT INTO users (id, first_name, last_name, user_name) VALUES (?, ?, ?, ?)",
            this::addRecord,
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getUserName());
  }

  @Override
  public UserRecord innerFetchOne(final UserRecord entity) throws SQLException {
    return sql().query("SELECT * FROM users WHERE id=?",
            this::fetchOneRecord,
            entity.getId());
  }

  @Override
  public int innerUpdate(final UserRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void innerDelete(final UserRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public List<UserRecord> fetchAll() {
    try {
      return sql().query("SELECT * FROM users",
              this::fetchAllRecords);
    } catch (SQLException e) {
      log.error("Unable to fetch messages", e);
    }
    return Collections.emptyList();
  }

  @Override
  protected UserRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Long id = resultSet.getLong(1);
    final String firstName = resultSet.getString(2);
    final String lastName = resultSet.getString(3);
    final String userName = resultSet.getString(4);
    return new UserRecord(id, firstName, lastName, userName);
  }
}
