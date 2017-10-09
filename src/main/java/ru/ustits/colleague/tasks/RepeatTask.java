package ru.ustits.colleague.tasks;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author ustits
 */
@Log4j2
public class RepeatTask implements Runnable {

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
      absSender.execute(message);
    } catch (TelegramApiException e) {
      log.error(e);
    }
  }
}
