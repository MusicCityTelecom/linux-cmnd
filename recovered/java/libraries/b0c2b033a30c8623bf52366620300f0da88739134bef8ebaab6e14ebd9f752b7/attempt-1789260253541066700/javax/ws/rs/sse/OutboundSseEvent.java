/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import java.lang.reflect.Type;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEvent;

public interface OutboundSseEvent
extends SseEvent {
    public Class<?> getType();

    public Type getGenericType();

    public MediaType getMediaType();

    public Object getData();

    public static interface Builder {
        public Builder id(String var1);

        public Builder name(String var1);

        public Builder reconnectDelay(long var1);

        public Builder mediaType(MediaType var1);

        public Builder comment(String var1);

        public Builder data(Class var1, Object var2);

        public Builder data(GenericType var1, Object var2);

        public Builder data(Object var1);

        public OutboundSseEvent build();
    }
}

