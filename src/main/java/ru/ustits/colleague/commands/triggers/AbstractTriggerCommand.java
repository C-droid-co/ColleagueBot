package ru.ustits.colleague.commands.triggers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.repositories.TriggerRepository;

/**
 * @author ustits
 */
@Log4j2
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTriggerCommand extends BotCommand {

  private final TriggerRepository repository;
  private final TriggerStrategy commandStrategy;

  public AbstractTriggerCommand(final String commandIdentifier, final String description,
                                final TriggerRepository repository, final TriggerStrategy commandStrategy) {
    super(commandIdentifier, description);
    this.repository = repository;
    this.commandStrategy = commandStrategy;
  }

}
