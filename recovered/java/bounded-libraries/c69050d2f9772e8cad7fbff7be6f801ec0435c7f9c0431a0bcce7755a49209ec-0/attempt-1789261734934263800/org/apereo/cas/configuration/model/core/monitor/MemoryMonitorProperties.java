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
@JsonFilter(value="MemoryMonitorProperties")
public class MemoryMonitorProperties
implements Serializable {
    private static final long serialVersionUID = -7147060071480971606L;
    private int freeMemThreshold = 10;

    @Generated
    public int getFreeMemThreshold() {
        return this.freeMemThreshold;
    }

    @Generated
    public MemoryMonitorProperties setFreeMemThreshold(int freeMemThreshold) {
        this.freeMemThreshold = freeMemThreshold;
        return this;
    }
}

