/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.MessageBodyWorkers;

@ConstrainedTo(value=RuntimeType.CLIENT)
class ChunkedInputReader
implements MessageBodyReader<ChunkedInput> {
    @Inject
    private Provider<MessageBodyWorkers> messageBodyWorkers;
    @Inject
    private Provider<PropertiesDelegate> propertiesDelegateProvider;

    ChunkedInputReader() {
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass.equals(ChunkedInput.class);
    }

    @Override
    public ChunkedInput readFrom(Class<ChunkedInput> chunkedInputClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers, InputStream inputStream) throws IOException, WebApplicationException {
        Type chunkType = ReflectionHelper.getTypeArgument(type, 0);
        return new ChunkedInput(chunkType, inputStream, annotations, mediaType, headers, this.messageBodyWorkers.get(), this.propertiesDelegateProvider.get());
    }
}

