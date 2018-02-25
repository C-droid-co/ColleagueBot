package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ustits.colleague.repositories.records.IgnoreTriggerRecord;

/**
 * @author ustits
 */
public interface IgnoreTriggerRepository extends CrudRepository<IgnoreTriggerRecord, Integer> {

  boolean existsByChatIdAndUserId(final Long chatId, final Long userId);

  @Transactional
  void deleteByChatIdAndUserId(final Long chatId, final Long userId);

}
