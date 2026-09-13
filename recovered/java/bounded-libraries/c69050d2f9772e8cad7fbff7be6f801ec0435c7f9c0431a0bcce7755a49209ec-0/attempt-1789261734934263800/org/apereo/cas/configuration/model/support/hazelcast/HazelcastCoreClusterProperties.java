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
import lombok.Generated;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastCoreClusterProperties")
public class HazelcastCoreClusterProperties
implements Serializable {
    private static final long serialVersionUID = -8374968308106013185L;
    private boolean asyncFillup = true;
    private boolean replicated;
    private String partitionMemberGroupType;
    private String loggingType = "slf4j";
    private int maxNoHeartbeatSeconds = 300;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String instanceName;
    private String mapMergePolicy = "PUT_IF_ABSENT";
    private int maxSize = 85;
    private String maxSizePolicy = "USED_HEAP_PERCENTAGE";
    private String evictionPolicy = "LRU";
    private int backupCount = 1;
    private int asyncBackupCount;
    private int timeout = 5;
    private int cpMemberCount;

    @Generated
    public boolean isAsyncFillup() {
        return this.asyncFillup;
    }

    @Generated
    public boolean isReplicated() {
        return this.replicated;
    }

    @Generated
    public String getPartitionMemberGroupType() {
        return this.partitionMemberGroupType;
    }

    @Generated
    public String getLoggingType() {
        return this.loggingType;
    }

    @Generated
    public int getMaxNoHeartbeatSeconds() {
        return this.maxNoHeartbeatSeconds;
    }

    @Generated
    public String getInstanceName() {
        return this.instanceName;
    }

    @Generated
    public String getMapMergePolicy() {
        return this.mapMergePolicy;
    }

    @Generated
    public int getMaxSize() {
        return this.maxSize;
    }

    @Generated
    public String getMaxSizePolicy() {
        return this.maxSizePolicy;
    }

    @Generated
    public String getEvictionPolicy() {
        return this.evictionPolicy;
    }

    @Generated
    public int getBackupCount() {
        return this.backupCount;
    }

    @Generated
    public int getAsyncBackupCount() {
        return this.asyncBackupCount;
    }

    @Generated
    public int getTimeout() {
        return this.timeout;
    }

    @Generated
    public int getCpMemberCount() {
        return this.cpMemberCount;
    }

    @Generated
    public HazelcastCoreClusterProperties setAsyncFillup(boolean asyncFillup) {
        this.asyncFillup = asyncFillup;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setReplicated(boolean replicated) {
        this.replicated = replicated;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setPartitionMemberGroupType(String partitionMemberGroupType) {
        this.partitionMemberGroupType = partitionMemberGroupType;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setLoggingType(String loggingType) {
        this.loggingType = loggingType;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setMaxNoHeartbeatSeconds(int maxNoHeartbeatSeconds) {
        this.maxNoHeartbeatSeconds = maxNoHeartbeatSeconds;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setMapMergePolicy(String mapMergePolicy) {
        this.mapMergePolicy = mapMergePolicy;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setMaxSizePolicy(String maxSizePolicy) {
        this.maxSizePolicy = maxSizePolicy;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setEvictionPolicy(String evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setBackupCount(int backupCount) {
        this.backupCount = backupCount;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setAsyncBackupCount(int asyncBackupCount) {
        this.asyncBackupCount = asyncBackupCount;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public HazelcastCoreClusterProperties setCpMemberCount(int cpMemberCount) {
        this.cpMemberCount = cpMemberCount;
        return this;
    }
}

