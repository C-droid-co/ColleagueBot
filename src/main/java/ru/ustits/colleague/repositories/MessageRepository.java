package ru.ustits.colleague.repositories;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.tables.records.ChatsRecord;
import ru.ustits.colleague.tables.records.MessagesRecord;
import ru.ustits.colleague.tables.records.UsersRecord;

import java.sql.Timestamp;

import static ru.ustits.colleague.tables.Chats.CHATS;
import static ru.ustits.colleague.tables.Messages.MESSAGES;
import static ru.ustits.colleague.tables.Users.USERS;

/**
 * @author ustits
 */
public class MessageRepository extends BotRepository<Message, MessagesRecord> {

  @Override
  public void add(final Update update) {
    if (update.getEditedMessage() != null) {
      add(update.getEditedMessage(), true);
    } else {
      add(update.getMessage(), false);
    }
  }

  @Override
  protected MessagesRecord add(final Message entity) {
    final MessagesRecord record = dsl().newRecord(MESSAGES);
    record.setIsEdited(isEdited(entity));
    record.setMsgId(new Long(entity.getMessageId()));
    record.setDate(new Timestamp((long) entity.getDate() * 1000));
    record.setText(entity.getText());
    record.setUserId(new Long(entity.getFrom().getId()));
    record.setChatId(entity.getChat().getId());
    record.store();
    return record;
  }

  private boolean isEdited(final Message entity) {
    return entity.getEditDate() != null;
  }

  private void add(final Message message, final boolean isEdited) {
    final ChatsRecord chatsRecord = dsl().fetchOne(CHATS, CHATS.ID.equal(message.getChatId()));
    if (chatsRecord == null) {
      new ChatsRepository().add(message.getChat());
    }

    final UsersRecord usersRecord = dsl().fetchOne(USERS, USERS.ID.equal(new Long(message.getFrom().getId())));
    if (usersRecord == null) {
      new UserRepository().add(message.getFrom());
    }

    add(message);
  }
}
