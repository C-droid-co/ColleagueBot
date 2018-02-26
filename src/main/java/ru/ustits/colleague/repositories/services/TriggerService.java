package ru.ustits.colleague.repositories.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.triggers.ProcessState;
import ru.ustits.colleague.tools.triggers.ProcessingStrategy;
import ru.ustits.colleague.tools.triggers.TriggerProcessor;

import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class TriggerService {

  private final TriggerRepository triggerRepository;
  private final IgnoreTriggerRepository ignoreTriggerRepository;
  private final ChatService chatService;

  public List<String> findTriggers(final MessageRecord message) {
    final String text = message.getText();
    final Long chatId = message.getChatId();
    final Long userId = message.getUserId();
    if (!ignoreTriggerRepository.existsByChatIdAndUserId(chatId, userId)) {
      final ProcessState state = chatService.getChatState(chatId);
      log.debug("Searching triggers for user [{}] and message [{}]", userId, text);
      return findTriggers(chatId, text, state.getStrategy());
    } else {
      log.debug("Ignoring triggers for user {}", userId);
    }
    return Collections.emptyList();
  }

  protected final List<String> findTriggers(final Long chatId, final String text, final ProcessingStrategy strategy) {
    final List<TriggerRecord> triggers = triggerRepository.findAllByChatId(chatId);
    final TriggerProcessor processor = new TriggerProcessor(triggers, strategy);
    return processor.process(text);
  }

}
