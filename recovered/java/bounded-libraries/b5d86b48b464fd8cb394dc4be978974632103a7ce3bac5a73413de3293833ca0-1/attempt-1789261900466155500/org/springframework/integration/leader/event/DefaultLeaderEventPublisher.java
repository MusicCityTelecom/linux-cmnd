/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 */
package org.springframework.integration.leader.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.LeaderEventPublisher;
import org.springframework.integration.leader.event.OnFailedToAcquireMutexEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;

public class DefaultLeaderEventPublisher
implements LeaderEventPublisher,
ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    public DefaultLeaderEventPublisher() {
    }

    public DefaultLeaderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishOnGranted(Object source, Context context, String role) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent((ApplicationEvent)new OnGrantedEvent(source, context, role));
        }
    }

    @Override
    public void publishOnRevoked(Object source, Context context, String role) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent((ApplicationEvent)new OnRevokedEvent(source, context, role));
        }
    }

    @Override
    public void publishOnFailedToAcquire(Object source, Context context, String role) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent((ApplicationEvent)new OnFailedToAcquireMutexEvent(source, context, role));
        }
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

