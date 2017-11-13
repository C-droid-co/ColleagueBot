package ru.ustits.colleague.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import ru.ustits.colleague.tools.StringUtils;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public abstract class AbstractParser<T> implements Parser<T> {

  private final int parametersCount;

  @Override
  public int parametersCount() {
    return parametersCount;
  }

  protected Optional<String> parseString(final String[] args, final int position) {
    final String line;
    if (hasElement(args, position)) {
      line =  args[position];
      log.debug("Parsed string: {}", line);
    } else {
      line = null;
    }
    return ofNullable(line);
  }

  protected Optional<String> parseString(final String[] args, final int start, final int stop) {
    final String line = StringUtils.asString(args, start, stop);
    log.debug("Parsed string: {}", line);
    return ofNullable(line);
  }

  protected Optional<Long> parseLong(final String[] args, final int position) {
    final Long number;
    if (hasElement(args, position)) {
      number = NumberUtils.isParsable(args[position]) ? Long.parseLong(args[position]) : null;
      log.debug("Parsed long: {}", number);
    } else {
      number = null;
    }
    return ofNullable(number);
  }

  boolean hasElement(final String[] args, final int position) {
    return args.length > position;
  }
}
