/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.ServicesManager
 *  org.springframework.scheduling.annotation.Scheduled
 */
package org.apereo.cas.services;

import lombok.Generated;
import org.apereo.cas.services.ServicesManager;
import org.springframework.scheduling.annotation.Scheduled;

public class ServicesManagerScheduledLoader
implements Runnable {
    private final ServicesManager servicesManager;

    public static Runnable noOp() {
        return () -> {};
    }

    @Override
    @Scheduled(initialDelayString="${cas.service-registry.schedule.start-delay:PT20S}", fixedDelayString="${cas.service-registry.schedule.repeat-interval:PT60S}")
    public void run() {
        this.servicesManager.load();
    }

    @Generated
    public ServicesManagerScheduledLoader(ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }
}

