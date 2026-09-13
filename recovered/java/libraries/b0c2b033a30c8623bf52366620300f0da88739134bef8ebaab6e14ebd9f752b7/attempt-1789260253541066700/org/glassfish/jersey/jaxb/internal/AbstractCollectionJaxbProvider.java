/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbProvider;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;
import org.glassfish.jersey.jaxb.internal.NounInflector;
import org.glassfish.jersey.message.internal.EntityInputStream;

public abstract class AbstractCollectionJaxbProvider
extends AbstractJaxbProvider<Object> {
    private static final Logger LOGGER = Logger.getLogger(AbstractCollectionJaxbProvider.class.getName());
    private static final Class<?>[] DEFAULT_IMPLS = new Class[]{ArrayList.class, LinkedList.class, HashSet.class, TreeSet.class, Stack.class};
    private static final JaxbTypeChecker DefaultJaxbTypeCHECKER = new JaxbTypeChecker(){

        @Override
        public boolean isJaxbType(Class<?> type) {
            return type.isAnnotationPresent(XmlRootElement.class) || type.isAnnotationPresent(XmlType.class);
        }
    };
    private final NounInflector inflector = NounInflector.getInstance();

    public AbstractCollectionJaxbProvider(Providers ps) {
        super(ps);
    }

    public AbstractCollectionJaxbProvider(Providers ps, MediaType mt) {
        super(ps, mt);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (AbstractCollectionJaxbProvider.verifyCollectionSubclass(type)) {
            return AbstractCollectionJaxbProvider.verifyGenericType(genericType) && this.isSupported(mediaType);
        }
        return type.isArray() && AbstractCollectionJaxbProvider.verifyArrayType(type) && this.isSupported(mediaType);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (Collection.class.isAssignableFrom(type)) {
            return AbstractCollectionJaxbProvider.verifyGenericType(genericType) && this.isSupported(mediaType);
        }
        return type.isArray() && AbstractCollectionJaxbProvider.verifyArrayType(type) && this.isSupported(mediaType);
    }

    public static boolean verifyCollectionSubclass(Class<?> type) {
        try {
            if (Collection.class.isAssignableFrom(type)) {
                for (Class<?> c : DEFAULT_IMPLS) {
                    if (!type.isAssignableFrom(c)) continue;
                    return true;
                }
                return !Modifier.isAbstract(type.getModifiers()) && Modifier.isPublic(type.getConstructor(new Class[0]).getModifiers());
            }
        }
        catch (NoSuchMethodException ex) {
            LOGGER.log(Level.WARNING, LocalizationMessages.NO_PARAM_CONSTRUCTOR_MISSING(type.getName()), ex);
        }
        catch (SecurityException ex) {
            LOGGER.log(Level.WARNING, LocalizationMessages.UNABLE_TO_ACCESS_METHODS_OF_CLASS(type.getName()), ex);
        }
        return false;
    }

    private static boolean verifyArrayType(Class type) {
        return AbstractCollectionJaxbProvider.verifyArrayType(type, DefaultJaxbTypeCHECKER);
    }

    public static boolean verifyArrayType(Class type, JaxbTypeChecker checker) {
        return checker.isJaxbType(type = type.getComponentType()) || JAXBElement.class.isAssignableFrom(type);
    }

    private static boolean verifyGenericType(Type genericType) {
        return AbstractCollectionJaxbProvider.verifyGenericType(genericType, DefaultJaxbTypeCHECKER);
    }

    public static boolean verifyGenericType(Type genericType, JaxbTypeChecker checker) {
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType pt = (ParameterizedType)genericType;
        if (pt.getActualTypeArguments().length > 1) {
            return false;
        }
        Type ta = pt.getActualTypeArguments()[0];
        if (ta instanceof ParameterizedType) {
            ParameterizedType lpt = (ParameterizedType)ta;
            return lpt.getRawType() instanceof Class && JAXBElement.class.isAssignableFrom((Class)lpt.getRawType());
        }
        if (!(pt.getActualTypeArguments()[0] instanceof Class)) {
            return false;
        }
        Class listClass = (Class)pt.getActualTypeArguments()[0];
        return checker.isJaxbType(listClass);
    }

    @Override
    public final void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        try {
            List<Object> c = type.isArray() ? Arrays.asList((Object[])t) : (List<Object>)t;
            Class elementType = AbstractCollectionJaxbProvider.getElementClass(type, genericType);
            Charset charset = AbstractCollectionJaxbProvider.getCharset(mediaType);
            String charsetName = charset.name();
            Marshaller m = this.getMarshaller(elementType, mediaType);
            m.setProperty("jaxb.fragment", true);
            if (charset != UTF8) {
                m.setProperty("jaxb.encoding", charsetName);
            }
            this.setHeader(m, annotations);
            this.writeCollection(elementType, c, mediaType, charset, m, entityStream);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    public abstract void writeCollection(Class<?> var1, Collection<?> var2, MediaType var3, Charset var4, Marshaller var5, OutputStream var6) throws JAXBException, IOException;

    @Override
    public final Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream inputStream) throws IOException {
        EntityInputStream entityStream = EntityInputStream.create(inputStream);
        if (entityStream.isEmpty()) {
            throw new NoContentException(LocalizationMessages.ERROR_READING_ENTITY_MISSING());
        }
        try {
            Class elementType = AbstractCollectionJaxbProvider.getElementClass(type, genericType);
            Unmarshaller u = this.getUnmarshaller(elementType, mediaType);
            XMLStreamReader r = this.getXMLStreamReader(elementType, mediaType, u, entityStream);
            boolean jaxbElement = false;
            Collection l = null;
            if (type.isArray()) {
                l = new ArrayList();
            } else {
                try {
                    l = (Collection)type.newInstance();
                }
                catch (Exception e) {
                    for (Class<?> c : DEFAULT_IMPLS) {
                        if (!type.isAssignableFrom(c)) continue;
                        try {
                            l = (Collection)c.newInstance();
                            break;
                        }
                        catch (InstantiationException ex) {
                            LOGGER.log(Level.WARNING, LocalizationMessages.UNABLE_TO_INSTANTIATE_CLASS(c.getName()), ex);
                        }
                        catch (IllegalAccessException ex) {
                            LOGGER.log(Level.WARNING, LocalizationMessages.UNABLE_TO_INSTANTIATE_CLASS(c.getName()), ex);
                        }
                        catch (SecurityException ex) {
                            LOGGER.log(Level.WARNING, LocalizationMessages.UNABLE_TO_INSTANTIATE_CLASS(c.getName()), ex);
                        }
                    }
                }
            }
            if (l == null) {
                l = new ArrayList();
            }
            int event = r.next();
            while (event != 1) {
                event = r.next();
            }
            event = r.next();
            while (event != 1 && event != 8) {
                event = r.next();
            }
            while (event != 8) {
                if (elementType.isAnnotationPresent(XmlRootElement.class)) {
                    l.add(u.unmarshal(r));
                } else if (elementType.isAnnotationPresent(XmlType.class)) {
                    l.add(u.unmarshal(r, elementType).getValue());
                } else {
                    l.add(u.unmarshal(r, elementType));
                    jaxbElement = true;
                }
                event = r.getEventType();
                while (event != 1 && event != 8) {
                    event = r.next();
                }
            }
            return type.isArray() ? AbstractCollectionJaxbProvider.createArray(l, jaxbElement ? JAXBElement.class : elementType) : l;
        }
        catch (UnmarshalException ex) {
            throw new BadRequestException(ex);
        }
        catch (XMLStreamException ex) {
            throw new BadRequestException(ex);
        }
        catch (JAXBException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    private static Object createArray(Collection<?> collection, Class componentType) {
        Object array = Array.newInstance(componentType, collection.size());
        int i = 0;
        for (Object value : collection) {
            Array.set(array, i++, value);
        }
        return array;
    }

    protected abstract XMLStreamReader getXMLStreamReader(Class<?> var1, MediaType var2, Unmarshaller var3, InputStream var4) throws XMLStreamException;

    protected static Class getElementClass(Class<?> type, Type genericType) {
        Type ta = genericType instanceof ParameterizedType ? ((ParameterizedType)genericType).getActualTypeArguments()[0] : (genericType instanceof GenericArrayType ? ((GenericArrayType)genericType).getGenericComponentType() : type.getComponentType());
        if (ta instanceof ParameterizedType) {
            ta = ((ParameterizedType)ta).getActualTypeArguments()[0];
        }
        return ta;
    }

    private static String convertToXmlName(String name) {
        return name.replace("$", "_");
    }

    protected final String getRootElementName(Class<?> elementType) {
        if (this.isXmlRootElementProcessing()) {
            return AbstractCollectionJaxbProvider.convertToXmlName(this.inflector.pluralize(this.inflector.demodulize(AbstractCollectionJaxbProvider.getElementName(elementType))));
        }
        return AbstractCollectionJaxbProvider.convertToXmlName(this.inflector.decapitalize(this.inflector.pluralize(this.inflector.demodulize(elementType.getName()))));
    }

    protected static String getElementName(Class<?> elementType) {
        String name = elementType.getName();
        XmlRootElement xre = elementType.getAnnotation(XmlRootElement.class);
        if (xre != null && !"##default".equals(xre.name())) {
            name = xre.name();
        }
        return name;
    }

    public static interface JaxbTypeChecker {
        public boolean isJaxbType(Class<?> var1);
    }
}

