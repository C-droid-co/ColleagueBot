package ru.ustits.colleague;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.ustits.colleague.commands.TriggerCommand;

/**
 * @author ustits
 */
@Configuration
@ComponentScan
public class AppConfigs {

    private final static String TRIGGER_COMMAND = "trigger";

    @Bean
    public TriggerCommand triggerCommand() {
        return new TriggerCommand(TRIGGER_COMMAND);
    }
}
