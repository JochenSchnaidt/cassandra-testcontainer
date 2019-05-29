package me.schnaidt.persistence.impl;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import me.schnaidt.CassandraTest;
import me.schnaidt.persistence.PersonService;
import me.schnaidt.persistence.domain.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceImplTest extends CassandraTest {

  @Autowired
  private PersonService personService;

  private static Session session;

  @BeforeClass
  public static void init() {

    session = new Cluster.Builder().withoutMetrics().addContactPoints(azureContainer.getContainerIpAddress() ).withPort(azureContainer.getMappedPort(9042)).build().connect();

    session.execute("CREATE KEYSPACE myspace WITH replication = {'class':'SimpleStrategy', 'replication_factor':'1'};");
    session.execute("CREATE TABLE myspace.person (id text, name text, age int, PRIMARY KEY (id));");
  }

  @AfterClass
  public static void clean() {
    Collection<TableMetadata> tables = session.getCluster().getMetadata().getKeyspace("myspace").getTables();
    tables.forEach(table ->
        session.execute(QueryBuilder.truncate(table))
    );
    session.execute("DROP KEYSPACE myspace;");
  }

  @Test
  public void persist() {

    personService.persist("John Doe", 42);

  }

  @Test
  public void findById() {
    personService.persist("John Doe", 42);
    Optional<Person> johnDoe = personService.findById("johnDoe");
    assertTrue(johnDoe.isPresent());
  }

}