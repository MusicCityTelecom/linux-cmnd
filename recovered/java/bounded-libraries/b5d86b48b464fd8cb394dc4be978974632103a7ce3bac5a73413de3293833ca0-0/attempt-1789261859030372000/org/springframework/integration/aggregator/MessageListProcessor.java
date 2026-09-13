/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.Collection;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageListProcessor {
    public Object process(Collection<? extends Message<?>> var1);
}

