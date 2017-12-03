package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author ustits
 */
public final class CachedRepository<T> extends RepositoryWrapper<T> {

  private static final long DEFAULT_UPDATE_TIME = 600000;

  private final long updateDelay;

  @Getter(AccessLevel.PROTECTED)
  private List<T> entities;
  private Long lastUpdateTime;

  public CachedRepository(final Repository<T> innerRepository) {
    this(innerRepository, DEFAULT_UPDATE_TIME);
  }

  public CachedRepository(final Repository<T> innerRepository, final long updateDelay) {
    this(innerRepository, null, updateDelay);
  }

  protected CachedRepository(final Repository<T> innerRepository, final List<T> entities) {
    this(innerRepository, entities, DEFAULT_UPDATE_TIME);
  }

  protected CachedRepository(final Repository<T> innerRepository, final List<T> entities, final long updateDelay) {
    super(innerRepository);
    this.entities = entities;
    this.updateDelay = updateDelay;
  }

  protected CachedRepository(final Repository<T> innerRepository, final List<T> entities, final long updateDelay,
                             final long lastUpdateTime) {
    this(innerRepository, entities, updateDelay);
    this.lastUpdateTime = lastUpdateTime;
  }

  @Override
  public T add(final T entity) {
    final T dbRecord = super.add(entity);
    if (dbRecord != null && entities != null) {
      entities.add(dbRecord);
    }
    return dbRecord;
  }

  @Override
  public boolean exists(final T entity) {
    if (entities != null) {
      return entities.contains(entity);
    } else {
      return super.exists(entity);
    }
  }

  @Override
  public T fetchOne(final T entity) {
    final T dbEntity = super.fetchOne(entity);
    if (entities != null && !entities.contains(dbEntity)) {
      entities.add(dbEntity);
    }
    return dbEntity;
  }

  @Override
  public void delete(final T entity) {
    super.delete(entity);
    if (entities != null) {
      entities.remove(entity);
    }
  }

  @Override
  public List<T> fetchAll() {
    if (entities == null || isTimeToUpdate()) {
      entities = super.fetchAll();
      lastUpdateTime = new Date().getTime();
    }
    return entities;
  }

  protected boolean isTimeToUpdate() {
    final long currentTime = new Date().getTime();
    return isTimeToUpdate(lastUpdateTime, currentTime);
  }

  protected boolean isTimeToUpdate(final long was, final long become) {
    return lastUpdateTime == null || become > was + updateDelay;
  }

}
