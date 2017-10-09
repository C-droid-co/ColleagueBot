package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.tools.ButtonCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class HelpCommand extends BotCommand {

  private final ICommandRegistry commandRegistry;

  public HelpCommand(final ICommandRegistry commandRegistry, final String command) {
    super(command, "list all commands");
    this.commandRegistry = commandRegistry;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
    helpMessageBuilder.append("These are the registered commands for this Bot:\n\n");

    for (final BotCommand botCommand : commandRegistry.getRegisteredCommands()) {
      helpMessageBuilder.append(botCommand.toString()).append("\n\n");
    }

    final SendMessage helpMessage = new SendMessage();
    helpMessage.setChatId(chat.getId().toString());
    helpMessage.enableHtml(true);
    helpMessage.setText(helpMessageBuilder.toString());
    helpMessage.setReplyMarkup(createButtons());

    try {
      absSender.execute(helpMessage);
    } catch (TelegramApiException e) {
      log.error(getCommandIdentifier(), e);
    }
  }

  private InlineKeyboardMarkup createButtons() {
    final InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    final List<InlineKeyboardButton> row = new ArrayList<>();
    row.add(ButtonCreator.create("/help", "*Ex.:* `/help`"));
    row.add(ButtonCreator.create("/trigger", "*Ex.:* `/trigger [trigger_message] [response_message]`"));
    row.add(ButtonCreator.create("/repeat", "*Ex.:* `/repeat [repeat_message] [hour] [minutes] [seconds]`"));
    keyboard.add(row);
    markup.setKeyboard(keyboard);
    return markup;
  }
}
