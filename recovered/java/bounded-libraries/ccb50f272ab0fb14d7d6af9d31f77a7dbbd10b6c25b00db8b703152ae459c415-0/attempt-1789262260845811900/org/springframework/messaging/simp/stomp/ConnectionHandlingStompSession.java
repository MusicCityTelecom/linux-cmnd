/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompTcpConnectionHandler;
import org.springframework.util.concurrent.ListenableFuture;

public interface ConnectionHandlingStompSession
extends StompSession,
StompTcpConnectionHandler<byte[]> {
    public ListenableFuture<StompSession> getSessionFuture();
}

