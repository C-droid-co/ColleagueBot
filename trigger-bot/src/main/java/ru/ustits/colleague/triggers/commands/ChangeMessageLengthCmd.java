package ru.ustits.colleague.triggers.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.tools.IntParser;

import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
public final class ChangeMessageLengthCmd extends BotCommand {

  private final TriggerCmdConfig config;

  public ChangeMessageLengthCmd(final String commandIdentifier, final TriggerCmdConfig config) {
    super(commandIdentifier, "change the limit for message length");
    this.config = config;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final String arg = arguments[0];
    final Optional<Integer> number = new IntParser().parse(arg);
    final SendMessage answer = new SendMessage().setChatId(chat.getId());
    if (number.isPresent()) {
      config.setMessageLength(number.get());
      answer.setText("New trigger message length is " + number.get() + " symbols");
    } else {
      answer.setText("Unable to set new trigger message length");
    }
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }

}
