package com.oyst.kafka.connect.nats;

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
    LOG.info("Base ConfigDef");
    return new ConfigDef()
            .define("topic", Type.STRING, "nats", Importance.LOW,
                    "Kafka topic to put received data \n Depends on message processor")
            .define("nats.subject", Type.STRING, null, Importance.MEDIUM,
                    "The NATS SUBJECT")
            .define("nats.host", Type.STRING, "nats://localhost:4222", Importance.HIGH,
                    "THE NATS HOST");
  }

  static ConfigDef config = baseConfigDef();

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
