/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import javax.xml.transform.Source;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.guava.Primitives;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.DataStructures;
import org.glassfish.jersey.internal.util.collection.KeyComparator;
import org.glassfish.jersey.internal.util.collection.KeyComparatorHashMap;
import org.glassfish.jersey.internal.util.collection.KeyComparatorLinkedHashMap;
import org.glassfish.jersey.message.AbstractEntityProviderModel;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.ReaderModel;
import org.glassfish.jersey.message.WriterModel;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.message.internal.MsgTraceEvent;
import org.glassfish.jersey.message.internal.ReaderInterceptorExecutor;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.message.internal.WriterInterceptorExecutor;

public class MessageBodyFactory
implements MessageBodyWorkers {
    private static final Logger LOGGER = Logger.getLogger(MessageBodyFactory.class.getName());
    public static final KeyComparator<MediaType> MEDIA_TYPE_KEY_COMPARATOR = new KeyComparator<MediaType>(){
        private static final long serialVersionUID = 1616819828630827763L;

        @Override
        public boolean equals(MediaType mt1, MediaType mt2) {
            return mt1.isCompatible(mt2);
        }

        @Override
        public int hash(MediaType mt) {
            return mt.getType().toLowerCase(Locale.ROOT).hashCode() + mt.getSubtype().toLowerCase(Locale.ROOT).hashCode();
        }
    };
    static final Comparator<AbstractEntityProviderModel<?>> WORKER_BY_TYPE_COMPARATOR = new Comparator<AbstractEntityProviderModel<?>>(){

        @Override
        public int compare(AbstractEntityProviderModel<?> o1, AbstractEntityProviderModel<?> o2) {
            Class<?> o2ProviderClassParam;
            Class<?> o1ProviderClassParam = o1.providedType();
            if (o1ProviderClassParam == (o2ProviderClassParam = o2.providedType())) {
                return this.compare(o2.declaredTypes(), o1.declaredTypes());
            }
            if (o1ProviderClassParam.isAssignableFrom(o2ProviderClassParam)) {
                return 1;
            }
            if (o2ProviderClassParam.isAssignableFrom(o1ProviderClassParam)) {
                return -1;
            }
            return CLASS_BY_NAME_COMPARATOR.compare(o1ProviderClassParam, o2ProviderClassParam);
        }

        @Override
        private int compare(List<MediaType> mediaTypeList1, List<MediaType> mediaTypeList2) {
            mediaTypeList1 = mediaTypeList1.isEmpty() ? MediaTypes.WILDCARD_TYPE_SINGLETON_LIST : mediaTypeList1;
            mediaTypeList2 = mediaTypeList2.isEmpty() ? MediaTypes.WILDCARD_TYPE_SINGLETON_LIST : mediaTypeList2;
            return MediaTypes.MEDIA_TYPE_LIST_COMPARATOR.compare(mediaTypeList2, mediaTypeList1);
        }
    };
    private static final Comparator<Class<?>> CLASS_BY_NAME_COMPARATOR = Comparator.comparing(Class::getName);
    private InjectionManager injectionManager;
    private final Boolean legacyProviderOrdering;
    private List<ReaderModel> readers;
    private List<WriterModel> writers;
    private final Map<MediaType, List<MessageBodyReader>> readersCache = new KeyComparatorHashMap<MediaType, List<MessageBodyReader>>(MEDIA_TYPE_KEY_COMPARATOR);
    private final Map<MediaType, List<MessageBodyWriter>> writersCache = new KeyComparatorHashMap<MediaType, List<MessageBodyWriter>>(MEDIA_TYPE_KEY_COMPARATOR);
    private static final int LOOKUP_CACHE_INITIAL_CAPACITY = 32;
    private static final float LOOKUP_CACHE_LOAD_FACTOR = 0.75f;
    private final Map<Class<?>, List<ReaderModel>> mbrTypeLookupCache = new ConcurrentHashMap(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private final Map<Class<?>, List<WriterModel>> mbwTypeLookupCache = new ConcurrentHashMap(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private final Map<Class<?>, List<MediaType>> typeToMediaTypeReadersCache = new ConcurrentHashMap(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private final Map<Class<?>, List<MediaType>> typeToMediaTypeWritersCache = new ConcurrentHashMap(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private final Map<ModelLookupKey, List<ReaderModel>> mbrLookupCache = new ConcurrentHashMap<ModelLookupKey, List<ReaderModel>>(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private final Map<ModelLookupKey, List<WriterModel>> mbwLookupCache = new ConcurrentHashMap<ModelLookupKey, List<WriterModel>>(32, 0.75f, DataStructures.DEFAULT_CONCURENCY_LEVEL);
    private static final Function<WriterModel, MessageBodyWriter> MODEL_TO_WRITER = AbstractEntityProviderModel::provider;
    private static final Function<ReaderModel, MessageBodyReader> MODEL_TO_READER = AbstractEntityProviderModel::provider;

    public MessageBodyFactory(Configuration configuration) {
        this.legacyProviderOrdering = configuration != null && PropertiesHelper.isProperty(configuration.getProperty("jersey.config.workers.legacyOrdering"));
    }

    public void initialize(InjectionManager injectionManager) {
        this.injectionManager = injectionManager;
        this.readers = new ArrayList<ReaderModel>();
        Set<MessageBodyReader> customMbrs = Providers.getCustomProviders(injectionManager, MessageBodyReader.class);
        Set<MessageBodyReader> mbrs = Providers.getProviders(injectionManager, MessageBodyReader.class);
        MessageBodyFactory.addReaders(this.readers, customMbrs, true);
        mbrs.removeAll(customMbrs);
        MessageBodyFactory.addReaders(this.readers, mbrs, false);
        if (this.legacyProviderOrdering.booleanValue()) {
            this.readers.sort(new LegacyWorkerComparator(MessageBodyReader.class));
            for (ReaderModel model : this.readers) {
                for (MediaType mediaType : model.declaredTypes()) {
                    List<MessageBodyReader> readerList = this.readersCache.get(mediaType);
                    if (readerList == null) {
                        readerList = new ArrayList<MessageBodyReader>();
                        this.readersCache.put(mediaType, readerList);
                    }
                    readerList.add((MessageBodyReader)model.provider());
                }
            }
        }
        this.writers = new ArrayList<WriterModel>();
        Set<MessageBodyWriter> customMbws = Providers.getCustomProviders(injectionManager, MessageBodyWriter.class);
        Set<MessageBodyWriter> mbws = Providers.getProviders(injectionManager, MessageBodyWriter.class);
        MessageBodyFactory.addWriters(this.writers, customMbws, true);
        mbws.removeAll(customMbws);
        MessageBodyFactory.addWriters(this.writers, mbws, false);
        if (this.legacyProviderOrdering.booleanValue()) {
            this.writers.sort(new LegacyWorkerComparator(MessageBodyWriter.class));
            for (AbstractEntityProviderModel abstractEntityProviderModel : this.writers) {
                for (MediaType mt : abstractEntityProviderModel.declaredTypes()) {
                    List<MessageBodyWriter> writerList = this.writersCache.get(mt);
                    if (writerList == null) {
                        writerList = new ArrayList<MessageBodyWriter>();
                        this.writersCache.put(mt, writerList);
                    }
                    writerList.add((MessageBodyWriter)abstractEntityProviderModel.provider());
                }
            }
        }
    }

    private static void addReaders(List<ReaderModel> models, Set<MessageBodyReader> readers, boolean custom) {
        for (MessageBodyReader provider : readers) {
            List<MediaType> values = MediaTypes.createFrom(provider.getClass().getAnnotation(Consumes.class));
            models.add(new ReaderModel(provider, values, custom));
        }
    }

    private static void addWriters(List<WriterModel> models, Set<MessageBodyWriter> writers, boolean custom) {
        for (MessageBodyWriter provider : writers) {
            List<MediaType> values = MediaTypes.createFrom(provider.getClass().getAnnotation(Produces.class));
            models.add(new WriterModel(provider, values, custom));
        }
    }

    @Override
    public Map<MediaType, List<MessageBodyReader>> getReaders(MediaType mediaType) {
        KeyComparatorLinkedHashMap<MediaType, List<MessageBodyReader>> subSet = new KeyComparatorLinkedHashMap<MediaType, List<MessageBodyReader>>(MEDIA_TYPE_KEY_COMPARATOR);
        MessageBodyFactory.getCompatibleProvidersMap(mediaType, this.readers, subSet);
        return subSet;
    }

    @Override
    public Map<MediaType, List<MessageBodyWriter>> getWriters(MediaType mediaType) {
        KeyComparatorLinkedHashMap<MediaType, List<MessageBodyWriter>> subSet = new KeyComparatorLinkedHashMap<MediaType, List<MessageBodyWriter>>(MEDIA_TYPE_KEY_COMPARATOR);
        MessageBodyFactory.getCompatibleProvidersMap(mediaType, this.writers, subSet);
        return subSet;
    }

    @Override
    public String readersToString(Map<MediaType, List<MessageBodyReader>> readers) {
        return this.toString(readers);
    }

    @Override
    public String writersToString(Map<MediaType, List<MessageBodyWriter>> writers) {
        return this.toString(writers);
    }

    private <T> String toString(Map<MediaType, List<T>> set) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        for (Map.Entry<MediaType, List<T>> e : set.entrySet()) {
            pw.append(e.getKey().toString()).println(" ->");
            for (T t : e.getValue()) {
                pw.append("  ").println(t.getClass().getName());
            }
        }
        pw.flush();
        return sw.toString();
    }

    @Override
    public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> c, Type t, Annotation[] as, MediaType mediaType) {
        return this.getMessageBodyReader(c, t, as, mediaType, null);
    }

    @Override
    public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> c, Type t, Annotation[] as, MediaType mediaType, PropertiesDelegate propertiesDelegate) {
        MessageBodyReader<T> p = null;
        if (this.legacyProviderOrdering.booleanValue()) {
            if (mediaType != null && (p = this._getMessageBodyReader(c, t, as, mediaType, mediaType, propertiesDelegate)) == null) {
                p = this._getMessageBodyReader(c, t, as, mediaType, MediaTypes.getTypeWildCart(mediaType), propertiesDelegate);
            }
            if (p == null) {
                p = this._getMessageBodyReader(c, t, as, mediaType, MediaType.WILDCARD_TYPE, propertiesDelegate);
            }
        } else {
            p = this._getMessageBodyReader(c, t, as, mediaType, this.readers, propertiesDelegate);
        }
        return p;
    }

    @Override
    public List<MediaType> getMessageBodyReaderMediaTypes(Class<?> type, Type genericType, Annotation[] annotations) {
        LinkedHashSet<MediaType> readableMediaTypes = new LinkedHashSet<MediaType>();
        for (ReaderModel model : this.readers) {
            boolean readableWorker = false;
            for (MediaType mt : model.declaredTypes()) {
                if (model.isReadable(type, genericType, annotations, mt)) {
                    readableMediaTypes.add(mt);
                    readableWorker = true;
                }
                if (readableMediaTypes.contains(MediaType.WILDCARD_TYPE) || !readableWorker || !model.declaredTypes().contains(MediaType.WILDCARD_TYPE)) continue;
                readableMediaTypes.add(MediaType.WILDCARD_TYPE);
            }
        }
        ArrayList<MediaType> mtl = new ArrayList<MediaType>(readableMediaTypes);
        mtl.sort(MediaTypes.PARTIAL_ORDER_COMPARATOR);
        return mtl;
    }

    private <T> boolean isCompatible(AbstractEntityProviderModel<T> model, Class c, MediaType mediaType) {
        if (model.providedType().equals(Object.class) || model.providedType().isAssignableFrom(c) || c.isAssignableFrom(model.providedType())) {
            for (MediaType mt : model.declaredTypes()) {
                if (mediaType == null) {
                    return true;
                }
                if (!mediaType.isCompatible(mt)) continue;
                return true;
            }
        }
        return false;
    }

    private <T> MessageBodyReader<T> _getMessageBodyReader(Class<T> c, Type t, Annotation[] as, MediaType mediaType, List<ReaderModel> models, PropertiesDelegate propertiesDelegate) {
        ReaderModel model;
        MediaType lookupType = mediaType == null || mediaType.getParameters().isEmpty() ? mediaType : new MediaType(mediaType.getType(), mediaType.getSubtype());
        ModelLookupKey lookupKey = new ModelLookupKey(c, lookupType);
        List<ReaderModel> readers = this.mbrLookupCache.get(lookupKey);
        if (readers == null) {
            readers = new ArrayList<ReaderModel>();
            for (ReaderModel model2 : models) {
                if (!this.isCompatible(model2, c, mediaType)) continue;
                readers.add(model2);
            }
            readers.sort(new WorkerComparator(c, mediaType));
            this.mbrLookupCache.put(lookupKey, readers);
        }
        if (readers.isEmpty()) {
            return null;
        }
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        MessageBodyReader selected = null;
        Iterator<ReaderModel> iterator = readers.iterator();
        while (iterator.hasNext()) {
            model = iterator.next();
            if (model.isReadable(c, t, as, mediaType)) {
                selected = (MessageBodyReader)model.provider();
                tracingLogger.log(MsgTraceEvent.MBR_SELECTED, selected);
                break;
            }
            tracingLogger.log(MsgTraceEvent.MBR_NOT_READABLE, model.provider());
        }
        if (tracingLogger.isLogEnabled(MsgTraceEvent.MBR_SKIPPED)) {
            while (iterator.hasNext()) {
                model = iterator.next();
                tracingLogger.log(MsgTraceEvent.MBR_SKIPPED, model.provider());
            }
        }
        return selected;
    }

    private <T> MessageBodyReader<T> _getMessageBodyReader(Class<T> c, Type t, Annotation[] as, MediaType mediaType, MediaType lookup, PropertiesDelegate propertiesDelegate) {
        MessageBodyReader p;
        List<MessageBodyReader> readers = this.readersCache.get(lookup);
        if (readers == null) {
            return null;
        }
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        MessageBodyReader selected = null;
        Iterator<MessageBodyReader> iterator = readers.iterator();
        while (iterator.hasNext()) {
            p = iterator.next();
            if (MessageBodyFactory.isReadable(p, c, t, as, mediaType)) {
                selected = p;
                tracingLogger.log(MsgTraceEvent.MBR_SELECTED, selected);
                break;
            }
            tracingLogger.log(MsgTraceEvent.MBR_NOT_READABLE, p);
        }
        if (tracingLogger.isLogEnabled(MsgTraceEvent.MBR_SKIPPED)) {
            while (iterator.hasNext()) {
                p = iterator.next();
                tracingLogger.log(MsgTraceEvent.MBR_SKIPPED, p);
            }
        }
        return selected;
    }

    @Override
    public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> c, Type t, Annotation[] as, MediaType mediaType) {
        return this.getMessageBodyWriter(c, t, as, mediaType, null);
    }

    @Override
    public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> c, Type t, Annotation[] as, MediaType mediaType, PropertiesDelegate propertiesDelegate) {
        MessageBodyWriter<T> p = null;
        if (this.legacyProviderOrdering.booleanValue()) {
            if (mediaType != null && (p = this._getMessageBodyWriter(c, t, as, mediaType, mediaType, propertiesDelegate)) == null) {
                p = this._getMessageBodyWriter(c, t, as, mediaType, MediaTypes.getTypeWildCart(mediaType), propertiesDelegate);
            }
            if (p == null) {
                p = this._getMessageBodyWriter(c, t, as, mediaType, MediaType.WILDCARD_TYPE, propertiesDelegate);
            }
        } else {
            p = this._getMessageBodyWriter(c, t, as, mediaType, this.writers, propertiesDelegate);
        }
        return p;
    }

    private <T> MessageBodyWriter<T> _getMessageBodyWriter(Class<T> c, Type t, Annotation[] as, MediaType mediaType, List<WriterModel> models, PropertiesDelegate propertiesDelegate) {
        WriterModel model;
        MediaType lookupType = mediaType == null || mediaType.getParameters().isEmpty() ? mediaType : new MediaType(mediaType.getType(), mediaType.getSubtype());
        ModelLookupKey lookupKey = new ModelLookupKey(c, lookupType);
        List<WriterModel> writers = this.mbwLookupCache.get(lookupKey);
        if (writers == null) {
            writers = new ArrayList<WriterModel>();
            for (WriterModel model2 : models) {
                if (!this.isCompatible(model2, c, mediaType)) continue;
                writers.add(model2);
            }
            writers.sort(new WorkerComparator(c, mediaType));
            this.mbwLookupCache.put(lookupKey, writers);
        }
        if (writers.isEmpty()) {
            return null;
        }
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        MessageBodyWriter selected = null;
        Iterator<WriterModel> iterator = writers.iterator();
        while (iterator.hasNext()) {
            model = iterator.next();
            if (model.isWriteable(c, t, as, mediaType)) {
                selected = (MessageBodyWriter)model.provider();
                tracingLogger.log(MsgTraceEvent.MBW_SELECTED, selected);
                break;
            }
            tracingLogger.log(MsgTraceEvent.MBW_NOT_WRITEABLE, model.provider());
        }
        if (tracingLogger.isLogEnabled(MsgTraceEvent.MBW_SKIPPED)) {
            while (iterator.hasNext()) {
                model = iterator.next();
                tracingLogger.log(MsgTraceEvent.MBW_SKIPPED, model.provider());
            }
        }
        return selected;
    }

    private <T> MessageBodyWriter<T> _getMessageBodyWriter(Class<T> c, Type t, Annotation[] as, MediaType mediaType, MediaType lookup, PropertiesDelegate propertiesDelegate) {
        MessageBodyWriter p;
        List<MessageBodyWriter> writers = this.writersCache.get(lookup);
        if (writers == null) {
            return null;
        }
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        MessageBodyWriter selected = null;
        Iterator<MessageBodyWriter> iterator = writers.iterator();
        while (iterator.hasNext()) {
            p = iterator.next();
            if (MessageBodyFactory.isWriteable(p, c, t, as, mediaType)) {
                selected = p;
                tracingLogger.log(MsgTraceEvent.MBW_SELECTED, selected);
                break;
            }
            tracingLogger.log(MsgTraceEvent.MBW_NOT_WRITEABLE, p);
        }
        if (tracingLogger.isLogEnabled(MsgTraceEvent.MBW_SKIPPED)) {
            while (iterator.hasNext()) {
                p = iterator.next();
                tracingLogger.log(MsgTraceEvent.MBW_SKIPPED, p);
            }
        }
        return selected;
    }

    private static <T> void getCompatibleProvidersMap(MediaType mediaType, List<? extends AbstractEntityProviderModel<T>> set, Map<MediaType, List<T>> subSet) {
        if (mediaType.isWildcardType()) {
            MessageBodyFactory.getCompatibleProvidersList(mediaType, set, subSet);
        } else if (mediaType.isWildcardSubtype()) {
            MessageBodyFactory.getCompatibleProvidersList(mediaType, set, subSet);
            MessageBodyFactory.getCompatibleProvidersList(MediaType.WILDCARD_TYPE, set, subSet);
        } else {
            MessageBodyFactory.getCompatibleProvidersList(mediaType, set, subSet);
            MessageBodyFactory.getCompatibleProvidersList(MediaTypes.getTypeWildCart(mediaType), set, subSet);
            MessageBodyFactory.getCompatibleProvidersList(MediaType.WILDCARD_TYPE, set, subSet);
        }
    }

    private static <T> void getCompatibleProvidersList(MediaType mediaType, List<? extends AbstractEntityProviderModel<T>> set, Map<MediaType, List<T>> subSet) {
        List providers = set.stream().filter(model -> model.declaredTypes().contains(mediaType)).map(AbstractEntityProviderModel::provider).collect(Collectors.toList());
        if (!providers.isEmpty()) {
            subSet.put(mediaType, Collections.unmodifiableList(providers));
        }
    }

    @Override
    public List<MediaType> getMessageBodyWriterMediaTypes(Class<?> c, Type t, Annotation[] as) {
        LinkedHashSet<MediaType> writeableMediaTypes = new LinkedHashSet<MediaType>();
        for (WriterModel model : this.writers) {
            boolean writeableWorker = false;
            for (MediaType mt : model.declaredTypes()) {
                if (model.isWriteable(c, t, as, mt)) {
                    writeableMediaTypes.add(mt);
                    writeableWorker = true;
                }
                if (writeableMediaTypes.contains(MediaType.WILDCARD_TYPE) || !writeableWorker || !model.declaredTypes().contains(MediaType.WILDCARD_TYPE)) continue;
                writeableMediaTypes.add(MediaType.WILDCARD_TYPE);
            }
        }
        ArrayList<MediaType> mtl = new ArrayList<MediaType>(writeableMediaTypes);
        mtl.sort(MediaTypes.PARTIAL_ORDER_COMPARATOR);
        return mtl;
    }

    @Override
    public List<MessageBodyWriter> getMessageBodyWritersForType(Class<?> type) {
        return this.getWritersModelsForType(type).stream().map(MODEL_TO_WRITER).collect(Collectors.toList());
    }

    @Override
    public List<WriterModel> getWritersModelsForType(Class<?> type) {
        List<WriterModel> writerModels = this.mbwTypeLookupCache.get(type);
        if (writerModels != null) {
            return writerModels;
        }
        return this.processMessageBodyWritersForType(type);
    }

    private List<WriterModel> processMessageBodyWritersForType(Class<?> clazz) {
        ArrayList<WriterModel> suitableWriters = new ArrayList<WriterModel>();
        if (Response.class.isAssignableFrom(clazz)) {
            suitableWriters.addAll(this.writers);
        } else {
            Class<?> wrapped = Primitives.wrap(clazz);
            for (WriterModel model : this.writers) {
                if (model.providedType() != null && model.providedType() != clazz && !model.providedType().isAssignableFrom(wrapped)) continue;
                suitableWriters.add(model);
            }
        }
        suitableWriters.sort(WORKER_BY_TYPE_COMPARATOR);
        this.mbwTypeLookupCache.put(clazz, suitableWriters);
        this.typeToMediaTypeWritersCache.put(clazz, MessageBodyFactory.getMessageBodyWorkersMediaTypesByType(suitableWriters));
        return suitableWriters;
    }

    @Override
    public List<MediaType> getMessageBodyWriterMediaTypesByType(Class<?> type) {
        if (!this.typeToMediaTypeWritersCache.containsKey(type)) {
            this.processMessageBodyWritersForType(type);
        }
        return this.typeToMediaTypeWritersCache.get(type);
    }

    @Override
    public List<MediaType> getMessageBodyReaderMediaTypesByType(Class<?> type) {
        if (!this.typeToMediaTypeReadersCache.containsKey(type)) {
            this.processMessageBodyReadersForType(type);
        }
        return this.typeToMediaTypeReadersCache.get(type);
    }

    private static <T> List<MediaType> getMessageBodyWorkersMediaTypesByType(List<? extends AbstractEntityProviderModel<T>> workerModels) {
        HashSet<MediaType> mediaTypeSet = new HashSet<MediaType>();
        for (AbstractEntityProviderModel<T> model : workerModels) {
            mediaTypeSet.addAll(model.declaredTypes());
        }
        ArrayList<MediaType> mediaTypes = new ArrayList<MediaType>(mediaTypeSet);
        mediaTypes.sort(MediaTypes.PARTIAL_ORDER_COMPARATOR);
        return mediaTypes;
    }

    @Override
    public List<MessageBodyReader> getMessageBodyReadersForType(Class<?> type) {
        return this.getReaderModelsForType(type).stream().map(MODEL_TO_READER).collect(Collectors.toList());
    }

    @Override
    public List<ReaderModel> getReaderModelsForType(Class<?> type) {
        if (!this.mbrTypeLookupCache.containsKey(type)) {
            this.processMessageBodyReadersForType(type);
        }
        return this.mbrTypeLookupCache.get(type);
    }

    private List<ReaderModel> processMessageBodyReadersForType(Class<?> clazz) {
        ArrayList<ReaderModel> suitableReaders = new ArrayList<ReaderModel>();
        Class<?> wrapped = Primitives.wrap(clazz);
        for (ReaderModel reader : this.readers) {
            if (reader.providedType() != null && reader.providedType() != clazz && !reader.providedType().isAssignableFrom(wrapped)) continue;
            suitableReaders.add(reader);
        }
        suitableReaders.sort(WORKER_BY_TYPE_COMPARATOR);
        this.mbrTypeLookupCache.put(clazz, suitableReaders);
        this.typeToMediaTypeReadersCache.put(clazz, MessageBodyFactory.getMessageBodyWorkersMediaTypesByType(suitableReaders));
        return suitableReaders;
    }

    @Override
    public MediaType getMessageBodyWriterMediaType(Class<?> c, Type t, Annotation[] as, List<MediaType> acceptableMediaTypes) {
        for (MediaType acceptable : acceptableMediaTypes) {
            for (WriterModel model : this.writers) {
                for (MediaType mt : model.declaredTypes()) {
                    if (!mt.isCompatible(acceptable) || !model.isWriteable(c, t, as, acceptable)) continue;
                    return MediaTypes.mostSpecific(mt, acceptable);
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object readFrom(Class<?> rawType, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, PropertiesDelegate propertiesDelegate, InputStream entityStream, Iterable<ReaderInterceptor> readerInterceptors, boolean translateNce) throws WebApplicationException, IOException {
        Object object;
        ReaderInterceptorExecutor executor = new ReaderInterceptorExecutor(rawType, type, annotations, mediaType, httpHeaders, propertiesDelegate, entityStream, this, readerInterceptors, translateNce, this.injectionManager);
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        long timestamp = tracingLogger.timestamp(MsgTraceEvent.RI_SUMMARY);
        try {
            InputStream stream;
            Object instance = executor.proceed();
            if (!(instance instanceof Closeable) && !(instance instanceof Source) && (stream = executor.getInputStream()) != entityStream && stream != null) {
                ReaderWriter.safelyClose(stream);
            }
            object = instance;
        }
        catch (Throwable throwable) {
            tracingLogger.logDuration(MsgTraceEvent.RI_SUMMARY, timestamp, executor.getProcessedCount());
            throw throwable;
        }
        tracingLogger.logDuration(MsgTraceEvent.RI_SUMMARY, timestamp, executor.getProcessedCount());
        return object;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public OutputStream writeTo(Object t, Class<?> rawType, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, PropertiesDelegate propertiesDelegate, OutputStream entityStream, Iterable<WriterInterceptor> writerInterceptors) throws IOException, WebApplicationException {
        WriterInterceptorExecutor executor = new WriterInterceptorExecutor(t, rawType, type, annotations, mediaType, httpHeaders, propertiesDelegate, entityStream, this, writerInterceptors, this.injectionManager);
        TracingLogger tracingLogger = TracingLogger.getInstance(propertiesDelegate);
        long timestamp = tracingLogger.timestamp(MsgTraceEvent.WI_SUMMARY);
        try {
            executor.proceed();
        }
        catch (Throwable throwable) {
            tracingLogger.logDuration(MsgTraceEvent.WI_SUMMARY, timestamp, executor.getProcessedCount());
            throw throwable;
        }
        tracingLogger.logDuration(MsgTraceEvent.WI_SUMMARY, timestamp, executor.getProcessedCount());
        return executor.getOutputStream();
    }

    public static boolean isWriteable(MessageBodyWriter<?> provider, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        try {
            return provider.isWriteable(type, genericType, annotations, mediaType);
        }
        catch (Exception ex) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, LocalizationMessages.ERROR_MBW_ISWRITABLE(provider.getClass().getName()), ex);
            }
            return false;
        }
    }

    public static boolean isReadable(MessageBodyReader<?> provider, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        try {
            return provider.isReadable(type, genericType, annotations, mediaType);
        }
        catch (Exception ex) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, LocalizationMessages.ERROR_MBR_ISREADABLE(provider.getClass().getName()), ex);
            }
            return false;
        }
    }

    private static class ModelLookupKey {
        final Class<?> clazz;
        final MediaType mediaType;

        private ModelLookupKey(Class<?> clazz, MediaType mediaType) {
            this.clazz = clazz;
            this.mediaType = mediaType;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ModelLookupKey that = (ModelLookupKey)o;
            return !(this.clazz == null ? that.clazz != null : !this.clazz.equals(that.clazz)) && !(this.mediaType == null ? that.mediaType != null : !this.mediaType.equals(that.mediaType));
        }

        public int hashCode() {
            int result = this.clazz != null ? this.clazz.hashCode() : 0;
            result = 31 * result + (this.mediaType != null ? this.mediaType.hashCode() : 0);
            return result;
        }
    }

    private static class LegacyWorkerComparator<T>
    implements Comparator<AbstractEntityProviderModel<T>> {
        final DeclarationDistanceComparator<T> distanceComparator;

        private LegacyWorkerComparator(Class<T> type) {
            this.distanceComparator = new DeclarationDistanceComparator<T>(type);
        }

        @Override
        public int compare(AbstractEntityProviderModel<T> modelA, AbstractEntityProviderModel<T> modelB) {
            MediaType mtB;
            if (modelA.isCustom() ^ modelB.isCustom()) {
                return modelA.isCustom() ? -1 : 1;
            }
            MediaType mtA = modelA.declaredTypes().get(0);
            int mediaTypeComparison = MediaTypes.PARTIAL_ORDER_COMPARATOR.compare(mtA, mtB = modelB.declaredTypes().get(0));
            if (mediaTypeComparison != 0 && !mtA.isCompatible(mtB)) {
                return mediaTypeComparison;
            }
            return this.distanceComparator.compare(modelA.provider(), modelB.provider());
        }
    }

    private static class WorkerComparator<T>
    implements Comparator<AbstractEntityProviderModel<T>> {
        final Class wantedType;
        final MediaType wantedMediaType;

        private WorkerComparator(Class wantedType, MediaType wantedMediaType) {
            this.wantedType = wantedType;
            this.wantedMediaType = wantedMediaType;
        }

        @Override
        public int compare(AbstractEntityProviderModel<T> modelA, AbstractEntityProviderModel<T> modelB) {
            int distance = this.compareTypeDistances(modelA.providedType(), modelB.providedType());
            if (distance != 0) {
                return distance;
            }
            int mediaTypeComparison = this.getMediaTypeDistance(this.wantedMediaType, modelA.declaredTypes()) - this.getMediaTypeDistance(this.wantedMediaType, modelB.declaredTypes());
            if (mediaTypeComparison != 0) {
                return mediaTypeComparison;
            }
            if (modelA.isCustom() ^ modelB.isCustom()) {
                return modelA.isCustom() ? -1 : 1;
            }
            return 0;
        }

        private int getMediaTypeDistance(MediaType wanted, List<MediaType> mtl) {
            if (wanted == null) {
                return 0;
            }
            int distance = 2;
            for (MediaType mt : mtl) {
                if (MediaTypes.typeEqual(wanted, mt)) {
                    return 0;
                }
                if (distance <= 1 || !MediaTypes.typeEqual(MediaTypes.getTypeWildCart(wanted), mt)) continue;
                distance = 1;
            }
            return distance;
        }

        private int compareTypeDistances(Class<?> providerClassParam1, Class<?> providerClassParam2) {
            return this.getTypeDistance(providerClassParam1) - this.getTypeDistance(providerClassParam2);
        }

        private int getTypeDistance(Class<?> classParam) {
            Class tmp1 = this.wantedType;
            Class<?> tmp2 = classParam;
            Iterator<Class<?>> it1 = this.getClassHierarchyIterator(tmp1);
            Iterator<Class<?>> it2 = this.getClassHierarchyIterator(tmp2);
            int distance = 0;
            while (!this.wantedType.equals(tmp2) && !classParam.equals(tmp1)) {
                ++distance;
                if (!this.wantedType.equals(tmp2)) {
                    Class<?> clazz = tmp2 = it2.hasNext() ? it2.next() : null;
                }
                if (!classParam.equals(tmp1)) {
                    Class clazz = tmp1 = it1.hasNext() ? it1.next() : null;
                }
                if (tmp2 != null || tmp1 != null) continue;
                return Integer.MAX_VALUE;
            }
            return distance;
        }

        private Iterator<Class<?>> getClassHierarchyIterator(Class<?> classParam) {
            if (classParam == null) {
                return Collections.emptyList().iterator();
            }
            ArrayList<Class<Object>> classes = new ArrayList<Class<Object>>();
            LinkedList unprocessed = new LinkedList();
            boolean objectFound = false;
            unprocessed.add(classParam);
            while (!unprocessed.isEmpty()) {
                Class clazz = (Class)unprocessed.removeFirst();
                if (Object.class.equals((Object)clazz)) {
                    objectFound = true;
                } else {
                    classes.add(clazz);
                }
                unprocessed.addAll(Arrays.asList(clazz.getInterfaces()));
                Class superclazz = clazz.getSuperclass();
                if (superclazz == null) continue;
                unprocessed.add(superclazz);
            }
            if (objectFound) {
                classes.add(Object.class);
            }
            return classes.iterator();
        }
    }

    private static class DeclarationDistanceComparator<T>
    implements Comparator<T> {
        private final Class<T> declared;
        private final Map<Class, Integer> distanceMap = new HashMap<Class, Integer>();

        DeclarationDistanceComparator(Class<T> declared) {
            this.declared = declared;
        }

        @Override
        public int compare(T o1, T o2) {
            int d1 = this.getDistance(o1);
            int d2 = this.getDistance(o2);
            return d2 - d1;
        }

        private int getDistance(T t) {
            Integer distance = this.distanceMap.get(t.getClass());
            if (distance != null) {
                return distance;
            }
            distance = 0;
            for (Class a = (as = ReflectionHelper.getParameterizedClassArguments(p = ReflectionHelper.getClass(t.getClass(), this.declared))) != null ? as[0] : null; a != null && a != Object.class; a = a.getSuperclass()) {
                Integer n = distance;
                Integer n2 = distance = Integer.valueOf(distance + 1);
            }
            this.distanceMap.put(t.getClass(), distance);
            return distance;
        }
    }

    public static class MessageBodyWorkersConfigurator
    implements BootstrapConfigurator {
        private MessageBodyFactory messageBodyFactory;

        @Override
        public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            this.messageBodyFactory = new MessageBodyFactory(bootstrapBag.getConfiguration());
            InstanceBinding binding = (InstanceBinding)Bindings.service(this.messageBodyFactory).to(MessageBodyWorkers.class);
            injectionManager.register(binding);
        }

        @Override
        public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            this.messageBodyFactory.initialize(injectionManager);
            bootstrapBag.setMessageBodyWorkers(this.messageBodyFactory);
        }
    }
}

