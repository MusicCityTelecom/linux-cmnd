/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatClusteringProperties")
public class CasEmbeddedApacheTomcatClusteringProperties
implements Serializable {
    private static final long serialVersionUID = 620356002948464740L;
    private String clusteringType = "DEFAULT";
    private String cloudMembershipProvider = "dns";
    private boolean expireSessionsOnShutdown;
    private int channelSendOptions = 8;
    private int receiverPort = 4000;
    private int receiverTimeout = 5000;
    private int receiverMaxThreads = 6;
    private String receiverAddress = "auto";
    private int receiverAutoBind = 100;
    private String clusterMembers;
    private int membershipPort = 45564;
    private String membershipAddress = "228.0.0.4";
    private int membershipFrequency = 500;
    private int membershipDropTime = 3000;
    private boolean membershipRecoveryEnabled = true;
    private boolean membershipLocalLoopbackDisabled;
    private int membershipRecoveryCounter = 10;
    private String managerType = "DELTA";
    @RequiredProperty
    private boolean enabled;

    @Generated
    public String getClusteringType() {
        return this.clusteringType;
    }

    @Generated
    public String getCloudMembershipProvider() {
        return this.cloudMembershipProvider;
    }

    @Generated
    public boolean isExpireSessionsOnShutdown() {
        return this.expireSessionsOnShutdown;
    }

    @Generated
    public int getChannelSendOptions() {
        return this.channelSendOptions;
    }

    @Generated
    public int getReceiverPort() {
        return this.receiverPort;
    }

    @Generated
    public int getReceiverTimeout() {
        return this.receiverTimeout;
    }

    @Generated
    public int getReceiverMaxThreads() {
        return this.receiverMaxThreads;
    }

    @Generated
    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    @Generated
    public int getReceiverAutoBind() {
        return this.receiverAutoBind;
    }

    @Generated
    public String getClusterMembers() {
        return this.clusterMembers;
    }

    @Generated
    public int getMembershipPort() {
        return this.membershipPort;
    }

    @Generated
    public String getMembershipAddress() {
        return this.membershipAddress;
    }

    @Generated
    public int getMembershipFrequency() {
        return this.membershipFrequency;
    }

    @Generated
    public int getMembershipDropTime() {
        return this.membershipDropTime;
    }

    @Generated
    public boolean isMembershipRecoveryEnabled() {
        return this.membershipRecoveryEnabled;
    }

    @Generated
    public boolean isMembershipLocalLoopbackDisabled() {
        return this.membershipLocalLoopbackDisabled;
    }

    @Generated
    public int getMembershipRecoveryCounter() {
        return this.membershipRecoveryCounter;
    }

    @Generated
    public String getManagerType() {
        return this.managerType;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setClusteringType(String clusteringType) {
        this.clusteringType = clusteringType;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setCloudMembershipProvider(String cloudMembershipProvider) {
        this.cloudMembershipProvider = cloudMembershipProvider;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setExpireSessionsOnShutdown(boolean expireSessionsOnShutdown) {
        this.expireSessionsOnShutdown = expireSessionsOnShutdown;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setChannelSendOptions(int channelSendOptions) {
        this.channelSendOptions = channelSendOptions;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setReceiverPort(int receiverPort) {
        this.receiverPort = receiverPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setReceiverTimeout(int receiverTimeout) {
        this.receiverTimeout = receiverTimeout;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setReceiverMaxThreads(int receiverMaxThreads) {
        this.receiverMaxThreads = receiverMaxThreads;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setReceiverAutoBind(int receiverAutoBind) {
        this.receiverAutoBind = receiverAutoBind;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setClusterMembers(String clusterMembers) {
        this.clusterMembers = clusterMembers;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipPort(int membershipPort) {
        this.membershipPort = membershipPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipAddress(String membershipAddress) {
        this.membershipAddress = membershipAddress;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipFrequency(int membershipFrequency) {
        this.membershipFrequency = membershipFrequency;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipDropTime(int membershipDropTime) {
        this.membershipDropTime = membershipDropTime;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipRecoveryEnabled(boolean membershipRecoveryEnabled) {
        this.membershipRecoveryEnabled = membershipRecoveryEnabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipLocalLoopbackDisabled(boolean membershipLocalLoopbackDisabled) {
        this.membershipLocalLoopbackDisabled = membershipLocalLoopbackDisabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setMembershipRecoveryCounter(int membershipRecoveryCounter) {
        this.membershipRecoveryCounter = membershipRecoveryCounter;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setManagerType(String managerType) {
        this.managerType = managerType;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

