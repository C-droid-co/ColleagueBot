package ru.ustits.colleague;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author ustits
 */
public class ColleagueApp {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        BotContext.init(args[0]);
        TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new ColleagueBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
