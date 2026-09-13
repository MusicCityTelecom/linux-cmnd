/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.messaging.simp.broker;

import org.springframework.context.ApplicationEvent;

public class BrokerAvailabilityEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -8156742505179181002L;
    private final boolean brokerAvailable;

    public BrokerAvailabilityEvent(boolean brokerAvailable, Object source) {
        super(source);
        this.brokerAvailable = brokerAvailable;
    }

    public boolean isBrokerAvailable() {
        return this.brokerAvailable;
    }

    public String toString() {
        return "BrokerAvailabilityEvent[available=" + this.brokerAvailable + ", " + this.getSource() + "]";
    }
}

