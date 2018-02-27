package ru.ustits.colleague.triggers.tools;

import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
public final class NoTriggers implements ProcessingStrategy {

  @Override
  public List<String> process(final List<String> messages) {
    return Collections.emptyList();
  }
}
