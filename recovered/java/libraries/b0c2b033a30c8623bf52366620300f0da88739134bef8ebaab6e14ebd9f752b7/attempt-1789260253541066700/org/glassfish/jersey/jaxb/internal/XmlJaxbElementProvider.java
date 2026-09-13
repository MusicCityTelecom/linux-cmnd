/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbElementProvider;

public abstract class XmlJaxbElementProvider
extends AbstractJaxbElementProvider {
    private final Provider<SAXParserFactory> spf;

    public XmlJaxbElementProvider(Provider<SAXParserFactory> spf, Providers ps) {
        super(ps);
        this.spf = spf;
    }

    public XmlJaxbElementProvider(Provider<SAXParserFactory> spf, Providers ps, MediaType mt) {
        super(ps, mt);
        this.spf = spf;
    }

    @Override
    protected final JAXBElement<?> readFrom(Class<?> type, MediaType mediaType, Unmarshaller unmarshaller, InputStream entityStream) throws JAXBException {
        return unmarshaller.unmarshal(XmlJaxbElementProvider.getSAXSource(this.spf.get(), entityStream), type);
    }

    @Override
    protected final void writeTo(JAXBElement<?> t, MediaType mediaType, Charset c, Marshaller m, OutputStream entityStream) throws JAXBException {
        m.marshal(t, entityStream);
    }

    @Produces(value={"*/*,*/*+xml"})
    @Consumes(value={"*/*,*/*+xml"})
    @Singleton
    public static final class General
    extends XmlJaxbElementProvider {
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
    extends XmlJaxbElementProvider {
        public Text(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.TEXT_XML_TYPE);
        }
    }

    @Produces(value={"application/xml"})
    @Consumes(value={"application/xml"})
    @Singleton
    public static final class App
    extends XmlJaxbElementProvider {
        public App(@Context Provider<SAXParserFactory> spf, @Context Providers ps) {
            super(spf, ps, MediaType.APPLICATION_XML_TYPE);
        }
    }
}

