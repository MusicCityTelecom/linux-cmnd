/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.lang.reflect.Type;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.media.sse.LocalizationMessages;

public final class OutboundEvent
implements OutboundSseEvent {
    private final String name;
    private final String comment;
    private final String id;
    private final GenericType type;
    private final MediaType mediaType;
    private final Object data;
    private final long reconnectDelay;

    OutboundEvent(String name, String id, long reconnectDelay, GenericType type, MediaType mediaType, Object data, String comment) {
        this.name = name;
        this.comment = comment;
        this.id = id;
        this.reconnectDelay = reconnectDelay;
        this.type = type;
        this.mediaType = mediaType;
        this.data = data;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getReconnectDelay() {
        return this.reconnectDelay;
    }

    @Override
    public boolean isReconnectDelaySet() {
        return this.reconnectDelay > -1L;
    }

    @Override
    public Class<?> getType() {
        return this.type == null ? null : this.type.getRawType();
    }

    @Override
    public Type getGenericType() {
        return this.type == null ? null : this.type.getType();
    }

    @Override
    public MediaType getMediaType() {
        return this.mediaType;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    public static class Builder
    implements OutboundSseEvent.Builder {
        private String name;
        private String comment;
        private String id;
        private long reconnectDelay = -1L;
        private GenericType type;
        private Object data;
        private MediaType mediaType = MediaType.TEXT_PLAIN_TYPE;

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder reconnectDelay(long milliseconds) {
            if (milliseconds < 0L) {
                milliseconds = -1L;
            }
            this.reconnectDelay = milliseconds;
            return this;
        }

        @Override
        public Builder mediaType(MediaType mediaType) {
            if (mediaType == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_MEDIA_TYPE_NULL());
            }
            this.mediaType = mediaType;
            return this;
        }

        @Override
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        @Override
        public Builder data(Class type, Object data) {
            if (data == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_DATA_NULL());
            }
            if (type == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_DATA_TYPE_NULL());
            }
            this.type = new GenericType(type);
            this.data = data;
            return this;
        }

        @Override
        public Builder data(GenericType type, Object data) {
            if (data == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_DATA_NULL());
            }
            if (type == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_DATA_TYPE_NULL());
            }
            this.type = type;
            this.data = data instanceof GenericEntity ? ((GenericEntity)data).getEntity() : data;
            return this;
        }

        @Override
        public Builder data(Object data) {
            if (data == null) {
                throw new NullPointerException(LocalizationMessages.OUT_EVENT_DATA_NULL());
            }
            return this.data(ReflectionHelper.genericTypeFor(data), data);
        }

        @Override
        public OutboundEvent build() {
            if (this.comment == null && this.data == null && this.type == null) {
                throw new IllegalStateException(LocalizationMessages.OUT_EVENT_NOT_BUILDABLE());
            }
            return new OutboundEvent(this.name, this.id, this.reconnectDelay, this.type, this.mediaType, this.data, this.comment);
        }
    }
}

