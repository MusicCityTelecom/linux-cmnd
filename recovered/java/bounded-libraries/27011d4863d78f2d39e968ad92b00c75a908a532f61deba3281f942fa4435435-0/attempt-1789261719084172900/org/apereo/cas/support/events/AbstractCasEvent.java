/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.context.ApplicationEvent
 */
package org.apereo.cas.support.events;

import lombok.Generated;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractCasEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 8059647975948452375L;

    protected AbstractCasEvent(Object source) {
        super(source);
    }

    @Generated
    public String toString() {
        return "AbstractCasEvent()";
    }
}

