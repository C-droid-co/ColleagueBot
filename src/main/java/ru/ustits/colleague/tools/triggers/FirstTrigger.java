package ru.ustits.colleague.tools.triggers;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
public final class FirstTrigger implements ProcessingStrategy {

  @Override
  public List<SendMessage> process(final List<SendMessage> messages) {
    if (messages.isEmpty()) {
      return messages;
    } else {
      final SendMessage first = messages.get(0);
      return Collections.singletonList(first);
    }
  }
}
