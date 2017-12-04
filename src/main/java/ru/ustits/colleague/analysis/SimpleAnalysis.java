package ru.ustits.colleague.analysis;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.analysis.filters.EmptyFilter;
import ru.ustits.colleague.analysis.filters.TwitterFilter;
import ru.ustits.colleague.analysis.mappers.ReplaceSymbols;
import ru.ustits.colleague.analysis.mappers.ToLowerCase;
import ru.ustits.colleague.tools.ListUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author ustits
 */
@Log4j2
public final class SimpleAnalysis {

  private final List<Predicate<String>> messageFilters;
  private final List<Function<String, String>> mappers;
  private final List<Predicate<String>> wordFilters;

  public SimpleAnalysis(final List<Predicate<String>> messageFilters,
                        final List<Function<String, String>> mappers,
                        final List<Predicate<String>> wordFilters) {
    this.messageFilters = messageFilters;
    this.mappers = mappers;
    this.wordFilters = wordFilters;
  }

  public SimpleAnalysis() {
    this(asList(new EmptyFilter(), new TwitterFilter()),
            asList(new ToLowerCase(), new ReplaceSymbols()),
            singletonList(new EmptyFilter()));
  }

  public Map<String, Integer> mostCommonWords(final List<String> tokens, final List<String> stopWords) {
    final Map<String, Integer> raw = mostCommonWords(tokens);
    tokens.forEach(s -> {
      if (stopWords.contains(s)) {
        raw.remove(s);
      }
    });
    return raw;
  }

  public Map<String, Integer> mostCommonWords(final List<String> tokens) {
    log.info("Searching common words in {} tokens", tokens.size());
    List<String> raw = applyFilters(tokens, messageFilters);
    raw = applyMappers(raw);
    raw = applyFilters(raw, wordFilters);
    final Map<String, Integer> stats = ListUtils.count(raw);
    log.info("Mapped unique {} tokens", stats.size());
    return stats;
  }

  private List<String> applyFilters(final List<String> tokens, final List<Predicate<String>> filters) {
    List<String> filtered = tokens;
    for (final Predicate<String> filter : filters) {
      filtered = filtered.stream().filter(filter).collect(Collectors.toList());
    }
    log.debug("Filtered {} tokens", tokens.size() - filtered.size());
    return filtered;
  }

  private List<String> applyMappers(final List<String> tokens) {
    List<String> mapped = tokens;
    for (final Function<String, String> mapper : mappers) {
      mapped = mapped.stream().map(mapper).collect(Collectors.toList());
    }
    return mapped;
  }

}
