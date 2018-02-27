package ru.ustits.colleague;

import lombok.extern.log4j.Log4j2;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * @author ustits
 */
@Log4j2
public final class ColleagueApp {

  private final Class<?>[] configs;

  public ColleagueApp(final Class<?>... configs) {
    this.configs = configs;
  }

  public void register(final Class<? extends ColleagueBot> botClass) {
    BotLogger.registerLogger(new SLF4JBridgeHandler());
    ApiContextInitializer.init();
    final TelegramBotsApi api = new TelegramBotsApi();

    final ApplicationContext context =
            new AnnotationConfigApplicationContext(configs);
    final ColleagueBot bot = context.getBean(botClass);

    try {
      api.registerBot(bot);
    } catch (TelegramApiRequestException e) {
      log.error(e);
    }
  }

}
