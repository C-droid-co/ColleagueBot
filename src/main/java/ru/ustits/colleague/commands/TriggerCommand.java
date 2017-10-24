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
import ru.ustits.colleague.tools.StringUtils;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class TriggerCommand extends BotCommand {

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
      final String trigger = resolveTrigger(arguments);
      final String message = resolveMessage(arguments);
      final TriggerRecord result = repository.fetchOne(trigger, chat.getId(), toUnsignedLong(user.getId()));

      final TriggerRecord record;
      if (result == null) {
        record = repository.add(trigger, message, chat.getId(), toUnsignedLong(user.getId()));
        answer = new SendMessage().setText(String.format("Trigger [%s] added", record.getTrigger()));
      } else {
        if (repository.update(message, result) <= 0) {
          answer = new SendMessage().setText("Ooops, i couldn't update trigger");
        } else {
          answer = new SendMessage().setText(String.format("Trigger [%s] was updated", trigger));
        }
      }
    } else {
      answer = new SendMessage().setText(failResult());
    }
    return answer.setChatId(chat.getId());
  }

  private boolean enough(final String[] arguments) {
    return arguments != null && arguments.length >= 2;
  }

  final String resolveTrigger(final String[] args) {
    return args[0].toLowerCase();
  }

  protected String resolveMessage(final String[] args) {
    return StringUtils.asString(args, 1);
  }

  protected String failResult() {
    return String.format("Couldn't add trigger. Please use \"/%s trigger response_text\" construction",
            getCommandIdentifier());
  }
}
