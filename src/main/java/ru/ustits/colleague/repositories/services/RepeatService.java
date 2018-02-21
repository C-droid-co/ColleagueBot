package ru.ustits.colleague.repositories.services;

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
    final ChatRecord chatRecord = new ChatRecord(chat.getId(), chat.getTitle());
    if (!chatsRepository.exists(chatRecord)) {
      chatsRepository.add(chatRecord);
    }
    final UserRecord userRecord = new UserRecord(toUnsignedLong(user.getId()),
            user.getFirstName(), user.getLastName(), user.getUserName());
    if (!userRepository.exists(userRecord)) {
      userRepository.add(userRecord);
    }
    return repeatRepository.add(record);
  }

  public RepeatRecord addRepeat(final String message, final String cron, final Chat chat, final User user) {
    final ChatRecord chatRecord = new ChatRecord(chat.getId(), chat.getTitle());
    if (!chatsRepository.exists(chatRecord)) {
      chatsRepository.add(chatRecord);
    }
    final UserRecord userRecord = new UserRecord(toUnsignedLong(user.getId()),
            user.getFirstName(), user.getLastName(), user.getUserName());
    if (!userRepository.exists(userRecord)) {
      userRepository.add(userRecord);
    }
    final RepeatRecord record = new RepeatRecord(message, cron, chat.getId(), toUnsignedLong(user.getId()));
    return repeatRepository.add(record);
  }

  public void deleteRepeat(final RepeatRecord record) {
    repeatRepository.delete(record);
  }

  public List<RepeatRecord> fetchAllRepeats() {
    final List<RepeatRecord> allRepeats = new ArrayList<>();
    final List<ChatRecord> chats = chatsRepository.fetchAll();
    for (final ChatRecord chat : chats) {
      final List<RepeatRecord> repeats = repeatRepository.fetchAll(chat.getId());
      allRepeats.addAll(repeats);
    }
    return allRepeats;
  }
}
