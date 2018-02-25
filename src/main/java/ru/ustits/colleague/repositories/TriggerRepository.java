package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;

/**
 * @author ustits
 */
public interface TriggerRepository extends CrudRepository<TriggerRecord, Integer> {

  List<TriggerRecord> findAllByChatId(final Long chatId);

  boolean existsByTriggerAndChatIdAndUserId(final String trigger, final Long chatId, final Long userId);

  @Transactional
  void deleteByTriggerAndChatIdAndUserId(final String trigger, final Long chatId, final Long userId);

}
