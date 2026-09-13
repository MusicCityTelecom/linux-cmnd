/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.TriggerContext
 *  org.springframework.util.Assert
 */
package org.springframework.integration.util;

import java.time.Duration;
import java.util.Date;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.util.Assert;

public class DynamicPeriodicTrigger
implements Trigger {
    private Duration initialDuration = Duration.ofMillis(0L);
    private Duration duration;
    private boolean fixedRate = false;

    public DynamicPeriodicTrigger(long period) {
        this(Duration.ofMillis(period));
    }

    public DynamicPeriodicTrigger(Duration duration) {
        Assert.notNull((Object)duration, (String)"duration must not be null");
        Assert.isTrue((!duration.isNegative() ? 1 : 0) != 0, (String)"duration must not be negative");
        this.duration = duration;
    }

    public void setInitialDuration(Duration initialDuration) {
        Assert.notNull((Object)initialDuration, (String)"initialDuration must not be null");
        Assert.isTrue((!initialDuration.isNegative() ? 1 : 0) != 0, (String)"initialDuration must not be negative");
        this.initialDuration = initialDuration;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getInitialDuration() {
        return this.initialDuration;
    }

    public void setFixedRate(boolean fixedRate) {
        this.fixedRate = fixedRate;
    }

    public boolean isFixedRate() {
        return this.fixedRate;
    }

    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date lastScheduled = triggerContext.lastScheduledExecutionTime();
        if (lastScheduled == null) {
            return new Date(System.currentTimeMillis() + this.initialDuration.toMillis());
        }
        if (this.fixedRate) {
            return new Date(lastScheduled.getTime() + this.duration.toMillis());
        }
        return new Date(triggerContext.lastCompletionTime().getTime() + this.duration.toMillis());
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.duration == null ? 0 : this.duration.hashCode());
        result = 31 * result + (this.fixedRate ? 1231 : 1237);
        result = 31 * result + (this.initialDuration == null ? 0 : this.initialDuration.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DynamicPeriodicTrigger other = (DynamicPeriodicTrigger)obj;
        if (this.duration == null ? other.duration != null : !this.duration.equals(other.duration)) {
            return false;
        }
        if (this.fixedRate != other.fixedRate) {
            return false;
        }
        if (this.initialDuration == null) {
            return other.initialDuration == null;
        }
        return this.initialDuration.equals(other.initialDuration);
    }
}

