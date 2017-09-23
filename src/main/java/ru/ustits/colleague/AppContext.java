package ru.ustits.colleague;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.RepeatCommand;
import ru.ustits.colleague.commands.TriggerCommand;
import ru.ustits.colleague.repositories.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ustits
 */
@Log4j2
@Configuration
@ComponentScan
@PropertySource("classpath:bot_config.properties")
public class AppContext {

  private static final String TRIGGER_COMMAND = "trigger";
  private static final String HELP_COMMAND = "help";
  private static final String REPEAT_COMMAND = "repeat";

  private static final int REPEAT_POOL_SIZE = 1;

  @Autowired
  private Environment env;

  @Bean
  public ColleagueBot bot() {
    final ColleagueBot bot = new ColleagueBot();
    bot.registerAll(triggerCommand(),
            helpCommand(bot),
            repeatCommand());
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
  public TriggerProcessor triggerProcessor(final TriggerRepository repository) {
    return new TriggerProcessor(repository);
  }

  @Bean
  public QueryRunner sql() {
    return new QueryRunner(dataSource());
  }

  @Bean
  public DataSource dataSource() {
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName(env.getRequiredProperty("db.url"));
    dataSource.setDatabaseName(env.getRequiredProperty("db.name"));
    dataSource.setUser(env.getRequiredProperty("db.user"));
    dataSource.setPassword(env.getRequiredProperty("db.password"));
    dataSource.setPortNumber(Integer.valueOf(env.getRequiredProperty("db.port")));
    return dataSource;
  }

  @Bean
  public Connection connection() {
    final String url = env.getRequiredProperty("db.jdbc_url");
    final String user = env.getRequiredProperty("db.user");
    final String password = env.getRequiredProperty("db.password");

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
  public ChatsRepository chatsRepository() {
    return new ChatsRepository();
  }

  @Bean
  public UserRepository userRepository() {
    return new UserRepository();
  }

  @Bean
  public TriggerRepository triggerRepository() {
    return new TriggerRepository();
  }

  @Bean
  public RepeatRepository repeatRepository() {
    return new RepeatRepository();
  }

  @Bean
  public String botName() {
    return env.getRequiredProperty("bot.name");
  }

  @Bean
  public String botToken() {
    return env.getRequiredProperty("bot.token");
  }
}
