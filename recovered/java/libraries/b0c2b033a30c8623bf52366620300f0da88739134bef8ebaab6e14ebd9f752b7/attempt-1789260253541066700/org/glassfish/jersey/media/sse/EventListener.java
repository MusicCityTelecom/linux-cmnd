/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import org.glassfish.jersey.media.sse.InboundEvent;

public interface EventListener {
    public void onEvent(InboundEvent var1);
}

