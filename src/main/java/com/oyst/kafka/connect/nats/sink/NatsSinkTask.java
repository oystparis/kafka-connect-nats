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
package com.oyst.kafka.connect.nats.sink;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Nats;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.kafka.connect.storage.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.oyst.kafka.connect.nats.sink.NatsSinkConnectorConstants.NATS_SUBJECT;
import static com.oyst.kafka.connect.nats.sink.NatsSinkConnectorConstants.NATS_URL;

public class NatsSinkTask extends SinkTask {
  private static final Logger LOG = LoggerFactory.getLogger(NatsSinkTask.class);
  private Connection nc;
  private String subject;

  @Override
  public void start(Map<String, String> map) {
    LOG.info("Start the Nats Sink Task");
    String[] nhost = map.get(NATS_URL).split(",");
    this.subject = map.get(NATS_SUBJECT);
    try {
      if (nhost.length == 1)
        this.nc = Nats.connect(nhost[0]);
      else{
        ConnectionFactory cf = new ConnectionFactory();
        cf.setServers(nhost);
        cf.setMaxReconnect(5);
        cf.setReconnectWait(2000);
        cf.setNoRandomize(true);
        this.nc = cf.createConnection();
      }
      LOG.info("Connected to the next NATS URL(master) : " + this.nc.getConnectedUrl());
    } catch (IOException e){
      LOG.error(e.getMessage(), e);
    }
  }

  @Override
  public void stop() {
    this.nc.close();
  }

  @Override
  public void put(Collection<SinkRecord> collection) {
    List<SinkRecord> records = new ArrayList<>(collection);
    for (int i = 0; i < records.size(); i++) {
      SinkRecord sinkRecord = records.get(i);

      StringConverter stringConverter = new StringConverter();
      String key = new String(stringConverter.fromConnectData(sinkRecord.topic(),
              sinkRecord.keySchema(), sinkRecord.key()));
      byte[] value = stringConverter.fromConnectData(sinkRecord.topic(),
              sinkRecord.valueSchema(), sinkRecord.value());
      try {
        LOG.info("publishing next key : {}", key);
        LOG.info("publishing next value : {}", value);
        this.nc.publish(this.subject, value);
      } catch (IOException e){
        LOG.error(e.getMessage(), e);
      }
    }
  }


  @Override
  public String version() {
    return AppInfoParser.getVersion();
  }
}
