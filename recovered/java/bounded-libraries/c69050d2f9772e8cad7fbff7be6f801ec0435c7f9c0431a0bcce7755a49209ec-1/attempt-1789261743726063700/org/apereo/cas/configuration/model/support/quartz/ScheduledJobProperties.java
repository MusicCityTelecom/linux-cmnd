/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.quartz;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="ScheduledJobProperties")
public class ScheduledJobProperties
implements Serializable {
    private static final long serialVersionUID = 9059671958275130605L;
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties();

    public ScheduledJobProperties(String startDelay, String repeatInterval) {
        this.schedule.setEnabled(true);
        this.schedule.setStartDelay(startDelay);
        this.schedule.setRepeatInterval(repeatInterval);
    }

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public ScheduledJobProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }

    @Generated
    public ScheduledJobProperties() {
    }
}

