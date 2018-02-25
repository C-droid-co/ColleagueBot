package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.ChatStateRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public interface ChatStateRepository extends CrudRepository<ChatStateRecord, Integer> {

  Optional<ChatStateRecord> findByChatId(final ChatRecord chatRecord);

}
