package com.oyst.kafka.connect.nats;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.common.utils.AppInfoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NatsSourceConnector extends SourceConnector {
  private static final Logger LOG = LoggerFactory.getLogger(NatsSourceConnector.class);
  private Map<String, String> mConfigProperties;

  /**
   * Start the NATS connector
   * @param props Connector's properties
   */
  @Override
  public void start(Map<String, String> props) {
    LOG.info("Start the NATS Source Connector with the next properties : {}", props);
    this.mConfigProperties = props;

  }

  @Override
  public void stop() {
    LOG.info("Stop the Nats Source Connector");
  }

  @Override
  public String version() {
    return AppInfoParser.getVersion();
  }

  @Override
  public Class<? extends Task> taskClass() {
    return NatsSourceTask.class;
  }

  @Override
  public ConfigDef config() {
    return NatsSourceConnectorConfig.config;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int capacity) {
    List<Map<String, String>> taskConfigs = new ArrayList<>(capacity);
    Map<String, String> taskProps = new HashMap<>(mConfigProperties);
    for (int i = 0; i < capacity; i++){
      taskConfigs.add(taskProps);
    }
    return taskConfigs;
  }
}
