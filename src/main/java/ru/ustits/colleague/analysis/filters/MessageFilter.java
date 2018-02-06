package ru.ustits.colleague.analysis.filters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public final class MessageFilter implements Predicate<String> {

  private final String message;

  public static MessageFilter newInstance(final MessageFilters filter) {
    return new MessageFilter(filter.getText());
  }

  @Override
  public boolean test(final String s) {
    return !s.contains(message);
  }

  @Getter
  @RequiredArgsConstructor
  public enum MessageFilters {

    TWITTER("Посмотрите, о чем твитнул");

    private final String text;

  }

}
