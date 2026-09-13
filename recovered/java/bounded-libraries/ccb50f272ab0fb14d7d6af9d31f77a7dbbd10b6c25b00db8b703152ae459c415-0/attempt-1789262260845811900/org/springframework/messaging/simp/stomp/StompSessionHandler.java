/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

public interface StompSessionHandler
extends StompFrameHandler {
    public void afterConnected(StompSession var1, StompHeaders var2);

    public void handleException(StompSession var1, @Nullable StompCommand var2, StompHeaders var3, byte[] var4, Throwable var5);

    public void handleTransportError(StompSession var1, Throwable var2);
}

