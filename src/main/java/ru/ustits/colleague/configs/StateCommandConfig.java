package ru.ustits.colleague.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.commands.ArgsAwareCommand;
import ru.ustits.colleague.commands.NoWhitespaceCommand;
import ru.ustits.colleague.commands.triggers.ListProcessStatesCommand;
import ru.ustits.colleague.commands.triggers.ProcessStateCommand;
import ru.ustits.colleague.commands.triggers.ShowStateCommand;
import ru.ustits.colleague.repositories.services.ChatService;
import ru.ustits.colleague.tools.triggers.ProcessState;

/**
 * @author ustits
 */
@Configuration
class StateCommandConfig extends CommandConfig {

  private static final String PROCESS_STATE_COMMAND = "state_switch";
  private static final String LIST_PROCESS_STATE_COMMAND = "state_ls";
  private static final String SHOW_CURRENT_STATE_COMMAND = "state";

  @Bean
  public BotCommand changeStateCommand(final Long adminId, final ChatService chatService) {
    return admin(
            new NoWhitespaceCommand(
                    new ArgsAwareCommand(
                            new ProcessStateCommand(
                                    PROCESS_STATE_COMMAND,
                                    "change trigger reaction",
                                    chatService),
                            1
                    )),
            adminId);
  }

  @Bean
  public BotCommand listStateCommand() {
    return new ListProcessStatesCommand(LIST_PROCESS_STATE_COMMAND, "list all trigger reactions");
  }

  @Bean
  public BotCommand showStateCommand(final ChatService chatService) {
    return new ShowStateCommand(
            SHOW_CURRENT_STATE_COMMAND,
            "show current trigger reaction",
            chatService);
  }

  @Bean
  public ProcessState defaultProcessState() {
    return ProcessState.ALL;
  }

}
