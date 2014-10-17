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
package org.apache.stratos.autoscaler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.stratos.autoscaler.monitor.Monitor;
import org.apache.stratos.autoscaler.monitor.application.ApplicationMonitor;
import org.apache.stratos.autoscaler.monitor.cluster.LbClusterMonitor;
import org.apache.stratos.autoscaler.status.checker.StatusChecker;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is there for accumulating cluster details which are not there in Topology
 */
public class AutoscalerContext {

    private static final Log log = LogFactory.getLog(AutoscalerContext.class);
    // Map<ClusterId, ClusterMonitor>
    private Map<String, Monitor> monitors;
    // Map<ClusterId, ClusterMonitor>
    private Map<String, StatusChecker> statusCheckers;
    // Map<LBClusterId, LBClusterMonitor>
    private Map<String, LbClusterMonitor> lbMonitors;
    private Map<String, ApplicationMonitor> appMonitors;


    private AutoscalerContext() {
        try {
            setMonitors(new HashMap<String, Monitor>());
            setLbMonitors(new HashMap<String, LbClusterMonitor>());
            setAppMonitors(new HashMap<String, ApplicationMonitor>());
        } catch (Exception e) {
            log.error("Rule evaluateMinCheck error", e);
        }
    }

    public static AutoscalerContext getInstance() {
        return Holder.INSTANCE;
    }

    public Map<String, ApplicationMonitor> getAppMonitors() {
        return appMonitors;
    }

    public void setAppMonitors(Map<String, ApplicationMonitor> appMonitors) {
        this.appMonitors = appMonitors;
    }

    public ApplicationMonitor getAppMonitor(String applicationId) {
        return appMonitors.get(applicationId);
    }

    public Map<String, StatusChecker> getStatusCheckers() {
        return statusCheckers;
    }

    public void addMonitor(Monitor monitor) {
        monitors.put(monitor.getId(), monitor);
    }

    public Monitor getMonitor(String clusterId) {
        return monitors.get(clusterId);
    }

    public boolean monitorExist(String clusterId) {
        return monitors.containsKey(clusterId);
    }

    public void addAppMonitor(ApplicationMonitor appMonitor) {
        appMonitors.put(appMonitor.getId(), appMonitor);
    }

    public boolean appMonitorExist(String appId) {
        return appMonitors.containsKey(appId);
    }

    public boolean lbMonitorExist(String clusterId) {
        return lbMonitors.containsKey(clusterId);
    }

    public LbClusterMonitor getLBMonitor(String clusterId) {
        return lbMonitors.get(clusterId);
    }

    public Monitor removeMonitor(String clusterId) {
        if (!monitorExist(clusterId)) {
            log.fatal("Cluster monitor not found for cluster id: " + clusterId);
            return null;
        }
        log.info("Removed monitor [cluster id]: " + clusterId);
        return monitors.remove(clusterId);
    }

    public LbClusterMonitor removeLbMonitor(String clusterId) {
        if (!lbMonitorExist(clusterId)) {
            log.fatal("LB monitor not found for cluster id: " + clusterId);
            return null;
        }
        log.info("Removed LB monitor [cluster id]: " + clusterId);
        return lbMonitors.remove(clusterId);
    }

    public Map<String, Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(Map<String, Monitor> monitors) {
        this.monitors = monitors;
    }

    public void setLbMonitors(Map<String, LbClusterMonitor> monitors) {
        this.lbMonitors = monitors;
    }

    public void addLbMonitor(LbClusterMonitor monitor) {
        lbMonitors.put(monitor.getClusterId(), monitor);
    }

    public ApplicationMonitor removeAppMonitor(String appId) {
        if (!appMonitorExist(appId)) {
            log.fatal("LB monitor not found for App id: " + appId);
            return null;
        }
        log.info("Removed APP monitor [App id]: " + appId);
        return appMonitors.remove(appId);
    }

    private static class Holder {
        private static final AutoscalerContext INSTANCE = new AutoscalerContext();
    }

}
