package ru.ustits.colleague.repositories;

import ru.ustits.colleague.repositories.records.MockRecord;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author ustits
 */
public class MockRepository implements Repository<MockRecord> {

  @Override
  public MockRecord add(final MockRecord entity) {
    return entity;
  }

  @Override
  public boolean exists(final MockRecord entity) {
    return true;
  }

  @Override
  public MockRecord fetchOne(final MockRecord entity) {
    return entity;
  }

  @Override
  public int update(final MockRecord entity) {
    return 1;
  }

  @Override
  public void delete(final MockRecord entity) {
  }

  @Override
  public List<MockRecord> fetchAll() {
    return asList(new MockRecord(), new MockRecord(), new MockRecord());
  }
}
