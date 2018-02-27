package ru.ustits.colleague.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ustits
 */
public final class ListUtils {

  private ListUtils() {
  }

  public static <V> Map<V, Integer> count(final List<V> values) {
    final Map<V, Integer> count = new HashMap<>();
    for (final V value : values) {
      final int counter;
      if (count.containsKey(value)) {
        counter = count.get(value) + 1;
      } else {
        counter = 1;
      }
      count.put(value, counter);
    }
    return count;
  }

}
