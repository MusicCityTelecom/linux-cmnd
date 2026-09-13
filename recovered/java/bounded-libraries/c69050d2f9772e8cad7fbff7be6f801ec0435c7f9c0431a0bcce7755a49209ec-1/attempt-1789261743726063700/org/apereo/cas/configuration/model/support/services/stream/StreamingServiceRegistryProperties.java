/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.services.stream;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.services.stream.StreamingServicesCoreProperties;
import org.apereo.cas.configuration.model.support.services.stream.hazelcast.StreamServicesHazelcastProperties;
import org.apereo.cas.configuration.model.support.services.stream.hazelcast.StreamServicesKafkaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-service-registry-stream")
@JsonFilter(value="StreamingServiceRegistryProperties")
public class StreamingServiceRegistryProperties
implements Serializable {
    private static final long serialVersionUID = 4957127900906059461L;
    @NestedConfigurationProperty
    private StreamingServicesCoreProperties core = new StreamingServicesCoreProperties();
    @NestedConfigurationProperty
    private StreamServicesHazelcastProperties hazelcast = new StreamServicesHazelcastProperties();
    @NestedConfigurationProperty
    private StreamServicesKafkaProperties kafka = new StreamServicesKafkaProperties();

    @Generated
    public StreamingServicesCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public StreamServicesHazelcastProperties getHazelcast() {
        return this.hazelcast;
    }

    @Generated
    public StreamServicesKafkaProperties getKafka() {
        return this.kafka;
    }

    @Generated
    public StreamingServiceRegistryProperties setCore(StreamingServicesCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public StreamingServiceRegistryProperties setHazelcast(StreamServicesHazelcastProperties hazelcast) {
        this.hazelcast = hazelcast;
        return this;
    }

    @Generated
    public StreamingServiceRegistryProperties setKafka(StreamServicesKafkaProperties kafka) {
        this.kafka = kafka;
        return this;
    }
}

