package ru.ustits.colleague.tools;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author ustits
 */
public final class RandomTrigger implements ProcessingStrategy {

  @Override
  public List<SendMessage> process(final List<SendMessage> messages) {
    if (messages.isEmpty()) {
      return messages;
    } else {
      final Random random = new Random();
      final int index = random.nextInt(messages.size());
      return Collections.singletonList(messages.get(index));
    }
  }
}
