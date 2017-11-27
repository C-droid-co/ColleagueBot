package ru.ustits.colleague.tools.triggers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ustits
 */
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

}
