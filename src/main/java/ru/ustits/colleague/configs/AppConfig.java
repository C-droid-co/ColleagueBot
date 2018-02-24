package ru.ustits.colleague.configs;

import org.hibernate.jpa.HibernatePersistenceProvider;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.ustits.colleague.tasks.RepeatScheduler;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ustits
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.ustits.colleague.repositories")
@ComponentScan(basePackages = "ru.ustits.colleague")
@PropertySource("classpath:bot_config.properties")
public class AppConfig {

  public static final int MAX_MESSAGE_LENGTH = 4096;

  @Autowired
  private Environment env;

  @Autowired
  private ResourceLoader loader;

  @Bean
  public RepeatScheduler scheduler() throws SchedulerException {
    final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
    return new RepeatScheduler(scheduler);
  }

  @Bean
  public JdbcTemplate sql(final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
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
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
    final LocalContainerEntityManagerFactoryBean manager = new LocalContainerEntityManagerFactoryBean();
    manager.setDataSource(dataSource);
    manager.setPackagesToScan("ru.ustits.colleague.repositories.records");
    manager.setPersistenceProvider(new HibernatePersistenceProvider());
    final Resource resource = loader.getResource("classpath:hibernate.properties");
    final Properties properties = new Properties();
    try (final InputStream data = resource.getInputStream()) {
      properties.load(data);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read hibernate properties");
    }
    manager.setJpaProperties(properties);
    return manager;
  }

  @Bean
  public JpaTransactionManager transactionManager(final DataSource dataSource) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
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
