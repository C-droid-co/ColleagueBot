package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractRepository<T> implements Repository<T> {

  private final QueryRunner sql;

  @Override
  public boolean exists(final T entity) {
    return fetchOne(entity) != null;
  }

  protected final T addRecord(final ResultSet resultSet) throws SQLException {
    resultSet.next();
    final T dbRecord = toRecord(resultSet);
    log.info("Added record: {}", dbRecord);
    return dbRecord;
  }

  protected final T fetchOneRecord(final ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      final T dbRecord = toRecord(resultSet);
      log.info("Fetched record: {}", dbRecord);
      return dbRecord;
    }
    return null;
  }

  protected final List<T> fetchAllRecords(final ResultSet resultSet) throws SQLException {
    final List<T> records = new ArrayList<>();
    while (resultSet.next()) {
      records.add(toRecord(resultSet));
    }
    log.info("Fetched {} records", records.size());
    return records;
  }

  protected abstract T toRecord(final ResultSet resultSet) throws SQLException;
}
