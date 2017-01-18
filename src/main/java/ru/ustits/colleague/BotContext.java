package ru.ustits.colleague;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author ustits
 */
@Accessors(fluent = true)
public final class BotContext {

    @Getter
    private static String botToken;
    @Getter
    private static String botName;

    public static void init(final String botToken) {
        BotContext.botToken = botToken;
        BotContext.botName = "Colleague bot";
    }
}
