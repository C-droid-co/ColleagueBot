package ru.ustits.colleague.tools;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

/**
 * @author ustits
 */
@Log4j2
public final class IntParser {

  public Optional<Integer> parse(final String arg) {
    if (arg == null || arg.isEmpty()) {
      log.debug("Unable to parse empty arg");
      return empty();
    }
    if (isParsable(arg)) {
      final Integer number = parseInt(arg);
      if (number == null) {
        return empty();
      } else {
        log.debug("Parsed number: {}", number);
        return of(number);
      }
    } else {
      log.debug("{} not a number", arg);
      return empty();
    }
  }

  private Integer parseInt(final String arg) {
    try {
      return Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      log.error("Unable to parse number " + arg, e);
      return null;
    }
  }

}
