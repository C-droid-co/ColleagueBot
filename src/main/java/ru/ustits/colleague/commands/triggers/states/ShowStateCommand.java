package ru.ustits.colleague.commands.triggers.states;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.services.ChatService;
import ru.ustits.colleague.tools.triggers.ProcessState;

/**
 * @author ustits
 */
@Log4j2
public final class ShowStateCommand extends BotCommand {

  private final ChatService chatService;

  public ShowStateCommand(final String commandIdentifier, final String description,
                          final ChatService chatService) {
    super(commandIdentifier, description);
    this.chatService = chatService;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final ProcessState state = chatService.getChatState(chat.getId());
    try {
      absSender.execute(new SendMessage(chat.getId(), "Current mode [" + state.getName() + "]"));
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }

}
