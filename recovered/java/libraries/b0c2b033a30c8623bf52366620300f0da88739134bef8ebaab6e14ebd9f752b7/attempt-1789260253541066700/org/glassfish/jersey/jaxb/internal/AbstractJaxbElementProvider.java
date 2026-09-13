/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbProvider;
import org.glassfish.jersey.message.internal.EntityInputStream;

public abstract class AbstractJaxbElementProvider
extends AbstractJaxbProvider<JAXBElement<?>> {
    public AbstractJaxbElementProvider(Providers providers) {
        super(providers);
    }

    public AbstractJaxbElementProvider(Providers providers, MediaType resolverMediaType) {
        super(providers, resolverMediaType);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == JAXBElement.class && genericType instanceof ParameterizedType && this.isSupported(mediaType);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return JAXBElement.class.isAssignableFrom(type) && this.isSupported(mediaType);
    }

    @Override
    public final JAXBElement<?> readFrom(Class<JAXBElement<?>> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream inputStream) throws IOException {
        EntityInputStream entityStream = EntityInputStream.create(inputStream);
        if (entityStream.isEmpty()) {
            throw new NoContentException(LocalizationMessages.ERROR_READING_ENTITY_MISSING());
        }
        ParameterizedType pt = (ParameterizedType)genericType;
        Class ta = (Class)pt.getActualTypeArguments()[0];
        try {
            return this.readFrom(ta, mediaType, this.getUnmarshaller(ta, mediaType), entityStream);
        }
        catch (UnmarshalException ex) {
            throw new BadRequestException(ex);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    protected abstract JAXBElement<?> readFrom(Class<?> var1, MediaType var2, Unmarshaller var3, InputStream var4) throws JAXBException;

    @Override
    public final void writeTo(JAXBElement<?> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        try {
            Marshaller m = this.getMarshaller(t.getDeclaredType(), mediaType);
            Charset c = AbstractJaxbElementProvider.getCharset(mediaType);
            if (c != UTF8) {
                m.setProperty("jaxb.encoding", c.name());
            }
            this.setHeader(m, annotations);
            this.writeTo(t, mediaType, c, m, entityStream);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    protected abstract void writeTo(JAXBElement<?> var1, MediaType var2, Charset var3, Marshaller var4, OutputStream var5) throws JAXBException;
}

