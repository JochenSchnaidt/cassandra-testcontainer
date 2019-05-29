package me.schnaidt;

import org.junit.ClassRule;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;


@ContextConfiguration(initializers = CassandraTest.Initializer.class)
public class CassandraTest {

  @ClassRule
  public static GenericContainer azureContainer = new GenericContainer("cassandra:2.2.8").withExposedPorts(9042);

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configContext) {

      System.setProperty("cassandra.contactpoints", azureContainer.getContainerIpAddress());
      System.setProperty("cassandra.port", azureContainer.getMappedPort(9042).toString());

//      TestPropertyValues.of(
//          "cassandra.contactpoints=" + azureContainer.getContainerIpAddress() + ";" +
//          "cassandra.port=" + azureContainer.getMappedPort(9042) + ";"
//      ).applyTo(configContext);
    }

  }

}
