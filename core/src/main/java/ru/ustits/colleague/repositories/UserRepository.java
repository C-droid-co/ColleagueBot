package ru.ustits.colleague.repositories;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.UserRecord;

/**
 * @author ustits
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository extends CrudRepository<UserRecord, Long> {

  @Override
  @CacheEvict(allEntries = true)
  <S extends UserRecord> S save(S entity);

  @Override
  @Cacheable
  boolean existsById(Long aLong);

}
