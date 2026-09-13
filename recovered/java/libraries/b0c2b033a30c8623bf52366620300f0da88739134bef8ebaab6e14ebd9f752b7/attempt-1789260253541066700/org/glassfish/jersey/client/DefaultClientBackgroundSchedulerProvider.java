/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import org.glassfish.jersey.client.ClientBackgroundScheduler;
import org.glassfish.jersey.spi.ScheduledThreadPoolExecutorProvider;

@ClientBackgroundScheduler
class DefaultClientBackgroundSchedulerProvider
extends ScheduledThreadPoolExecutorProvider {
    DefaultClientBackgroundSchedulerProvider() {
        super("jersey-client-background-scheduler");
    }

    @Override
    protected int getCorePoolSize() {
        return 1;
    }
}

