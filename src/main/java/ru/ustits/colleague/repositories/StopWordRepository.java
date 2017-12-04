package ru.ustits.colleague.repositories;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.QueryRunner;
import ru.ustits.colleague.repositories.records.StopWordRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public class StopWordRepository extends AbstractRepository<StopWordRecord> {

  public StopWordRepository(final QueryRunner sql) {
    super(sql);
  }

  @Override
  protected StopWordRecord innerAdd(final StopWordRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected StopWordRecord innerFetchOne(final StopWordRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected int innerUpdate(final StopWordRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void innerDelete(final StopWordRecord entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<StopWordRecord> fetchAll() {
    try {
      return sql().query("SELECT * FROM stopwords",
              this::fetchAllRecords);
    } catch (SQLException e) {
      log.error("Unable to fetch stop words", e);
    }
    return Collections.emptyList();
  }

  @Override
  public StopWordRecord toRecord(final ResultSet resultSet) throws SQLException {
    final Integer id = resultSet.getInt(1);
    final String word = resultSet.getString(2);
    return new StopWordRecord(id, word);
  }

}
