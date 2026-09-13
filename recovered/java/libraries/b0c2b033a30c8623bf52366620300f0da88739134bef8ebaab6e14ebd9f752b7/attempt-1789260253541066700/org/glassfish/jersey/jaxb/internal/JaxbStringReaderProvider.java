/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;
import javax.inject.Provider;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;
import org.xml.sax.InputSource;

public class JaxbStringReaderProvider {
    private static final Map<Class, JAXBContext> jaxbContexts = new WeakHashMap<Class, JAXBContext>();
    private final Value<ContextResolver<JAXBContext>> mtContext;
    private final Value<ContextResolver<Unmarshaller>> mtUnmarshaller;

    public JaxbStringReaderProvider(final Providers ps) {
        this.mtContext = Values.lazy(new Value<ContextResolver<JAXBContext>>(){

            @Override
            public ContextResolver<JAXBContext> get() {
                return ps.getContextResolver(JAXBContext.class, null);
            }
        });
        this.mtUnmarshaller = Values.lazy(new Value<ContextResolver<Unmarshaller>>(){

            @Override
            public ContextResolver<Unmarshaller> get() {
                return ps.getContextResolver(Unmarshaller.class, null);
            }
        });
    }

    protected final Unmarshaller getUnmarshaller(Class type) throws JAXBException {
        Unmarshaller u;
        ContextResolver<Unmarshaller> unmarshallerContextResolver = this.mtUnmarshaller.get();
        if (unmarshallerContextResolver != null && (u = unmarshallerContextResolver.getContext(type)) != null) {
            return u;
        }
        return this.getJAXBContext(type).createUnmarshaller();
    }

    private JAXBContext getJAXBContext(Class type) throws JAXBException {
        JAXBContext c;
        ContextResolver<JAXBContext> jaxbContextContextResolver = this.mtContext.get();
        if (jaxbContextContextResolver != null && (c = jaxbContextContextResolver.getContext(type)) != null) {
            return c;
        }
        return this.getStoredJAXBContext(type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected JAXBContext getStoredJAXBContext(Class type) throws JAXBException {
        Map<Class, JAXBContext> map = jaxbContexts;
        synchronized (map) {
            JAXBContext c = jaxbContexts.get(type);
            if (c == null) {
                c = JAXBContext.newInstance(type);
                jaxbContexts.put(type, c);
            }
            return c;
        }
    }

    public static class RootElementProvider
    extends JaxbStringReaderProvider
    implements ParamConverterProvider {
        private final Provider<SAXParserFactory> spfProvider;

        public RootElementProvider(@Context Provider<SAXParserFactory> spfProvider, @Context Providers ps) {
            super(ps);
            this.spfProvider = spfProvider;
        }

        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            boolean supported;
            boolean bl = supported = rawType.getAnnotation(XmlRootElement.class) != null || rawType.getAnnotation(XmlType.class) != null;
            if (!supported) {
                return null;
            }
            return new ParamConverter<T>(){

                @Override
                public T fromString(String value) {
                    try {
                        SAXSource source = new SAXSource(((SAXParserFactory)spfProvider.get()).newSAXParser().getXMLReader(), new InputSource(new StringReader(value)));
                        Unmarshaller u = this.getUnmarshaller(rawType);
                        if (rawType.isAnnotationPresent(XmlRootElement.class)) {
                            return rawType.cast(u.unmarshal(source));
                        }
                        return u.unmarshal(source, rawType).getValue();
                    }
                    catch (UnmarshalException ex) {
                        throw new ExtractorException(LocalizationMessages.ERROR_UNMARSHALLING_JAXB(rawType), ex);
                    }
                    catch (JAXBException ex) {
                        throw new ProcessingException(LocalizationMessages.ERROR_UNMARSHALLING_JAXB(rawType), ex);
                    }
                    catch (Exception ex) {
                        throw new ProcessingException(LocalizationMessages.ERROR_UNMARSHALLING_JAXB(rawType), ex);
                    }
                }

                @Override
                public String toString(T value) throws IllegalArgumentException {
                    return "test";
                }
            };
        }
    }
}

