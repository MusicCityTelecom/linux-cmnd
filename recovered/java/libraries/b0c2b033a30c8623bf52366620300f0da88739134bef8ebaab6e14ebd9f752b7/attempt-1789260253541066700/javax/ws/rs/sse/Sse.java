/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;

public interface Sse {
    public OutboundSseEvent.Builder newEventBuilder();

    default public OutboundSseEvent newEvent(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter 'data' must not be null.");
        }
        return this.newEventBuilder().data(String.class, (Object)data).build();
    }

    default public OutboundSseEvent newEvent(String name, String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter 'data' must not be null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' must not be null.");
        }
        return this.newEventBuilder().data(String.class, (Object)data).name(name).build();
    }

    public SseBroadcaster newBroadcaster();
}

