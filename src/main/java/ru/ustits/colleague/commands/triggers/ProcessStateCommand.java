package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.tools.AllTriggers;
import ru.ustits.colleague.tools.RandomTrigger;

/**
 * @author ustits
 */
@Log4j2
public final class ProcessStateCommand extends BotCommand {

  private static final String ALL_STATE = "all";
  private static final String RANDOM_STATE = "random";

  private final ColleagueBot bot;

  public ProcessStateCommand(final String commandIdentifier, final String description,
                             final ColleagueBot bot) {
    super(commandIdentifier, description);
    this.bot = bot;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final String mode = arguments[0];
    try {
      if (changeState(mode)) {
        log.info("Switched to trigger mode [{}]", mode);
        absSender.execute(new SendMessage(chat.getId(), "Switched mode [" + mode + "]"));
      } else {
        absSender.execute(new SendMessage(chat.getId(), "Unknown mode [" + mode + "]"));
      }
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }

  private boolean changeState(final String arg) {
    if (arg.equals(ALL_STATE)) {
      bot.setProcessingStrategy(new AllTriggers());
      return true;
    } else if (arg.equals(RANDOM_STATE)) {
      bot.setProcessingStrategy(new RandomTrigger());
      return true;
    }
    return false;
  }
}
