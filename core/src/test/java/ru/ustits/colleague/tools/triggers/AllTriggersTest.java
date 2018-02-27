package ru.ustits.colleague.tools.triggers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class AllTriggersTest {

  @Test
  public void testProcess() {
    final AllTriggers strategy = new AllTriggers();
    final List<String> original = new ArrayList<>();
    final List<String> processed = strategy.process(original);
    assertThat(processed).isEqualTo(original);
  }

}