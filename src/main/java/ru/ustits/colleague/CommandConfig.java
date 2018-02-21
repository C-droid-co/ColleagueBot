package ru.ustits.colleague;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import ru.ustits.colleague.commands.AdminAwareCommand;
import ru.ustits.colleague.commands.HelpCommand;
import ru.ustits.colleague.commands.stats.StatsCommand;
import ru.ustits.colleague.commands.stats.WordStatsCmd;
import ru.ustits.colleague.repositories.Repository;
import ru.ustits.colleague.repositories.records.StopWordRecord;
import ru.ustits.colleague.repositories.services.MessageService;

/**
 * @author ustits
 */
@Configuration
public class CommandConfig {

  protected static final String ADMIN_PREFIX = "a_";
  private static final String HELP_COMMAND = "help";
  private static final String STATS_COMMAND = "stats";
  private static final String WORD_STATS_CMD = "word_stats";

  @Bean
  public HelpCommand helpCommand(final ColleagueBot bot) {
    return new HelpCommand(bot, HELP_COMMAND);
  }

  @Bean
  public StatsCommand statsCommand(final MessageService messageService) {
    return new StatsCommand(STATS_COMMAND, messageService);
  }

  @Bean
  public WordStatsCmd wordStatsCommand(final MessageService messageService,
                                       final Repository<StopWordRecord> stopWordRepository) {
    return new WordStatsCmd(WORD_STATS_CMD, messageService, stopWordRepository);
  }

  protected final AdminAwareCommand admin(final BotCommand command, final Long adminId) {
    return new AdminAwareCommand(command, adminId);
  }

}
