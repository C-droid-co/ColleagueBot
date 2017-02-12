package ru.ustits.colleague.repositories;

import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.tables.records.UsersRecord;

import static ru.ustits.colleague.tables.Users.USERS;

/**
 * @author ustits
 */
public class UserRepository extends BotRepository<User, UsersRecord> {

  @Override
  protected UsersRecord add(final User entity) {
    final UsersRecord record = dsl().newRecord(USERS);
    record.setId(new Long(entity.getId()));
    record.setFirstName(entity.getFirstName());
    record.setLastName(entity.getLastName());
    record.setUserName(entity.getUserName());
    record.store();
    return record;
  }
}
