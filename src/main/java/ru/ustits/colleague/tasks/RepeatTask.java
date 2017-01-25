package ru.ustits.colleague.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author ustits
 */
public class RepeatTask implements Runnable {

  private static final Logger log = LogManager.getLogger();

  private final AbsSender absSender;
  private final SendMessage message;

  public RepeatTask(final AbsSender absSender, final SendMessage message) {
    this.absSender = absSender;
    this.message = message;
  }

  @Override
  public void run() {
    log.info(String.format("Repeated: %s in chat: %s", message.getText(), message.getChatId()));
    try {
      absSender.sendMessage(message);
    } catch (TelegramApiException e) {
      log.error(e);
    }
  }
}
