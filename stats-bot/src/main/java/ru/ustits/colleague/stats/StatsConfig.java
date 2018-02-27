package ru.ustits.colleague.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ustits.colleague.AppConfig;
import ru.ustits.colleague.repositories.StopWordRepository;
import ru.ustits.colleague.services.MessageService;
import ru.ustits.colleague.stats.commands.StatsCommand;
import ru.ustits.colleague.stats.commands.WordStatsCmd;

/**
 * @author ustits
 */
@Configuration
class StatsConfig extends AppConfig {

  private static final String STATS_COMMAND = "stats";
  private static final String WORD_STATS_CMD = "word_stats";

  @Bean
  public StatsCommand statsCommand(final MessageService messageService) {
    return new StatsCommand(STATS_COMMAND, messageService);
  }

  @Bean
  public WordStatsCmd wordStatsCommand(final MessageService messageService,
                                       final StopWordRepository stopWordRepository) {
    return new WordStatsCmd(WORD_STATS_CMD, messageService, stopWordRepository);
  }

}
