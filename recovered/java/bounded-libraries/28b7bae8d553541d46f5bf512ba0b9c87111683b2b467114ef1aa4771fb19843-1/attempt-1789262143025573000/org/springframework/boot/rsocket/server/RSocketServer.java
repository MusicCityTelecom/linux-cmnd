/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.rsocket.server;

import java.net.InetSocketAddress;
import org.springframework.boot.rsocket.server.RSocketServerException;

public interface RSocketServer {
    public void start() throws RSocketServerException;

    public void stop() throws RSocketServerException;

    public InetSocketAddress address();

    public static enum Transport {
        TCP,
        WEBSOCKET;

    }
}

