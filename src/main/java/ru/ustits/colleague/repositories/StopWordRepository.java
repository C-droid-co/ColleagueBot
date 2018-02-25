package ru.ustits.colleague.repositories;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.StopWordRecord;

/**
 * @author ustits
 */
@CacheConfig(cacheNames = "stopwords")
public interface StopWordRepository extends CrudRepository<StopWordRecord, Integer> {

  @Override
  @Cacheable
  Iterable<StopWordRecord> findAll();

}
