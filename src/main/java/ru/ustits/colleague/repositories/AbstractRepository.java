package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ustits
 */
@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractRepository<T> implements Repository<T> {

  @Autowired
  private QueryRunner sql;

  @Override
  public boolean exists(final T entity) {
    return fetchOne(entity) != null;
  }
}
