/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.stomp;

import java.lang.reflect.Type;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompHeaders;

public interface StompFrameHandler {
    public Type getPayloadType(StompHeaders var1);

    public void handleFrame(StompHeaders var1, @Nullable Object var2);
}

