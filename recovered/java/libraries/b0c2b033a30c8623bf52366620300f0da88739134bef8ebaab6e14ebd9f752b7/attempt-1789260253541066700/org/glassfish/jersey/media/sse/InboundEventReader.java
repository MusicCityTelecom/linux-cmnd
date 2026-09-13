/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.MessageUtils;

@ConstrainedTo(value=RuntimeType.CLIENT)
class InboundEventReader
implements MessageBodyReader<InboundEvent> {
    private static final Logger LOGGER = Logger.getLogger(InboundEventReader.class.getName());
    private static final byte[] EOL_DATA = new byte[]{10};
    @Inject
    private Provider<MessageBodyWorkers> messageBodyWorkers;

    InboundEventReader() {
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return InboundEvent.class.equals(type) && SseFeature.SERVER_SENT_EVENTS_TYPE.isCompatible(mediaType);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public InboundEvent readFrom(Class<InboundEvent> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers, InputStream entityStream) throws IOException, WebApplicationException {
        ByteArrayOutputStream tokenData = new ByteArrayOutputStream();
        String charsetName = MessageUtils.getCharset(mediaType).name();
        InboundEvent.Builder eventBuilder = new InboundEvent.Builder(this.messageBodyWorkers.get(), annotations, mediaType, headers);
        int b = -1;
        State currentState = State.SKIPPING_PREPENDED_EMPTY_EVENTS;
        block5: do {
            switch (currentState) {
                case SKIPPING_PREPENDED_EMPTY_EVENTS: 
                case NEW_LINE: {
                    b = b == 13 ? ((b = entityStream.read()) == 10 ? entityStream.read() : b) : entityStream.read();
                    if (b == 10 || b == 13 || b == -1) {
                        if (currentState != State.SKIPPING_PREPENDED_EMPTY_EVENTS) break block5;
                        break;
                    }
                    if (b == 58) {
                        currentState = State.COMMENT;
                        break;
                    }
                    tokenData.write(b);
                    currentState = State.FIELD;
                    break;
                }
                case COMMENT: {
                    b = this.readLineUntil(entityStream, 10, tokenData);
                    String commentLine = tokenData.toString(charsetName);
                    tokenData.reset();
                    eventBuilder.commentLine(commentLine.trim());
                    currentState = State.NEW_LINE;
                    break;
                }
                case FIELD: {
                    b = this.readLineUntil(entityStream, 58, tokenData);
                    String fieldName = tokenData.toString(charsetName);
                    tokenData.reset();
                    if (b == 58) {
                        while ((b = entityStream.read()) == 32) {
                        }
                        if (b != 10 && b != 13 && b != -1) {
                            tokenData.write(b);
                            b = this.readLineUntil(entityStream, 10, tokenData);
                        }
                    }
                    this.processField(eventBuilder, fieldName, mediaType, tokenData.toByteArray());
                    tokenData.reset();
                    currentState = State.NEW_LINE;
                }
            }
        } while (b != -1);
        return eventBuilder.build();
    }

    private int readLineUntil(InputStream in, int delimiter, OutputStream out) throws IOException {
        int b;
        while ((b = in.read()) != -1 && b != delimiter && b != 10 && b != 13) {
            if (out == null) continue;
            out.write(b);
        }
        return b;
    }

    private void processField(InboundEvent.Builder inboundEventBuilder, String name, MediaType mediaType, byte[] value) {
        String valueString = new String(value, MessageUtils.getCharset(mediaType));
        if ("event".equals(name)) {
            inboundEventBuilder.name(valueString);
        } else if ("data".equals(name)) {
            inboundEventBuilder.write(value);
            inboundEventBuilder.write(EOL_DATA);
        } else if ("id".equals(name)) {
            inboundEventBuilder.id(valueString);
        } else if ("retry".equals(name)) {
            try {
                inboundEventBuilder.reconnectDelay(Long.parseLong(valueString));
            }
            catch (NumberFormatException ex) {
                LOGGER.log(Level.FINE, LocalizationMessages.IN_EVENT_RETRY_PARSE_ERROR(valueString), ex);
            }
        } else {
            LOGGER.fine(LocalizationMessages.IN_EVENT_FIELD_NOT_RECOGNIZED(name, valueString));
        }
    }

    private static enum State {
        SKIPPING_PREPENDED_EMPTY_EVENTS,
        NEW_LINE,
        COMMENT,
        FIELD;

    }
}

