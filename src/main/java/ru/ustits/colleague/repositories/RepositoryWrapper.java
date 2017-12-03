package ru.ustits.colleague.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author ustits
 */
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class RepositoryWrapper<T> implements Repository<T> {

  private final Repository<T> innerRepository;

  @Override
  public T add(final T entity) {
    return innerRepository.add(entity);
  }

  @Override
  public boolean exists(final T entity) {
    return innerRepository.exists(entity);
  }

  @Override
  public T fetchOne(final T entity) {
    return innerRepository.fetchOne(entity);
  }

  @Override
  public int update(final T entity) {
    return innerRepository.update(entity);
  }

  @Override
  public void delete(final T entity) {
    innerRepository.delete(entity);
  }

  @Override
  public List<T> fetchAll() {
    return innerRepository.fetchAll();
  }

}
