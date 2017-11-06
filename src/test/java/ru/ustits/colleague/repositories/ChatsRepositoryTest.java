package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.ChatRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ChatsRepositoryTest extends RepositoryTest {

  private ChatsRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new ChatsRepository(sql);
  }

  @Test
  public void testAdd() throws Exception {
    final ChatRecord record = new ChatRecord(aLong(), string());
    final ChatRecord dbRecord = repository.add(record);
    assertThat(dbRecord).isEqualTo(record);
  }

  @Test
  public void testFetchOne() throws Exception {
    final ChatRecord record = new ChatRecord(1L);
    final ChatRecord dbRecord = repository.fetchOne(record);
    assertThat(dbRecord.getTitle()).isNotNull();
  }

  @Test
  public void testFetchAll() throws Exception {
    final List<ChatRecord> records = repository.fetchAll();
    assertThat(records).hasSize(FETCH_ALL_RESULT);
  }

}