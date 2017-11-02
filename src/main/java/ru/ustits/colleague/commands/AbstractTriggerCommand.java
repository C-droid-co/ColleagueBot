package ru.ustits.colleague.commands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.TriggerRepository;

/**
 * @author ustits
 */
@Log4j2
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTriggerCommand extends ArgsAwareCommand {

  private final TriggerRepository repository;

  public AbstractTriggerCommand(final String commandIdentifier, final String description,
                                final TriggerRepository repository, final int minArgsLen) {
    super(commandIdentifier, description, minArgsLen);
    this.repository = repository;
  }

  protected final String resolveTrigger(final String[] args) {
    return args[0].toLowerCase();
  }

}
