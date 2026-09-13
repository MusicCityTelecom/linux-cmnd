/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.plugins.management;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHookPlugin
implements SchedulerPlugin {
    private boolean cleanShutdown = true;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean isCleanShutdown() {
        return this.cleanShutdown;
    }

    public void setCleanShutdown(boolean b) {
        this.cleanShutdown = b;
    }

    protected Logger getLog() {
        return this.log;
    }

    @Override
    public void initialize(String name, final Scheduler scheduler, ClassLoadHelper classLoadHelper) throws SchedulerException {
        this.getLog().info("Registering Quartz shutdown hook.");
        Thread t = new Thread("Quartz Shutdown-Hook " + scheduler.getSchedulerName()){

            @Override
            public void run() {
                ShutdownHookPlugin.this.getLog().info("Shutting down Quartz...");
                try {
                    scheduler.shutdown(ShutdownHookPlugin.this.isCleanShutdown());
                }
                catch (SchedulerException e) {
                    ShutdownHookPlugin.this.getLog().info("Error shutting down Quartz: " + e.getMessage(), (Throwable)e);
                }
            }
        };
        Runtime.getRuntime().addShutdownHook(t);
    }

    @Override
    public void start() {
    }

    @Override
    public void shutdown() {
    }
}

