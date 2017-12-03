package ru.ustits.colleague.analysis.mappers;

import java.util.function.Function;

/**
 * @author ustits
 */
public final class ToLowerCase implements Function<String, String> {

  @Override
  public String apply(final String s) {
    return s.toLowerCase();
  }
}
