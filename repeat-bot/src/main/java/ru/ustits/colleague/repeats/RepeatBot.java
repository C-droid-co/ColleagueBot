package ru.ustits.colleague.repeats;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.repeats.services.RepeatService;
import ru.ustits.colleague.repeats.tasks.RepeatScheduler;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.List;

/**
 * @author ustits
 */
@Service
public class RepeatBot extends ColleagueBot {

  private final RepeatService repeatService;
  private final RepeatScheduler scheduler;

  public RepeatBot(final String botName, final String botToken, final BotCommand[] commands,
                   final RepeatService repeatService, final RepeatScheduler scheduler) {
    super(botName, botToken, commands);
    this.repeatService = repeatService;
    this.scheduler = scheduler;
  }

  @Override
  protected void initialize() {
    final List<RepeatRecord> records = repeatService.fetchAllRepeats();
    scheduler.scheduleTasks(records, this);
    super.initialize();
  }

}
