package ru.ustits.colleague.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import ru.ustits.colleague.tables.records.RepeatersRecord;
import ru.ustits.colleague.tasks.RepeatTask;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.ustits.colleague.tables.Repeaters.REPEATERS;

/**
 * @author ustits
 */
public class RepeatCommand extends BotCommand {

  private static final Logger log = LogManager.getLogger();

  private static final int HOURS = 1;
  private static final int MINUTES = 2;
  private static final int SECONDS = 3;
  private static final Long DAY_PERIOD = TimeUnit.DAYS.toSeconds(1);

  @Autowired
  private DSLContext dsl;

  private final ScheduledExecutorService scheduler;

  public RepeatCommand(final String commandIdentifier, final int poolSize) {
    super(commandIdentifier, "command for adding repeatable messages");
    scheduler = Executors.newScheduledThreadPool(poolSize);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final String text = parseMessage(arguments);
    final LocalTime time = parseTime(arguments);
    final long delay = delay(time);

    final SendMessage message = new SendMessage()
            .setChatId(chat.getId())
            .setText(text);

    final RepeatersRecord record = dsl.newRecord(REPEATERS);
    record.setMessage(text);
    record.setChatId(chat.getId());
    record.setUserId(new Long(user.getId()));
    record.setTime(new Time(time.getHour(), time.getMinute(), time.getSecond()));
    record.store();
    log.info(String.format("%s was scheduled in %d", text, delay));

    scheduler.scheduleAtFixedRate(new RepeatTask(absSender, message), delay, DAY_PERIOD, TimeUnit.SECONDS);
  }

  private LocalTime parseTime(final String[] arguments) {
    final int hours = parseHours(arguments);
    final int minutes = parseMinutes(arguments);
    final int seconds = parseSeconds(arguments);
    return LocalTime.of(hours, minutes, seconds);
  }

  private String parseMessage(final String[] arguments) {
    return arguments[0];
  }

  private int parseHours(final String[] arguments) {
    return getTimeUnit(arguments, HOURS);
  }

  private int parseMinutes(final String[] arguments) {
    return getTimeUnit(arguments, MINUTES);
  }

  private int parseSeconds(final String[] arguments) {
    return getTimeUnit(arguments, SECONDS);
  }

  private long delay(final LocalTime then) {
    final LocalTime now = LocalTime.now();
    final Duration duration = Duration.between(now, then);
    if (duration.isNegative()) {
      return duration.plusDays(1).getSeconds();
    }
    return duration.getSeconds();
  }

  private int getTimeUnit(final String[] arguments, final int unit) {
    if (arguments.length <= unit) {
      return 0;
    }
    return Integer.parseInt(arguments[unit]);
  }
}
