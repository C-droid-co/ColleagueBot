package ru.ustits.colleague.tools;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;

/**
 * @author ustits
 */
public final class AllTriggers implements ProcessingStrategy {

  @Override
  public List<SendMessage> process(final List<SendMessage> messages) {
    return messages;
  }
}
