/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.quartz.wrappers;

import org.quartz.JobDetail;
import org.quartz.spi.OperableTrigger;
import org.terracotta.quartz.wrappers.JobWrapper;
import org.terracotta.quartz.wrappers.TriggerWrapper;

public interface WrapperFactory {
    public JobWrapper createJobWrapper(JobDetail var1);

    public TriggerWrapper createTriggerWrapper(OperableTrigger var1, boolean var2);
}

