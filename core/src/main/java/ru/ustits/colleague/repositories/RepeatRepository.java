package ru.ustits.colleague.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.List;

/**
 * @author ustits
 */
public interface RepeatRepository extends CrudRepository<RepeatRecord, Integer> {

  List<RepeatRecord> findAllByChatId(final Long chatId);

}