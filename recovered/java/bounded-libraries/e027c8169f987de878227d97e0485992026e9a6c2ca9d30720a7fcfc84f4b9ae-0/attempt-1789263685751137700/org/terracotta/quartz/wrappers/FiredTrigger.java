/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.quartz.wrappers;

import java.io.Serializable;
import java.util.Date;
import org.quartz.TriggerKey;

public class FiredTrigger
implements Serializable {
    private final String clientId;
    private final TriggerKey triggerKey;
    private final long scheduledFireTime;
    private final long fireTime;

    public FiredTrigger(String clientId, TriggerKey triggerKey, long scheduledFireTime) {
        this(clientId, triggerKey, scheduledFireTime, System.currentTimeMillis());
    }

    public FiredTrigger(String clientId, TriggerKey triggerKey, long scheduledFireTime, long now) {
        this.clientId = clientId;
        this.triggerKey = triggerKey;
        this.scheduledFireTime = scheduledFireTime;
        this.fireTime = now;
    }

    public String getClientId() {
        return this.clientId;
    }

    public TriggerKey getTriggerKey() {
        return this.triggerKey;
    }

    public long getScheduledFireTime() {
        return this.scheduledFireTime;
    }

    public long getFireTime() {
        return this.fireTime;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.triggerKey + ", " + this.getClientId() + ", " + new Date(this.fireTime) + ")";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.clientId == null ? 0 : this.clientId.hashCode());
        result = 31 * result + (int)(this.scheduledFireTime ^ this.scheduledFireTime >>> 32);
        result = 31 * result + (int)(this.fireTime ^ this.fireTime >>> 32);
        result = 31 * result + (this.triggerKey == null ? 0 : this.triggerKey.hashCode());
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
        FiredTrigger other = (FiredTrigger)obj;
        if (this.clientId == null ? other.clientId != null : !this.clientId.equals(other.clientId)) {
            return false;
        }
        if (this.scheduledFireTime != other.scheduledFireTime) {
            return false;
        }
        if (this.fireTime != other.fireTime) {
            return false;
        }
        return !(this.triggerKey == null ? other.triggerKey != null : !this.triggerKey.equals(other.triggerKey));
    }
}

