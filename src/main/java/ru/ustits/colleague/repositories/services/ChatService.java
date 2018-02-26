package ru.ustits.colleague.repositories.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import ru.ustits.colleague.repositories.ChatStateRepository;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.ChatStateRecord;
import ru.ustits.colleague.tools.triggers.ProcessState;

import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class ChatService {

  private final ChatsRepository chatsRepository;
  private final ChatStateRepository chatStateRepository;
  private final ProcessState defaultProcessState;

  public ChatRecord changeState(final Chat chat, final ProcessState state) {
    final ChatRecord chatRecord = identifyChat(chat);
    final ChatStateRecord oldState = chatRecord.getState();
    final String stateName = state.getName();
    final ChatStateRecord newState;
    if (oldState == null) {
      newState = new ChatStateRecord(chatRecord, stateName);
    } else {
      newState = new ChatStateRecord(oldState, stateName);
    }
    final ChatStateRecord stateRecord = chatStateRepository.save(newState);
    return chatsRepository.save(new ChatRecord(chatRecord, stateRecord));
  }

  private ChatRecord identifyChat(final Chat chat) {
    final Long chatId = chat.getId();
    final Optional<ChatRecord> chatDbEntry = chatsRepository.findById(chatId);
    return chatDbEntry.orElseGet(() ->
            chatsRepository.save(new ChatRecord(chatId, chat.getTitle())));
  }

  public ProcessState getChatState(final Long chatId) {
    final Optional<ChatRecord> dbEntity = chatsRepository.findById(chatId);
    final ProcessState result;
    if (dbEntity.isPresent() && dbEntity.get().getState() != null) {
      final ChatStateRecord record = dbEntity.get().getState();
      final Optional<ProcessState> state = ProcessState.toState(record.getState());
      result = state.orElse(defaultProcessState);
    } else {
      result = defaultProcessState;
    }
    return result;
  }

}
