package ru.ustits.colleague;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author ustits
 */
public class ColleagueApp {

  public static void main(final String[] args) {
    ApiContextInitializer.init();
    final TelegramBotsApi api = new TelegramBotsApi();

    final ApplicationContext context =
            new AnnotationConfigApplicationContext(AppContext.class);
    final ColleagueBot bot = context.getBean(ColleagueBot.class);

    try {
      api.registerBot(bot);
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }
  }
}
