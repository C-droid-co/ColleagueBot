package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.repositories.RepeatRepository;
import ru.ustits.colleague.tasks.RepeatTask;
import ru.ustits.colleague.tools.TimeParser;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ustits
 */
@Log4j2
public class RepeatCommand extends BotCommand {

  private static final Long DAY_PERIOD = TimeUnit.DAYS.toSeconds(1);

  @Autowired
  private RepeatRepository repository;

  private final ScheduledExecutorService scheduler;

  public RepeatCommand(final String commandIdentifier, final int poolSize) {
    super(commandIdentifier, "command for adding repeatable messages");
    scheduler = Executors.newScheduledThreadPool(poolSize);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final String text = parseMessage(arguments);
    final LocalTime time = TimeParser.parse(arguments);
    final long delay = delay(time);

    final SendMessage message = new SendMessage()
            .setChatId(chat.getId())
            .setText(text);


    if (repository.add(text, chat.getId(), new Long(user.getId()),
            new Time(time.getHour(), time.getMinute(), time.getSecond())) != null) {
      scheduler.scheduleAtFixedRate(new RepeatTask(absSender, message), delay, DAY_PERIOD, TimeUnit.SECONDS);
      log.info(String.format("%s was scheduled in %d", text, delay));
    }
  }

  private String parseMessage(final String[] arguments) {
    return arguments[0];
  }

  private long delay(final LocalTime then) {
    final LocalTime now = LocalTime.now();
    final Duration duration = Duration.between(now, then);
    if (duration.isNegative()) {
      return duration.plusDays(1).getSeconds();
    }
    return duration.getSeconds();
  }
}
