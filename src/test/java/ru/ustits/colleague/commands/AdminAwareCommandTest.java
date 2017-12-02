package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;

/**
 * @author ustits
 */
public class AdminAwareCommandTest {

  private AdminAwareCommand command;
  private Long adminId;

  @Before
  public void setUp() throws Exception {
    adminId = aLong();
    command = new AdminAwareCommand(new MockCommand(), adminId);
  }

  @Test
  public void testIsAdmin() throws Exception {
    assertThat(command.isAdmin(adminId)).isTrue();
  }

  @Test
  public void testIsNotAdmin() throws Exception {
    assertThat(command.isAdmin(aLong())).isFalse();
  }

  @Test
  public void testIsNotAdminWithNullPassed() throws Exception {
    assertThat(command.isAdmin(null)).isFalse();
  }

}