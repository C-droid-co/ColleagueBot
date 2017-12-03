package ru.ustits.colleague.repositories.services;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.MessageRepository;
import ru.ustits.colleague.repositories.RepositoryTest;
import ru.ustits.colleague.repositories.records.MessageRecord;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author ustits
 */
public class MessageServiceTest extends RepositoryTest {

  private MessageService service;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    service = new MessageService(sql, mock(MessageRepository.class));
  }

  @Test
  public void testCount() {
    final Map<String, Integer> result = service.count(1L, false);
    assertThat(result).containsOnlyKeys("name1", "name2", "name3").containsValues(1);
  }

  @Test
  public void testMessagesForUser() {
    final List<MessageRecord> records = service.messagesForUser(1L, 1L);
    assertThat(records).hasSize(1);
  }

}