package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.tables.records.TriggersRecord;

import static ru.ustits.colleague.tables.Triggers.TRIGGERS;

/**
 * @author ustits
 */
@Log4j2
public class TriggerCommand extends BotCommand {

  @Autowired
  private DSLContext dsl;

  public TriggerCommand(final String command) {
    super(command, "add trigger");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer = createRecord(user, chat, arguments);
    try {
      absSender.sendMessage(answer);
    } catch (TelegramApiException e) {
      log.error(getCommandIdentifier(), e);
    }
  }

  protected SendMessage createRecord(final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer;
    if (enough(arguments)) {
      final String trigger = arguments[0];
      final String message = resolveMessage(arguments);
      final Result result = dsl.fetch(TRIGGERS, triggerExists(chat.getId(), new Long(user.getId()), trigger));

      if (result.isEmpty()) {
        answer = createTrigger(trigger, chat.getId(), new Long(user.getId()), message);
      } else {
        answer = updateTrigger(message, result);
      }
    } else {
      answer = new SendMessage().setText(failResult());
    }
    return answer.setChatId(chat.getId());
  }

  private boolean enough(final String[] arguments) {
    return arguments != null && arguments.length >= 2;
  }

  protected String resolveMessage(final String[] array) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 1; i < array.length; i++) {
      builder.append(array[i]).append(" ");
    }
    return builder.substring(0, builder.length() - 1);
  }

  private Condition triggerExists(final Long chatId, final Long userId, final String trigger) {
    final Condition sameChat = TRIGGERS.CHAT_ID.eq(chatId);
    final Condition sameUser = TRIGGERS.USER_ID.eq(userId);
    final Condition sameTrigger = TRIGGERS.TRIGGER.eq(trigger);
    return sameChat.and(sameUser.and(sameTrigger));
  }

  private SendMessage createTrigger(final String trigger, final Long chatId, final Long userId, final String message) {
    final TriggersRecord record = dsl.newRecord(TRIGGERS);
    record.setTrigger(trigger);
    record.setChatId(chatId);
    record.setMessage(message);
    record.setUserId(userId);
    record.store();
    final String logMessage = String.format("Added trigger:%n %s", record.toString());
    log.info(logMessage);
    return new SendMessage().setText(String.format("Trigger [%s] added", record.getTrigger()));
  }

  private SendMessage updateTrigger(final String message, final Result result) {
    final TriggersRecord record = (TriggersRecord) result.get(0);
    record.setMessage(message);
    record.update();
    final String logMessage = String.format("Updated trigger:%n %s", record.toString());
    log.info(logMessage);
    return new SendMessage().setText(String.format("Trigger [%s] was updated", record.getTrigger()));
  }

  protected String failResult() {
    return String.format("Couldn't add trigger. Please use \"/%s trigger response_text\" construction",
            getCommandIdentifier());
  }
}
