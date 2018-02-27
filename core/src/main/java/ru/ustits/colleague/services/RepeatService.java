package ru.ustits.colleague.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.RepeatRepository;
import ru.ustits.colleague.repositories.UserRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Component
@RequiredArgsConstructor
public class RepeatService {

  private final RepeatRepository repeatRepository;
  private final ChatsRepository chatsRepository;
  private final UserRepository userRepository;

  public RepeatRecord addRepeat(final RepeatRecord record, final Chat chat, final User user) {
    final Long chatId = chat.getId();
    final ChatRecord chatRecord = new ChatRecord(chatId, chat.getTitle());
    if (!chatsRepository.existsById(chatId)) {
      chatsRepository.save(chatRecord);
    }
    final Long userId = toUnsignedLong(user.getId());
    final UserRecord userRecord = new UserRecord(userId, user.getFirstName(),
            user.getLastName(), user.getUserName());
    if (!userRepository.existsById(userId)) {
      userRepository.save(userRecord);
    }
    return repeatRepository.save(record);
  }

  public RepeatRecord addRepeat(final String message, final String cron, final Chat chat, final User user) {
    final Long chatId = chat.getId();
    final ChatRecord chatRecord = new ChatRecord(chatId, chat.getTitle());
    if (!chatsRepository.existsById(chatId)) {
      chatsRepository.save(chatRecord);
    }
    final Long userId = toUnsignedLong(user.getId());
    final UserRecord userRecord = new UserRecord(userId,
            user.getFirstName(), user.getLastName(), user.getUserName());
    if (!userRepository.existsById(userId)) {
      userRepository.save(userRecord);
    }
    final RepeatRecord record = new RepeatRecord(message, cron, chat.getId(), toUnsignedLong(user.getId()));
    return repeatRepository.save(record);
  }

  public void deleteRepeat(final Integer repeatId) {
    repeatRepository.deleteById(repeatId);
  }

  public List<RepeatRecord> fetchAllRepeats() {
    final List<RepeatRecord> allRepeats = new ArrayList<>();
    final Iterable<ChatRecord> chats = chatsRepository.findAll();
    for (final ChatRecord chat : chats) {
      final List<RepeatRecord> repeats = repeatRepository.findAllByChatId(chat.getId());
      allRepeats.addAll(repeats);
    }
    return allRepeats;
  }

}
