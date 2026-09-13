/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.stream.StreamSource;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbProvider;
import org.glassfish.jersey.message.internal.EntityInputStream;

public abstract class AbstractRootElementJaxbProvider
extends AbstractJaxbProvider<Object> {
    public AbstractRootElementJaxbProvider(Providers providers) {
        super(providers);
    }

    public AbstractRootElementJaxbProvider(Providers providers, MediaType resolverMediaType) {
        super(providers, resolverMediaType);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (type.getAnnotation(XmlRootElement.class) != null || type.getAnnotation(XmlType.class) != null) && this.isSupported(mediaType);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.getAnnotation(XmlRootElement.class) != null && this.isSupported(mediaType);
    }

    @Override
    public final Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream inputStream) throws IOException {
        try {
            EntityInputStream entityStream = EntityInputStream.create(inputStream);
            if (entityStream.isEmpty()) {
                throw new NoContentException(LocalizationMessages.ERROR_READING_ENTITY_MISSING());
            }
            return this.readFrom(type, mediaType, this.getUnmarshaller(type, mediaType), entityStream);
        }
        catch (UnmarshalException ex) {
            throw new BadRequestException(ex);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    protected Object readFrom(Class<Object> type, MediaType mediaType, Unmarshaller u, InputStream entityStream) throws JAXBException {
        if (type.isAnnotationPresent(XmlRootElement.class)) {
            return u.unmarshal(entityStream);
        }
        return u.unmarshal(new StreamSource(entityStream), type).getValue();
    }

    @Override
    public final void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        try {
            Marshaller m = this.getMarshaller(type, mediaType);
            Charset c = AbstractRootElementJaxbProvider.getCharset(mediaType);
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

    protected void writeTo(Object t, MediaType mediaType, Charset c, Marshaller m, OutputStream entityStream) throws JAXBException {
        m.marshal(t, entityStream);
    }
}

