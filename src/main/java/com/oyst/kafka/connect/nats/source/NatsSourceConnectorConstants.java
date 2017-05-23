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

public class NatsSourceConnectorConstants {
  // Connector Constants
  public static final String KAFKA_TOPIC = "topic";
  public static final String NATS_SUBJECT = "nats.subject";
  public static final String NATS_HOST = "nats.host";
  public static final String NATS_QUEUE_GROUP = "nats.queue.group";
  // Connector Constants Docs
  public static final String KAFKA_TOPIC_DOC = "Kafka topic to put received data";
  public static final String NATS_SUBJECT_DOC = "The NATS SUBJECT";
  public static final String NATS_HOST_DOC = "THE NATS HOST";
  public static final String NATS_QUEUE_GROUP_DOC = "The NATS Queue Group";
}
