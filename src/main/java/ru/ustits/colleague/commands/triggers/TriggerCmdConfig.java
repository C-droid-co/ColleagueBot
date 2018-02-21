package ru.ustits.colleague.commands.triggers;

import lombok.Data;
import ru.ustits.colleague.AppConfig;

/**
 * @author ustits
 */
@Data
public final class TriggerCmdConfig {

  private int messageLength = AppConfig.MAX_MESSAGE_LENGTH;

}
