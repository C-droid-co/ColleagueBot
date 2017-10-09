package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
@Log4j2
public class TriggerCommand extends BotCommand {

  @Autowired
  private TriggerRepository repository;

  public TriggerCommand(final String command) {
    super(command, "add trigger");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer = createRecord(user, chat, arguments);
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error(getCommandIdentifier(), e);
    }
  }

  protected SendMessage createRecord(final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer;
    if (enough(arguments)) {
      final String trigger = arguments[0];
      final String message = resolveMessage(arguments);
      final TriggerRecord result = repository.fetchOne(trigger, chat.getId(), new Long(user.getId()));

      final TriggerRecord record;
      if (result == null) {
        record = repository.add(trigger, message, chat.getId(), new Long(user.getId()));
        answer = new SendMessage().setText(String.format("Trigger [%s] added", record.getTrigger()));
      } else {
        record = repository.update(message, result);
        answer = new SendMessage().setText(String.format("Trigger [%s] was updated", record.getTrigger()));
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

  protected String failResult() {
    return String.format("Couldn't add trigger. Please use \"/%s trigger response_text\" construction",
            getCommandIdentifier());
  }
}
