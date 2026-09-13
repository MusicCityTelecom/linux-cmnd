/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastCoreClusterProperties;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastNetworkClusterProperties;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastWANReplicationProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastDiscoveryProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastClusterProperties")
public class HazelcastClusterProperties
implements Serializable {
    private static final long serialVersionUID = 1817784607045775145L;
    @NestedConfigurationProperty
    private HazelcastDiscoveryProperties discovery = new HazelcastDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastWANReplicationProperties wanReplication = new HazelcastWANReplicationProperties();
    @NestedConfigurationProperty
    private HazelcastCoreClusterProperties core = new HazelcastCoreClusterProperties();
    @NestedConfigurationProperty
    private HazelcastNetworkClusterProperties network = new HazelcastNetworkClusterProperties();

    @Generated
    public HazelcastDiscoveryProperties getDiscovery() {
        return this.discovery;
    }

    @Generated
    public HazelcastWANReplicationProperties getWanReplication() {
        return this.wanReplication;
    }

    @Generated
    public HazelcastCoreClusterProperties getCore() {
        return this.core;
    }

    @Generated
    public HazelcastNetworkClusterProperties getNetwork() {
        return this.network;
    }

    @Generated
    public HazelcastClusterProperties setDiscovery(HazelcastDiscoveryProperties discovery) {
        this.discovery = discovery;
        return this;
    }

    @Generated
    public HazelcastClusterProperties setWanReplication(HazelcastWANReplicationProperties wanReplication) {
        this.wanReplication = wanReplication;
        return this;
    }

    @Generated
    public HazelcastClusterProperties setCore(HazelcastCoreClusterProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public HazelcastClusterProperties setNetwork(HazelcastNetworkClusterProperties network) {
        this.network = network;
        return this;
    }
}

