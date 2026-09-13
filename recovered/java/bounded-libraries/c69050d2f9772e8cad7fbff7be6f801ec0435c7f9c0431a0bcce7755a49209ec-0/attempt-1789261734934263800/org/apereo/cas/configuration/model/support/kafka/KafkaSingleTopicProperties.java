/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.kafka;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-kafka-core")
public class KafkaSingleTopicProperties
implements Serializable {
    private static final long serialVersionUID = -1844529231331941592L;
    private String name;
    private int partitions = 1;
    private int replicas = 1;
    private String compressionType = "gzip";
    private Map<String, String> config = new HashMap<String, String>();

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getPartitions() {
        return this.partitions;
    }

    @Generated
    public int getReplicas() {
        return this.replicas;
    }

    @Generated
    public String getCompressionType() {
        return this.compressionType;
    }

    @Generated
    public Map<String, String> getConfig() {
        return this.config;
    }

    @Generated
    public KafkaSingleTopicProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public KafkaSingleTopicProperties setPartitions(int partitions) {
        this.partitions = partitions;
        return this;
    }

    @Generated
    public KafkaSingleTopicProperties setReplicas(int replicas) {
        this.replicas = replicas;
        return this;
    }

    @Generated
    public KafkaSingleTopicProperties setCompressionType(String compressionType) {
        this.compressionType = compressionType;
        return this;
    }

    @Generated
    public KafkaSingleTopicProperties setConfig(Map<String, String> config) {
        this.config = config;
        return this;
    }
}

