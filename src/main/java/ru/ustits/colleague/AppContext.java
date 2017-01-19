package ru.ustits.colleague;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.TriggerCommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ustits
 */
@Configuration
@ComponentScan
@PropertySource("classpath:bot_config.properties")
public class AppContext {

    private final static String TRIGGER_COMMAND = "trigger";
    private final static String HELP_COMMAND = "help";

    @Autowired
    private Environment environment;

    @Bean
    public ColleagueBot bot() {
        final ColleagueBot bot = new ColleagueBot();
        bot.setTriggerCommand(triggerCommand());
        bot.setHelpCommand(helpCommand(bot));
        bot.setBotName(getBotName());
        bot.setBotToken(getBotToken());
        return bot;
    }

    @Bean
    public TriggerCommand triggerCommand() {
        return new TriggerCommand(TRIGGER_COMMAND);
    }

    @Bean
    public HelpCommand helpCommand(final ColleagueBot bot) {
        return new HelpCommand(bot, HELP_COMMAND);
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(connection(), SQLDialect.POSTGRES);
    }

    @Bean
    public Connection connection() {
        final String url = "jdbc:postgresql://localhost:5432/colleague";
        final String user = environment.getRequiredProperty("db.user");
        final String password = environment.getRequiredProperty("db.password");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getBotName() {
        return environment.getRequiredProperty("bot.name");
    }

    private String getBotToken() {
        return environment.getRequiredProperty("bot.token");
    }
}
