/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer.support;

import org.springframework.integration.transformer.support.AbstractHeaderValueMessageProcessor;
import org.springframework.messaging.Message;

public class StaticHeaderValueMessageProcessor<T>
extends AbstractHeaderValueMessageProcessor<T> {
    private final T value;

    public StaticHeaderValueMessageProcessor(T value) {
        this.value = value;
    }

    @Override
    public T processMessage(Message<?> message) {
        return this.value;
    }
}

