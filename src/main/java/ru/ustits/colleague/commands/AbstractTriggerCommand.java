package ru.ustits.colleague.commands;

import lombok.AccessLevel;
import lombok.Getter;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.repositories.TriggerRepository;

/**
 * @author ustits
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTriggerCommand extends BotCommand {

  private final TriggerRepository repository;

  public AbstractTriggerCommand(final String commandIdentifier, final String description,
                                final TriggerRepository repository) {
    super(commandIdentifier, description);
    this.repository = repository;
  }

  protected final String resolveTrigger(final String[] args) {
    return args[0].toLowerCase();
  }

}
