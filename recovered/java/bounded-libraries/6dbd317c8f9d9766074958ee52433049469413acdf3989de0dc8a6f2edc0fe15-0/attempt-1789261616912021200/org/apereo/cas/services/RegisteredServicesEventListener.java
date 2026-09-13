/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicesRefreshEvent
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.springframework.cloud.context.environment.EnvironmentChangeEvent
 *  org.springframework.context.event.EventListener
 *  org.springframework.scheduling.annotation.Async
 */
package org.apereo.cas.services;

import org.apereo.cas.support.events.service.CasRegisteredServiceExpiredEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicesRefreshEvent;
import org.apereo.cas.util.spring.CasEventListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface RegisteredServicesEventListener
extends CasEventListener {
    @EventListener
    @Async
    public void handleRefreshEvent(CasRegisteredServicesRefreshEvent var1);

    @EventListener
    @Async
    public void handleEnvironmentChangeEvent(EnvironmentChangeEvent var1);

    @EventListener
    @Async
    public void handleRegisteredServiceExpiredEvent(CasRegisteredServiceExpiredEvent var1);
}

