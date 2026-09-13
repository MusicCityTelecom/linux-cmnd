/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.internal.util.collection.NullableMultivaluedHashMap;
import org.glassfish.jersey.message.internal.AbstractFormProvider;

@Produces(value={"application/x-www-form-urlencoded"})
@Consumes(value={"application/x-www-form-urlencoded"})
@Singleton
public final class FormMultivaluedMapProvider
extends AbstractFormProvider<MultivaluedMap<String, String>> {
    private final Type mapType;

    public FormMultivaluedMapProvider() {
        ParameterizedType iface = (ParameterizedType)this.getClass().getGenericSuperclass();
        this.mapType = iface.getActualTypeArguments()[0];
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == MultivaluedMap.class && (type == genericType || this.mapType.equals(genericType));
    }

    @Override
    public MultivaluedMap<String, String> readFrom(Class<MultivaluedMap<String, String>> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        return this.readFrom(new NullableMultivaluedHashMap(), mediaType, true, entityStream);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return MultivaluedMap.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(MultivaluedMap<String, String> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        this.writeTo(t, mediaType, entityStream);
    }
}

