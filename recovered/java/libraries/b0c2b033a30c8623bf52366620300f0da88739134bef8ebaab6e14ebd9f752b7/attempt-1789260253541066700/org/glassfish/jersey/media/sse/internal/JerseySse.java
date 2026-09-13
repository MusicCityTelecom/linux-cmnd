/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.util.concurrent.ExecutorService;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.internal.JerseySseBroadcaster;

class JerseySse
implements Sse {
    @Context
    private ExecutorService executorService;

    JerseySse() {
    }

    @Override
    public OutboundSseEvent.Builder newEventBuilder() {
        return new OutboundEvent.Builder();
    }

    @Override
    public SseBroadcaster newBroadcaster() {
        return new JerseySseBroadcaster(this.executorService);
    }
}

