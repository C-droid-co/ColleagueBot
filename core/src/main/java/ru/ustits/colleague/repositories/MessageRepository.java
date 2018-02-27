package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.MessageRecord;

import java.util.List;

/**
 * @author ustits
 */
public interface MessageRepository extends CrudRepository<MessageRecord, Integer> {

  List<MessageRecord> findAllByUserIdAndChatId(final Long userId, final Long chatId);

}
