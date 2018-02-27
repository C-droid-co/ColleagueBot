package ru.ustits.colleague.stats.analysis.mappers;

import java.util.function.Function;

/**
 * @author ustits
 */
public final class ReplaceSymbols implements Function<String, String> {

  @Override
  public String apply(final String s) {
    return s.replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\\\]", "");
  }

}
