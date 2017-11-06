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

  @Override
  public final T add(final T entity) {
    try {
      return innerAdd(entity);
    } catch (SQLException e) {
      log.error("Unable to add record: " + entity, e);
    }
    return null;
  }

  @Override
  public final T fetchOne(final T entity) {
    try {
      return innerFetchOne(entity);
    } catch (SQLException e) {
      log.error("Unable to fetch record: " + entity, e);
    }
    return null;
  }

  @Override
  public final int update(final T entity) {
    try {
      final int rows = innerUpdate(entity);
      log.info("Updated record: {}", entity);
      return rows;
    } catch (SQLException e) {
      log.error("Unable to update record: " + entity, e);
    }
    return 0;
  }

  @Override
  public final void delete(final T entity) {
    try {
      innerDelete(entity);
      log.info("Deleted: {}", entity);
    } catch (SQLException e) {
      log.error("Unable to delete record" + entity, e);
    }
  }

  protected abstract T innerAdd(final T entity) throws SQLException;

  protected abstract T innerFetchOne(final T entity) throws SQLException;

  protected abstract int innerUpdate(final T entity) throws SQLException;

  protected abstract void innerDelete(final T entity) throws SQLException;

  protected abstract T toRecord(final ResultSet resultSet) throws SQLException;

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
}
