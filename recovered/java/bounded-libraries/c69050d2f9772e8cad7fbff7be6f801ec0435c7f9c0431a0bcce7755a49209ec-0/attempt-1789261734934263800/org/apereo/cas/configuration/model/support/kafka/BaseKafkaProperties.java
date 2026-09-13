/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.kafka;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-kafka-core")
public abstract class BaseKafkaProperties
implements Serializable {
    private static final long serialVersionUID = -3844529231331941592L;
    private String bootstrapAddress;

    @Generated
    public String getBootstrapAddress() {
        return this.bootstrapAddress;
    }

    @Generated
    public BaseKafkaProperties setBootstrapAddress(String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
        return this;
    }
}

