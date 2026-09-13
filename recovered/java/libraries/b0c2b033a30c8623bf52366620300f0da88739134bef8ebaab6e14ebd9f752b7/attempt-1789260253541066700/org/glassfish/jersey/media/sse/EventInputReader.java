/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.ReaderInterceptorExecutor;

class EventInputReader
implements MessageBodyReader<EventInput> {
    @Inject
    private Provider<MessageBodyWorkers> messageBodyWorkers;
    @Inject
    private Provider<PropertiesDelegate> propertiesDelegateProvider;

    EventInputReader() {
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.equals(EventInput.class);
    }

    @Override
    public EventInput readFrom(Class<EventInput> chunkedInputClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers, InputStream inputStream) throws IOException, WebApplicationException {
        InputStream closeableInputStream = ReaderInterceptorExecutor.closeableInputStream(inputStream);
        return new EventInput(closeableInputStream, annotations, mediaType, headers, this.messageBodyWorkers.get(), this.propertiesDelegateProvider.get());
    }
}

