package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class UserRepositoryTest extends RepositoryTest {

  private UserRepository repository;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    repository = new UserRepository(sql);
  }

  @Test
  public void testAdd() throws Exception {
    final UserRecord user = new UserRecord(aLong(), string(), string(), string());
    final UserRecord dbRecord = repository.add(user);
    assertThat(dbRecord).isEqualTo(user);
  }

  @Test
  public void testFetchOne() throws Exception {
    final UserRecord record = new UserRecord(1L);
    final UserRecord dbRecord = repository.fetchOne(record);
    assertThat(dbRecord).isNotNull();
  }

  @Test
  public void testExists() throws Exception {
    final UserRecord record = new UserRecord(1L);
    assertThat(repository.exists(record)).isTrue();
  }

  @Test
  public void testFetchAll() throws Exception {
    final List<UserRecord> users = repository.fetchAll();
    assertThat(users).hasSize(FETCH_ALL_RESULT);
  }

}