/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

public abstract class IntegrationEvent
extends ApplicationEvent {
    protected final Throwable cause;

    public IntegrationEvent(Object source) {
        this(source, null);
    }

    public IntegrationEvent(Object source, @Nullable Throwable cause) {
        super(source);
        this.cause = cause;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public <T> T getSourceAsType() {
        return (T)this.getSource();
    }

    public String toString() {
        return ((Object)((Object)this)).getClass().getSimpleName() + " [source=" + this.getSource() + (this.cause == null ? "" : ", cause=" + this.cause) + "]";
    }
}

