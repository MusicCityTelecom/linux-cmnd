/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.services.stream.hazelcast;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.BaseHazelcastProperties;
import org.apereo.cas.configuration.model.support.services.stream.BaseStreamServicesProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-service-registry-stream-hazelcast")
public class StreamServicesHazelcastProperties
extends BaseStreamServicesProperties {
    private static final long serialVersionUID = -1583614089051161614L;
    private static final int PORT = 5801;
    @DurationCapable
    private String duration = "PT1M";
    @NestedConfigurationProperty
    private BaseHazelcastProperties config = new BaseHazelcastProperties();

    public StreamServicesHazelcastProperties() {
        this.config.getCluster().getNetwork().setPort(5801);
        this.config.getCluster().getCore().setInstanceName("localhost-services-replication");
    }

    @Generated
    public String getDuration() {
        return this.duration;
    }

    @Generated
    public BaseHazelcastProperties getConfig() {
        return this.config;
    }

    @Generated
    public StreamServicesHazelcastProperties setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    @Generated
    public StreamServicesHazelcastProperties setConfig(BaseHazelcastProperties config) {
        this.config = config;
        return this;
    }
}

