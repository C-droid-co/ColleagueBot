package ru.ustits.colleague.commands.stats;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.analysis.SimpleAnalysis;
import ru.ustits.colleague.analysis.SimpleTokenizer;
import ru.ustits.colleague.commands.StatsCommand;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.services.MessageService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class WordStatsCmd extends StatsCommand {

  private final SimpleTokenizer tokenizer = new SimpleTokenizer();
  private final SimpleAnalysis analysis = new SimpleAnalysis();

  public WordStatsCmd(final String commandIdentifier, final MessageService service) {
    super(commandIdentifier, service);
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
    final Map<String, Integer> stats = analysis.mostCommonWords(tokens);

    sendStats(stats, chatId, absSender);
  }

}
