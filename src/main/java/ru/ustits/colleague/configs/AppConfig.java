package ru.ustits.colleague.configs;

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
import ru.ustits.colleague.tasks.RepeatScheduler;

import javax.sql.DataSource;

/**
 * @author ustits
 */
@Configuration
@ComponentScan(basePackages = "ru.ustits.colleague")
@PropertySource("classpath:bot_config.properties")
public class AppConfig {

  public static final int MAX_MESSAGE_LENGTH = 4096;

  @Autowired
  private Environment env;

  @Bean
  public RepeatScheduler scheduler() throws SchedulerException {
    final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
    return new RepeatScheduler(scheduler);
  }

  @Bean
  public QueryRunner sql(final DataSource dataSource) {
    return new QueryRunner(dataSource);
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
  public Long adminId() {
    return Long.parseLong(env.getRequiredProperty("admin.id"));
  }

  @Bean
  public String botName() {
    return env.getRequiredProperty("bot.name");
  }

  @Bean
  public String botToken() {
    return env.getRequiredProperty("bot.token");
  }

  @Bean
  public Integer defaultMessageLength() {
    final String raw = env.getProperty("bot.message_length");
    return raw == null ? MAX_MESSAGE_LENGTH : Integer.parseInt(raw);
  }

}
