package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Update;

/**
 * @author ustits
 */
@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
public abstract class BotRepository<T, V extends Record> implements Repository {

  @Autowired
  private DSLContext dsl;

  @Override
  public void add(final Update update) {
    throw new UnsupportedOperationException("Method is not supported for this repository.");
  }

  protected abstract V add(final T entity);
}
