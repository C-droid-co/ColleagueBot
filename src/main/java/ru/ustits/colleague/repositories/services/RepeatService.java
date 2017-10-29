package ru.ustits.colleague.repositories.services;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.RepeatRepository;
import ru.ustits.colleague.repositories.UserRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public class RepeatService {

  private final RepeatRepository repeatRepository;
  private final ChatsRepository chatsRepository;
  private final UserRepository userRepository;

  public RepeatRecord addRepeat(final String message, final String cron, final Chat chat, final User user) {
    if (!chatsRepository.exists(chat)) {
      chatsRepository.add(chat);
    }
    if (!userRepository.exists(user)) {
      userRepository.add(user);
    }
    final RepeatRecord record = RepeatRecord.builder()
            .message(message)
            .chatId(chat.getId())
            .userId(toUnsignedLong(user.getId()))
            .cron(cron).build();
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
