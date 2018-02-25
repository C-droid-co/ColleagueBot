package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;

/**
 * @author ustits
 */
public interface ChatsRepository extends CrudRepository<ChatRecord, Long> {

}
