/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.quartz;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="SchedulingProperties")
public class SchedulingProperties
implements Serializable {
    private static final long serialVersionUID = -1522227059439367394L;
    private boolean enabled = true;
    private String enabledOnHost = ".*";
    @DurationCapable
    private String startDelay = "PT15S";
    @DurationCapable
    private String repeatInterval = "PT2M";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getEnabledOnHost() {
        return this.enabledOnHost;
    }

    @Generated
    public String getStartDelay() {
        return this.startDelay;
    }

    @Generated
    public String getRepeatInterval() {
        return this.repeatInterval;
    }

    @Generated
    public SchedulingProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public SchedulingProperties setEnabledOnHost(String enabledOnHost) {
        this.enabledOnHost = enabledOnHost;
        return this;
    }

    @Generated
    public SchedulingProperties setStartDelay(String startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    @Generated
    public SchedulingProperties setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
        return this;
    }
}

