package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author ustits
 */
public final class CachedRepository<T> extends RepositoryWrapper<T> {

  @Getter(AccessLevel.PROTECTED)
  private List<T> entities;

  public CachedRepository(final Repository<T> innerRepository) {
    super(innerRepository);
  }

  protected CachedRepository(final Repository<T> innerRepository, final List<T> entities) {
    this(innerRepository);
    this.entities = entities;
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
    if (entities == null) {
      entities = super.fetchAll();
    }
    return entities;
  }

}
