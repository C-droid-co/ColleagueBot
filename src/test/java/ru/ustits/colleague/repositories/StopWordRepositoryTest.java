package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.StopWordRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class StopWordRepositoryTest extends RepositoryTest {

  private StopWordRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new StopWordRepository(sql);
  }

  @Test
  public void testFetchAll() {
    final List<StopWordRecord> stopWords = repository.fetchAll();
    assertThat(stopWords).hasSize(3);
  }

}