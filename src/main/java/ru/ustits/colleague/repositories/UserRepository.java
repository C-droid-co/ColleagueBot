package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.UserRecord;

/**
 * @author ustits
 */
public interface UserRepository extends CrudRepository<UserRecord, Long> {

}
