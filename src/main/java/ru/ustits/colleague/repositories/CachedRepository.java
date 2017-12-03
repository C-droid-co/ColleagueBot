package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * @author ustits
 */
public final class CachedRepository<T> implements Repository<T> {

  private final Repository<T> innerRepository;

  @Getter(AccessLevel.PROTECTED)
  private List<T> entities;

  public CachedRepository(final Repository<T> innerRepository) {
    this.innerRepository = innerRepository;
  }

  protected CachedRepository(final Repository<T> innerRepository, final List<T> entities) {
    this.innerRepository = innerRepository;
    this.entities = entities;
  }

  @Override
  public T add(final T entity) {
    final T dbRecord = innerRepository.add(entity);
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
      return innerRepository.exists(entity);
    }
  }

  @Override
  public T fetchOne(final T entity) {
    final T dbEntity = innerRepository.fetchOne(entity);
    if (entities != null && !entities.contains(dbEntity)) {
      entities.add(dbEntity);
    }
    return dbEntity;
  }

  @Override
  public int update(final T entity) {
    return innerRepository.update(entity);
  }

  @Override
  public void delete(final T entity) {
    innerRepository.delete(entity);
    if (entities != null) {
      entities.remove(entity);
    }
  }

  @Override
  public List<T> fetchAll() {
    if (entities == null) {
      entities = innerRepository.fetchAll();
    }
    return entities;
  }

}
