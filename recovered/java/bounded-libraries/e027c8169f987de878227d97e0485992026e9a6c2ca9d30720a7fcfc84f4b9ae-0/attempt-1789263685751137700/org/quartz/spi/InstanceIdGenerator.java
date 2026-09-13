/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import org.quartz.SchedulerException;

public interface InstanceIdGenerator {
    public String generateInstanceId() throws SchedulerException;
}

