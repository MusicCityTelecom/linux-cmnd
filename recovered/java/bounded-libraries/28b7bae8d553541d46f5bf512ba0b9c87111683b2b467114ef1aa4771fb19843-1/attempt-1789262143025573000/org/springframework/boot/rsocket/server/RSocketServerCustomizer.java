/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.core.RSocketServer
 */
package org.springframework.boot.rsocket.server;

import io.rsocket.core.RSocketServer;

@FunctionalInterface
public interface RSocketServerCustomizer {
    public void customize(RSocketServer var1);
}

