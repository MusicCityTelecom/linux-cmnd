/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.parsers.SAXParserFactory;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbProvider;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.EntityInputStream;

public abstract class XmlRootObjectJaxbProvider
extends AbstractJaxbProvider<Object> {
    private final Provider<SAXParserFactory> spf;

    XmlRootObjectJaxbProvider(Provider<SAXParserFactory> spf, Providers ps) {
        super(ps);
        this.spf = spf;
    }

    XmlRootObjectJaxbProvider(Provider<SAXParserFactory> spf, Providers ps, MediaType mt) {
        super(ps, mt);
        this.spf = spf;
    }

    @Override
    protected JAXBContext getStoredJaxbContext(Class type) throws JAXBException {
        return null;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        try {
            return Object.class == type && this.isSupported(mediaType) && this.getUnmarshaller(type, mediaType) != null;
        }
        catch (JAXBException cause) {
            throw new RuntimeException(LocalizationMessages.ERROR_UNMARSHALLING_JAXB(type), cause);
        }
    }

    @Override
    public final Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream inputStream) throws IOException {
        EntityInputStream entityStream = EntityInputStream.create(inputStream);
        if (entityStream.isEmpty()) {
            throw new NoContentException(LocalizationMessages.ERROR_READING_ENTITY_MISSING());
        }
        try {
            return this.getUnmarshaller(type, mediaType).unmarshal(XmlRootObjectJaxbProvider.getSAXSource(this.spf.get(), entityStream));
        }
        catch (UnmarshalException ex) {
            throw new BadRequestException(ex);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType mediaType) {
        return false;
    }

    @Override
    public void writeTo(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4, MultivaluedMap<String, Object> arg5, OutputStream arg6) throws IOException, WebApplicationException {
        throw new IllegalArgumentException();
    }

    @Produces(value={"*/*"})
    @Consumes(value={"*/*"})
    @Singleton
    public static final class General
    extends XmlRootObjectJaxbProvider {
        public General(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps);
        }

        @Override
        protected boolean isSupported(MediaType m) {
            return m.getSubtype().endsWith("+xml");
        }
    }

    @Produces(value={"text/xml"})
    @Consumes(value={"text/xml"})
    @Singleton
    public static final class Text
    extends XmlRootObjectJaxbProvider {
        public Text(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.TEXT_XML_TYPE);
        }
    }

    @Produces(value={"application/xml"})
    @Consumes(value={"application/xml"})
    @Singleton
    public static final class App
    extends XmlRootObjectJaxbProvider {
        public App(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.APPLICATION_XML_TYPE);
        }
    }
}

