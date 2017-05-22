package com.oyst.kafka.connect.nats.source;

import com.oyst.kafka.connect.nats.NatsSourceConnector;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class NatsSourceConnectorConfig extends AbstractConfig {
  private static final Logger LOG = LoggerFactory.getLogger(NatsSourceConnector.class);
 /**
   * Create default mConfig.
   * @return default mConfig
   */
  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
            .define(NatsSourceConnectorConstants.KAFKA_TOPIC, Type.STRING, "nats",
                    Importance.LOW, NatsSourceConnectorConstants.KAFKA_TOPIC_DOC)
            .define(NatsSourceConnectorConstants.NATS_SUBJECT, Type.STRING, null,
                    Importance.MEDIUM, NatsSourceConnectorConstants.NATS_SUBJECT_DOC)
            .define(NatsSourceConnectorConstants.NATS_HOST, Type.STRING, "nats://localhost:4222",
                    Importance.HIGH, NatsSourceConnectorConstants.NATS_HOST_DOC);
  }

  public static final ConfigDef config = baseConfigDef();

  /**
   * Transform process properties.
   *
   * @param properties associative array with properties to be process
   */
  public NatsSourceConnectorConfig(Map<String, String> properties) {
    super(config, properties);
    LOG.info("Initialize transform process properties");
  }

  public static void main(String[] args) {
    System.out.println(config.toRst());
  }
}
