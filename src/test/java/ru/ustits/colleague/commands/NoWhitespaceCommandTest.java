package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class NoWhitespaceCommandTest {

  private NoWhitespaceCommand command;

  @Before
  public void setUp() throws Exception {
    command = new NoWhitespaceCommand(new MockCommand());
  }

  @Test
  public void testRemoveWhitespaces() throws Exception {
    final String tab = "\t";
    final String[] args = { string(), string(), SPACE, string(), LF, CR, tab };
    final String[] processedArgs = command.removeWhitespaces(args);
    assertThat(processedArgs).doesNotContainSequence(SPACE, LF, CR, tab);
  }

  @Test
  public void testRemoveWhitespacesWithNoWhitespaces() throws Exception {
    final String[] args = { string(), string(), string() };
    final String[] processedArgs = command.removeWhitespaces(args);
    assertThat(processedArgs).isEqualTo(args);
  }

  @Test
  public void testRemoveWhitespacesWithNoArgs() throws Exception {
    final String[] args = {};
    final String[] processedArgs = command.removeWhitespaces(args);
    assertThat(processedArgs).isEqualTo(args);
  }

}