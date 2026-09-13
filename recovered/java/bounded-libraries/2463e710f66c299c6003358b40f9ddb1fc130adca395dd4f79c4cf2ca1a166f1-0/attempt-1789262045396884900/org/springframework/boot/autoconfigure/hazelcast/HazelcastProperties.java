/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.hazelcast;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

@ConfigurationProperties(prefix="spring.hazelcast")
public class HazelcastProperties {
    private Resource config;

    public Resource getConfig() {
        return this.config;
    }

    public void setConfig(Resource config) {
        this.config = config;
    }

    public Resource resolveConfigLocation() {
        if (this.config == null) {
            return null;
        }
        Assert.isTrue((boolean)this.config.exists(), () -> "Hazelcast configuration does not exist '" + this.config.getDescription() + "'");
        return this.config;
    }
}

