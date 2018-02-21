package ru.ustits.colleague.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.commands.ArgsAwareCommand;
import ru.ustits.colleague.commands.ChatIdParser;
import ru.ustits.colleague.commands.NoWhitespaceCommand;
import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.commands.repeats.*;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

/**
 * @author ustits
 */
@Configuration
class RepeatCommandConfig extends CommandConfig {

  private static final String REPEAT_COMMAND = "repeat";
  private static final String ADMIN_REPEAT_COMMAND = ADMIN_PREFIX + REPEAT_COMMAND;
  private static final String REPEAT_DAILY_COMMAND = REPEAT_COMMAND + "_d";
  private static final String ADMIN_REPEAT_DAILY_COMMAND = ADMIN_PREFIX + REPEAT_DAILY_COMMAND;
  private static final String REPEAT_WORKDAYS_COMMAND = REPEAT_COMMAND + "_wd";
  private static final String ADMIN_REPEAT_WORKDAYS_COMMAND = ADMIN_PREFIX + REPEAT_WORKDAYS_COMMAND;
  private static final String REPEAT_WEEKENDS_COMMAND = REPEAT_COMMAND + "_we";
  private static final String ADMIN_REPEAT_WEEKENDS_COMMAND = ADMIN_PREFIX + REPEAT_WEEKENDS_COMMAND;

  @Bean
  public BotCommand repeatCommand(final RepeatService repeatService, final RepeatScheduler scheduler) {
    return repeatCommand(
            REPEAT_COMMAND,
            "repeat message with cron expression",
            new PlainParser(),
            repeatService,
            scheduler);
  }

  @Bean
  public BotCommand adminRepeatCommand(final RepeatService repeatService, final RepeatScheduler scheduler,
                                       final Long adminId) {
    return admin(
            repeatCommand(
                    ADMIN_REPEAT_COMMAND,
                    "add repeat message with cron expression to any chat",
                    new ChatIdParser<>(
                            new PlainParser(8, 1)),
                    repeatService,
                    scheduler),
            adminId);
  }

  @Bean
  public BotCommand repeatDailyCommand(final RepeatService repeatService, final RepeatScheduler scheduler) {
    return repeatCommand(
            REPEAT_DAILY_COMMAND,
            "repeat message everyday",
            new DailyParser(),
            repeatService,
            scheduler);
  }

  @Bean
  public BotCommand adminRepeatDailyCommand(final RepeatService repeatService, final RepeatScheduler scheduler,
                                            final Long adminId) {
    return admin(
            repeatCommand(
                    ADMIN_REPEAT_DAILY_COMMAND,
                    "repeat message everyday (admin)",
                    new ChatIdParser<>(
                            adminDailyParser()),
                    repeatService,
                    scheduler),
            adminId);
  }

  @Bean
  public BotCommand repeatWorkDaysCommand(final RepeatService repeatService, final RepeatScheduler scheduler) {
    return repeatCommand(REPEAT_WORKDAYS_COMMAND, "repeat message every work day",
            new WorkDaysParser(),
            repeatService,
            scheduler);
  }

  @Bean
  public BotCommand adminRepeatWorkDaysCommand(final RepeatService repeatService, final RepeatScheduler scheduler,
                                               final Long adminId) {
    return admin(
            repeatCommand(
                    ADMIN_REPEAT_WORKDAYS_COMMAND,
                    "repeat message every work day (admin)",
                    new ChatIdParser<>(
                            new WorkDaysParser(
                                    adminDailyParser())),
                    repeatService,
                    scheduler),
            adminId);
  }

  @Bean
  public BotCommand repeatWeekEndCommand(final RepeatService repeatService, final RepeatScheduler scheduler) {
    return repeatCommand(
            REPEAT_WEEKENDS_COMMAND,
            "repeat message every weekend",
            new WeekendsParser(),
            repeatService,
            scheduler);
  }

  @Bean
  public BotCommand adminRepeatWeekEndCommand(final RepeatService repeatService, final RepeatScheduler scheduler,
                                              final Long adminId) {
    return admin(
            repeatCommand(
                    ADMIN_REPEAT_WEEKENDS_COMMAND,
                    "repeat message every weekend (admin)",
                    new ChatIdParser<>(
                            new WeekendsParser(
                                    adminDailyParser())),
                    repeatService,
                    scheduler),
            adminId);
  }

  private BotCommand repeatCommand(final String command, final String description,
                                   final Parser<RepeatRecord> strategy, final RepeatService repeatService,
                                   final RepeatScheduler scheduler) {
    return new NoWhitespaceCommand(
            new ArgsAwareCommand(
                    new RepeatCommand(command, description, strategy, scheduler, repeatService),
                    strategy.parametersCount()));
  }

  private DailyParser adminDailyParser() {
    return new DailyParser(4, 1);
  }

}
