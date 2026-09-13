/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.integration.leader.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.integration.leader.Context;

public abstract class AbstractLeaderEvent
extends ApplicationEvent {
    private final Context context;
    private final String role;

    public AbstractLeaderEvent(Object source) {
        this(source, null, null);
    }

    public AbstractLeaderEvent(Object source, Context context, String role) {
        super(source);
        this.context = context;
        this.role = role;
    }

    public Context getContext() {
        return this.context;
    }

    public String getRole() {
        return this.role;
    }

    public String toString() {
        return ((Object)((Object)this)).getClass().getSimpleName() + " [role=" + this.role + ", context=" + this.context + ", source=" + this.source + "]";
    }
}

