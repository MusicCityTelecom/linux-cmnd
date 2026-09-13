/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.support.events.config.CasConfigurationModifiedEvent
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.springframework.cloud.context.environment.EnvironmentChangeEvent
 *  org.springframework.context.event.EventListener
 *  org.springframework.scheduling.annotation.Async
 */
package org.apereo.cas.services;

import org.apereo.cas.support.events.config.CasConfigurationModifiedEvent;
import org.apereo.cas.util.spring.CasEventListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface ServiceRegistryInitializerEventListener
extends CasEventListener {
    @EventListener
    @Async
    public void handleEnvironmentChangeEvent(EnvironmentChangeEvent var1);

    @EventListener
    @Async
    public void handleRefreshEvent(EnvironmentChangeEvent var1);

    @EventListener
    @Async
    public void handleConfigurationModifiedEvent(CasConfigurationModifiedEvent var1);
}

