package ru.ustits.colleague.commands;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import ru.ustits.colleague.tables.Triggers;
import ru.ustits.colleague.tables.records.TriggersRecord;

/**
 * @author ustits
 */
public class TriggerCommand extends BotCommand {

    @Autowired
    private DSLContext dsl;

    public TriggerCommand(final String command) {
        super(command, "add trigger");
    }

    public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
        final SendMessage answer = processArgumentsAndSetResponse(arguments);
        try {
            absSender.sendMessage(answer.setChatId(chat.getId().toString()));
        } catch (TelegramApiException e) {
            BotLogger.error(getCommandIdentifier(), e);
        }
    }

    private SendMessage processArgumentsAndSetResponse(final String[] arguments) {
        final SendMessage commandResult = new SendMessage();
        if (arguments.length >= 2) {
            final String trigger = addTrigger(arguments);
            commandResult.setText(String.format("Trigger [%s] successfully added", trigger));
        } else {
            commandResult.setText(failResult());
        }
        return commandResult;
    }

    private String addTrigger(final String[] arguments) {
        final String trigger = arguments[0];
        final String message = convertStringArrayToString(arguments);

        final TriggersRecord record = dsl.newRecord(Triggers.TRIGGERS);
        record.setTrigger(trigger);
        record.setMessage(message);
        record.store();

        final String logMessage = String.format("Added trigger:\n %s", record.toString());
        System.out.println(logMessage);

        return trigger;
    }

    private String convertStringArrayToString(final String[] array) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            builder.append(array[i]).append(" ");
        }
        builder.substring(0, builder.length() - 1);
        return builder.toString();
    }

    private String failResult() {
        return String.format("Couldn't add trigger. Please use \"/%s trigger response_text\" construction",
                getCommandIdentifier());
    }
}
