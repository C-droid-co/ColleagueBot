package ru.ustits.colleague;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

/**
 * @author ustits
 */
@Log4j2
@Service
public abstract class ColleagueBot extends TelegramLongPollingCommandBot {

  private final String botToken;

  private BotCommand[] commands;

  public ColleagueBot(final String botName, final String botToken) {
    super(new DefaultBotOptions(), true, botName);
    this.botToken = botToken;
  }

  @PostConstruct
  protected void initialize() {
    registerAll(commands);
  }

  @Override
  public void processNonCommandUpdate(final Update update) {
    log.info(update);
    if (update.hasCallbackQuery()) {
      processCallback(update.getCallbackQuery());
    }
  }

  @Autowired
  public void setCommands(final BotCommand[] commands) {
    this.commands = commands;
  }

  @Override
  public void onClosing() {
    log.info("Closing");
  }

  protected final boolean hasMessage(final Update update) {
    return isMessage(update) || isEditMessage(update);
  }

  protected final boolean isMessage(final Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }

  protected final boolean isEditMessage(final Update update) {
    return update.hasEditedMessage() && update.getEditedMessage().hasText();
  }

  private void processCallback(final CallbackQuery callback) {
    final SendMessage helpMessage = new SendMessage();
    helpMessage.setText(callback.getData());
    helpMessage.enableMarkdown(true);
    sendMessage(callback.getMessage().getChatId(), helpMessage);
  }

  protected final void sendMessage(final Long chatId, final PartialBotApiMethod object) {
    try {
      if (object instanceof SendMessage) {
        final SendMessage message = ((SendMessage) object).setChatId(chatId);
        execute(message);
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
}
