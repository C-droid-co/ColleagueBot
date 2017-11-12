package ru.ustits.colleague.commands.triggers;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.tools.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.valuesMoreThan;

/**
 * @author ustits
 */
public class TriggerParserTest {

  private int parametersCount;
  private TriggerParser strategy;
  private String[] args;

  @Before
  public void setUp() throws Exception {
    parametersCount = 3;
    strategy = new MockParser(parametersCount);
    args = valuesMoreThan(parametersCount);
  }

  @Test
  public void testParseMessage() throws Exception {
    final String result = strategy.parseMessage(args);
    assertThat(result).isEqualTo(StringUtils.asString(args, parametersCount - 1));
  }

  @Test
  public void testParseTrigger() throws Exception {
    final String result = strategy.parseTrigger(args);
    assertThat(result).isEqualTo(args[0].toLowerCase());
  }
}