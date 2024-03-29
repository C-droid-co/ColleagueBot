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
public class ColleagueApp {

  private ColleagueApp() {
  }

  public static void main(final String[] args) {
    BotLogger.registerLogger(new SLF4JBridgeHandler());
    ApiContextInitializer.init();
    final TelegramBotsApi api = new TelegramBotsApi();

    final ApplicationContext context =
            new AnnotationConfigApplicationContext(AppContext.class);
    final ColleagueBot bot = context.getBean(ColleagueBot.class);

    try {
      api.registerBot(bot);
      bot.startRepeats();
    } catch (TelegramApiRequestException e) {
      log.error(e);
    }
  }
}
