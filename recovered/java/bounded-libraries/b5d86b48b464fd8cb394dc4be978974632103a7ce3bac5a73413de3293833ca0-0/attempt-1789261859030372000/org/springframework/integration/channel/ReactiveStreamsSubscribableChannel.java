/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.channel;

import org.reactivestreams.Publisher;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.messaging.Message;

public interface ReactiveStreamsSubscribableChannel
extends IntegrationPattern {
    public void subscribeTo(Publisher<? extends Message<?>> var1);

    @Override
    default public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.reactive_channel;
    }
}

