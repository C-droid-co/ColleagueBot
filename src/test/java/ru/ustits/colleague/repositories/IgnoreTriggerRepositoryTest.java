package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.IgnoreTriggerRecord;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class IgnoreTriggerRepositoryTest extends RepositoryTest {

  private IgnoreTriggerRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new IgnoreTriggerRepository(sql);
  }

  @Test
  public void testAdd() {
    final IgnoreTriggerRecord record = new IgnoreTriggerRecord(1L, 1L);
    final IgnoreTriggerRecord dbRecord = repository.add(record);
    assertThat(record.getId()).isNull();
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  public void testExist() {
    final IgnoreTriggerRecord record = existingRecord();
    final boolean result = repository.exists(record);
    assertThat(result).isTrue();
  }

  @Test
  public void testFetchOne() {
    final IgnoreTriggerRecord record = existingRecord();
    final IgnoreTriggerRecord dbRecord = repository.fetchOne(record);
    assertThat(record.getId()).isNull();
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  public void testDelete() {
    final IgnoreTriggerRecord record = existingRecord();
    assertThat(repository.exists(record)).isTrue();
    repository.delete(record);
    assertThat(repository.exists(record)).isFalse();
  }

  private IgnoreTriggerRecord existingRecord() {
    return new IgnoreTriggerRecord(3L, 3L);
  }

}