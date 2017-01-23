package ru.ustits.colleague.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import ru.ustits.colleague.tasks.RepeatTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;

/**
 * @author ustits
 */
public class RepeatCommand extends BotCommand {

  public RepeatCommand(final String commandIdentifier) {
    super(commandIdentifier, "command for adding repeatable messages");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final Timer timer = new Timer(true);
    final LocalDateTime time = parseTime(arguments);
    final SendMessage message = new SendMessage()
            .setChatId(chat.getId())
            .setText(parseMessage(arguments));
    timer.schedule(new RepeatTask(absSender, message), delay(time));
  }

  private LocalDateTime parseTime(final String[] arguments) {
    final int hours = parseHours(arguments);
    final int minutes = parseMinutes(arguments);
    final int seconds = parseSeconds(arguments);
    return LocalDateTime.now()
            .withHour(hours)
            .withMinute(minutes)
            .withSecond(seconds);
  }

  private String parseMessage(final String[] arguments) {
    return "";
  }

  private int parseHours(final String[] arguments) {
    return 0;
  }

  private int parseMinutes(final String[] arguments) {
    return 0;
  }

  private int parseSeconds(final String[] arguments) {
    return 0;
  }

  private long delay(final LocalDateTime then) {
    final LocalDateTime now = LocalDateTime.now();
    final Duration duration = Duration.between(now, then);
    return duration.getSeconds();
  }
}
