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

  private final TriggerCmdConfig config;

  public AddTriggerCommand(final String commandIdentifier, final String description,
                           final TriggerRepository repository, final Parser<TriggerRecord> parser,
                           final TriggerCmdConfig config) {
    super(commandIdentifier, description, repository, parser);
    this.config = config;
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
    log.info("Trying to add {}", toAdd);
    final SendMessage answer = new SendMessage().setChatId(chat.getId());
    if (isMessageTooLong(toAdd)) {
      final int messageLimit = config.getMessageLength();
      log.info("Can't add {}: expecting message length to be {}", toAdd, messageLimit);
      answer.setText(
              "Trigger's message length must be less or equal to [" + messageLimit + "] symbols");
    } else if (!getRepository().existsByTriggerAndChatIdAndUserId(toAdd.getTrigger(),
            toAdd.getChatId(), toAdd.getUserId())) {
      final TriggerRecord record = getRepository().save(toAdd);
      answer.setText(String.format("Trigger [%s] added", record.getTrigger()));
    } else {
      getRepository().save(toAdd);
      answer.setText(String.format("Trigger [%s] was updated", toAdd.getTrigger()));
    }
    return answer;
  }

  protected boolean isMessageTooLong(final TriggerRecord record) {
    return record.getMessage().length() > config.getMessageLength();
  }

}
