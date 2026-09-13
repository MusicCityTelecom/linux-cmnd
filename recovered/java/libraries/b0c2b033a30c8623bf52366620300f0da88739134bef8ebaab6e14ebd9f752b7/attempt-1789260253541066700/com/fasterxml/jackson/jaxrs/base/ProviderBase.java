/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.base;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.jaxrs.base.NoContentExceptionSupplier;
import com.fasterxml.jackson.jaxrs.base.nocontent.JaxRS1NoContentExceptionSupplier;
import com.fasterxml.jackson.jaxrs.base.nocontent.JaxRS2NoContentExceptionSupplier;
import com.fasterxml.jackson.jaxrs.cfg.AnnotationBundleKey;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.JaxRSFeature;
import com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectReaderInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectReaderModifier;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;
import com.fasterxml.jackson.jaxrs.util.ClassKey;
import com.fasterxml.jackson.jaxrs.util.LRUMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

public abstract class ProviderBase<THIS extends ProviderBase<THIS, MAPPER, EP_CONFIG, MAPPER_CONFIG>, MAPPER extends ObjectMapper, EP_CONFIG extends EndpointConfigBase<EP_CONFIG>, MAPPER_CONFIG extends MapperConfiguratorBase<MAPPER_CONFIG, MAPPER>>
implements MessageBodyReader<Object>,
MessageBodyWriter<Object>,
Versioned {
    public static final String HEADER_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    protected static final String CLASS_NAME_NO_CONTENT_EXCEPTION = "javax.ws.rs.core.NoContentException";
    protected final NoContentExceptionSupplier noContentExceptionSupplier = ProviderBase._createNoContentExceptionSupplier();
    protected static final HashSet<ClassKey> DEFAULT_UNTOUCHABLES = new HashSet();
    public static final Class<?>[] DEFAULT_UNREADABLES;
    public static final Class<?>[] DEFAULT_UNWRITABLES;
    protected static final int JAXRS_FEATURE_DEFAULTS;
    protected final MAPPER_CONFIG _mapperConfig;
    protected HashMap<ClassKey, Boolean> _cfgCustomUntouchables;
    protected boolean _cfgCheckCanSerialize = false;
    protected boolean _cfgCheckCanDeserialize = false;
    protected int _jaxRSFeatures;
    protected Class<?> _defaultReadView;
    protected Class<?> _defaultWriteView;
    public static final HashSet<ClassKey> _untouchables;
    public static final Class<?>[] _unreadableClasses;
    public static final Class<?>[] _unwritableClasses;
    protected final LRUMap<AnnotationBundleKey, EP_CONFIG> _readers = new LRUMap(16, 120);
    protected final LRUMap<AnnotationBundleKey, EP_CONFIG> _writers = new LRUMap(16, 120);
    protected final AtomicReference<IOException> _noContentExceptionRef = new AtomicReference();

    protected ProviderBase(MAPPER_CONFIG mconfig) {
        this._mapperConfig = mconfig;
        this._jaxRSFeatures = JAXRS_FEATURE_DEFAULTS;
    }

    @Deprecated
    protected ProviderBase() {
        this._mapperConfig = null;
        this._jaxRSFeatures = JAXRS_FEATURE_DEFAULTS;
    }

    public void checkCanDeserialize(boolean state) {
        this._cfgCheckCanDeserialize = state;
    }

    public void checkCanSerialize(boolean state) {
        this._cfgCheckCanSerialize = state;
    }

    public void addUntouchable(Class<?> type) {
        if (this._cfgCustomUntouchables == null) {
            this._cfgCustomUntouchables = new HashMap();
        }
        this._cfgCustomUntouchables.put(new ClassKey(type), Boolean.TRUE);
    }

    public void removeUntouchable(Class<?> type) {
        if (this._cfgCustomUntouchables == null) {
            this._cfgCustomUntouchables = new HashMap();
        }
        this._cfgCustomUntouchables.put(new ClassKey(type), Boolean.FALSE);
    }

    public void setAnnotationsToUse(Annotations[] annotationsToUse) {
        ((MapperConfiguratorBase)this._mapperConfig).setAnnotationsToUse(annotationsToUse);
    }

    public void setMapper(MAPPER m) {
        ((MapperConfiguratorBase)this._mapperConfig).setMapper(m);
    }

    public THIS setDefaultReadView(Class<?> view) {
        this._defaultReadView = view;
        return this._this();
    }

    public THIS setDefaultWriteView(Class<?> view) {
        this._defaultWriteView = view;
        return this._this();
    }

    public THIS setDefaultView(Class<?> view) {
        this._defaultWriteView = view;
        this._defaultReadView = this._defaultWriteView;
        return this._this();
    }

    public THIS configure(JaxRSFeature feature, boolean state) {
        return state ? this.enable(feature) : this.disable(feature);
    }

    public THIS enable(JaxRSFeature feature) {
        this._jaxRSFeatures |= feature.getMask();
        return this._this();
    }

    public THIS enable(JaxRSFeature first, JaxRSFeature ... f2) {
        this._jaxRSFeatures |= first.getMask();
        for (JaxRSFeature f : f2) {
            this._jaxRSFeatures |= f.getMask();
        }
        return this._this();
    }

    public THIS disable(JaxRSFeature feature) {
        this._jaxRSFeatures &= ~feature.getMask();
        return this._this();
    }

    public THIS disable(JaxRSFeature first, JaxRSFeature ... f2) {
        this._jaxRSFeatures &= ~first.getMask();
        for (JaxRSFeature f : f2) {
            this._jaxRSFeatures &= ~f.getMask();
        }
        return this._this();
    }

    public boolean isEnabled(JaxRSFeature f) {
        return (this._jaxRSFeatures & f.getMask()) != 0;
    }

    public THIS configure(DeserializationFeature f, boolean state) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, state);
        return this._this();
    }

    public THIS enable(DeserializationFeature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, true);
        return this._this();
    }

    public THIS disable(DeserializationFeature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, false);
        return this._this();
    }

    public THIS configure(SerializationFeature f, boolean state) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, state);
        return this._this();
    }

    public THIS enable(SerializationFeature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, true);
        return this._this();
    }

    public THIS disable(SerializationFeature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, false);
        return this._this();
    }

    public THIS enable(JsonParser.Feature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, true);
        return this._this();
    }

    public THIS enable(JsonGenerator.Feature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, true);
        return this._this();
    }

    public THIS disable(JsonParser.Feature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, false);
        return this._this();
    }

    public THIS disable(JsonGenerator.Feature f) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, false);
        return this._this();
    }

    public THIS configure(JsonParser.Feature f, boolean state) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, state);
        return this._this();
    }

    public THIS configure(JsonGenerator.Feature f, boolean state) {
        ((MapperConfiguratorBase)this._mapperConfig).configure(f, state);
        return this._this();
    }

    protected boolean hasMatchingMediaTypeForReading(MediaType mediaType) {
        return this.hasMatchingMediaType(mediaType);
    }

    protected boolean hasMatchingMediaTypeForWriting(MediaType mediaType) {
        return this.hasMatchingMediaType(mediaType);
    }

    protected abstract boolean hasMatchingMediaType(MediaType var1);

    protected abstract MAPPER _locateMapperViaProvider(Class<?> var1, MediaType var2);

    protected EP_CONFIG _configForReading(MAPPER mapper, Annotation[] annotations, Class<?> defaultView) {
        ObjectReader r = defaultView != null ? ((ObjectMapper)mapper).readerWithView(defaultView) : ((ObjectMapper)mapper).reader();
        return this._configForReading(r, annotations);
    }

    protected EP_CONFIG _configForWriting(MAPPER mapper, Annotation[] annotations, Class<?> defaultView) {
        ObjectWriter w = defaultView != null ? ((ObjectMapper)mapper).writerWithView(defaultView) : ((ObjectMapper)mapper).writer();
        return this._configForWriting(w, annotations);
    }

    protected abstract EP_CONFIG _configForReading(ObjectReader var1, Annotation[] var2);

    protected abstract EP_CONFIG _configForWriting(ObjectWriter var1, Annotation[] var2);

    @Override
    public long getSize(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1L;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!this.hasMatchingMediaType(mediaType)) {
            return false;
        }
        Boolean customUntouchable = this._findCustomUntouchable(type);
        if (customUntouchable != null) {
            return customUntouchable == false;
        }
        if (this._isIgnorableForWriting(new ClassKey(type))) {
            return false;
        }
        for (Class<?> cls : _unwritableClasses) {
            if (!cls.isAssignableFrom(type)) continue;
            return false;
        }
        return !this._cfgCheckCanSerialize || ((ObjectMapper)this.locateMapper(type, mediaType)).canSerialize(type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        EP_CONFIG endpoint = this._endpointForWriting(value, type, genericType, annotations, mediaType, httpHeaders);
        this._modifyHeaders(value, type, genericType, annotations, httpHeaders, endpoint);
        ObjectWriter writer = ((EndpointConfigBase)endpoint).getWriter();
        JsonEncoding enc = this.findEncoding(mediaType, httpHeaders);
        JsonGenerator g = this._createGenerator(writer, entityStream, enc);
        boolean ok = false;
        try {
            JavaType baseType;
            TypeFactory typeFactory;
            if (writer.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
                g.useDefaultPrettyPrinter();
            }
            JavaType rootType = null;
            if (genericType != null && value != null && !(genericType instanceof Class) && (rootType = (typeFactory = writer.getTypeFactory()).constructSpecializedType(baseType = typeFactory.constructType(genericType), type)).getRawClass() == Object.class) {
                rootType = null;
            }
            if (rootType != null) {
                writer = writer.forType(rootType);
            }
            value = ((EndpointConfigBase)endpoint).modifyBeforeWrite(value);
            ObjectWriterModifier mod = ObjectWriterInjector.getAndClear();
            if (mod != null) {
                writer = mod.modify((EndpointConfigBase<?>)endpoint, httpHeaders, value, writer, g);
            }
            writer.writeValue(g, value);
            ok = true;
        }
        finally {
            if (ok) {
                g.close();
            } else {
                try {
                    g.close();
                }
                catch (Exception exception) {}
            }
        }
    }

    protected JsonEncoding findEncoding(MediaType mediaType, MultivaluedMap<String, Object> httpHeaders) {
        return JsonEncoding.UTF8;
    }

    protected void _modifyHeaders(Object value, Class<?> type, Type genericType, Annotation[] annotations, MultivaluedMap<String, Object> httpHeaders, EP_CONFIG endpoint) throws IOException {
        if (this.isEnabled(JaxRSFeature.ADD_NO_SNIFF_HEADER)) {
            httpHeaders.add(HEADER_CONTENT_TYPE_OPTIONS, "nosniff");
        }
    }

    protected JsonGenerator _createGenerator(ObjectWriter writer, OutputStream rawStream, JsonEncoding enc) throws IOException {
        JsonGenerator g = writer.getFactory().createGenerator(rawStream, enc);
        g.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        return g;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected EP_CONFIG _endpointForWriting(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders) {
        EndpointConfigBase<Object> endpoint;
        if (!this.isEnabled(JaxRSFeature.CACHE_ENDPOINT_WRITERS)) {
            return this._configForWriting(this.locateMapper(type, mediaType), annotations, this._defaultWriteView);
        }
        AnnotationBundleKey key = new AnnotationBundleKey(annotations, type);
        LRUMap<AnnotationBundleKey, EP_CONFIG> lRUMap = this._writers;
        synchronized (lRUMap) {
            endpoint = (EndpointConfigBase)this._writers.get(key);
        }
        if (endpoint == null) {
            MAPPER mapper = this.locateMapper(type, mediaType);
            endpoint = this._configForWriting(mapper, annotations, this._defaultWriteView);
            LRUMap<AnnotationBundleKey, EP_CONFIG> lRUMap2 = this._writers;
            synchronized (lRUMap2) {
                this._writers.put(key.immutableKey(), endpoint);
            }
        }
        return (EP_CONFIG)endpoint;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!this.hasMatchingMediaType(mediaType)) {
            return false;
        }
        Boolean customUntouchable = this._findCustomUntouchable(type);
        if (customUntouchable != null) {
            return customUntouchable == false;
        }
        if (this._isIgnorableForReading(new ClassKey(type))) {
            return false;
        }
        for (Class<?> cls : _unreadableClasses) {
            if (!cls.isAssignableFrom(type)) continue;
            return false;
        }
        if (this._cfgCheckCanDeserialize) {
            if (this._isSpecialReadable(type)) {
                return true;
            }
            MAPPER mapper = this.locateMapper(type, mediaType);
            if (!((ObjectMapper)mapper).canDeserialize(((ObjectMapper)mapper).constructType(type))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        boolean multiValued;
        EP_CONFIG endpoint = this._endpointForReading(type, genericType, annotations, mediaType, httpHeaders);
        ObjectReader reader = ((EndpointConfigBase)endpoint).getReader();
        JsonParser p = this._createParser(reader, entityStream);
        if (p == null || p.nextToken() == null) {
            if (JaxRSFeature.ALLOW_EMPTY_INPUT.enabledIn(this._jaxRSFeatures)) {
                return null;
            }
            IOException fail = this._noContentExceptionRef.get();
            if (fail == null) {
                fail = this._createNoContentException();
            }
            throw fail;
        }
        Class<Object> rawType = type;
        if (rawType == JsonParser.class) {
            return p;
        }
        TypeFactory tf = reader.getTypeFactory();
        JavaType resolvedType = tf.constructType(genericType);
        boolean bl = multiValued = rawType == MappingIterator.class;
        if (multiValued) {
            JavaType[] contents = tf.findTypeParameters(resolvedType, MappingIterator.class);
            JavaType valueType = contents == null || contents.length == 0 ? tf.constructType((Type)((Object)Object.class)) : contents[0];
            reader = reader.forType(valueType);
        } else {
            reader = reader.forType(resolvedType);
        }
        ObjectReaderModifier mod = ObjectReaderInjector.getAndClear();
        if (mod != null) {
            reader = mod.modify((EndpointConfigBase<?>)endpoint, httpHeaders, resolvedType, reader, p);
        }
        if (multiValued) {
            return reader.readValues(p);
        }
        return reader.readValue(p);
    }

    protected JsonParser _createParser(ObjectReader reader, InputStream rawStream) throws IOException {
        JsonParser p = reader.getFactory().createParser(rawStream);
        p.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        return p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected EP_CONFIG _endpointForReading(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders) {
        EndpointConfigBase<Object> endpoint;
        if (!this.isEnabled(JaxRSFeature.CACHE_ENDPOINT_READERS)) {
            return this._configForReading(this.locateMapper(type, mediaType), annotations, this._defaultReadView);
        }
        AnnotationBundleKey key = new AnnotationBundleKey(annotations, type);
        LRUMap<AnnotationBundleKey, EP_CONFIG> lRUMap = this._readers;
        synchronized (lRUMap) {
            endpoint = (EndpointConfigBase)this._readers.get(key);
        }
        if (endpoint == null) {
            MAPPER mapper = this.locateMapper(type, mediaType);
            endpoint = this._configForReading(mapper, annotations, this._defaultReadView);
            LRUMap<AnnotationBundleKey, EP_CONFIG> lRUMap2 = this._readers;
            synchronized (lRUMap2) {
                this._readers.put(key.immutableKey(), endpoint);
            }
        }
        return (EP_CONFIG)endpoint;
    }

    public MAPPER locateMapper(Class<?> type, MediaType mediaType) {
        if (this.isEnabled(JaxRSFeature.DYNAMIC_OBJECT_MAPPER_LOOKUP)) {
            MAPPER m = this._locateMapperViaProvider(type, mediaType);
            if (m == null && (m = ((MapperConfiguratorBase)this._mapperConfig).getConfiguredMapper()) == null) {
                m = ((MapperConfiguratorBase)this._mapperConfig).getDefaultMapper();
            }
            return m;
        }
        Object m = ((MapperConfiguratorBase)this._mapperConfig).getConfiguredMapper();
        if (m == null && (m = this._locateMapperViaProvider(type, mediaType)) == null) {
            m = ((MapperConfiguratorBase)this._mapperConfig).getDefaultMapper();
        }
        return m;
    }

    protected boolean _isSpecialReadable(Class<?> type) {
        return JsonParser.class == type;
    }

    protected boolean _isIgnorableForReading(ClassKey typeKey) {
        return _untouchables.contains(typeKey);
    }

    protected boolean _isIgnorableForWriting(ClassKey typeKey) {
        return _untouchables.contains(typeKey);
    }

    protected IOException _createNoContentException() {
        return this.noContentExceptionSupplier.createNoContentException();
    }

    protected static boolean _containedIn(Class<?> mainType, HashSet<ClassKey> set) {
        if (set != null) {
            ClassKey key = new ClassKey(mainType);
            if (set.contains(key)) {
                return true;
            }
            for (Class<?> cls : ProviderBase.findSuperTypes(mainType, null)) {
                key.reset(cls);
                if (!set.contains(key)) continue;
                return true;
            }
        }
        return false;
    }

    protected Boolean _findCustomUntouchable(Class<?> mainType) {
        if (this._cfgCustomUntouchables != null) {
            ClassKey key = new ClassKey(mainType);
            Boolean b = this._cfgCustomUntouchables.get(key);
            if (b != null) {
                return b;
            }
            for (Class<?> cls : ProviderBase.findSuperTypes(mainType, null)) {
                key.reset(cls);
                b = this._cfgCustomUntouchables.get(key);
                if (b == null) continue;
                return b;
            }
        }
        return null;
    }

    protected static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
        return ProviderBase.findSuperTypes(cls, endBefore, new ArrayList(8));
    }

    protected static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
        ProviderBase._addSuperTypes(cls, endBefore, result, false);
        return result;
    }

    protected static void _addSuperTypes(Class<?> cls, Class<?> endBefore, Collection<Class<?>> result, boolean addClassItself) {
        if (cls == endBefore || cls == null || cls == Object.class) {
            return;
        }
        if (addClassItself) {
            if (result.contains(cls)) {
                return;
            }
            result.add(cls);
        }
        for (Class<?> intCls : cls.getInterfaces()) {
            ProviderBase._addSuperTypes(intCls, endBefore, result, true);
        }
        ProviderBase._addSuperTypes(cls.getSuperclass(), endBefore, result, true);
    }

    private final THIS _this() {
        return (THIS)this;
    }

    private static NoContentExceptionSupplier _createNoContentExceptionSupplier() {
        try {
            final Class<?> cls = Class.forName(CLASS_NAME_NO_CONTENT_EXCEPTION, false, ProviderBase.getClassLoader());
            Constructor<?> ctor = System.getSecurityManager() == null ? cls.getDeclaredConstructor(String.class) : (Constructor<?>)AccessController.doPrivileged(new PrivilegedAction<Constructor<?>>(){

                @Override
                public Constructor<?> run() {
                    try {
                        return cls.getDeclaredConstructor(String.class);
                    }
                    catch (NoSuchMethodException ignore) {
                        return null;
                    }
                }
            });
            if (ctor != null) {
                return new JaxRS2NoContentExceptionSupplier();
            }
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
        return new JaxRS1NoContentExceptionSupplier();
    }

    private static ClassLoader getClassLoader() {
        if (System.getSecurityManager() == null) {
            return ProviderBase.class.getClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return ProviderBase.class.getClassLoader();
            }
        });
    }

    static {
        DEFAULT_UNTOUCHABLES.add(new ClassKey(InputStream.class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(Reader.class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(OutputStream.class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(Writer.class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(char[].class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(String.class));
        DEFAULT_UNTOUCHABLES.add(new ClassKey(byte[].class));
        DEFAULT_UNREADABLES = new Class[]{InputStream.class, Reader.class};
        DEFAULT_UNWRITABLES = new Class[]{InputStream.class, OutputStream.class, Writer.class, StreamingOutput.class, Response.class};
        JAXRS_FEATURE_DEFAULTS = JaxRSFeature.collectDefaults();
        _untouchables = DEFAULT_UNTOUCHABLES;
        _unreadableClasses = DEFAULT_UNREADABLES;
        _unwritableClasses = DEFAULT_UNWRITABLES;
    }
}

