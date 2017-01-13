package ru.ustits.colleague;

/**
 * @author ustits
 */
public final class BotContext {

    private static String botToken;
    private static String botName;

    public static void init(String botToken) {
        BotContext.botToken = botToken;
        BotContext.botName = "Colleague bot";
    }

    public static String getBotToken() {
        return botToken;
    }

    public static String getBotName() {
        return botName;
    }
}
