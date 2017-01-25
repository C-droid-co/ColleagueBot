package ru.ustits.colleague;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.RepeatCommand;
import ru.ustits.colleague.commands.RequestCommand;
import ru.ustits.colleague.commands.TriggerCommand;

import java.util.List;

/**
 * @author ustits
 */
@Accessors(fluent = true)
@Setter
public class ColleagueBot extends TelegramLongPollingCommandBot {

  private static final Logger log = LogManager.getLogger();

  private TriggerProcessor triggerProcessor;
  private String botName;
  private String botToken;

  @Override
  public void processNonCommandUpdate(final Update update) {
    log.info(update);
    if (update.hasCallbackQuery()) {
      processCallback(update.getCallbackQuery());
    } else if (isMessage(update) && isNotEdited(update)) {
      findTriggers(update);
    }
  }

  private boolean isNotEdited(final Update update) {
    return update.getEditedMessage() == null;
  }

  private boolean isMessage(final Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }

  private void findTriggers(final Update update) {
    final List<SendMessage> messages = triggerProcessor.process(update);
    for (final SendMessage message : messages) {
      sendMessage(update.getMessage().getChatId(), message);
    }
  }

  private void processCallback(final CallbackQuery callback) {
    final SendMessage helpMessage = new SendMessage();
    helpMessage.setText(callback.getData());
    helpMessage.enableMarkdown(true);
    sendMessage(callback.getMessage().getChatId(), helpMessage);
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

  public ColleagueBot triggerCommand(final TriggerCommand triggerCommand) {
    register(triggerCommand);
    return this;
  }

  public ColleagueBot helpCommand(final HelpCommand helpCommand) {
    register(helpCommand);
    return this;
  }

  public ColleagueBot repeatCommand(final RepeatCommand repeatCommand) {
    register(repeatCommand);
    return this;
  }

  public ColleagueBot requestCommand(final RequestCommand requestCommand) {
    register(requestCommand);
    return this;
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
