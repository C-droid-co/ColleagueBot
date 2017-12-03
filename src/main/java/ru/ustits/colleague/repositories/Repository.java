package ru.ustits.colleague.repositories;

import java.util.List;

/**
 * @author ustits
 */
public interface Repository<T> {

  T add(final T entity);

  boolean exists(final T entity);

  T fetchOne(final T entity);

  int update(final T entity);

  void delete(final T entity);

  List<T> fetchAll();

}
