package ru.ustits.colleague.analysis.filters;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author ustits
 */
public final class NoUrlFilter implements Predicate<String> {

  private static final Pattern URL_PATTERN = Pattern.compile("http\\S+|www.\\S+", Pattern.CASE_INSENSITIVE);

  @Override
  public boolean test(final String s) {
    return !URL_PATTERN.asPredicate().test(s);
  }

}
