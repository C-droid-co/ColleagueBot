package ru.ustits.colleague.repositories;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ustits.colleague.repositories.records.IgnoreTriggerRecord;

/**
 * @author ustits
 */
@CacheConfig(cacheNames = "ignores")
public interface IgnoreTriggerRepository extends CrudRepository<IgnoreTriggerRecord, Integer> {

  @Override
  @CacheEvict(allEntries = true)
  <S extends IgnoreTriggerRecord> S save(final S entity);

  @Cacheable
  boolean existsByChatIdAndUserId(final Long chatId, final Long userId);

  @CacheEvict(allEntries = true)
  @Transactional
  void deleteByChatIdAndUserId(final Long chatId, final Long userId);

}
