package ru.ustits.colleague;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import org.postgresql.ds.PGSimpleDataSource;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.ustits.colleague.commands.*;
import ru.ustits.colleague.commands.repeats.*;
import ru.ustits.colleague.repositories.*;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

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
  private static final String TRIGGER_LIST_COMMAND = "trigger_ls";
  private static final String DELETE_TRIGGER_COMMAND = "trigger_rm";
  private static final String HELP_COMMAND = "help";
  private static final String REPEAT_COMMAND = "repeat";
  private static final String REPEAT_DAILY_COMMAND = "repeat_d";
  private static final String REPEAT_WORKDAYS_COMMAND = "repeat_wd";
  private static final String REPEAT_WEEKENDS_COMMAND = "repeat_we";
  private static final String STATS_COMMAND = "stats";

  @Autowired
  private Environment env;

  @Bean
  public ColleagueBot bot() throws SchedulerException {
    final ColleagueBot bot = new ColleagueBot(botName());
    bot.registerAll(triggerCommand(),
            helpCommand(bot),
            repeatCommand(REPEAT_COMMAND,"repeat message with cron expression", new PlainStrategy()),
            repeatCommand(REPEAT_DAILY_COMMAND,"repeat message everyday", new DailyStrategy()),
            repeatCommand(REPEAT_WORKDAYS_COMMAND,"repeat message every work day", new WorkDaysStrategy()),
            repeatCommand(REPEAT_WEEKENDS_COMMAND,"repeat message every weekend", new WeekendsStrategy()),
            listTriggersCommand(),
            statsCommand(),
            new DeleteTriggerCommand(DELETE_TRIGGER_COMMAND, triggerRepository()));
    return bot;
  }

  @Bean
  public TriggerCommand triggerCommand() {
    return new TriggerCommand(TRIGGER_COMMAND, triggerRepository());
  }

  @Bean
  public ListTriggersCommand listTriggersCommand() {
    return new ListTriggersCommand(TRIGGER_LIST_COMMAND);
  }

  @Bean
  public HelpCommand helpCommand(final ColleagueBot bot) {
    return new HelpCommand(bot, HELP_COMMAND);
  }

  @Bean
  public StatsCommand statsCommand() {
    return new StatsCommand(STATS_COMMAND);
  }

  public RepeatCommand repeatCommand(final String command, final String description,
                                     final RepeatStrategy strategy) throws SchedulerException {
    return new RepeatCommand(command, description, strategy, scheduler(), repeatService());
  }

  @Bean
  public RepeatScheduler scheduler() throws SchedulerException {
    final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
    return new RepeatScheduler(scheduler);
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
  public RepeatService repeatService() {
    return new RepeatService(repeatRepository(), chatsRepository(), userRepository());
  }

  @Bean
  public MessageRepository messageRepository() {
    return new MessageRepository(sql());
  }

  @Bean
  public ChatsRepository chatsRepository() {
    return new ChatsRepository(sql());
  }

  @Bean
  public UserRepository userRepository() {
    return new UserRepository(sql());
  }

  @Bean
  public TriggerRepository triggerRepository() {
    return new TriggerRepository(sql());
  }

  @Bean
  public RepeatRepository repeatRepository() {
    return new RepeatRepository(sql());
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
