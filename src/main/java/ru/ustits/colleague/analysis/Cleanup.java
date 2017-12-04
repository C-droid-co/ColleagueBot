package ru.ustits.colleague.analysis;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.analysis.filters.EmptyFilter;
import ru.ustits.colleague.analysis.filters.TwitterFilter;
import ru.ustits.colleague.analysis.mappers.ReplaceSymbols;
import ru.ustits.colleague.analysis.mappers.ToLowerCase;
import ru.ustits.colleague.analysis.mappers.WhitespaceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author ustits
 */
@Log4j2
public final class Cleanup {

  private final List<Predicate<String>> messageFilters;
  private final List<Function<String, String>> mappers;
  private final List<Predicate<String>> wordFilters;

  public Cleanup(final List<Predicate<String>> messageFilters,
                 final List<Function<String, String>> mappers,
                 final List<Predicate<String>> wordFilters) {
    this.messageFilters = messageFilters;
    this.mappers = mappers;
    this.wordFilters = wordFilters;
  }

  public Cleanup() {
    this(asList(new EmptyFilter(), new TwitterFilter()),
            asList(new ToLowerCase(), new ReplaceSymbols(), new WhitespaceMapper()),
            singletonList(new EmptyFilter()));
  }

  public List<String> clean(final List<String> tokens, final List<String> stopWords) {
    final List<String> cleaned = clean(tokens);
    final List<String> noStopWords = new ArrayList<>(cleaned);
    cleaned.forEach(s -> {
      if (stopWords.contains(s)) {
        noStopWords.remove(s);
      }
    });
    return noStopWords;
  }

  public List<String> clean(final List<String> tokens) {
    log.info("Searching common words in {} tokens", tokens.size());
    List<String> cleaned = applyFilters(tokens, messageFilters);
    cleaned = applyMappers(cleaned);
    cleaned = applyFilters(cleaned, wordFilters);
    return cleaned;
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
