package ru.ustits.colleague.triggers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.AppConfig;
import ru.ustits.colleague.commands.*;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.triggers.commands.*;

/**
 * @author ustits
 */
@Configuration
public class TriggerCommandConfig extends AppConfig {

  public static final int MAX_MESSAGE_LENGTH = 4096;
  private static final String ADD_TRIGGER_COMMAND = "trigger";
  private static final String ADMIN_ADD_TRIGGER_COMMAND = ADMIN_PREFIX + ADD_TRIGGER_COMMAND;
  private static final String TRIGGER_LIST_COMMAND = "trigger_ls";
  private static final String DELETE_TRIGGER_COMMAND = "trigger_rm";
  private static final String ADMIN_DELETE_TRIGGER_COMMAND = ADMIN_PREFIX + DELETE_TRIGGER_COMMAND;
  private static final String ADMIN_DELETE_USER_TRIGGER_COMMAND = ADMIN_DELETE_TRIGGER_COMMAND + "u";
  private static final String IGNORE_TRIGGERS_CMD = "ignore";
  private static final String CHANGE_TRIGGER_MESSAGE_LENGTH_CMD = ADMIN_PREFIX + "trigger_mes_len";

  @Bean
  public Integer defaultMessageLength() {
    final String raw = env.getProperty("bot.message_length");
    return raw == null ? MAX_MESSAGE_LENGTH : Integer.parseInt(raw);
  }

  @Bean
  public TriggerCmdConfig triggerCmdConfig(final int defaultMessageLength) {
    final TriggerCmdConfig config = new TriggerCmdConfig();
    config.setMessageLength(defaultMessageLength);
    return config;
  }

  @Bean
  public BotCommand adminAddTriggerCommand(final TriggerRepository triggerRepository,
                                           final TriggerCmdConfig triggerCmdConfig, final Long adminId) {
    return admin(
            triggerCommand(
                    ADMIN_ADD_TRIGGER_COMMAND,
                    "add trigger to a specific message and chat",
                    new ChatIdParser<>(
                            new TriggerParser(3, 1)),
                    triggerRepository,
                    triggerCmdConfig),
            adminId);
  }

  @Bean
  public BotCommand addTriggerCommand(final TriggerRepository triggerRepository,
                                      final TriggerCmdConfig triggerCmdConfig) {
    return triggerCommand(
            ADD_TRIGGER_COMMAND,
            "add trigger to a specific message",
            new TriggerParser(2),
            triggerRepository, triggerCmdConfig);
  }

  @Bean
  public BotCommand listTriggersCommand(final TriggerRepository triggerRepository) {
    return new ListTriggersCommand(TRIGGER_LIST_COMMAND, triggerRepository);
  }

  @Bean
  public BotCommand deleteTriggerCommand(final TriggerRepository triggerRepository) {
    return new ArgsAwareCommand(
            new DeleteTriggerCommand(
                    DELETE_TRIGGER_COMMAND,
                    "delete chat trigger",
                    triggerRepository,
                    new TriggerParser(1)),
            1);
  }

  @Bean
  public BotCommand adminDeleteTriggerCommand(final TriggerRepository triggerRepository, final Long adminId) {
    return admin(
            new ArgsAwareCommand(
                    new DeleteTriggerCommand(
                            ADMIN_DELETE_TRIGGER_COMMAND,
                            "delete admin's trigger for any chat",
                            triggerRepository,
                            new ChatIdParser<>(
                                    new TriggerParser(2, 1))),
                    2),
            adminId);
  }

  @Bean
  public BotCommand adminDeleteUserTriggerCommand(final TriggerRepository triggerRepository, final Long adminId) {
    return admin(
            new ArgsAwareCommand(
                    new DeleteTriggerCommand(
                            ADMIN_DELETE_USER_TRIGGER_COMMAND,
                            "delete any user's trigger for any chat",
                            triggerRepository,
                            new ChatIdParser<>(
                                    new UserIdParser<>(
                                            new TriggerParser(3, 2)))),
                    3),
            adminId);
  }

  @Bean
  public BotCommand ignoreTriggerCommand(final IgnoreTriggerRepository ignoreTriggerRepository) {
    return new IgnoreTriggerCmd(IGNORE_TRIGGERS_CMD, ignoreTriggerRepository);
  }

  @Bean
  public BotCommand changeTriggerMessageLengthCommand(final TriggerCmdConfig triggerCmdConfig, final Long adminId) {
    return admin(
            new NoWhitespaceCommand(
                    new ArgsAwareCommand(
                            new ChangeMessageLengthCmd(CHANGE_TRIGGER_MESSAGE_LENGTH_CMD, triggerCmdConfig),
                            1
                    )
            ),
            adminId);
  }

  private BotCommand triggerCommand(final String command, final String description,
                                    final Parser<TriggerRecord> strategy, final TriggerRepository triggerRepository,
                                    final TriggerCmdConfig triggerCmdConfig) {
    return new NoWhitespaceCommand(
            new ArgsAwareCommand(
                    new AddTriggerCommand(command, description, triggerRepository, strategy, triggerCmdConfig),
                    strategy.parametersCount()));
  }

}
