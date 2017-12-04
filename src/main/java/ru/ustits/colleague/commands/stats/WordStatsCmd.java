package ru.ustits.colleague.commands.stats;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.analysis.SimpleAnalysis;
import ru.ustits.colleague.analysis.SimpleTokenizer;
import ru.ustits.colleague.repositories.Repository;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.records.StopWordRecord;
import ru.ustits.colleague.repositories.services.MessageService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.toUnsignedLong;
import static ru.ustits.colleague.tools.MapUtils.limit;
import static ru.ustits.colleague.tools.MapUtils.sortByValue;

/**
 * @author ustits
 */
@Log4j2
public final class WordStatsCmd extends StatsCommand {

  private static final int DEFAULT_STATS_LEN = 10;

  private final SimpleTokenizer tokenizer = new SimpleTokenizer();
  private final SimpleAnalysis analysis = new SimpleAnalysis();
  private final Repository<StopWordRecord> stopWordRepository;
  private final int statsLength;

  public WordStatsCmd(final String commandIdentifier, final MessageService service,
                      final Repository<StopWordRecord> stopWordRepository) {
    this(commandIdentifier, service, stopWordRepository, DEFAULT_STATS_LEN);
  }

  public WordStatsCmd(final String commandIdentifier, final MessageService service,
                      final Repository<StopWordRecord> stopWordRepository, final int statsLength) {
    super(commandIdentifier, service);
    this.stopWordRepository = stopWordRepository;
    this.statsLength = statsLength;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final Long userId = toUnsignedLong(user.getId());
    final Long chatId = chat.getId();
    final List<MessageRecord> messages = getService().messagesForUser(userId, chatId);
    final List<String> tokens = tokenizer.tokenize(messages.stream()
            .map(MessageRecord::getText)
            .collect(Collectors.toList()));

    final List<StopWordRecord> stopWordRecords = stopWordRepository.fetchAll();
    final Map<String, Integer> stats;
    if (stopWordRecords == null) {
      stats = analysis.mostCommonWords(tokens);
    } else {
      final List<String> stopWords = stopWordRecords.stream().map(StopWordRecord::getWord)
              .collect(Collectors.toList());
      stats = analysis.mostCommonWords(tokens, stopWords);
    }
    sendStats(limit(sortByValue(stats), statsLength), chatId, absSender);
  }

}
