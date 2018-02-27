package ru.ustits.colleague.repositories;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;

/**
 * @author ustits
 */
@CacheConfig(cacheNames = "triggers")
public interface TriggerRepository extends CrudRepository<TriggerRecord, Integer> {

  @Override
  @CacheEvict(allEntries = true)
  <S extends TriggerRecord> S save(final S entity);

  @Override
  @CacheEvict(allEntries = true)
  void deleteById(final Integer id);

  @Cacheable
  List<TriggerRecord> findAllByChatId(final Long chatId);

  boolean existsByTriggerAndChatIdAndUserId(final String trigger, final Long chatId, final Long userId);

  @Transactional
  @CacheEvict(allEntries = true)
  void deleteByTriggerAndChatIdAndUserId(final String trigger, final Long chatId, final Long userId);

}
