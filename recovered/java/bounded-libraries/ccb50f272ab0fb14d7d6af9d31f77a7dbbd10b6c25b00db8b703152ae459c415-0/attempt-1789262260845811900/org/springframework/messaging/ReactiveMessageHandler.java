/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging;

import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ReactiveMessageHandler {
    public Mono<Void> handleMessage(Message<?> var1);
}

