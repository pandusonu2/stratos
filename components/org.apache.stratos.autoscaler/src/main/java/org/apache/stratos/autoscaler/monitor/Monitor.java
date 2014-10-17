/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.stratos.autoscaler.monitor;

import org.apache.stratos.autoscaler.monitor.events.MonitorStatusEvent;
import org.apache.stratos.messaging.domain.topology.ParentComponent;

import java.util.Map;

/**
 * Abstract class for the monitoring functionality in autoscaler.
 */
public abstract class Monitor implements EventHandler {
    protected String id;

    protected String appId;

    protected ParentComponentMonitor parent;

    //GroupMonitor map, key=GroupAlias and value=GroupMonitor
    protected Map<String, Monitor> aliasToMonitorsMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Map<String, Monitor> getAliasToMonitorsMap() {
        return aliasToMonitorsMap;
    }

    public void setAliasToMonitorsMap(Map<String, Monitor> aliasToMonitorsMap) {
        this.aliasToMonitorsMap = aliasToMonitorsMap;
    }

    public ParentComponentMonitor getParent() {
        return parent;
    }

    public void setParent(ParentComponentMonitor parent) {
        this.parent = parent;
    }
}
