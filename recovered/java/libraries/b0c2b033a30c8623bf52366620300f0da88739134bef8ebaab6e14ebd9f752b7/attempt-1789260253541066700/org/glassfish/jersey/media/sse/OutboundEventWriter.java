/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.sse.OutboundSseEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.MessageUtils;

class OutboundEventWriter
implements MessageBodyWriter<OutboundSseEvent> {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final byte[] COMMENT_LEAD = ": ".getBytes(UTF8);
    private static final byte[] NAME_LEAD = "event: ".getBytes(UTF8);
    private static final byte[] ID_LEAD = "id: ".getBytes(UTF8);
    private static final byte[] RETRY_LEAD = "retry: ".getBytes(UTF8);
    private static final byte[] DATA_LEAD = "data: ".getBytes(UTF8);
    private static final byte[] EOL = new byte[]{10};
    @Inject
    private Provider<MessageBodyWorkers> workersProvider;

    OutboundEventWriter() {
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return OutboundSseEvent.class.isAssignableFrom(type) && SseFeature.SERVER_SENT_EVENTS_TYPE.isCompatible(mediaType);
    }

    @Override
    public long getSize(OutboundSseEvent incomingEvent, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1L;
    }

    @Override
    public void writeTo(OutboundSseEvent outboundEvent, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        Charset charset = MessageUtils.getCharset(mediaType);
        if (outboundEvent.getComment() != null) {
            for (String comment : outboundEvent.getComment().split("\n")) {
                entityStream.write(COMMENT_LEAD);
                entityStream.write(comment.getBytes(charset));
                entityStream.write(EOL);
            }
        }
        if (outboundEvent.getType() != null) {
            if (outboundEvent.getName() != null) {
                entityStream.write(NAME_LEAD);
                entityStream.write(outboundEvent.getName().getBytes(charset));
                entityStream.write(EOL);
            }
            if (outboundEvent.getId() != null) {
                entityStream.write(ID_LEAD);
                entityStream.write(outboundEvent.getId().getBytes(charset));
                entityStream.write(EOL);
            }
            if (outboundEvent.getReconnectDelay() > -1L) {
                entityStream.write(RETRY_LEAD);
                entityStream.write(Long.toString(outboundEvent.getReconnectDelay()).getBytes(charset));
                entityStream.write(EOL);
            }
            MediaType eventMediaType = outboundEvent.getMediaType() == null ? MediaType.TEXT_PLAIN_TYPE : outboundEvent.getMediaType();
            MessageBodyWriter<?> messageBodyWriter = this.workersProvider.get().getMessageBodyWriter(outboundEvent.getType(), outboundEvent.getGenericType(), annotations, eventMediaType);
            messageBodyWriter.writeTo(outboundEvent.getData(), outboundEvent.getType(), outboundEvent.getGenericType(), annotations, eventMediaType, httpHeaders, new OutputStream(){
                private boolean start = true;

                @Override
                public void write(int i) throws IOException {
                    if (this.start) {
                        entityStream.write(DATA_LEAD);
                        this.start = false;
                    }
                    entityStream.write(i);
                    if (i == 10) {
                        entityStream.write(DATA_LEAD);
                    }
                }
            });
            entityStream.write(EOL);
        }
    }
}

