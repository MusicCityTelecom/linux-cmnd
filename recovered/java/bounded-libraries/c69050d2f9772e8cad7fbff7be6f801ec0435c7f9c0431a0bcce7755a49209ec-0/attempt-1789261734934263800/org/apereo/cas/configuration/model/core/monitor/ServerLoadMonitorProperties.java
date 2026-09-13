/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.monitor.MonitorWarningProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="ServerLoadMonitorProperties")
public class ServerLoadMonitorProperties
implements Serializable {
    private static final long serialVersionUID = 5504478373010611957L;
    @NestedConfigurationProperty
    private MonitorWarningProperties warn = new MonitorWarningProperties(25);

    @Generated
    public MonitorWarningProperties getWarn() {
        return this.warn;
    }

    @Generated
    public ServerLoadMonitorProperties setWarn(MonitorWarningProperties warn) {
        this.warn = warn;
        return this;
    }
}

