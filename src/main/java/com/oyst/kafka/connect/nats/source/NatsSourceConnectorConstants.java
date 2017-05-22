package com.oyst.kafka.connect.nats.source;

public class NatsSourceConnectorConstants {
  // Constants
  public static final String KAFKA_TOPIC = "topic";
  public static final String NATS_SUBJECT = "nats.subject";
  public static final String NATS_HOST = "nats.host";

  // Constants Doc
  public static final String KAFKA_TOPIC_DOC = "Kafka topic to put received data";
  public static final String NATS_SUBJECT_DOC = "The NATS SUBJECT";
  public static final String NATS_HOST_DOC = "THE NATS HOST";
}
