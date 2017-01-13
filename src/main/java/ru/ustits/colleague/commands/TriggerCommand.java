package ru.ustits.colleague.commands;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.Map;

/**
 * @author ustits
 */
public class TriggerCommand extends BotCommand {

    private static final String COMMAND_TAG = "trigger";

    private final Map<String, PartialBotApiMethod> triggers;

    public TriggerCommand(Map<String, PartialBotApiMethod> triggers) {
        super(COMMAND_TAG, "add trigger");
        this.triggers = triggers;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage answer = processArgumentsAndSetResponse(arguments);
        try {
            absSender.sendMessage(answer.setChatId(chat.getId().toString()));
        } catch (TelegramApiException e) {
            BotLogger.error(COMMAND_TAG, e);
        }
    }

    private SendMessage processArgumentsAndSetResponse(String[] arguments) {
        SendMessage commandResult = new SendMessage();
        if (arguments.length >= 2) {
            String trigger = addTrigger(arguments);
            commandResult.setText(String.format("Trigger [%s] successfully added", trigger));
        } else {
            commandResult.setText(failResult());
        }
        return commandResult;
    }

    private String addTrigger(String[] arguments) {
        String trigger = arguments[0];
        String response = convertStringArrayToString(arguments);

        SendMessage message = new SendMessage();
        message.setText(response);

        triggers.put(trigger, message);
        return trigger;
    }

    private String convertStringArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            builder.append(array[i]).append(" ");
        }
        builder.substring(0, builder.length() - 1);
        return builder.toString();
    }

    private String failResult() {
        return String.format("Couldn't add trigger. Please use \"/%s trigger response_text\" construction",
                COMMAND_TAG);
    }
}
