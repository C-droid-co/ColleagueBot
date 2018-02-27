package ru.ustits.colleague.triggers.commands;

import lombok.Data;
import ru.ustits.colleague.triggers.TriggerCommandConfig;

/**
 * @author ustits
 */
@Data
public final class TriggerCmdConfig {

  private int messageLength = TriggerCommandConfig.MAX_MESSAGE_LENGTH;

}
