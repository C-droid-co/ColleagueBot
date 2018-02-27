package ru.ustits.colleague.triggers.commands.states;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.triggers.services.StateService;
import ru.ustits.colleague.triggers.tools.ProcessState;

import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
public final class ChangeStateCommand extends BotCommand {

  private final StateService stateService;

  public ChangeStateCommand(final String commandIdentifier, final String description,
                            final StateService stateService) {
    super(commandIdentifier, description);
    this.stateService = stateService;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final String mode = arguments[0];
    try {
      final Optional<ProcessState> state = ProcessState.toState(mode);
      if (state.isPresent()) {
        final ChatRecord stateRecord = stateService.changeState(chat, state.get());
        log.info("Changed state for chat: {}", stateRecord);
        absSender.execute(new SendMessage(chat.getId(), "Switched mode [" + mode + "]"));
      } else {
        absSender.execute(new SendMessage(chat.getId(), "Unknown mode [" + mode + "]"));
      }
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }

}
