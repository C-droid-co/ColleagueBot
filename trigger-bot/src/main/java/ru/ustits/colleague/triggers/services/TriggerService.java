package ru.ustits.colleague.triggers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.triggers.tools.ProcessState;
import ru.ustits.colleague.triggers.tools.ProcessingStrategy;
import ru.ustits.colleague.triggers.tools.TriggerProcessor;

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
  private final StateService stateService;

  public List<String> findTriggers(final Message message) {
    final String text = message.getText();
    final Long chatId = message.getChatId();
    final Long userId = Integer.toUnsignedLong(message.getFrom().getId());
    if (!ignoreTriggerRepository.existsByChatIdAndUserId(chatId, userId)) {
      final ProcessState state = stateService.getChatState(chatId);
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
