/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.stomp.ConnectionHandlingStompSession;
import org.springframework.messaging.simp.stomp.StompClientSupport;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.tcp.TcpOperations;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

public class ReactorNettyTcpStompClient
extends StompClientSupport {
    private final TcpOperations<byte[]> tcpClient;

    public ReactorNettyTcpStompClient() {
        this("127.0.0.1", 61613);
    }

    public ReactorNettyTcpStompClient(String host, int port) {
        this.tcpClient = ReactorNettyTcpStompClient.initTcpClient(host, port);
    }

    public ReactorNettyTcpStompClient(TcpOperations<byte[]> tcpClient) {
        Assert.notNull(tcpClient, (String)"'tcpClient' is required");
        this.tcpClient = tcpClient;
    }

    private static ReactorNettyTcpClient<byte[]> initTcpClient(String host, int port) {
        ReactorNettyTcpClient<byte[]> client = new ReactorNettyTcpClient<byte[]>(host, port, new StompReactorNettyCodec());
        client.setLogger(SimpLogging.forLog(client.getLogger()));
        return client;
    }

    public ListenableFuture<StompSession> connect(StompSessionHandler handler) {
        return this.connect(null, handler);
    }

    public ListenableFuture<StompSession> connect(@Nullable StompHeaders connectHeaders, StompSessionHandler handler) {
        ConnectionHandlingStompSession session = this.createSession(connectHeaders, handler);
        this.tcpClient.connect(session);
        return session.getSessionFuture();
    }

    public void shutdown() {
        this.tcpClient.shutdown();
    }

    public String toString() {
        return "ReactorNettyTcpStompClient[" + this.tcpClient + "]";
    }
}

