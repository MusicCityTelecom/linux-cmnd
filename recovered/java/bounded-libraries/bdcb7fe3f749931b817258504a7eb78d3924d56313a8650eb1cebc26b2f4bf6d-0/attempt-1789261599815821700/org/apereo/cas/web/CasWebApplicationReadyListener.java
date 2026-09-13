/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.context.event.EventListener
 *  org.springframework.scheduling.annotation.Async
 */
package org.apereo.cas.web;

import org.apereo.cas.util.spring.CasEventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@FunctionalInterface
public interface CasWebApplicationReadyListener
extends CasEventListener {
    @EventListener
    @Async
    public void handleApplicationReadyEvent(ApplicationReadyEvent var1);
}

