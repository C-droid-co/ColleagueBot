package ru.ustits.colleague;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.RepeatCommand;
import ru.ustits.colleague.commands.RequestCommand;
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

  private static final Logger log = LogManager.getLogger();

  private static final String TRIGGER_COMMAND = "trigger";
  private static final String HELP_COMMAND = "help";
  private static final String REPEAT_COMMAND = "repeat";
  private static final String REQUEST_COMMAND = "request";

  private static final int REPEAT_POOL_SIZE = 1;

  @Autowired
  private Environment environment;

  @Bean
  public ColleagueBot bot() {
    final ColleagueBot bot = new ColleagueBot();
    bot.registerAll(triggerCommand(),
            helpCommand(bot),
            repeatCommand(),
            requestCommand());
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
  public RepeatCommand repeatCommand() {
    return new RepeatCommand(REPEAT_COMMAND, REPEAT_POOL_SIZE);
  }

  @Bean
  public TriggerProcessor triggerProcessor(final DefaultDSLContext dsl) {
    return new TriggerProcessor(dsl);
  }

  @Bean
  public RequestCommand requestCommand() {
    return new RequestCommand(REQUEST_COMMAND);
  }

  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(connection(), SQLDialect.POSTGRES);
  }

  @Bean
  public Connection connection() {
    final String url = environment.getRequiredProperty("db.url");
    final String user = environment.getRequiredProperty("db.user");
    final String password = environment.getRequiredProperty("db.password");

    try {
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      log.error(e);
    }
    return null;
  }

  @Bean
  public MessageRepository messageRepository() {
    return new MessageRepository();
  }

  @Bean
  public String botName() {
    return environment.getRequiredProperty("bot.name");
  }

  @Bean
  public String botToken() {
    return environment.getRequiredProperty("bot.token");
  }
}
