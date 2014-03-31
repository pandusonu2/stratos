/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.stratos.messaging.message.receiver.health.stat;

import javax.jms.TextMessage;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements a blocking queue for managing instance notifier event messages.
 */
public class HealthStatEventMessageQueue extends LinkedBlockingQueue<TextMessage> {
    private static volatile HealthStatEventMessageQueue instance;

    private HealthStatEventMessageQueue(){
    }

    public static HealthStatEventMessageQueue getInstance() {
        if (instance == null) {
            synchronized (HealthStatEventMessageQueue.class){
                if (instance == null) {
                    instance = new HealthStatEventMessageQueue();
                }
            }
        }
        return instance;
    }
}
