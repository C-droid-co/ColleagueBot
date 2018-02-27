package ru.ustits.colleague.stats.analysis.mappers;

import org.junit.Test;
import ru.ustits.colleague.tools.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class WhitespaceMapperTest {

  @Test
  public void testApply() {
    final WhitespaceMapper mapper = new WhitespaceMapper();
    final String message = string();
    final String result = mapper.apply(StringUtils.CR + StringUtils.TAB +  message + StringUtils.LF);
    assertThat(result).isEqualTo(message);
  }

}