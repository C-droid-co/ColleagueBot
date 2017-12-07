package ru.ustits.colleague.commands.stats;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.services.MessageService;

import java.util.Map;

/**
 * @author ustits
 */
@Getter(AccessLevel.PROTECTED)
@Log4j2
public class StatsCommand extends BotCommand {

  static final String NO_STAT_MESSAGE = "No statistic yet";

  private final MessageService service;

  public StatsCommand(final String commandIdentifier, final MessageService service) {
    super(commandIdentifier, "show message statistics");
    this.service = service;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final Map<String, Integer> stats = service.count(chat.getId(), false);
    final String text = String.format("%s%n%s", "*Chat stats:*", buildText(stats));
    sendStats(text, chat.getId(), absSender);
  }

  protected final void sendStats(final String text, final Long chatId, final AbsSender sender) {
    final SendMessage message = new SendMessage(chatId, text);
    message.enableMarkdown(true);
    try {
      sender.execute(message);
    } catch (TelegramApiException e) {
      log.error("Unable to send stats", e);
    }
  }

  final String buildText(@NonNull final Map<String, Integer> stats) {
    if (stats.isEmpty()) {
      return NO_STAT_MESSAGE;
    }
    final StringBuilder builder = new StringBuilder();
    stats.forEach((userName, count) ->
            builder.append(userName)
                    .append(": ")
                    .append(count)
                    .append("\n"));
    return builder.toString();
  }

}
