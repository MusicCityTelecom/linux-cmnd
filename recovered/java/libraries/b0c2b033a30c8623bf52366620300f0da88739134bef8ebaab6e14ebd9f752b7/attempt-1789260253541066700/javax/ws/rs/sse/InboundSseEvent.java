/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEvent;

public interface InboundSseEvent
extends SseEvent {
    public boolean isEmpty();

    public String readData();

    public <T> T readData(Class<T> var1);

    public <T> T readData(GenericType<T> var1);

    public <T> T readData(Class<T> var1, MediaType var2);

    public <T> T readData(GenericType<T> var1, MediaType var2);
}

