/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.tcp;

import org.springframework.messaging.Message;
import org.springframework.messaging.tcp.TcpConnection;

public interface TcpConnectionHandler<P> {
    public void afterConnected(TcpConnection<P> var1);

    public void afterConnectFailure(Throwable var1);

    public void handleMessage(Message<P> var1);

    public void handleFailure(Throwable var1);

    public void afterConnectionClosed();
}

