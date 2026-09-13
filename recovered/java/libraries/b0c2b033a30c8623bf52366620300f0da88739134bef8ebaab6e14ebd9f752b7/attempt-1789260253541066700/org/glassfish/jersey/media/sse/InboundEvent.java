/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.sse.InboundSseEvent;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;

public class InboundEvent
implements InboundSseEvent {
    private static final GenericType<String> STRING_AS_GENERIC_TYPE = new GenericType((Type)((Object)String.class));
    private final String name;
    private final String id;
    private final String comment;
    private final byte[] data;
    private final long reconnectDelay;
    private final MessageBodyWorkers messageBodyWorkers;
    private final Annotation[] annotations;
    private final MediaType mediaType;
    private final MultivaluedMap<String, String> headers;

    private InboundEvent(String name, String id, String comment, long reconnectDelay, byte[] data, MessageBodyWorkers messageBodyWorkers, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers) {
        this.name = name;
        this.id = id;
        this.comment = comment;
        this.reconnectDelay = reconnectDelay;
        this.data = InboundEvent.stripLastLineBreak(data);
        this.messageBodyWorkers = messageBodyWorkers;
        this.annotations = annotations;
        this.mediaType = mediaType;
        this.headers = headers;
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
    public String getComment() {
        return this.comment;
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
    public boolean isEmpty() {
        return this.data.length == 0;
    }

    @Override
    public String readData() {
        return this.readData(STRING_AS_GENERIC_TYPE, null);
    }

    @Override
    public <T> T readData(Class<T> type) {
        return this.readData(new GenericType(type), null);
    }

    @Override
    public <T> T readData(GenericType<T> type) {
        return this.readData(type, null);
    }

    @Override
    public <T> T readData(Class<T> messageType, MediaType mediaType) {
        return this.readData(new GenericType(messageType), mediaType);
    }

    @Override
    public <T> T readData(GenericType<T> type, MediaType mediaType) {
        MediaType effectiveMediaType = mediaType == null ? this.mediaType : mediaType;
        MessageBodyReader<?> reader = this.messageBodyWorkers.getMessageBodyReader(type.getRawType(), type.getType(), this.annotations, mediaType);
        if (reader == null) {
            throw new MessageBodyProviderNotFoundException(LocalizationMessages.EVENT_DATA_READER_NOT_FOUND());
        }
        return this.readAndCast(type, effectiveMediaType, reader);
    }

    private <T> T readAndCast(GenericType<T> type, MediaType effectiveMediaType, MessageBodyReader reader) {
        try {
            return (T)reader.readFrom(type.getRawType(), type.getType(), this.annotations, effectiveMediaType, this.headers, new ByteArrayInputStream(this.data));
        }
        catch (IOException ex) {
            throw new ProcessingException(ex);
        }
    }

    public byte[] getRawData() {
        if (this.isEmpty()) {
            return this.data;
        }
        return Arrays.copyOf(this.data, this.data.length);
    }

    public String toString() {
        String s;
        try {
            s = this.readData();
        }
        catch (ProcessingException e) {
            s = "<Error reading data into a string>";
        }
        return "InboundEvent{name='" + this.name + '\'' + ", id='" + this.id + '\'' + ", comment=" + (this.comment == null ? "[no comments]" : '\'' + this.comment + '\'') + ", data=" + s + '}';
    }

    private static byte[] stripLastLineBreak(byte[] data) {
        if (data.length > 0 && data[data.length - 1] == 10) {
            return Arrays.copyOf(data, data.length - 1);
        }
        return data;
    }

    static class Builder {
        private String name;
        private String id;
        private long reconnectDelay = -1L;
        private final ByteArrayOutputStream dataStream;
        private final MessageBodyWorkers workers;
        private final Annotation[] annotations;
        private final MediaType mediaType;
        private final MultivaluedMap<String, String> headers;
        private final StringBuilder commentBuilder;

        public Builder(MessageBodyWorkers workers, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers) {
            this.workers = workers;
            this.annotations = annotations;
            this.mediaType = mediaType;
            this.headers = headers;
            this.commentBuilder = new StringBuilder();
            this.dataStream = new ByteArrayOutputStream();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder commentLine(CharSequence commentLine) {
            if (commentLine != null) {
                this.commentBuilder.append(commentLine).append('\n');
            }
            return this;
        }

        public Builder reconnectDelay(long milliseconds) {
            if (milliseconds < 0L) {
                milliseconds = -1L;
            }
            this.reconnectDelay = milliseconds;
            return this;
        }

        public Builder write(byte[] data) {
            if (data == null || data.length == 0) {
                return this;
            }
            try {
                this.dataStream.write(data);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return this;
        }

        public InboundEvent build() {
            return new InboundEvent(this.name, this.id, this.commentBuilder.length() > 0 ? this.commentBuilder.substring(0, this.commentBuilder.length() - 1) : null, this.reconnectDelay, this.dataStream.toByteArray(), this.workers, this.annotations, this.mediaType, this.headers);
        }
    }
}

