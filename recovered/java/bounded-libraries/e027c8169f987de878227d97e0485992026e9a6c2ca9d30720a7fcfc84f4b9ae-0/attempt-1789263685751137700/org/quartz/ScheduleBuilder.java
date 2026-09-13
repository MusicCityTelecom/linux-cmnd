/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.Trigger;
import org.quartz.spi.MutableTrigger;

public abstract class ScheduleBuilder<T extends Trigger> {
    protected abstract MutableTrigger build();
}

