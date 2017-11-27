package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.tools.triggers.ProcessState;

/**
 * @author ustits
 */
@Log4j2
public final class ShowStateCommand extends BotCommand {

  private final ColleagueBot bot;

  public ShowStateCommand(final String commandIdentifier, final String description,
                          final ColleagueBot bot) {
    super(commandIdentifier, description);
    this.bot = bot;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final ProcessState state = bot.getProcessState();
    try {
      absSender.execute(new SendMessage(chat.getId(), "Current mode [" + state.getName() + "]"));
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }
}
