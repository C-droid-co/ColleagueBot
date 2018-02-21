package ru.ustits.colleague;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.commands.*;
import ru.ustits.colleague.commands.repeats.*;
import ru.ustits.colleague.commands.stats.StatsCommand;
import ru.ustits.colleague.commands.stats.WordStatsCmd;
import ru.ustits.colleague.commands.triggers.*;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.Repository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.records.StopWordRecord;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.repositories.services.MessageService;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

/**
 * @author ustits
 */
@Configuration
public class CommandConfig {

  private static final String ADMIN_PREFIX = "a_";
  private static final String ADD_TRIGGER_COMMAND = "trigger";
  private static final String ADMIN_ADD_TRIGGER_COMMAND = ADMIN_PREFIX + ADD_TRIGGER_COMMAND;
  private static final String TRIGGER_LIST_COMMAND = "trigger_ls";
  private static final String DELETE_TRIGGER_COMMAND = "trigger_rm";
  private static final String ADMIN_DELETE_TRIGGER_COMMAND = ADMIN_PREFIX + DELETE_TRIGGER_COMMAND;
  private static final String ADMIN_DELETE_USER_TRIGGER_COMMAND = ADMIN_DELETE_TRIGGER_COMMAND + "u";
  private static final String HELP_COMMAND = "help";
  private static final String REPEAT_COMMAND = "repeat";
  private static final String ADMIN_REPEAT_COMMAND = ADMIN_PREFIX + "repeat";
  private static final String REPEAT_DAILY_COMMAND = "repeat_d";
  private static final String ADMIN_REPEAT_DAILY_COMMAND = ADMIN_PREFIX + "repeat_d";
  private static final String ADMIN_REPEAT_WORKDAYS_COMMAND = ADMIN_PREFIX + "repeat_wd";
  private static final String REPEAT_WORKDAYS_COMMAND = "repeat_wd";
  private static final String ADMIN_REPEAT_WEEKENDS_COMMAND = ADMIN_PREFIX + "repeat_we";
  private static final String REPEAT_WEEKENDS_COMMAND = "repeat_we";
  private static final String STATS_COMMAND = "stats";
  private static final String WORD_STATS_CMD = "word_stats";
  private static final String PROCESS_STATE_COMMAND = "state_switch";
  private static final String LIST_PROCESS_STATE_COMMAND = "state_ls";
  private static final String SHOW_CURRENT_STATE_COMMAND = "state";
  private static final String CHANGE_TRIGGER_MESSAGE_LENGTH_CMD = ADMIN_PREFIX + "trigger_mes_len";
  private static final String IGNORE_TRIGGERS_CMD = "ignore";

