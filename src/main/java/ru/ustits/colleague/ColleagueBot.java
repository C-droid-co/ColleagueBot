package ru.ustits.colleague;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.*;
import ru.ustits.colleague.repositories.records.*;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;
import ru.ustits.colleague.tools.triggers.ProcessState;
import ru.ustits.colleague.tools.triggers.TriggerProcessor;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public class ColleagueBot extends TelegramLongPollingCommandBot {

  private final MessageRepository messageRepository;
  private final ChatsRepository chatsRepository;
  private final UserRepository userRepository;
  private final TriggerRepository triggerRepository;
  private final RepeatService repeatService;
  private final IgnoreTriggerRepository ignoreTriggerRepository;
  private final RepeatScheduler scheduler;
  private final String botToken;

  @Getter
  @Setter
  private ProcessState processState = ProcessState.ALL;

  public ColleagueBot(final String botName, final String botToken, final MessageRepository messageRepository,
                      final ChatsRepository chatsRepository, final UserRepository userRepository,
                      final TriggerRepository triggerRepository, final RepeatService repeatService,
                      final IgnoreTriggerRepository ignoreTriggerRepository, final RepeatScheduler scheduler) {
    super(new DefaultBotOptions(), true, botName);
    this.botToken = botToken;
    this.messageRepository = messageRepository;
    this.chatsRepository = chatsRepository;
    this.userRepository = userRepository;
    this.triggerRepository = triggerRepository;
    this.repeatService = repeatService;
    this.ignoreTriggerRepository = ignoreTriggerRepository;
    this.scheduler = scheduler;
  }

  @PostConstruct
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
        findTriggers(update.getMessage());
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
                    toUnsignedLong(message.getMessageId()),
                    new Timestamp((long) message.getDate() * 1000),
                    message.getText(),
                    message.getEditDate() != null,
                    chat.getId(),
                    toUnsignedLong(user.getId()));
    messageRepository.add(messageRecord);
  }

  private void findTriggers(final Message message) {
    final String text = message.getText();
    final Long chatId = message.getChatId();
    final Long userId = toUnsignedLong(message.getFrom().getId());
    final IgnoreTriggerRecord ignoredUser = new IgnoreTriggerRecord(chatId, userId);
    if (!ignoreTriggerRepository.exists(ignoredUser)) {
      log.debug("Searching triggers for user [{}] and message [{}]", userId, text);
      final List<TriggerRecord> triggers = triggerRepository.fetchAll(chatId);
      final TriggerProcessor processor = new TriggerProcessor(triggers, processState.getStrategy());
      final List<SendMessage> messages = processor.process(text);
      for (final SendMessage msg : messages) {
        sendMessage(chatId, msg);
      }
    } else {
      log.debug("Ignoring triggers for user {}", userId);
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
