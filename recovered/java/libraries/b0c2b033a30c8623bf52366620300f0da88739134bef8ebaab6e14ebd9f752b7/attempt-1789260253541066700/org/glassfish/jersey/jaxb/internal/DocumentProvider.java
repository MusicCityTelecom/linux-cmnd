/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Produces(value={"application/xml", "text/xml", "*/*"})
@Consumes(value={"application/xml", "text/xml", "*/*"})
@Singleton
public final class DocumentProvider
extends AbstractMessageReaderWriterProvider<Document> {
    @Inject
    private Provider<DocumentBuilderFactory> dbf;
    @Inject
    private Provider<TransformerFactory> tf;

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Document.class == type;
    }

    @Override
    public Document readFrom(Class<Document> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        try {
            return this.dbf.get().newDocumentBuilder().parse(entityStream);
        }
        catch (SAXException ex) {
            throw new BadRequestException(ex);
        }
        catch (ParserConfigurationException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Document.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(Document t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        try {
            StreamResult sr = new StreamResult(entityStream);
            this.tf.get().newTransformer().transform(new DOMSource(t), sr);
        }
        catch (TransformerException ex) {
            throw new InternalServerErrorException(ex);
        }
    }
}

