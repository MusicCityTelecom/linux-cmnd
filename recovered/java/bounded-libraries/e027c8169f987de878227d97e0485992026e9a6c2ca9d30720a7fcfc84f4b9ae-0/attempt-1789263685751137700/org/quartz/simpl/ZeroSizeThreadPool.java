/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.simpl;

import org.quartz.SchedulerConfigException;
import org.quartz.spi.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeroSizeThreadPool
implements ThreadPool {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Logger getLog() {
        return this.log;
    }

    @Override
    public int getPoolSize() {
        return 0;
    }

    @Override
    public void initialize() throws SchedulerConfigException {
    }

    public void shutdown() {
        this.shutdown(true);
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) {
        this.getLog().debug("shutdown complete");
    }

    @Override
    public boolean runInThread(Runnable runnable) {
        throw new UnsupportedOperationException("This ThreadPool should not be used on Scheduler instances that are start()ed.");
    }

    @Override
    public int blockForAvailableThreads() {
        throw new UnsupportedOperationException("This ThreadPool should not be used on Scheduler instances that are start()ed.");
    }

    @Override
    public void setInstanceId(String schedInstId) {
    }

    @Override
    public void setInstanceName(String schedName) {
    }
}

