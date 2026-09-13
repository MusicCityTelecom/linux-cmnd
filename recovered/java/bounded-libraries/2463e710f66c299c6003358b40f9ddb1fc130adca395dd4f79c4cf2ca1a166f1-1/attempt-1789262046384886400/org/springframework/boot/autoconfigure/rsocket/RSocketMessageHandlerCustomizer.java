/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
 */
package org.springframework.boot.autoconfigure.rsocket;

import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

@FunctionalInterface
public interface RSocketMessageHandlerCustomizer {
    public void customize(RSocketMessageHandler var1);
}

