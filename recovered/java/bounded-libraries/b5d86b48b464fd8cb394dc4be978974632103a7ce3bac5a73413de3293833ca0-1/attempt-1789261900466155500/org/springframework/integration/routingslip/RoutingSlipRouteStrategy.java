/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.routingslip;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface RoutingSlipRouteStrategy {
    public Object getNextPath(Message<?> var1, Object var2);
}

