package ru.ustits.colleague.tools.triggers;

import java.util.List;

/**
 * @author ustits
 */
public final class AllTriggers implements ProcessingStrategy {

  @Override
  public List<String> process(final List<String> messages) {
    return messages;
  }
}
