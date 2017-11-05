package ru.ustits.colleague;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.MessageRepository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.UserRepository;
import ru.ustits.colleague.repositories.records.*;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;
import ru.ustits.colleague.tools.TriggerProcessor;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public class ColleagueBot extends TelegramLongPollingCommandBot {

  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private ChatsRepository chatsRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TriggerRepository triggerRepository;
  @Autowired
  private RepeatService repeatService;
  @Autowired
  private RepeatScheduler scheduler;
  @Autowired
  private String botName;
  @Autowired
  private String botToken;

  public ColleagueBot(final String botUsername) {
    super(new DefaultBotOptions(), true, botUsername);
  }

  void startRepeats() {
    final List<RepeatRecord> records = repeatService.fetchAllRepeats();
    scheduler.scheduleTasks(records, this);
  }

  @Override
  public void processNonCommandUpdate(final Update update) {
    log.info(update);
    if (update.hasCallbackQuery()) {
      processCallback(update.getCallbackQuery());
    } else if (hasMessage(update)) {
      addMessage(update);
      if (isMessage(update)) {
        findTriggers(update);
      }
    }
  }

  @Override
  public void onClosing() {
    log.info("Closing");
  }

  private boolean hasMessage(final Update update) {
    return isMessage(update) || isEditMessage(update);
  }

  private boolean isMessage(final Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }

  private boolean isEditMessage(final Update update) {
    return update.hasEditedMessage() && update.getEditedMessage().hasText();
  }

  private void addMessage(final Update update) {
    if (isMessage(update)) {
      addMessage(update.getMessage());
    } else if (isEditMessage(update)) {
      addMessage(update.getEditedMessage());
    }
  }

  private void addMessage(final Message message) {
    final Chat chat = message.getChat();
    final ChatRecord chatRecord = new ChatRecord(chat.getId(), null, chat.getTitle());
    if (!chatsRepository.exists(chatRecord)) {
      chatsRepository.add(chatRecord);
    }

    final User user = message.getFrom();
    final UserRecord userRecord = new UserRecord(toUnsignedLong(user.getId()),
            user.getFirstName(), user.getLastName(), user.getUserName());
    if (!userRepository.exists(userRecord)) {
      userRepository.add(userRecord);
    }
    final MessageRecord messageRecord =
            new MessageRecord(
                    0,
                    toUnsignedLong(message.getMessageId()),
                    new Timestamp((long) message.getDate() * 1000),
                    message.getText(),
                    message.getEditDate() != null,
                    chat.getId(),
                    toUnsignedLong(user.getId()));
    messageRepository.add(messageRecord);
  }

  private void findTriggers(final Update update) {
    final String text = update.getMessage().getText();
    final Long chatId = update.getMessage().getChatId();
    final List<TriggerRecord> triggers = triggerRepository.fetchAll(chatId);
    final TriggerProcessor processor = new TriggerProcessor(triggers);
    final List<SendMessage> messages = processor.process(text);
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
