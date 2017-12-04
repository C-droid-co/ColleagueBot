package ru.ustits.colleague.tools;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.anInt;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class MapUtilsTest {

  private final int TEST_STATS_LEN = 3;

  @Test
  public void testLimit() {
    final Map<String, Integer> data = new HashMap<>();
    final int initialSize = TEST_STATS_LEN * 2;
    for (int i = 0; i < initialSize; i++) {
      data.put(string(), anInt());
    }
    assertThat(data).hasSize(initialSize);
    assertThat(MapUtils.limit(data, TEST_STATS_LEN)).hasSize(TEST_STATS_LEN);
  }

  @Test
  public void testLimitWithSmallMap() {
    final Map<String, Integer> data = singletonMap(string(), anInt());
    assertThat(MapUtils.limit(data, TEST_STATS_LEN)).hasSize(data.size());
  }

  @Test
  public void testLimitWithEmptyMap() {
    assertThat(MapUtils.limit(emptyMap(), TEST_STATS_LEN)).isEmpty();
  }

}