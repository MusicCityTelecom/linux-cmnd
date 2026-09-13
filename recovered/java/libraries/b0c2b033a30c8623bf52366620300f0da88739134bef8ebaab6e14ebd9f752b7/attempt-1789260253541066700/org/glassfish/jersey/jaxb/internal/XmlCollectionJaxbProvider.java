/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.glassfish.jersey.jaxb.internal.AbstractCollectionJaxbProvider;

public abstract class XmlCollectionJaxbProvider
extends AbstractCollectionJaxbProvider {
    private final Provider<XMLInputFactory> xif;

    XmlCollectionJaxbProvider(Provider<XMLInputFactory> xif, Providers ps) {
        super(ps);
        this.xif = xif;
    }

    XmlCollectionJaxbProvider(Provider<XMLInputFactory> xif, Providers ps, MediaType mt) {
        super(ps, mt);
        this.xif = xif;
    }

    @Override
    protected final XMLStreamReader getXMLStreamReader(Class<?> elementType, MediaType mediaType, Unmarshaller u, InputStream entityStream) throws XMLStreamException {
        return this.xif.get().createXMLStreamReader(entityStream);
    }

    @Override
    public final void writeCollection(Class<?> elementType, Collection<?> t, MediaType mediaType, Charset c, Marshaller m, OutputStream entityStream) throws JAXBException, IOException {
        String header;
        String rootElement = this.getRootElementName(elementType);
        String cName = c.name();
        entityStream.write(String.format("<?xml version=\"1.0\" encoding=\"%s\" standalone=\"yes\"?>", cName).getBytes(cName));
        String property = "com.sun.xml.bind.xmlHeaders";
        try {
            header = (String)m.getProperty(property);
        }
        catch (PropertyException e) {
            property = "com.sun.xml.internal.bind.xmlHeaders";
            try {
                header = (String)m.getProperty(property);
            }
            catch (PropertyException ex) {
                header = null;
                Logger.getLogger(XmlCollectionJaxbProvider.class.getName()).log(Level.WARNING, "@XmlHeader annotation is not supported with this JAXB implementation. Please use JAXB RI if you need this feature.");
            }
        }
        if (header != null) {
            m.setProperty(property, "");
            entityStream.write(header.getBytes(cName));
        }
        entityStream.write(String.format("<%s>", rootElement).getBytes(cName));
        for (Object o : t) {
            m.marshal(o, entityStream);
        }
        entityStream.write(String.format("</%s>", rootElement).getBytes(cName));
    }

    @Produces(value={"*/*"})
    @Consumes(value={"*/*"})
    @Singleton
    public static final class General
    extends XmlCollectionJaxbProvider {
        public General(@Context Provider<XMLInputFactory> xif, @Context Providers ps) {
            super(xif, ps);
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
    extends XmlCollectionJaxbProvider {
        public Text(@Context Provider<XMLInputFactory> xif, @Context Providers ps) {
            super(xif, ps, MediaType.TEXT_XML_TYPE);
        }
    }

    @Produces(value={"application/xml"})
    @Consumes(value={"application/xml"})
    @Singleton
    public static final class App
    extends XmlCollectionJaxbProvider {
        public App(@Context Provider<XMLInputFactory> xif, @Context Providers ps) {
            super(xif, ps, MediaType.APPLICATION_XML_TYPE);
        }
    }
}

