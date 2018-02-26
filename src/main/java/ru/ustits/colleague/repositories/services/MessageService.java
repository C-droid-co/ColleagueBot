package ru.ustits.colleague.repositories.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.MessageRepository;
import ru.ustits.colleague.repositories.UserRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
@Component
@RequiredArgsConstructor
public final class MessageService {

  private final JdbcTemplate sql;
  private final MessageRepository messageRepository;
  private final ChatsRepository chatsRepository;
  private final UserRepository userRepository;

  public Map<String, Integer> count(final Long chatId, final boolean isEdited) {
    final Map<String, Integer> counts = new LinkedHashMap<>();
    return sql.query("SELECT users.first_name, count(*) as messages_count " +
                    "FROM messages " +
                    "INNER JOIN users ON (messages.user_id = users.id) " +
                    "WHERE messages.chat_id=? AND messages.is_edited=? " +
                    "GROUP BY users.first_name " +
                    "ORDER BY messages_count DESC",
            new Object[]{chatId, isEdited},
            rs -> {
              while (rs.next()) {
                final String name = rs.getString(1);
                final int count = rs.getInt(2);
                counts.put(name, count);
              }
              log.info("Fetched: {}", counts);
              return counts;
            });
  }

  public List<MessageRecord> messagesForUser(final Long userId, final Long chatId) {
    return messageRepository.findAllByUserIdAndChatId(userId, chatId);
  }

  public MessageRecord addMessage(final Message message) {
    final Chat chat = message.getChat();
    final Long chatId = chat.getId();
    final ChatRecord chatRecord = new ChatRecord(chatId, chat.getTitle());
    if (!chatsRepository.existsById(chatId)) {
      chatsRepository.save(chatRecord);
    }

    final User user = message.getFrom();
    final Long userId = toUnsignedLong(user.getId());
    final UserRecord userRecord = new UserRecord(userId, user.getFirstName(),
            user.getLastName(), user.getUserName());
    if (!userRepository.existsById(userId)) {
      userRepository.save(userRecord);
    }
    final MessageRecord messageRecord =
            new MessageRecord(
                    toUnsignedLong(message.getMessageId()),
                    new Date((long) message.getDate() * 1000),
                    message.getText(),
                    message.getEditDate() != null,
                    chat.getId(),
                    toUnsignedLong(user.getId()));
    return messageRepository.save(messageRecord);
  }

}
