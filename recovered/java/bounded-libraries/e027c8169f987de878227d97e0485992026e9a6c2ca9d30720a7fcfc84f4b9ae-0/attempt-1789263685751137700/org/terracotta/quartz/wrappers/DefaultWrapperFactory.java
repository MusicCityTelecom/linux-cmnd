/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.quartz.wrappers;

import org.quartz.JobDetail;
import org.quartz.spi.OperableTrigger;
import org.terracotta.quartz.wrappers.JobWrapper;
import org.terracotta.quartz.wrappers.TriggerWrapper;
import org.terracotta.quartz.wrappers.WrapperFactory;

public class DefaultWrapperFactory
implements WrapperFactory {
    @Override
    public JobWrapper createJobWrapper(JobDetail jobDetail) {
        return new JobWrapper(jobDetail);
    }

    @Override
    public TriggerWrapper createTriggerWrapper(OperableTrigger trigger, boolean jobDisallowsConcurrence) {
        return new TriggerWrapper(trigger, jobDisallowsConcurrence);
    }
}

