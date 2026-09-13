/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.ee.jta;

import org.quartz.ExecuteInJTATransaction;
import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.ee.jta.JTAJobRunShell;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.utils.ClassUtils;

public class JTAAnnotationAwareJobRunShellFactory
implements JobRunShellFactory {
    private Scheduler scheduler;

    @Override
    public void initialize(Scheduler sched) throws SchedulerConfigException {
        this.scheduler = sched;
    }

    @Override
    public JobRunShell createJobRunShell(TriggerFiredBundle bundle) throws SchedulerException {
        ExecuteInJTATransaction jtaAnnotation = ClassUtils.getAnnotation(bundle.getJobDetail().getJobClass(), ExecuteInJTATransaction.class);
        if (jtaAnnotation == null) {
            return new JobRunShell(this.scheduler, bundle);
        }
        int timeout = jtaAnnotation.timeout();
        if (timeout >= 0) {
            return new JTAJobRunShell(this.scheduler, bundle, timeout);
        }
        return new JTAJobRunShell(this.scheduler, bundle);
    }
}

