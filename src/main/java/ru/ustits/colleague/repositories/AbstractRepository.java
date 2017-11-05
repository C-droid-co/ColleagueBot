package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author ustits
 */
@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractRepository<T> implements Repository<T> {

  private final QueryRunner sql;

  @Override
  public boolean exists(final T entity) {
    return fetchOne(entity) != null;
  }
}
