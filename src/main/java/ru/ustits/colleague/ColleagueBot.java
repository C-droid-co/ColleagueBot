package ru.ustits.colleague;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.RepeatCommand;
import ru.ustits.colleague.commands.TriggerCommand;

import java.util.List;

/**
 * @author ustits
 */
@Setter
public class ColleagueBot extends TelegramLongPollingCommandBot {

  private static final Logger log = LogManager.getLogger();

  private TriggerProcessor triggerProcessor;
  private TriggerCommand triggerCommand;
  private HelpCommand helpCommand;
  private RepeatCommand repeatCommand;
  private String botName;
  private String botToken;

  @Override
  public void processNonCommandUpdate(final Update update) {
    log.info(update);
    if (update.hasCallbackQuery()) {
      processCallback(update.getCallbackQuery());
    } else if (isNotEditedMessage(update)) {
      register(triggerCommand);
      register(helpCommand);
      register(repeatCommand);
      if (isMessage(update)) {
        findTriggers(update);
      }
    }
  }

  private boolean isNotEditedMessage(final Update update) {
    return update.getEditedMessage() == null;
  }

  private boolean isMessage(final Update update) { return update.hasMessage() && update.getMessage().hasText(); }

  private void findTriggers(final Update update) {
    final List<SendMessage> messages = triggerProcessor.process(update);
    for (final SendMessage message : messages) {
      sendMessage(update.getMessage().getChatId(), message);
    }
  }

  private void processCallback(final CallbackQuery callback) {
    final EditMessageText editMessage = new EditMessageText();
    editMessage.setChatId(callback.getMessage().getChatId());
    editMessage.setMessageId(callback.getMessage().getMessageId());
    editMessage.setText(callback.getData());
    try {
      editMessageText(editMessage);
    } catch (TelegramApiException e) {
      log.error(e);
    }
  }

  private void sendMessage(final Long chatId, final PartialBotApiMethod object) {
    try {
      if (object instanceof SendMessage) {
        final SendMessage message = ((SendMessage) object).setChatId(chatId);
        sendMessage(message);
      } else if (object instanceof SendSticker) {
        final SendSticker sticker = ((SendSticker) object).setChatId(chatId);
        sendSticker(sticker);
      } else if (object instanceof SendDocument) {
        final SendDocument document = ((SendDocument) object).setChatId(chatId);
        sendDocument(document);
      }
    } catch (TelegramApiException e) {
      log.error(e);
    }
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public String getBotUsername() {
    return botName;
  }
}
