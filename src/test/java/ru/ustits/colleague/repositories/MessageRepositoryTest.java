package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.MessageRecord;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class MessageRepositoryTest extends RepositoryTest {

  private MessageRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new MessageRepository(sql);
  }

  @Test
  @Override
  public void testAdd() throws Exception {
    final MessageRecord record = new MessageRecord(null, aLong(),
            new Timestamp(System.currentTimeMillis()), string(),
            false, 1L, 1L);
    final MessageRecord dbRecord = repository.add(record);
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  @Override
  public void testFetchAll() throws Exception {
    final List<MessageRecord> messages = repository.fetchAll(1L);
    assertThat(messages).hasSize(3);
  }

  @Test
  @Ignore
  @Override
  public void testFetchOne() throws Exception {

  }

  @Test
  @Ignore
  @Override
  public void testExists() throws Exception {

  }

  @Test
  @Ignore
  @Override
  public void testUpdate() throws Exception {

  }

  @Test
  @Ignore
  @Override
  public void testDelete() throws Exception {

  }
}