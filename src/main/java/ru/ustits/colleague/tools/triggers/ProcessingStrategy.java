package ru.ustits.colleague.tools.triggers;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;

/**
 * @author ustits
 */
public interface ProcessingStrategy {

  List<SendMessage> process(final List<SendMessage> messages);

}
