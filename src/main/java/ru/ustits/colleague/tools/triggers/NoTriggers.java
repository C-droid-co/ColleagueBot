package ru.ustits.colleague.tools.triggers;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
public final class NoTriggers implements ProcessingStrategy {

  @Override
  public List<SendMessage> process(final List<SendMessage> messages) {
    return Collections.emptyList();
  }
}
