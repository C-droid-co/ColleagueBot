package ru.ustits.colleague.tools.triggers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
@Getter
@RequiredArgsConstructor
public enum ProcessState {

  ALL(new AllTriggers(), "all"),
  RANDOM(new RandomTrigger(), "random"),
  NOTHING(new NoTriggers(), "nothing"),
  FIRST(new FirstTrigger(), "first"),
  LAST(new LastTrigger(), "last"),
  PERIODIC(new PeriodicTrigger(), "periodic");

  private final ProcessingStrategy strategy;
  private final String name;

  public static Optional<ProcessState> toState(final String state) {
    if (state == null) {
      log.warn("State can't be null");
      return Optional.empty();
    }
    for (final ProcessState value : ProcessState.values()) {
      if (state.equals(value.getName())) {
        return Optional.of(value);
      }
    }
    log.warn("Got unknown state {}", state);
    return Optional.empty();
  }

}
