/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.client.ChunkParser;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.message.MessageBodyWorkers;

public class EventInput
extends ChunkedInput<InboundEvent> {
    private static final ChunkParser SSE_EVENT_PARSER = ChunkedInput.createMultiParser("\n\n", "\r\n\r\n");

    EventInput(InputStream inputStream, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers, MessageBodyWorkers messageBodyWorkers, PropertiesDelegate propertiesDelegate) {
        super((Type)((Object)InboundEvent.class), inputStream, annotations, mediaType, headers, messageBodyWorkers, propertiesDelegate);
        super.setParser(SSE_EVENT_PARSER);
    }
}

