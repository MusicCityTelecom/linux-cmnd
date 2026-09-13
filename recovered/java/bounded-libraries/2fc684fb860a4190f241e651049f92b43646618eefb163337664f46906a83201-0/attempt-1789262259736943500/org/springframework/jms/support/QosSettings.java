/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.support;

import org.springframework.lang.Nullable;

public class QosSettings {
    private int deliveryMode;
    private int priority;
    private long timeToLive;

    public QosSettings() {
        this(2, 4, 0L);
    }

    public QosSettings(int deliveryMode, int priority, long timeToLive) {
        this.deliveryMode = deliveryMode;
        this.priority = priority;
        this.timeToLive = timeToLive;
    }

    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public int getDeliveryMode() {
        return this.deliveryMode;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof QosSettings)) {
            return false;
        }
        QosSettings otherSettings = (QosSettings)other;
        return this.deliveryMode == otherSettings.deliveryMode && this.priority == otherSettings.priority && this.timeToLive == otherSettings.timeToLive;
    }

    public int hashCode() {
        return this.deliveryMode * 31 + this.priority;
    }

    public String toString() {
        return "QosSettings{deliveryMode=" + this.deliveryMode + ", priority=" + this.priority + ", timeToLive=" + this.timeToLive + '}';
    }
}