  @Bean
  public BotCommand[] commands(final ColleagueBot bot, final TriggerRepository triggerRepository,
                               final RepeatService repeatService, final MessageService messageService,
                               final Repository<StopWordRecord> stopWordRepository,
                               final IgnoreTriggerRepository ignoreTriggerRepository,
                               final TriggerCmdConfig triggerCmdConfig, final Long adminId,
                               final RepeatScheduler scheduler) {
    return new BotCommand[] {
            admin(
                    triggerCommand(
                            ADMIN_ADD_TRIGGER_COMMAND,
                            "add trigger to a specific message and chat",
                            new ChatIdParser<>(
                                    new TriggerParser(3, 1)),
                            triggerRepository,
                            triggerCmdConfig),
                    adminId),
            triggerCommand(
                    ADD_TRIGGER_COMMAND,
                    "add trigger to a specific message",
                    new TriggerParser(2),
                    triggerRepository, triggerCmdConfig),
            helpCommand(bot),
            repeatCommand(
                    REPEAT_COMMAND,
                    "repeat message with cron expression",
                    new PlainParser(),
                    repeatService,
                    scheduler),
            admin(
                    repeatCommand(
                            ADMIN_REPEAT_COMMAND,
                            "add repeat message with cron expression to any chat",
                            new ChatIdParser<>(
                                    new PlainParser(8, 1)),
                            repeatService,
                            scheduler),
                    adminId),
            repeatCommand(
                    REPEAT_DAILY_COMMAND,
                    "repeat message everyday",
                    new DailyParser(),
                    repeatService,
                    scheduler),
            admin(
                    repeatCommand(
                            ADMIN_REPEAT_DAILY_COMMAND,
                            "repeat message everyday (admin)",
                            new ChatIdParser<>(
                                    adminDailyParser()),
                            repeatService,
                            scheduler),
                    adminId),
            repeatCommand(REPEAT_WORKDAYS_COMMAND, "repeat message every work day",
                    new WorkDaysParser(),
                    repeatService,
                    scheduler),
            admin(
                    repeatCommand(
                            ADMIN_REPEAT_WORKDAYS_COMMAND,
                            "repeat message every work day (admin)",
                            new ChatIdParser<>(
                                    new WorkDaysParser(
                                            adminDailyParser())),
                            repeatService,
                            scheduler),
                    adminId),
            repeatCommand(
                    REPEAT_WEEKENDS_COMMAND,
                    "repeat message every weekend",
                    new WeekendsParser(),
                    repeatService,
                    scheduler),
            admin(
                    repeatCommand(
                            ADMIN_REPEAT_WEEKENDS_COMMAND,
                            "repeat message every weekend (admin)",
                            new ChatIdParser<>(
                                    new WeekendsParser(
                                            adminDailyParser())),
                            repeatService,
                            scheduler),
                    adminId),
            listTriggersCommand(triggerRepository),
            statsCommand(messageService),
            wordStatsCommand(messageService, stopWordRepository),
            new ArgsAwareCommand(
                    new DeleteTriggerCommand(
                            DELETE_TRIGGER_COMMAND,
                            "delete chat trigger",
                            triggerRepository,
                            new TriggerParser(1)),
                    1),
            admin(
                    new ArgsAwareCommand(
                            new DeleteTriggerCommand(
                                    ADMIN_DELETE_TRIGGER_COMMAND,
                                    "delete admin's trigger for any chat",
                                    triggerRepository,
                                    new ChatIdParser<>(
                                            new TriggerParser(2, 1))),
                            2),
                    adminId),
            admin(
                    new ArgsAwareCommand(
                            new DeleteTriggerCommand(
                                    ADMIN_DELETE_USER_TRIGGER_COMMAND,
                                    "delete any user's trigger for any chat",
                                    triggerRepository,
                                    new ChatIdParser<>(
                                            new UserIdParser<>(
                                                    new TriggerParser(3, 2)))),
                            3),
                    adminId),
            admin(
                    new NoWhitespaceCommand(
                            new ArgsAwareCommand(
                                    new ProcessStateCommand(
                                            PROCESS_STATE_COMMAND,
                                            "change trigger reaction",
                                            bot),
                                    1
                            )),
                    adminId),
            new ListProcessStatesCommand(LIST_PROCESS_STATE_COMMAND, "list all trigger reactions"),
            new ShowStateCommand(
                    SHOW_CURRENT_STATE_COMMAND,
                    "show current trigger reaction",
                    bot),
            admin(
                    new NoWhitespaceCommand(
                            new ArgsAwareCommand(
                                    new ChangeMessageLengthCmd(CHANGE_TRIGGER_MESSAGE_LENGTH_CMD, triggerCmdConfig),
                                    1
                            )
                    ),
                    adminId),
            new IgnoreTriggerCmd(IGNORE_TRIGGERS_CMD, ignoreTriggerRepository)
    };
  }

  @Bean
  public TriggerCmdConfig triggerCmdConfig(final int defaultMessageLength) {
    final TriggerCmdConfig config = new TriggerCmdConfig();
    config.setMessageLength(defaultMessageLength);
    return config;
  }

  private BotCommand triggerCommand(final String command, final String description,
                                    final Parser<TriggerRecord> strategy, final TriggerRepository triggerRepository,
                                    final TriggerCmdConfig triggerCmdConfig) {
    return new NoWhitespaceCommand(
            new ArgsAwareCommand(
                    new AddTriggerCommand(command, description, triggerRepository, strategy, triggerCmdConfig),
                    strategy.parametersCount()));
  }

  private AdminAwareCommand admin(final BotCommand command, final Long adminId) {
    return new AdminAwareCommand(command, adminId);
  }

  private DailyParser adminDailyParser() {
    return new DailyParser(4, 1);
  }

  private ListTriggersCommand listTriggersCommand(final TriggerRepository triggerRepository) {
    return new ListTriggersCommand(TRIGGER_LIST_COMMAND, triggerRepository);
  }

  private HelpCommand helpCommand(final ColleagueBot bot) {
    return new HelpCommand(bot, HELP_COMMAND);
  }

  private StatsCommand statsCommand(final MessageService messageService) {
    return new StatsCommand(STATS_COMMAND, messageService);
  }

  private WordStatsCmd wordStatsCommand(final MessageService messageService,
                                        final Repository<StopWordRecord> stopWordRepository) {
    return new WordStatsCmd(WORD_STATS_CMD, messageService, stopWordRepository);
  }

  private BotCommand repeatCommand(final String command, final String description,
                                   final Parser<RepeatRecord> strategy, final RepeatService repeatService,
                                   final RepeatScheduler scheduler) {
    return new NoWhitespaceCommand(
            new ArgsAwareCommand(
                    new RepeatCommand(command, description, strategy, scheduler, repeatService),
                    strategy.parametersCount()));
  }

}
