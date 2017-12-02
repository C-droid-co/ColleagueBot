package ru.ustits.colleague.commands.triggers;

import lombok.Data;
import ru.ustits.colleague.AppContext;

/**
 * @author ustits
 */
@Data
public final class TriggerCmdConfig {

  private int messageLength = AppContext.MAX_MESSAGE_LENGTH;

}
