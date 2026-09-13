/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface CorrelationStrategy {
    public Object getCorrelationKey(Message<?> var1);
}

