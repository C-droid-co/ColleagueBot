package ru.ustits.colleague.repositories;

/**
 * @author ustits
 */
public interface Repository<T, V> {

  V add(final T entity);

  boolean exists(final T entity);
}
