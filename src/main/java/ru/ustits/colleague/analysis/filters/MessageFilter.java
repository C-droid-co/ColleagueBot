package ru.ustits.colleague.analysis.filters;

import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public class MessageFilter implements Predicate<String> {

  private final String message;

  @Override
  public boolean test(final String s) {
    return !s.contains(message);
  }

}
