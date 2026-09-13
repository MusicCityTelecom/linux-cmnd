/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.hazelcast.discovery;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastClusterMulticastProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastAwsDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastAzureDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastDockerSwarmDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastGoogleCloudPlatformDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastJCloudsDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastKubernetesDiscoveryProperties;
import org.apereo.cas.configuration.model.support.hazelcast.discovery.HazelcastZooKeeperDiscoveryProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastDiscoveryProperties")
public class HazelcastDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = -8281223487171101795L;
    private boolean enabled;
    @NestedConfigurationProperty
    private HazelcastAwsDiscoveryProperties aws = new HazelcastAwsDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastJCloudsDiscoveryProperties jclouds = new HazelcastJCloudsDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastAzureDiscoveryProperties azure = new HazelcastAzureDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastZooKeeperDiscoveryProperties zookeeper = new HazelcastZooKeeperDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastKubernetesDiscoveryProperties kubernetes = new HazelcastKubernetesDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastDockerSwarmDiscoveryProperties dockerSwarm = new HazelcastDockerSwarmDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastGoogleCloudPlatformDiscoveryProperties gcp = new HazelcastGoogleCloudPlatformDiscoveryProperties();
    @NestedConfigurationProperty
    private HazelcastClusterMulticastProperties multicast = new HazelcastClusterMulticastProperties();

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties getAws() {
        return this.aws;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties getJclouds() {
        return this.jclouds;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties getAzure() {
        return this.azure;
    }

    @Generated
    public HazelcastZooKeeperDiscoveryProperties getZookeeper() {
        return this.zookeeper;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties getKubernetes() {
        return this.kubernetes;
    }

    @Generated
    public HazelcastDockerSwarmDiscoveryProperties getDockerSwarm() {
        return this.dockerSwarm;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties getGcp() {
        return this.gcp;
    }

    @Generated
    public HazelcastClusterMulticastProperties getMulticast() {
        return this.multicast;
    }

    @Generated
    public HazelcastDiscoveryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setAws(HazelcastAwsDiscoveryProperties aws) {
        this.aws = aws;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setJclouds(HazelcastJCloudsDiscoveryProperties jclouds) {
        this.jclouds = jclouds;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setAzure(HazelcastAzureDiscoveryProperties azure) {
        this.azure = azure;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setZookeeper(HazelcastZooKeeperDiscoveryProperties zookeeper) {
        this.zookeeper = zookeeper;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setKubernetes(HazelcastKubernetesDiscoveryProperties kubernetes) {
        this.kubernetes = kubernetes;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setDockerSwarm(HazelcastDockerSwarmDiscoveryProperties dockerSwarm) {
        this.dockerSwarm = dockerSwarm;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setGcp(HazelcastGoogleCloudPlatformDiscoveryProperties gcp) {
        this.gcp = gcp;
        return this;
    }

    @Generated
    public HazelcastDiscoveryProperties setMulticast(HazelcastClusterMulticastProperties multicast) {
        this.multicast = multicast;
        return this;
    }
}

