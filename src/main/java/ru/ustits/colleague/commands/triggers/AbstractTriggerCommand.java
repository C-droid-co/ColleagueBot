package ru.ustits.colleague.commands.triggers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
@Log4j2
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTriggerCommand extends BotCommand {

  private final TriggerRepository repository;
  private final Parser<TriggerRecord> parser;

  public AbstractTriggerCommand(final String commandIdentifier, final String description,
                                final TriggerRepository repository, final Parser<TriggerRecord> parser) {
    super(commandIdentifier, description);
    this.repository = repository;
    this.parser = parser;
  }

}
