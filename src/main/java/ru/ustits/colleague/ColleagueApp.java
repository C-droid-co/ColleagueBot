package ru.ustits.colleague;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.ustits.colleague.tables.Triggers;
import ru.ustits.colleague.tables.records.TriggersRecord;

/**
 * @author ustits
 */
public class ColleagueApp {

    public static void main(final String[] args) {
        ApiContextInitializer.init();
        BotContext.init(args[0]);
        DBContext.init(args[1], args[2]);
        final TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new ColleagueBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        printAllTriggers();
    }

    private static void printAllTriggers() {
        final DSLContext create = DSL.using(DBContext.connection(), SQLDialect.POSTGRES);

        for (final TriggersRecord record : create.fetch(Triggers.TRIGGERS)) {
            final Integer id = record.getId();
            final String trigger = record.getTrigger();
            final String message = record.getMessage();
            System.out.println("ID: " + id + " trigger: " + trigger + " message: " + message);
        }
    }
}
