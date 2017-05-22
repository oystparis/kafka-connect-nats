package com.oyst.kafka.connect.nats.test;

import com.oyst.kafka.connect.nats.NatsSourceConnector;
import com.oyst.kafka.connect.nats.source.NatsSourceTask;
import org.apache.kafka.connect.connector.ConnectorContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NatsSourceConnectorTest {
  private NatsSourceConnector connector;
  private ConnectorContext context;

  @Before
  public void setup() {
    connector = new NatsSourceConnector();
    context = PowerMock.createMock(ConnectorContext.class);
    connector.initialize(context);
  }

  private Map<String, String> buildSourceProperties(){
    final Map<String, String> sourceProperties = new HashMap<>();
    sourceProperties.put("topic", "nats");
    sourceProperties.put("nats.subject", "POST");
    sourceProperties.put("nats.host", "nats://localhost:4222");
    return sourceProperties;
  }

  @Test
  public void testSourceTasks() {
    PowerMock.replayAll();
    connector.start(buildSourceProperties());
    List<Map<String, String>> taskConfigs = connector.taskConfigs(1);
    Assert.assertEquals(1, taskConfigs.size());
    Assert.assertEquals("nats", taskConfigs.get(0).get("topic"));
    Assert.assertEquals("POST", taskConfigs.get(0).get("nats.subject"));
    Assert.assertEquals("nats://localhost:4222", taskConfigs.get(0).get("nats.host"));
    PowerMock.verifyAll();
  }

  @Test
  public void testMultipleTasks() {
    PowerMock.replayAll();
    connector.start(buildSourceProperties());
    List<Map<String, String>> taskConfigs = connector.taskConfigs(2);
    Assert.assertEquals(2, taskConfigs.size());
    Assert.assertEquals("nats", taskConfigs.get(0).get("topic"));
    Assert.assertEquals("POST", taskConfigs.get(0).get("nats.subject"));
    Assert.assertEquals("nats://localhost:4222", taskConfigs.get(0).get("nats.host"));

    Assert.assertEquals("nats", taskConfigs.get(1).get("topic"));
    Assert.assertEquals("POST", taskConfigs.get(1).get("nats.subject"));
    Assert.assertEquals("nats://localhost:4222", taskConfigs.get(1).get("nats.host"));
    PowerMock.verifyAll();
  }

  @Test
  public void testTaskClass() {
    PowerMock.replayAll();
    connector.start(buildSourceProperties());
    Assert.assertEquals(NatsSourceTask.class, connector.taskClass());
    PowerMock.verifyAll();
  }
}
