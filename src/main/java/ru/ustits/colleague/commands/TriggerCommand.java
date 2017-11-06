package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.StringUtils;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class TriggerCommand extends AbstractTriggerCommand {

  private static final int MIN_ARGS = 2;

  public TriggerCommand(final String commandIdentifier, final TriggerRepository repository) {
    super(commandIdentifier, "add trigger", repository, MIN_ARGS);
  }

  @Override
  protected void executeInternal(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer = createAnswer(user, chat, arguments);
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error(getCommandIdentifier(), e);
    }
  }

  protected SendMessage createAnswer(final User user, final Chat chat, final String[] arguments) {
    final String trigger = resolveTrigger(arguments);
    final String message = resolveMessage(arguments);
    final TriggerRecord toAdd = new TriggerRecord(trigger, message, chat.getId(), toUnsignedLong(user.getId()));
    final boolean exists = getRepository().exists(toAdd);
    final TriggerRecord record;
    final SendMessage answer;
    if (!exists) {
      record = getRepository().add(toAdd);
      answer = new SendMessage().setText(String.format("Trigger [%s] added", record.getTrigger()));
    } else {
      if (getRepository().update(toAdd) <= 0) {
        answer = new SendMessage().setText("Ooops, i couldn't update trigger");
      } else {
        answer = new SendMessage().setText(String.format("Trigger [%s] was updated", trigger));
      }
    }

    return answer.setChatId(chat.getId());
  }

  protected String resolveMessage(final String[] args) {
    return StringUtils.asString(args, 1);
  }

}
