/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="MonitorWarningProperties")
public class MonitorWarningProperties
implements Serializable {
    private static final long serialVersionUID = 2788617778375787703L;
    private int threshold = 10;
    private long evictionThreshold;

    public MonitorWarningProperties(int threshold) {
        this.threshold = threshold;
    }

    @Generated
    public int getThreshold() {
        return this.threshold;
    }

    @Generated
    public long getEvictionThreshold() {
        return this.evictionThreshold;
    }

    @Generated
    public MonitorWarningProperties setThreshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    @Generated
    public MonitorWarningProperties setEvictionThreshold(long evictionThreshold) {
        this.evictionThreshold = evictionThreshold;
        return this;
    }

    @Generated
    public MonitorWarningProperties() {
    }
}

