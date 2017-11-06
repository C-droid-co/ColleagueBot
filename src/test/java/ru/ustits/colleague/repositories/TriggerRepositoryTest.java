package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class TriggerRepositoryTest extends RepositoryTest {

  private TriggerRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new TriggerRepository(sql);
  }

  @Test
  @Override
  public void testAdd() throws Exception {
    final TriggerRecord record = new TriggerRecord(string(), string(), 1L, 1L);
    final TriggerRecord dbRecord = repository.add(record);
    assertThat(record.getId()).isNull();
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  @Override
  public void testFetchOne() throws Exception {
    final TriggerRecord record = existingRecord();
    final TriggerRecord dbRecord = repository.fetchOne(record);
    assertThat(record.getId()).isNull();
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  @Override
  public void testExists() throws Exception {
    final TriggerRecord record = existingRecord();
    assertThat(repository.exists(record)).isTrue();
  }

  @Test
  @Override
  public void testFetchAll() throws Exception {
    final List<TriggerRecord> records = repository.fetchAll(1L);
    assertThat(records).hasSize(FETCH_ALL_RESULT);
  }

  @Test
  @Override
  public void testUpdate() throws Exception {
    final String newMessage = string();
    final TriggerRecord record = new TriggerRecord("trigger2", newMessage, 1L, 1L);
    final int updateCount = repository.update(record);
    assertThat(updateCount).isEqualTo(1);
    final TriggerRecord updatedRecord = repository.fetchOne(record);
    assertThat(updatedRecord.getMessage()).isEqualTo(newMessage);
  }

  @Test
  @Override
  public void testDelete() throws Exception {
    final TriggerRecord record = existingRecord();
    assertThat(repository.exists(record)).isTrue();
    repository.delete(record);
    assertThat(repository.exists(record)).isFalse();
  }

  private TriggerRecord existingRecord() {
    return new TriggerRecord("trigger1", 1L, 1L);
  }
}