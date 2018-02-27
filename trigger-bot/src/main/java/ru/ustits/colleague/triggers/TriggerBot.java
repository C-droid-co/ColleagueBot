package ru.ustits.colleague.triggers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.triggers.services.TriggerService;

import java.util.List;

/**
 * @author ustits
 */
@Service
public class TriggerBot extends ColleagueBot {

  private final TriggerService triggerService;

  public TriggerBot(final String botName, final String botToken, final TriggerService triggerService) {
    super(botName, botToken);
    this.triggerService = triggerService;
  }

  @Override
  public void processNonCommandUpdate(final Update update) {
    super.processNonCommandUpdate(update);
    if (isMessage(update)) {
      replyWithTriggers(update.getMessage());
    }
  }

  private void replyWithTriggers(final Message message) {
    final List<String> triggers = triggerService.findTriggers(message);
    triggers.forEach(s -> {
      final SendMessage msg = new SendMessage();
      msg.setText(s);
      sendMessage(message.getChatId(), msg);
    });
  }

}
