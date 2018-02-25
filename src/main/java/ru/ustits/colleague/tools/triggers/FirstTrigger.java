package ru.ustits.colleague.tools.triggers;

import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
public final class FirstTrigger implements ProcessingStrategy {

  @Override
  public List<String> process(final List<String> messages) {
    if (messages.isEmpty()) {
      return messages;
    } else {
      final String first = messages.get(0);
      return Collections.singletonList(first);
    }
  }

}
