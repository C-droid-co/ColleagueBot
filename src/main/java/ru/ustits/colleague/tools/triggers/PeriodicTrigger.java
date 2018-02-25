package ru.ustits.colleague.tools.triggers;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author ustits
 */
public final class PeriodicTrigger implements ProcessingStrategy {

  private static final int RANDOM_BOUND = 100;
  private static final int DEFAULT_FAIL_CHANCE = 66;

  private final RandomTrigger randomStrategy = new RandomTrigger();
  private final int failChance;

  public PeriodicTrigger() {
    this(DEFAULT_FAIL_CHANCE);
  }

  public PeriodicTrigger(final int failChance) {
    this.failChance = failChance;
  }

  @Override
  public List<String> process(final List<String> messages) {
    final Random random = new Random();
    final int result = random.nextInt(RANDOM_BOUND);
    if (result >= failChance) {
      return randomStrategy.process(messages);
    } else {
      return Collections.emptyList();
    }
  }

}
