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

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NatsSourceTask extends SourceTask {
  private static final Logger LOG = LoggerFactory.getLogger(NatsSourceTask.class);
  private Connection nc;
  private String ktopic;
  private BlockingQueue<SourceRecord> mQueue = new LinkedBlockingQueue<>();

  @Override
  public void start(Map<String, String> map) {
    LOG.info("Start the Nats Source Task");
    String nsubject = map.get(NatsSourceConnectorConstants.NATS_SUBJECT);
    String nhost = map.get(NatsSourceConnectorConstants.NATS_HOST);
    this.ktopic = map.get(NatsSourceConnectorConstants.KAFKA_TOPIC);
    try {
      this.nc = Nats.connect(nhost);
      LOG.info("Connected to the next NATS URL : " + this.nc.getConnectedUrl());
    } catch (IOException e){
      e.printStackTrace();
    }

    this.nc.subscribe(nsubject, message -> {
      LOG.info("Sending the next message : {}", message);
      SourceRecord sc = new SourceRecord(null,null,
              ktopic ,Schema.STRING_SCHEMA, message.getSubject(),
              Schema.BYTES_SCHEMA, message.getData());
      mQueue.add(sc);
    });
  }

  @Override
  public void stop() {
    LOG.info("Stop the Nats Source Task");
    this.nc.close();
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    List<SourceRecord> records = new ArrayList<>();

    mQueue.drainTo(records);
    return records;
  }

  public String version() {
    return AppInfoParser.getVersion();
  }
}
