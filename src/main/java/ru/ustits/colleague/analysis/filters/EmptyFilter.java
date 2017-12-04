package ru.ustits.colleague.analysis.filters;

import java.util.function.Predicate;

/**
 * @author ustits
 */
public final class EmptyFilter implements Predicate<String> {

  @Override
  public boolean test(final String s) {
    return !s.isEmpty();
  }

}
