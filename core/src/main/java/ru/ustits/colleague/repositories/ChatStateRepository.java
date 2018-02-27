package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.ChatStateRecord;

/**
 * @author ustits
 */
public interface ChatStateRepository extends CrudRepository<ChatStateRecord, Integer> {

}
