package me.schnaidt;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;


@ContextConfiguration(initializers = CassandraTest.Initializer.class)
public class CassandraTest {

  @ClassRule
  public static GenericContainer cassandraContainer = new GenericContainer("cassandra:2.2.8").withExposedPorts(9042);

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configContext) {

      TestPropertyValues.of(
          "cassandra.contactpoints=" + cassandraContainer.getContainerIpAddress(),
          "cassandra.port=" + cassandraContainer.getMappedPort(9042)
      ).applyTo(configContext);
    }

  }

}
