/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  commonj.work.Work
 *  commonj.work.WorkManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.commonj;

import commonj.work.Work;
import commonj.work.WorkManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.commonj.DelegatingWork;
import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkManagerThreadExecutor
implements ThreadExecutor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String workManagerName;
    private WorkManager workManager;

    @Override
    public void execute(Thread thread) {
        DelegatingWork work = new DelegatingWork(thread);
        try {
            this.workManager.schedule((Work)work);
        }
        catch (Exception e) {
            this.log.error("Error attempting to schedule QuartzSchedulerThread: " + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void initialize() {
        try {
            this.workManager = (WorkManager)new InitialContext().lookup(this.workManagerName);
        }
        catch (NamingException e) {
            throw new IllegalStateException("Could not locate WorkManager: " + e.getMessage(), e);
        }
    }

    public void setWorkManagerName(String workManagerName) {
        this.workManagerName = workManagerName;
    }
}

