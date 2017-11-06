package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class RepeatRepositoryTest extends RepositoryTest {

  private RepeatRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new RepeatRepository(sql);
  }

  @Test
  @Override
  public void testAdd() throws Exception {
    final RepeatRecord record = new RepeatRecord(string(), string(),
            1L, 1L);
    final RepeatRecord dbRecord = repository.add(record);
    assertThat(dbRecord.getId()).isNotNull();
  }

  @Test
  @Override
  public void testFetchAll() throws Exception {
    final List<RepeatRecord> repeats = repository.fetchAll(1L);
    assertThat(repeats).hasSize(FETCH_ALL_RESULT);
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