package ru.ustits.colleague;

import java.util.Arrays;

import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomValue.length;

/**
 * @author ustits
 */
public final class RandomUtils {

  private static final int RANDOM_BOUND = 10;

  private RandomUtils() {
  }

  public static String[] values() {
    return length(RANDOM_BOUND).alphanumerics().toArray(new String[]{});
  }

  public static String[] values(final int bound) {
    final String[] values = values();
    return Arrays.copyOf(values, bound);
  }

  public static String string() {
    return alphanumeric(RANDOM_BOUND);
  }

  public static Long aLong() {
    return positiveLong();
  }

  public static Integer anInt() {
    return positiveInteger();
  }

  public static Integer anInt(final int bound) {
    return positiveInteger() % bound;
  }
}
