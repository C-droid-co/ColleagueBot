package ru.ustits.colleague.repositories.services;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.RepositoryTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class MessageServiceTest extends RepositoryTest {

  private MessageService service;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    service = new MessageService(sql);
  }

  @Test
  public void testCount() throws Exception {
    final Map<String, Long> result = service.count(1L, false);
    assertThat(result).containsOnlyKeys("name1", "name2", "name3").containsValues(1L);
  }
}