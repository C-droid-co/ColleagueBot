package ru.ustits.colleague.commands;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import ru.ustits.colleague.tables.records.RequestRecord;

import static ru.ustits.colleague.tables.Request.REQUEST;

/**
 * @author ustits
 */
public class RequestCommand extends BotCommand {

  @Autowired
  private DSLContext dsl;

  public RequestCommand(final String commandIdentifier) {
    super(commandIdentifier, "request something from user");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final String request = parseRequest(arguments);
    final String requester = parseRequester(arguments);
    final String executor = parseExecutor(arguments);
    final RequestRecord record = dsl.newRecord(REQUEST);
    record.setChatId(chat.getId());
    record.setMessage(request);
    record.setRequesterId(new Long(user.getId()));
  }

  private String parseRequest(final String[] arguments) {
    return null;
  }

  private String parseRequester(final String[] arguments) {
    return null;
  }

  private String parseExecutor(final String[] arguments) {
    return null;
  }

}
