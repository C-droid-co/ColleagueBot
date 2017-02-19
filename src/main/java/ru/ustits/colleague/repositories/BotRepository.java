package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ustits
 */
@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
public abstract class BotRepository<T, V extends Record> implements Repository<T, V> {

  @Autowired
  private DSLContext dsl;
}
