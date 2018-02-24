package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.StopWordRecord;

/**
 * @author ustits
 */
public interface StopWordRepository extends CrudRepository<StopWordRecord, Integer> {

}
