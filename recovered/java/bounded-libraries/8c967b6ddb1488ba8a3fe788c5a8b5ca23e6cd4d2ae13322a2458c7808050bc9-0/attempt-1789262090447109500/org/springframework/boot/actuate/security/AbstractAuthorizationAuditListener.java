/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.security.access.event.AbstractAuthorizationEvent
 */
package org.springframework.boot.actuate.security;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;

public abstract class AbstractAuthorizationAuditListener
implements ApplicationListener<AbstractAuthorizationEvent>,
ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    protected ApplicationEventPublisher getPublisher() {
        return this.publisher;
    }

    protected void publish(AuditEvent event) {
        if (this.getPublisher() != null) {
            this.getPublisher().publishEvent((ApplicationEvent)new AuditApplicationEvent(event));
        }
    }
}

