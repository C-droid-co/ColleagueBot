package ru.ustits.colleague.tools.triggers;

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
