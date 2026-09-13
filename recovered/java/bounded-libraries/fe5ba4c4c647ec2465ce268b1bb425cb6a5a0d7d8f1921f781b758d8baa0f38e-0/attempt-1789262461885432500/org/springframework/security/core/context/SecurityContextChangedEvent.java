/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.security.core.context;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextChangedEvent
extends ApplicationEvent {
    private final SecurityContext oldContext;
    private final SecurityContext newContext;

    public SecurityContextChangedEvent(SecurityContext oldContext, SecurityContext newContext) {
        super(SecurityContextHolder.class);
        this.oldContext = oldContext;
        this.newContext = newContext;
    }

    public SecurityContext getOldContext() {
        return this.oldContext;
    }

    public SecurityContext getNewContext() {
        return this.newContext;
    }
}

