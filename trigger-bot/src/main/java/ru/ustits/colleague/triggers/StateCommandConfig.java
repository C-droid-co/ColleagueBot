package ru.ustits.colleague.triggers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.AppConfig;
import ru.ustits.colleague.commands.ArgsAwareCommand;
import ru.ustits.colleague.commands.NoWhitespaceCommand;
import ru.ustits.colleague.triggers.commands.states.ChangeStateCommand;
import ru.ustits.colleague.triggers.commands.states.ListStatesCommand;
import ru.ustits.colleague.triggers.commands.states.ShowStateCommand;
import ru.ustits.colleague.triggers.services.StateService;
import ru.ustits.colleague.triggers.tools.ProcessState;

/**
 * @author ustits
 */
@Configuration
public class StateCommandConfig extends AppConfig {

  private static final String PROCESS_STATE_COMMAND = "state_switch";
  private static final String LIST_PROCESS_STATE_COMMAND = "state_ls";
  private static final String SHOW_CURRENT_STATE_COMMAND = "state";

  @Bean
  public ProcessState defaultProcessState() {
    return ProcessState.ALL;
  }

  @Bean
  public BotCommand changeStateCommand(final Long adminId, final StateService stateService) {
    return admin(
            new NoWhitespaceCommand(
                    new ArgsAwareCommand(
                            new ChangeStateCommand(
                                    PROCESS_STATE_COMMAND,
                                    "change trigger reaction",
                                    stateService),
                            1
                    )),
            adminId);
  }

  @Bean
  public BotCommand listStateCommand() {
    return new ListStatesCommand(LIST_PROCESS_STATE_COMMAND, "list all trigger reactions");
  }

  @Bean
  public BotCommand showStateCommand(final StateService stateService) {
    return new ShowStateCommand(
            SHOW_CURRENT_STATE_COMMAND,
            "show current trigger reaction",
            stateService);
  }

}
