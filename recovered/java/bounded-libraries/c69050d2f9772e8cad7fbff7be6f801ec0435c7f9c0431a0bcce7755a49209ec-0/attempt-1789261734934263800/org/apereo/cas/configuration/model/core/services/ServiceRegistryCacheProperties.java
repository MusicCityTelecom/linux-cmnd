/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.services;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-services", automated=true)
@JsonFilter(value="ServiceRegistryCacheProperties")
public class ServiceRegistryCacheProperties
implements Serializable {
    private static final long serialVersionUID = -368826011744304210L;
    @DurationCapable
    private String duration = "PT15M";
    private long cacheSize = 5000L;
    private int initialCapacity = 2000;

    @Generated
    public String getDuration() {
        return this.duration;
    }

    @Generated
    public long getCacheSize() {
        return this.cacheSize;
    }

    @Generated
    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    @Generated
    public ServiceRegistryCacheProperties setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    @Generated
    public ServiceRegistryCacheProperties setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    @Generated
    public ServiceRegistryCacheProperties setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        return this;
    }
}

