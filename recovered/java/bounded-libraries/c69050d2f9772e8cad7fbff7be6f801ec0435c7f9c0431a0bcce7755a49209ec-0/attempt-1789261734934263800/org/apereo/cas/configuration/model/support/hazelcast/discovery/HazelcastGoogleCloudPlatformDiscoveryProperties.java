/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast.discovery;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-discovery-gcp")
@JsonFilter(value="HazelcastDockerSwarmDiscoveryProperties")
public class HazelcastGoogleCloudPlatformDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = 6056456067944569289L;
    private String privateKeyPath;
    private String projects;
    private String zones;
    private String label;
    private String hzPort = "5701-5708";
    private String region;

    @Generated
    public String getPrivateKeyPath() {
        return this.privateKeyPath;
    }

    @Generated
    public String getProjects() {
        return this.projects;
    }

    @Generated
    public String getZones() {
        return this.zones;
    }

    @Generated
    public String getLabel() {
        return this.label;
    }

    @Generated
    public String getHzPort() {
        return this.hzPort;
    }

    @Generated
    public String getRegion() {
        return this.region;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
        return this;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setProjects(String projects) {
        this.projects = projects;
        return this;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setZones(String zones) {
        this.zones = zones;
        return this;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setLabel(String label) {
        this.label = label;
        return this;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setHzPort(String hzPort) {
        this.hzPort = hzPort;
        return this;
    }

    @Generated
    public HazelcastGoogleCloudPlatformDiscoveryProperties setRegion(String region) {
        this.region = region;
        return this;
    }
}

