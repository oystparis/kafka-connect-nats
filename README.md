# kafka-connect-nats
Kafka Connect NATS connector is used to load data from NATS to KAFKA.

# Building
You can build the connector with Maven using the standard lifecycle phases:
```
mvn clean
mvn package
```

# Source Connector

## Sample Configuration

```ini
name=nats_source
connector.class=com.oyst.kafka.connect.nats.NatsSourceConnector
tasks.max=1
topic=NATS-TOPIC
nats.subject=NATS-SUBJECT
nats.url=nats://localhost:4222
nats.queue.group=nats-queue
```

Since the 0.2 version, kafka-connect-nats manage the nats clustering, for example:
```ini
nats.url=nats://localhost:4222,localhost:4223
```
* **name**: Name of the connector
* **connector.class**: Class of the implementation of the connector
* **tasks.max**: Maximum number of tasks to create
* **topic**: The destination Kafka's Topic Name
* **nats.subject**: The name of NATS Subject
* **nats.url**: URI of NATS server(s)
* **nats.queue.group**: Name of NATS Queue Group

# Sink Connector

Each Key of Kafka's message as considered as a NATS subject and each value as NATS message. 
## Sample Configuration

```ini
name=nats_sink
connector.class=com.oyst.kafka.connect.nats.NatsSinkConnector
tasks.max=1
topics=KAFKA-TOPIC
nats.url=nats://localhost:4222
nats.subject=FROM-KAFKA
```

* **name**: Name of the connector
* **connector.class**: Class of the implementation of the connector
* **tasks.max**: Maximum number of tasks to create
* **topic**: The Kafka's Topic to Consume
* **nats.url**: URI of NATS server(s)
* **nats.subject**: The name of NATS Subject