/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.quartz.collections;

import java.io.Serializable;
import java.util.Date;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class TimeTrigger
implements Comparable<TimeTrigger>,
Serializable {
    private final TriggerKey triggerKey;
    private final Long nextFireTime;
    private final int priority;

    TimeTrigger(TriggerKey triggerKey, Date next, int priority) {
        this.triggerKey = triggerKey;
        this.nextFireTime = next == null ? null : Long.valueOf(next.getTime());
        this.priority = priority;
    }

    TriggerKey getTriggerKey() {
        return this.triggerKey;
    }

    int getPriority() {
        return this.priority;
    }

    Date getNextFireTime() {
        return this.nextFireTime == null ? null : new Date(this.nextFireTime);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TimeTrigger) {
            TimeTrigger other = (TimeTrigger)obj;
            return this.triggerKey.equals(other.triggerKey);
        }
        return false;
    }

    public int hashCode() {
        return this.triggerKey.hashCode();
    }

    public String toString() {
        return "TimeTrigger [triggerKey=" + this.triggerKey + ", nextFireTime=" + new Date(this.nextFireTime) + ", priority=" + this.priority + "]";
    }

    @Override
    public int compareTo(TimeTrigger tt2) {
        TimeTrigger tt1 = this;
        return Trigger.TriggerTimeComparator.compare(tt1.getNextFireTime(), tt1.getPriority(), tt1.getTriggerKey(), tt2.getNextFireTime(), tt2.getPriority(), tt2.getTriggerKey());
    }
}

