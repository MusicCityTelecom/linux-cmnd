/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.simpl;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobFactory
implements JobFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected Logger getLog() {
        return this.log;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler2) throws SchedulerException {
        JobDetail jobDetail = bundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        try {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Producing instance of Job '" + jobDetail.getKey() + "', class=" + jobClass.getName());
            }
            return jobClass.newInstance();
        }
        catch (Exception e) {
            SchedulerException se = new SchedulerException("Problem instantiating class '" + jobDetail.getJobClass().getName() + "'", e);
            throw se;
        }
    }
}

