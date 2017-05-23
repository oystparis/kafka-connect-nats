/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oyst.kafka.connect.nats.source;

import com.oyst.kafka.connect.nats.NatsSourceConnector;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.KAFKA_TOPIC;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_QUEUE_GROUP;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_URL;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_SUBJECT;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.KAFKA_TOPIC_DOC;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_QUEUE_GROUP_DOC;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_SUBJECT_DOC;
import static com.oyst.kafka.connect.nats.source.NatsSourceConnectorConstants.NATS_URL_DOC;

public class NatsSourceConnectorConfig extends AbstractConfig {
  private static final Logger LOG = LoggerFactory.getLogger(NatsSourceConnector.class);
 /**
   * Create default mConfig.
   * @return default mConfig
   */
  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
            .define(KAFKA_TOPIC, Type.STRING, "nats",
                    Importance.LOW, KAFKA_TOPIC_DOC)
            .define(NATS_SUBJECT, Type.STRING, "kafka",
                    Importance.MEDIUM, NATS_SUBJECT_DOC)
            .define(NATS_QUEUE_GROUP, Type.STRING, "nats-kafka",
                    Importance.LOW, NATS_QUEUE_GROUP_DOC)
            .define(NATS_URL, Type.STRING, "nats://localhost:4222",
                    Importance.HIGH, NATS_URL_DOC);
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
