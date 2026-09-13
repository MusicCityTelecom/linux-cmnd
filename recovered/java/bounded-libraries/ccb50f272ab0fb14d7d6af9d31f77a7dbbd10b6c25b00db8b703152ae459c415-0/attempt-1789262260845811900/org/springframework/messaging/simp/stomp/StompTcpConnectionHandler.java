/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.tcp.TcpConnectionHandler;

public interface StompTcpConnectionHandler<P>
extends TcpConnectionHandler<P> {
    public String getSessionId();

    public StompHeaderAccessor getConnectHeaders();
}

