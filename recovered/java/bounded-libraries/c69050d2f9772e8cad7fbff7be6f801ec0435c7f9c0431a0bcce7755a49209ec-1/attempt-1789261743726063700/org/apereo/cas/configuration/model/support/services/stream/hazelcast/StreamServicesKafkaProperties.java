/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.services.stream.hazelcast;

import java.util.UUID;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.kafka.BaseKafkaProperties;
import org.apereo.cas.configuration.model.support.kafka.KafkaSingleTopicProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-service-registry-stream-kafka")
public class StreamServicesKafkaProperties
extends BaseKafkaProperties {
    private static final long serialVersionUID = -7126701588226903867L;
    @NestedConfigurationProperty
    private KafkaSingleTopicProperties topic = new KafkaSingleTopicProperties();

    public StreamServicesKafkaProperties() {
        this.topic.setName(UUID.randomUUID().toString());
    }

    @Generated
    public KafkaSingleTopicProperties getTopic() {
        return this.topic;
    }

    @Generated
    public StreamServicesKafkaProperties setTopic(KafkaSingleTopicProperties topic) {
        this.topic = topic;
        return this;
    }
}

