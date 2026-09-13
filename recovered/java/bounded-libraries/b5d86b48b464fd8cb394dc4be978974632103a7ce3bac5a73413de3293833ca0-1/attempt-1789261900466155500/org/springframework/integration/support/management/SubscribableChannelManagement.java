/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedMetric
 *  org.springframework.jmx.support.MetricType
 */
package org.springframework.integration.support.management;

import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.support.MetricType;

public interface SubscribableChannelManagement {
    @ManagedMetric(metricType=MetricType.COUNTER, displayName="MessageChannel Subscriber Count")
    public int getSubscriberCount();
}

