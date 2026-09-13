/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.XmlHeader;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;
import org.xml.sax.InputSource;

public abstract class AbstractJaxbProvider<T>
extends AbstractMessageReaderWriterProvider<T> {
    private static final Map<Class<?>, WeakReference<JAXBContext>> jaxbContexts = new WeakHashMap();
    private final Providers jaxrsProviders;
    private final boolean fixedResolverMediaType;
    private final Value<ContextResolver<JAXBContext>> mtContext;
    private final Value<ContextResolver<Unmarshaller>> mtUnmarshaller;
    private final Value<ContextResolver<Marshaller>> mtMarshaller;
    private Value<Boolean> formattedOutput = Values.of(Boolean.FALSE);
    private Value<Boolean> xmlRootElementProcessing = Values.of(Boolean.FALSE);

    public AbstractJaxbProvider(Providers providers) {
        this(providers, null);
    }

    public AbstractJaxbProvider(final Providers providers, final MediaType resolverMediaType) {
        this.jaxrsProviders = providers;
        boolean bl = this.fixedResolverMediaType = resolverMediaType != null;
        if (this.fixedResolverMediaType) {
            this.mtContext = Values.lazy(new Value<ContextResolver<JAXBContext>>(){

                @Override
                public ContextResolver<JAXBContext> get() {
                    return providers.getContextResolver(JAXBContext.class, resolverMediaType);
                }
            });
            this.mtUnmarshaller = Values.lazy(new Value<ContextResolver<Unmarshaller>>(){

                @Override
                public ContextResolver<Unmarshaller> get() {
                    return providers.getContextResolver(Unmarshaller.class, resolverMediaType);
                }
            });
            this.mtMarshaller = Values.lazy(new Value<ContextResolver<Marshaller>>(){

                @Override
                public ContextResolver<Marshaller> get() {
                    return providers.getContextResolver(Marshaller.class, resolverMediaType);
                }
            });
        } else {
            this.mtContext = null;
            this.mtUnmarshaller = null;
            this.mtMarshaller = null;
        }
    }

    @Context
    public void setConfiguration(final Configuration config) {
        this.formattedOutput = Values.lazy(new Value<Boolean>(){

            @Override
            public Boolean get() {
                return PropertiesHelper.isProperty(config.getProperty("jersey.config.xml.formatOutput"));
            }
        });
        this.xmlRootElementProcessing = Values.lazy(new Value<Boolean>(){

            @Override
            public Boolean get() {
                return PropertiesHelper.isProperty(config.getProperty("jersey.config.jaxb.collections.processXmlRootElement"));
            }
        });
    }

    protected boolean isSupported(MediaType mediaType) {
        return true;
    }

    protected final Unmarshaller getUnmarshaller(Class type, MediaType mediaType) throws JAXBException {
        Unmarshaller u;
        if (this.fixedResolverMediaType) {
            return this.getUnmarshaller(type);
        }
        ContextResolver<Unmarshaller> unmarshallerResolver = this.jaxrsProviders.getContextResolver(Unmarshaller.class, mediaType);
        if (unmarshallerResolver != null && (u = unmarshallerResolver.getContext(type)) != null) {
            return u;
        }
        JAXBContext ctx = this.getJAXBContext(type, mediaType);
        return ctx == null ? null : ctx.createUnmarshaller();
    }

    private Unmarshaller getUnmarshaller(Class type) throws JAXBException {
        Unmarshaller u;
        ContextResolver<Unmarshaller> resolver = this.mtUnmarshaller.get();
        if (resolver != null && (u = resolver.getContext(type)) != null) {
            return u;
        }
        JAXBContext ctx = this.getJAXBContext(type);
        return ctx == null ? null : ctx.createUnmarshaller();
    }

    protected final Marshaller getMarshaller(Class type, MediaType mediaType) throws JAXBException {
        Marshaller m;
        if (this.fixedResolverMediaType) {
            return this.getMarshaller(type);
        }
        ContextResolver<Marshaller> mcr = this.jaxrsProviders.getContextResolver(Marshaller.class, mediaType);
        if (mcr != null && (m = mcr.getContext(type)) != null) {
            return m;
        }
        JAXBContext ctx = this.getJAXBContext(type, mediaType);
        if (ctx == null) {
            return null;
        }
        Marshaller m2 = ctx.createMarshaller();
        if (this.formattedOutput.get().booleanValue()) {
            m2.setProperty("jaxb.formatted.output", this.formattedOutput.get());
        }
        return m2;
    }

    private Marshaller getMarshaller(Class type) throws JAXBException {
        Marshaller u;
        ContextResolver<Marshaller> resolver = this.mtMarshaller.get();
        if (resolver != null && (u = resolver.getContext(type)) != null) {
            return u;
        }
        JAXBContext ctx = this.getJAXBContext(type);
        if (ctx == null) {
            return null;
        }
        Marshaller m = ctx.createMarshaller();
        if (this.formattedOutput.get().booleanValue()) {
            m.setProperty("jaxb.formatted.output", this.formattedOutput.get());
        }
        return m;
    }

    private JAXBContext getJAXBContext(Class type, MediaType mt) throws JAXBException {
        JAXBContext c;
        ContextResolver<JAXBContext> cr = this.jaxrsProviders.getContextResolver(JAXBContext.class, mt);
        if (cr != null && (c = cr.getContext(type)) != null) {
            return c;
        }
        return this.getStoredJaxbContext(type);
    }

    private JAXBContext getJAXBContext(Class type) throws JAXBException {
        JAXBContext c;
        ContextResolver<JAXBContext> resolver = this.mtContext.get();
        if (resolver != null && (c = resolver.getContext(type)) != null) {
            return c;
        }
        return this.getStoredJaxbContext(type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected JAXBContext getStoredJaxbContext(Class type) throws JAXBException {
        Map<Class<?>, WeakReference<JAXBContext>> map = jaxbContexts;
        synchronized (map) {
            JAXBContext c;
            WeakReference<JAXBContext> ref = jaxbContexts.get(type);
            JAXBContext jAXBContext = c = ref != null ? (JAXBContext)ref.get() : null;
            if (c == null) {
                c = JAXBContext.newInstance(type);
                jaxbContexts.put(type, new WeakReference<JAXBContext>(c));
            }
            return c;
        }
    }

    protected static SAXSource getSAXSource(SAXParserFactory spf, InputStream entityStream) throws JAXBException {
        try {
            return new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(entityStream));
        }
        catch (Exception ex) {
            throw new JAXBException("Error creating SAXSource", ex);
        }
    }

    protected boolean isFormattedOutput() {
        return this.formattedOutput.get();
    }

    protected boolean isXmlRootElementProcessing() {
        return this.xmlRootElementProcessing.get();
    }

    protected void setHeader(Marshaller marshaller, Annotation[] annotations) {
        for (Annotation a : annotations) {
            if (!(a instanceof XmlHeader)) continue;
            try {
                marshaller.setProperty("com.sun.xml.bind.xmlHeaders", ((XmlHeader)a).value());
            }
            catch (PropertyException e) {
                try {
                    marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", ((XmlHeader)a).value());
                }
                catch (PropertyException ex) {
                    Logger.getLogger(AbstractJaxbProvider.class.getName()).log(Level.WARNING, "@XmlHeader annotation is not supported with this JAXB implementation. Please use JAXB RI if you need this feature.");
                }
            }
            break;
        }
    }
}

