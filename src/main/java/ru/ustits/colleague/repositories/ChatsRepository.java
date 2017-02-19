package ru.ustits.colleague.repositories;

import org.telegram.telegrambots.api.objects.Chat;
import ru.ustits.colleague.tables.records.ChatsRecord;

import static ru.ustits.colleague.tables.Chats.CHATS;

/**
 * @author ustits
 */
public class ChatsRepository extends BotRepository<Chat, ChatsRecord> {

  @Override
  public boolean exists(final Chat entity) {
    final ChatsRecord chatsRecord = dsl().fetchOne(CHATS, CHATS.ID.equal(entity.getId()));
    return chatsRecord != null;
  }

  @Override
  public ChatsRecord add(final Chat entity) {
    final ChatsRecord record = dsl().newRecord(CHATS);
    record.setId(entity.getId());
    record.setTitle(entity.getTitle());
    record.store();
    return record;
  }
}
