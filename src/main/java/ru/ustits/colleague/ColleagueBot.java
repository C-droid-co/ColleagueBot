package ru.ustits.colleague;

import lombok.Setter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.TriggerCommand;
import ru.ustits.colleague.tables.Triggers;
import ru.ustits.colleague.tables.records.TriggersRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ustits
 */
@Setter
public class ColleagueBot extends TelegramLongPollingCommandBot {

    @Autowired
    private DSLContext dsl;

    private TriggerCommand triggerCommand;
    private HelpCommand helpCommand;
    private String botName;
    private String botToken;

    @Override
    public void processNonCommandUpdate(final Update update) {
        System.out.println(update);
        if (update.hasCallbackQuery()) {
            processCallback(update.getCallbackQuery());
        } else if (isNotEditedMessage(update)) {
            register(triggerCommand);
            register(helpCommand);
            if (update.hasMessage() && update.getMessage().hasText()) {
                processMessage(update.getMessage());
            }
        }
    }

    private boolean isNotEditedMessage(final Update update) {
        return update.getEditedMessage() == null;
    }

    private void processCallback(final CallbackQuery callback) {
        final EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(callback.getMessage().getChatId());
        editMessage.setMessageId(callback.getMessage().getMessageId());
        editMessage.setText(callback.getData());
        try {
            editMessageText(editMessage);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void processMessage(final Message message) {
        for (final TriggersRecord record : dsl.fetch(Triggers.TRIGGERS)) {
            final String trigger = record.getTrigger();
            final Pattern pattern = Pattern.compile(Pattern.quote(trigger),
                    Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
            final Matcher matcher = pattern.matcher(message.getText());
            if (matcher.find()) {
                final SendMessage sm = new SendMessage();
                sm.setText(record.getMessage());
                sendMessage(message.getChatId(), sm);
            }
        }
    }

    private void sendMessage(final Long chatId, final PartialBotApiMethod object) {
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
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
