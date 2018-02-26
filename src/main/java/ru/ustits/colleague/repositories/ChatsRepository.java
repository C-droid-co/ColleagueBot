package ru.ustits.colleague.repositories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public interface ChatsRepository extends CrudRepository<ChatRecord, Long> {

  @Override
  @CacheEvict(allEntries = true, cacheNames = {"chats", "chats_exists"})
  <S extends ChatRecord> S save(final S entity);

  @Override
  @Cacheable(cacheNames = "chats")
  Optional<ChatRecord> findById(final Long aLong);

  @Override
  @Cacheable(cacheNames = "chats_exists")
  boolean existsById(final Long aLong);

}
