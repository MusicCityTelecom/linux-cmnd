/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.support.events.service;

import org.apereo.cas.support.events.AbstractCasEvent;

public abstract class BaseCasRegisteredServiceEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 7828374109804253319L;

    protected BaseCasRegisteredServiceEvent(Object source) {
        super(source);
    }
}

