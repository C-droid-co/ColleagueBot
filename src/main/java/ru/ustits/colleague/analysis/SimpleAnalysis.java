package ru.ustits.colleague.analysis;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.analysis.filters.EmptyFilter;
import ru.ustits.colleague.analysis.filters.TwitterFilter;
import ru.ustits.colleague.analysis.mappers.ReplaceSymbols;
import ru.ustits.colleague.analysis.mappers.ToLowerCase;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author ustits
 */
@Log4j2
public final class SimpleAnalysis {

  private static final int DEFAULT_STATS_LEN = 10;

  private final List<Predicate<String>> filters;
  private final List<Function<String, String>> mappers;
  private final int statsLength;

  public SimpleAnalysis(final List<Predicate<String>> filters,
                        final List<Function<String, String>> mappers,
                        final int statsLength) {
    this.filters = filters;
    this.mappers = mappers;
    this.statsLength = statsLength;
  }

  public SimpleAnalysis(final int statsLength) {
    this(asList(new EmptyFilter(), new TwitterFilter()),
            asList(new ToLowerCase(), new ReplaceSymbols()),
            statsLength);
  }

  public SimpleAnalysis() {
    this(asList(new EmptyFilter(), new TwitterFilter()),
            asList(new ToLowerCase(), new ReplaceSymbols()),
            DEFAULT_STATS_LEN);
  }

  public Map<String, Integer> mostCommonWords(final List<String> tokens) {
    log.info("Searching common words in {} tokens", tokens.size());
    List<String> raw = applyFilters(tokens);
    raw = applyMappers(raw);
    final Map<String, Integer> stats = count(raw);
    log.info("Mapped unique {} tokens", stats.size());
    return limit(sortByValue(stats), statsLength);
  }

  private List<String> applyFilters(final List<String> tokens) {
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

  protected Map<String, Integer> count(final List<String> tokens) {
    final Map<String, Integer> count = new HashMap<>();
    for (final String text : tokens) {
      final int value;
      if (count.containsKey(text)) {
        value = count.get(text) + 1;
      } else {
        value = 1;
      }
      count.put(text, value);
    }
    return count;
  }

  protected Map<String, Integer> limit(final Map<String, Integer> map, final int size) {
    final Map<String, Integer> limited = new LinkedHashMap<>();
    int counter = 0;
    for (final Map.Entry<String, Integer> entry : map.entrySet()) {
      limited.put(entry.getKey(), entry.getValue());
      counter++;
      if (counter >= size) {
        break;
      }
    }
    return limited;
  }

  private Map<String, Integer> sortByValue(final Map<String, Integer> map) {
    return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
  }

}
