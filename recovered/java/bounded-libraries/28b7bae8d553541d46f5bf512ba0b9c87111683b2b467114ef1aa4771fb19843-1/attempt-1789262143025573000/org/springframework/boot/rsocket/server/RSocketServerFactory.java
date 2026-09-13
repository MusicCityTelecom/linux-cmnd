/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.SocketAcceptor
 */
package org.springframework.boot.rsocket.server;

import io.rsocket.SocketAcceptor;
import org.springframework.boot.rsocket.server.RSocketServer;

@FunctionalInterface
public interface RSocketServerFactory {
    public RSocketServer create(SocketAcceptor var1);
}

