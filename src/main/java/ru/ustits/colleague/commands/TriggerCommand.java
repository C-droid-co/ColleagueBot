package ru.ustits.colleague.commands;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import ru.ustits.colleague.tables.Triggers;
import ru.ustits.colleague.tables.records.TriggersRecord;

/**
 * @author ustits
 */
public class TriggerCommand extends BotCommand {

  @Autowired
  private DSLContext dsl;

  public TriggerCommand(final String command) {
    super(command, "add trigger");
  }

  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final TriggersRecord record = dsl.newRecord(Triggers.TRIGGERS);
    final SendMessage answer = processArgumentsAndSetResponse(arguments, record);
    try {
      record.setChatId(chat.getId());
      record.setUserId(new Long(user.getId()));
      record.store();
      absSender.sendMessage(answer.setChatId(chat.getId().toString()));
    } catch (TelegramApiException e) {
      BotLogger.error(getCommandIdentifier(), e);
    }
  }

  protected SendMessage processArgumentsAndSetResponse(final String[] arguments, final TriggersRecord record) {
    final SendMessage commandResult = new SendMessage();
    if (enough(arguments)) {
      final String trigger = addTrigger(arguments, record);
      commandResult.setText(String.format("Trigger [%s] successfully added", trigger));
    } else {
      commandResult.setText(failResult());
    }
    return commandResult;
  }

  private boolean enough(final String[] arguments) {
    return arguments != null && arguments.length >= 2;
  }

  protected String addTrigger(final String[] arguments, final TriggersRecord record) {
    final String trigger = arguments[0];
    final String message = convertStringArrayToString(arguments);

    record.setTrigger(trigger);
    record.setMessage(message);

    final String logMessage = String.format("Added trigger:\n %s", record.toString());
    System.out.println(logMessage);

    return trigger;
  }

  protected String convertStringArrayToString(final String[] array) {
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
