/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastWANReplicationTargetClusterProperties")
public class HazelcastWANReplicationTargetClusterProperties
implements Serializable {
    private static final long serialVersionUID = 1635330607045885145L;
    private String endpoints;
    private String clusterName;
    private String publisherId;
    private Map<String, Comparable> properties = new HashMap<String, Comparable>();
    private String consistencyCheckStrategy = "NONE";
    private String publisherClassName = "com.hazelcast.enterprise.wan.replication.WanBatchReplication";
    private String queueFullBehavior = "THROW_EXCEPTION";
    private String acknowledgeType = "ACK_ON_OPERATION_COMPLETE";
    private int queueCapacity = 10000;
    private int batchSize = 500;
    private boolean snapshotEnabled;
    private int batchMaximumDelayMilliseconds = 1000;
    private int responseTimeoutMilliseconds = 60000;
    private int executorThreadCount = 2;

    @Generated
    public String getEndpoints() {
        return this.endpoints;
    }

    @Generated
    public String getClusterName() {
        return this.clusterName;
    }

    @Generated
    public String getPublisherId() {
        return this.publisherId;
    }

    @Generated
    public Map<String, Comparable> getProperties() {
        return this.properties;
    }

    @Generated
    public String getConsistencyCheckStrategy() {
        return this.consistencyCheckStrategy;
    }

    @Generated
    public String getPublisherClassName() {
        return this.publisherClassName;
    }

    @Generated
    public String getQueueFullBehavior() {
        return this.queueFullBehavior;
    }

    @Generated
    public String getAcknowledgeType() {
        return this.acknowledgeType;
    }

    @Generated
    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    @Generated
    public int getBatchSize() {
        return this.batchSize;
    }

    @Generated
    public boolean isSnapshotEnabled() {
        return this.snapshotEnabled;
    }

    @Generated
    public int getBatchMaximumDelayMilliseconds() {
        return this.batchMaximumDelayMilliseconds;
    }

    @Generated
    public int getResponseTimeoutMilliseconds() {
        return this.responseTimeoutMilliseconds;
    }

    @Generated
    public int getExecutorThreadCount() {
        return this.executorThreadCount;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setEndpoints(String endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setClusterName(String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setPublisherId(String publisherId) {
        this.publisherId = publisherId;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setProperties(Map<String, Comparable> properties) {
        this.properties = properties;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setConsistencyCheckStrategy(String consistencyCheckStrategy) {
        this.consistencyCheckStrategy = consistencyCheckStrategy;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setPublisherClassName(String publisherClassName) {
        this.publisherClassName = publisherClassName;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setQueueFullBehavior(String queueFullBehavior) {
        this.queueFullBehavior = queueFullBehavior;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setAcknowledgeType(String acknowledgeType) {
        this.acknowledgeType = acknowledgeType;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setSnapshotEnabled(boolean snapshotEnabled) {
        this.snapshotEnabled = snapshotEnabled;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setBatchMaximumDelayMilliseconds(int batchMaximumDelayMilliseconds) {
        this.batchMaximumDelayMilliseconds = batchMaximumDelayMilliseconds;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setResponseTimeoutMilliseconds(int responseTimeoutMilliseconds) {
        this.responseTimeoutMilliseconds = responseTimeoutMilliseconds;
        return this;
    }

    @Generated
    public HazelcastWANReplicationTargetClusterProperties setExecutorThreadCount(int executorThreadCount) {
        this.executorThreadCount = executorThreadCount;
        return this;
    }
}

