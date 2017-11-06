package ru.ustits.colleague.repositories;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Rule;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public abstract class RepositoryTest {

  private static final String DB_SCRIPT = "db.sql";
  private static final String BUILD_SCRIPT = System.getProperty("user.dir") + "/db/" + DB_SCRIPT;
  private static final String TEST_ENV = "test-env.sql";
  private static final String INIT_DIR = "/docker-entrypoint-initdb.d/";
  private static final int PG_PORT = 5432;

  private String dbName = string();

  @Rule
  public PostgreSQLContainer container = (PostgreSQLContainer)
          new PostgreSQLContainer("postgres:9")
                  .withDatabaseName(dbName)
                  .withFileSystemBind(BUILD_SCRIPT,
                          INIT_DIR + DB_SCRIPT, BindMode.READ_ONLY)
                  .withClasspathResourceMapping(TEST_ENV,
                          INIT_DIR + TEST_ENV, BindMode.READ_ONLY);

  protected QueryRunner sql;

  @Before
  public void setUp() throws Exception {
    sql = new QueryRunner(dataSource());
  }

  public abstract void testAdd() throws Exception;

  public abstract void testFetchOne() throws Exception;

  public abstract void testExists() throws Exception;

  public abstract void testFetchAll() throws Exception;

  public abstract void testUpdate() throws Exception;

  public abstract void testDelete() throws Exception;

  private DataSource dataSource() {
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName(container.getContainerIpAddress());
    dataSource.setDatabaseName(dbName);
    dataSource.setUser(container.getUsername());
    dataSource.setPassword(container.getPassword());
    dataSource.setPortNumber(container.getMappedPort(PG_PORT));
    return dataSource;
  }

}
