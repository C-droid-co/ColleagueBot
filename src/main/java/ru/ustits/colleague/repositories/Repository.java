package ru.ustits.colleague.repositories;

import org.jooq.Record;

/**
 * @author ustits
 */
public interface Repository<T, V extends Record> {

  V add(final T entity);

  boolean exists(final T entity);
}
