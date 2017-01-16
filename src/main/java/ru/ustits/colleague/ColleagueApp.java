package ru.ustits.colleague;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.ustits.colleague.tables.Triggers;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author ustits
 */
public class ColleagueApp {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        BotContext.init(args[0]);
        final TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new ColleagueBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        sampleJooqQuering(args[1], args[2]);
    }

    private static void sampleJooqQuering(String userName, String password) {
        String url = "jdbc:postgresql://localhost:5432/colleague";

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(Triggers.TRIGGERS).fetch();

            for (Record r : result) {
                Integer id = r.getValue(Triggers.TRIGGERS.ID);
                String trigger = r.getValue(Triggers.TRIGGERS.TRIGGER);
                String message = r.getValue(Triggers.TRIGGERS.MESSAGE);
                System.out.println("ID: " + id + " trigger: " + trigger + " message: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
