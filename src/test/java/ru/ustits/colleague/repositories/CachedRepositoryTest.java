package ru.ustits.colleague.repositories;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.MockRecord;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author ustits
 */
public class CachedRepositoryTest {

  private CachedRepository<MockRecord> cachedRepository;
  private MockRepository innerRepository;

  @Before
  public void setUp() throws Exception {
    innerRepository = spy(new MockRepository());
  }

  @Test
  public void testAdd() {
    cachedRepository = noCache();
    final MockRecord record = new MockRecord();
    final MockRecord dbRecord = cachedRepository.add(record);
    assertThat(dbRecord).isEqualTo(record);
    assertThat(cachedRepository.getEntities()).isNull();
    verify(innerRepository).add(any());
  }

  @Test
  public void testAddWithExistingCache() {
    cachedRepository = withCache();
    final MockRecord record = new MockRecord();
    final MockRecord dbRecord = cachedRepository.add(record);
    assertThat(dbRecord).isEqualTo(record);
    assertThat(cachedRepository.getEntities()).isNotNull().contains(dbRecord);
    verify(innerRepository).add(any());
  }

  @Test
  public void testExists() {
    cachedRepository = noCache();
    assertThat(cachedRepository.exists(new MockRecord())).isTrue();
    verify(innerRepository).exists(any());
  }

  @Test
  public void testExistsWithExistingCache() {
    cachedRepository = withCache();
    assertThat(cachedRepository.exists(new MockRecord())).isFalse();
    verify(innerRepository, never()).exists(any());
  }

  @Test
  public void testFetchOne() {
    cachedRepository = noCache();
    assertThat(cachedRepository.fetchOne(new MockRecord())).isNotNull();
    assertThat(cachedRepository.getEntities()).isNull();
    verify(innerRepository).fetchOne(any());
  }

  @Test
  public void testFetchOneWithExistingCache() {
    cachedRepository = withCache();
    final MockRecord record = new MockRecord();
    assertThat(cachedRepository.fetchOne(record)).isNotNull();
    assertThat(cachedRepository.getEntities()).contains(record);
    verify(innerRepository).fetchOne(any());
  }

  @Test
  public void testUpdate() {
    cachedRepository = noCache();
    assertThat(cachedRepository.update(new MockRecord())).isPositive();
    verify(innerRepository).update(any());
  }

  @Test
  public void testDelete() {
    cachedRepository = noCache();
    cachedRepository.delete(new MockRecord());
    assertThat(cachedRepository.getEntities()).isNull();
    verify(innerRepository).delete(any());
  }

  @Test
  public void testDeleteWithExistingCache() {
    final MockRecord record = new MockRecord();
    cachedRepository = withCache(record);
    assertThat(cachedRepository.getEntities()).contains(record);
    cachedRepository.delete(record);
    assertThat(cachedRepository.getEntities()).doesNotContain(record);
    verify(innerRepository).delete(any());
  }

  @Test
  public void testFetchAll() {
    cachedRepository = noCache();
    assertThat(cachedRepository.getEntities()).isNull();
    assertThat(cachedRepository.fetchAll()).isNotNull();
    assertThat(cachedRepository.getEntities()).isNotNull();
    verify(innerRepository).fetchAll();
  }

  @Test
  public void testFetchAllWithExistingCache() {
    cachedRepository = withCacheAndTime(Integer.MAX_VALUE);
    assertThat(cachedRepository.getEntities()).isNotNull();
    assertThat(cachedRepository.fetchAll()).isNotNull();
    verify(innerRepository, never()).fetchAll();
  }

  @Test
  public void testIsTimeToUpdate() {
    cachedRepository = withCacheAndTime(0);
    final long was = new Date().getTime();
    final long become = new Date().getTime() + 1000;
    assertThat(cachedRepository.isTimeToUpdate(was, become)).isTrue();
  }

  @Test
  public void testIsTimeToUpdateBecauseWasNotPreviouslyInitialized() {
    cachedRepository = noCache();
    final long was = new Date().getTime() + 1000;
    final long become = new Date().getTime();
    assertThat(cachedRepository.isTimeToUpdate(was, become)).isTrue();
  }

  @Test
  public void testIsNotTimeToUpdate() {
    cachedRepository = withCacheAndTime(0);
    final long was = new Date().getTime() + 1000;
    final long become = new Date().getTime();
    assertThat(cachedRepository.isTimeToUpdate(was, become)).isFalse();
  }

  private CachedRepository<MockRecord> noCache() {
    return new CachedRepository<>(innerRepository);
  }

  private CachedRepository<MockRecord> withCache() {
    return new CachedRepository<>(innerRepository, new ArrayList<>());
  }

  private CachedRepository<MockRecord> withCache(final MockRecord... records) {
    return new CachedRepository<>(innerRepository, new ArrayList<>(asList(records)));
  }

  private CachedRepository<MockRecord> withCacheAndTime(final long updateDelay) {
    return new CachedRepository<>(innerRepository, new ArrayList<>(), updateDelay, new Date().getTime());
  }

}