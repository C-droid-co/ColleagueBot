package ru.ustits.colleague.tools;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ustits
 */
public final class MapUtils {

  private MapUtils() {
  }

  public static <K, V> Map<K, V> limit(final Map<K, V> map, final int size) {
    final Map<K, V> limited = new LinkedHashMap<>();
    int counter = 0;
    for (final Map.Entry<K, V> entry : map.entrySet()) {
      limited.put(entry.getKey(), entry.getValue());
      counter++;
      if (counter >= size) {
        break;
      }
    }
    return limited;
  }

  public static <K, V> Map<K, V> sortByValue(final Map<K, V> map) {
    return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
  }

}
