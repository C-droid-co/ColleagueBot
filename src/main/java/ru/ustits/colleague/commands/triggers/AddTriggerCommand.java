package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
@Log4j2
public final class AddTriggerCommand extends AbstractTriggerCommand {

  public AddTriggerCommand(final String commandIdentifier, final String description,
                           final TriggerRepository repository, final Parser<TriggerRecord> parser) {
    super(commandIdentifier, description, repository, parser);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final SendMessage answer = createAnswer(user, chat, arguments);
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error(getCommandIdentifier(), e);
    }
  }

  protected SendMessage createAnswer(final User user, final Chat chat, final String[] arguments) {
    final TriggerRecord toAdd = getParser().buildRecord(
            Integer.toUnsignedLong(user.getId()), chat.getId(), arguments);
    final SendMessage answer;
    if (toAdd == null) {
      answer = new SendMessage().setText("Unable to add trigger");
    } else if (!getRepository().exists(toAdd)) {
      final TriggerRecord record = getRepository().add(toAdd);
      answer = new SendMessage().setText(String.format("Trigger [%s] added", record.getTrigger()));
    } else {
      if (getRepository().update(toAdd) <= 0) {
        answer = new SendMessage().setText("Ooops, i couldn't update trigger");
      } else {
        answer = new SendMessage().setText(String.format("Trigger [%s] was updated", toAdd.getTrigger()));
      }
    }
    return answer.setChatId(chat.getId());
  }

}
