/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.stratos.messaging.message.receiver.cluster.status;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.stratos.messaging.util.Constants;
import org.apache.stratos.messaging.util.Util;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Implements functionality for receiving text based event messages from the
 * instance notifier
 * message broker topic and add them to the event queue.
 */
class ClusterStatusEventMessageListener implements MqttCallback {

    private static final Log log = LogFactory.getLog(ClusterStatusEventMessageListener.class);

    private final ClusterStatusEventMessageQueue messageQueue;

    public ClusterStatusEventMessageListener(ClusterStatusEventMessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void connectionLost(Throwable err) {
        if (log.isDebugEnabled()) {
            log.debug("MQTT connection lost", err);
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken err) {
        log.debug("Message delivery completed");
    }

    @Override
    public void messageArrived(String topicName, MqttMessage message)
            throws Exception {

        TextMessage receivedMessage = new ActiveMQTextMessage();
        if (log.isDebugEnabled()) {
            log.debug(String.format("instance notifier messege received...."));

        }

        receivedMessage.setText(new String(message.getPayload()));
        receivedMessage.setStringProperty(Constants.EVENT_CLASS_NAME,
                Util.getEventNameForTopic(topicName));

        try {
            if (log.isDebugEnabled()) {
                log.debug(String.format(
                        "Instance notifier message received: %s",
                        receivedMessage.getText()));
            }
            // Add received message to the queue
            messageQueue.add(receivedMessage);

        } catch (JMSException e) {
            log.error(e.getMessage(), e);
        }

    }
}
