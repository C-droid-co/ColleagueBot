package ru.ustits.colleague.stats;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.services.MessageService;

/**
 * @author ustits
 */
@Service
public class StatsBot extends ColleagueBot {

  private final MessageService messageService;

  public StatsBot(final String botName, final String botToken, final MessageService messageService) {
    super(botName, botToken);
    this.messageService = messageService;
  }

  @Override
  public void processNonCommandUpdate(final Update update) {
    super.processNonCommandUpdate(update);
    if (hasMessage(update)) {
      addMessage(update);
    }
  }

  private MessageRecord addMessage(final Update update) {
    final MessageRecord record;
    if (isMessage(update)) {
      record = messageService.addMessage(update.getMessage());
    } else if (isEditMessage(update)) {
      record = messageService.addMessage(update.getEditedMessage());
    } else {
      record = null;
    }
    return record;
  }

}
