/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.tcp;

import org.springframework.messaging.tcp.ReconnectStrategy;
import org.springframework.messaging.tcp.TcpConnectionHandler;
import org.springframework.util.concurrent.ListenableFuture;

public interface TcpOperations<P> {
    public ListenableFuture<Void> connect(TcpConnectionHandler<P> var1);

    public ListenableFuture<Void> connect(TcpConnectionHandler<P> var1, ReconnectStrategy var2);

    public ListenableFuture<Void> shutdown();
}

