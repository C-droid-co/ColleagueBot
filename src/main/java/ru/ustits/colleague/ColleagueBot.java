package ru.ustits.colleague;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.TriggerCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ustits
 */
public class ColleagueBot extends TelegramLongPollingCommandBot {

    private final Map<String, PartialBotApiMethod> triggers;

    private final TriggerCommand triggerCommand;

    public ColleagueBot() {
        triggers = new HashMap<>();
        triggerCommand = new TriggerCommand(triggers);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        System.out.println(update);
        if (isNotEditedMessage(update)) {
            register(triggerCommand);
            register(new HelpCommand(this));
            if (update.hasMessage() && update.getMessage().hasText()) {
                processMessage(update.getMessage());
            }
        }
    }

    private boolean isNotEditedMessage(Update update) {
        return update.getEditedMessage() == null;
    }

    private void processMessage(Message message) {
        for (Map.Entry<String, PartialBotApiMethod> trigger : triggers.entrySet()) {
            Pattern pattern = Pattern.compile(Pattern.quote(trigger.getKey()),
                    Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(message.getText());
            if (matcher.find()) {
                sendMessage(message.getChatId(), trigger.getValue());
            }
        }
    }

    private void sendMessage(Long chatId, PartialBotApiMethod object) {
        try {
            if (object instanceof SendMessage) {
                final SendMessage message = ((SendMessage) object).setChatId(chatId);
                sendMessage(message);
            } else if (object instanceof SendSticker) {
                final SendSticker sticker = ((SendSticker) object).setChatId(chatId);
                sendSticker(sticker);
            } else if (object instanceof SendDocument) {
                final SendDocument document = ((SendDocument) object).setChatId(chatId);
                sendDocument(document);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BotContext.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return BotContext.getBotName();
    }
}
