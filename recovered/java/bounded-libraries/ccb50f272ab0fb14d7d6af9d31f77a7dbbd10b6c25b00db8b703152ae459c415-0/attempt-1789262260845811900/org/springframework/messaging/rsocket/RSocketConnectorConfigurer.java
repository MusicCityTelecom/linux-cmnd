/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.core.RSocketConnector
 */
package org.springframework.messaging.rsocket;

import io.rsocket.core.RSocketConnector;

@FunctionalInterface
public interface RSocketConnectorConfigurer {
    public void configure(RSocketConnector var1);
}

