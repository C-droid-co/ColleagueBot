package ru.ustits.colleague.tools;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class StringUtilsTest {

  private String[] stringArray;
  private String line;

  @Before
  public void setUp() throws Exception {
    stringArray = new String[]{"some", "very", "long", "text"};
    line = "some very long text";
  }

  @Test
  public void testSplit() throws Exception {
    assertThat(StringUtils.split(line)).containsExactly(stringArray);
  }

  @Test
  public void testSplitEmpty() throws Exception {
    assertThat(StringUtils.split("")).isEmpty();
  }

  @Test
  public void testSplitNull() throws Exception {
    assertThat(StringUtils.split("")).isEmpty();
  }

  @Test
  public void testAsString() throws Exception {
    assertThat(StringUtils.asString(stringArray)).isEqualTo(line);
  }

  @Test
  public void testAsStringWithEmptyArray() throws Exception {
    assertThat(StringUtils.asString(new String[]{})).isEmpty();
  }

  @Test
  public void testAsStringWithNull() throws Exception {
    assertThat(StringUtils.asString(null)).isEmpty();
  }

  @Test
  public void testAsStringWithStart() throws Exception {
    final int start = 1;
    assertThat(StringUtils.asString(stringArray, start)).isEqualTo("very long text");
  }

  @Test
  public void testAsStringWithStartAndEnd() throws Exception {
    final int start = 1;
    final int end = 3;
    assertThat(StringUtils.asString(stringArray, start, end)).isEqualTo("very long");
  }
}