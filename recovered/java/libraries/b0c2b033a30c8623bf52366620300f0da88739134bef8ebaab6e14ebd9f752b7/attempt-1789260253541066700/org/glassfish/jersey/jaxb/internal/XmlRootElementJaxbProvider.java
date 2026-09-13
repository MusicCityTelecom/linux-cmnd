/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.InputStream;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.glassfish.jersey.jaxb.internal.AbstractRootElementJaxbProvider;

public abstract class XmlRootElementJaxbProvider
extends AbstractRootElementJaxbProvider {
    private final Provider<SAXParserFactory> spf;

    XmlRootElementJaxbProvider(Provider<SAXParserFactory> spf, Providers ps) {
        super(ps);
        this.spf = spf;
    }

    XmlRootElementJaxbProvider(Provider<SAXParserFactory> spf, Providers ps, MediaType mt) {
        super(ps, mt);
        this.spf = spf;
    }

    @Override
    protected Object readFrom(Class<Object> type, MediaType mediaType, Unmarshaller u, InputStream entityStream) throws JAXBException {
        SAXSource s = XmlRootElementJaxbProvider.getSAXSource(this.spf.get(), entityStream);
        if (type.isAnnotationPresent(XmlRootElement.class)) {
            return u.unmarshal(s);
        }
        return u.unmarshal(s, type).getValue();
    }

    @Produces(value={"*/*"})
    @Consumes(value={"*/*"})
    @Singleton
    public static final class General
    extends XmlRootElementJaxbProvider {
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
    extends XmlRootElementJaxbProvider {
        public Text(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.TEXT_XML_TYPE);
        }
    }

    @Produces(value={"application/xml"})
    @Consumes(value={"application/xml"})
    @Singleton
    public static final class App
    extends XmlRootElementJaxbProvider {
        public App(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.APPLICATION_XML_TYPE);
        }
    }
}

