/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.vavr.collection.LinkedHashMap
 *  io.vavr.collection.LinkedHashSet
 *  io.vavr.collection.List
 *  io.vavr.collection.Map
 *  io.vavr.collection.Seq
 *  io.vavr.collection.Set
 *  io.vavr.collection.Traversable
 *  org.eclipse.collections.api.RichIterable
 *  org.eclipse.collections.api.bag.ImmutableBag
 *  org.eclipse.collections.api.bag.MutableBag
 *  org.eclipse.collections.api.factory.Bags
 *  org.eclipse.collections.api.factory.Lists
 *  org.eclipse.collections.api.factory.Maps
 *  org.eclipse.collections.api.factory.Sets
 *  org.eclipse.collections.api.list.ImmutableList
 *  org.eclipse.collections.api.list.MutableList
 *  org.eclipse.collections.api.map.ImmutableMap
 *  org.eclipse.collections.api.map.MapIterable
 *  org.eclipse.collections.api.map.MutableMap
 *  org.eclipse.collections.api.set.ImmutableSet
 *  org.eclipse.collections.api.set.MutableSet
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.ConditionalConverter
 *  org.springframework.core.convert.converter.ConditionalGenericConverter
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.convert.converter.ConverterRegistry
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.data.util;

import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Traversable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.data.util.CustomCollectionRegistrar;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class CustomCollections {
    private static final java.util.Set<Class<?>> CUSTOM_TYPES;
    private static final java.util.Set<Class<?>> CUSTOM_MAP_TYPES;
    private static final java.util.Set<Class<?>> CUSTOM_COLLECTION_TYPES;
    private static final java.util.Set<Class<?>> PAGINATION_RETURN_TYPES;
    private static final java.util.Set<Class<?>> COLLECTIONS_AND_MAP;
    private static final SearchableTypes MAP_TYPES;
    private static final SearchableTypes COLLECTION_TYPES;
    private static final Collection<CustomCollectionRegistrar> REGISTRARS;

    public static java.util.Set<Class<?>> getCustomTypes() {
        return CUSTOM_TYPES;
    }

    public static java.util.Set<Class<?>> getPaginationReturnTypes() {
        return PAGINATION_RETURN_TYPES;
    }

    public static boolean isMapBaseType(Class<?> type) {
        Assert.notNull(type, (String)"Type must not be null");
        return MAP_TYPES.has(type);
    }

    public static Class<?> getMapBaseType(Class<?> type) throws IllegalArgumentException {
        return MAP_TYPES.getSuperType(type);
    }

    public static boolean isMap(Class<?> type) {
        return MAP_TYPES.hasSuperTypeFor(type);
    }

    public static boolean isCollection(Class<?> type) {
        return COLLECTION_TYPES.hasSuperTypeFor(type);
    }

    public static java.util.Set<Function<Object, Object>> getUnwrappers() {
        return REGISTRARS.stream().map(CustomCollectionRegistrar::toJavaNativeCollection).collect(Collectors.toSet());
    }

    public static void registerConvertersIn(ConverterRegistry registry) {
        Assert.notNull((Object)registry, (String)"ConverterRegistry must not be null");
        REGISTRARS.forEach(it -> it.registerConvertersIn(registry));
    }

    private static void registerCollectionType(Class<?> type) {
        CUSTOM_TYPES.add(type);
        CUSTOM_COLLECTION_TYPES.add(type);
    }

    private static void registerMapType(Class<?> type) {
        CUSTOM_TYPES.add(type);
        CUSTOM_MAP_TYPES.add(type);
    }

    static {
        COLLECTIONS_AND_MAP = new HashSet<Class>(Arrays.asList(Collection.class, List.class, java.util.Set.class, Map.class));
        CUSTOM_TYPES = new HashSet();
        PAGINATION_RETURN_TYPES = new HashSet();
        CUSTOM_MAP_TYPES = new HashSet();
        CUSTOM_COLLECTION_TYPES = new HashSet();
        REGISTRARS = SpringFactoriesLoader.loadFactories(CustomCollectionRegistrar.class, (ClassLoader)CustomCollections.class.getClassLoader()).stream().filter(CustomCollectionRegistrar::isAvailable).collect(Collectors.toList());
        REGISTRARS.forEach(it -> {
            it.getCollectionTypes().forEach(CustomCollections::registerCollectionType);
            it.getMapTypes().forEach(CustomCollections::registerMapType);
            it.getAllowedPaginationReturnTypes().forEach(PAGINATION_RETURN_TYPES::add);
        });
        MAP_TYPES = new SearchableTypes(CUSTOM_MAP_TYPES, Map.class);
        COLLECTION_TYPES = new SearchableTypes(CUSTOM_COLLECTION_TYPES, Collection.class);
    }

    static class EclipseCollections
    implements CustomCollectionRegistrar {
        EclipseCollections() {
        }

        @Override
        public boolean isAvailable() {
            return ClassUtils.isPresent((String)"org.eclipse.collections.api.list.ImmutableList", (ClassLoader)EclipseCollections.class.getClassLoader());
        }

        @Override
        public Collection<Class<?>> getCollectionTypes() {
            return Arrays.asList(ImmutableList.class, ImmutableSet.class, ImmutableBag.class, MutableList.class, MutableSet.class, MutableBag.class);
        }

        @Override
        public Collection<Class<?>> getMapTypes() {
            return Arrays.asList(ImmutableMap.class, MutableMap.class);
        }

        @Override
        public Collection<Class<?>> getAllowedPaginationReturnTypes() {
            return Arrays.asList(ImmutableList.class, MutableList.class);
        }

        @Override
        public Function<Object, Object> toJavaNativeCollection() {
            return source -> source instanceof RichIterable ? EclipseToJavaConverter.INSTANCE.convert(source) : source;
        }

        @Override
        public void registerConvertersIn(ConverterRegistry registry) {
            registry.addConverter((Converter)EclipseToJavaConverter.INSTANCE);
            registry.addConverter((GenericConverter)JavaToEclipseConverter.INSTANCE);
        }

        static enum JavaToEclipseConverter implements ConditionalGenericConverter
        {
            INSTANCE;

            private static final java.util.Set<GenericConverter.ConvertiblePair> CONVERTIBLE_PAIRS;

            @NonNull
            public java.util.Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
                return CONVERTIBLE_PAIRS;
            }

            public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
                if (sourceType.isCollection() && MapIterable.class.isAssignableFrom(targetType.getType())) {
                    return false;
                }
                return !sourceType.isMap() || MapIterable.class.isAssignableFrom(targetType.getType()) || targetType.getType().equals(RichIterable.class);
            }

            @Nullable
            public Object convert(@Nullable Object source, TypeDescriptor sourceDescriptor, TypeDescriptor targetDescriptor) {
                Class targetType = targetDescriptor.getType();
                if (ImmutableList.class.isAssignableFrom(targetType)) {
                    return Lists.immutable.ofAll((Iterable)source);
                }
                if (ImmutableSet.class.isAssignableFrom(targetType)) {
                    return Sets.immutable.ofAll((Iterable)source);
                }
                if (ImmutableBag.class.isAssignableFrom(targetType)) {
                    return Bags.immutable.ofAll((Iterable)source);
                }
                if (ImmutableMap.class.isAssignableFrom(targetType)) {
                    return Maps.immutable.ofAll((Map)source);
                }
                if (MutableList.class.isAssignableFrom(targetType)) {
                    return Lists.mutable.ofAll((Iterable)source);
                }
                if (MutableSet.class.isAssignableFrom(targetType)) {
                    return Sets.mutable.ofAll((Iterable)source);
                }
                if (MutableBag.class.isAssignableFrom(targetType)) {
                    return Bags.mutable.ofAll((Iterable)source);
                }
                if (MutableMap.class.isAssignableFrom(targetType)) {
                    return Maps.mutable.ofMap((Map)source);
                }
                if (source instanceof List) {
                    return Lists.mutable.ofAll((Iterable)source);
                }
                if (source instanceof java.util.Set) {
                    return Sets.mutable.ofAll((Iterable)source);
                }
                if (source instanceof Map) {
                    return Maps.mutable.ofMap((Map)source);
                }
                return source;
            }

            static {
                HashSet<GenericConverter.ConvertiblePair> pairs = new HashSet<GenericConverter.ConvertiblePair>();
                pairs.add(new GenericConverter.ConvertiblePair(Collection.class, RichIterable.class));
                pairs.add(new GenericConverter.ConvertiblePair(java.util.Set.class, MutableSet.class));
                pairs.add(new GenericConverter.ConvertiblePair(java.util.Set.class, MutableList.class));
                pairs.add(new GenericConverter.ConvertiblePair(java.util.Set.class, ImmutableSet.class));
                pairs.add(new GenericConverter.ConvertiblePair(java.util.Set.class, ImmutableList.class));
                pairs.add(new GenericConverter.ConvertiblePair(List.class, MutableList.class));
                pairs.add(new GenericConverter.ConvertiblePair(List.class, ImmutableList.class));
                pairs.add(new GenericConverter.ConvertiblePair(Map.class, RichIterable.class));
                pairs.add(new GenericConverter.ConvertiblePair(Map.class, MutableMap.class));
                pairs.add(new GenericConverter.ConvertiblePair(Map.class, ImmutableMap.class));
                CONVERTIBLE_PAIRS = Collections.unmodifiableSet(pairs);
            }
        }

        static enum EclipseToJavaConverter implements Converter<Object, Object>,
        ConditionalConverter
        {
            INSTANCE;

            private static final TypeDescriptor RICH_ITERABLE_DESCRIPTOR;

            public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
                return sourceType.isAssignableTo(RICH_ITERABLE_DESCRIPTOR) && COLLECTIONS_AND_MAP.contains(targetType.getType());
            }

            @Nullable
            public Object convert(@Nullable Object source) {
                if (source instanceof ImmutableList) {
                    return ((ImmutableList)source).toList();
                }
                if (source instanceof ImmutableBag) {
                    return ((ImmutableBag)source).toList();
                }
                if (source instanceof ImmutableSet) {
                    return ((ImmutableSet)source).toSet();
                }
                if (source instanceof ImmutableMap) {
                    return ((ImmutableMap)source).toMap();
                }
                return source;
            }

            static {
                RICH_ITERABLE_DESCRIPTOR = TypeDescriptor.valueOf(RichIterable.class);
            }
        }
    }

    static class VavrCollections
    implements CustomCollectionRegistrar {
        private static final TypeDescriptor OBJECT_DESCRIPTOR = TypeDescriptor.valueOf(Object.class);

        VavrCollections() {
        }

        @Override
        public boolean isAvailable() {
            return ClassUtils.isPresent((String)"io.vavr.control.Option", (ClassLoader)VavrCollections.class.getClassLoader());
        }

        @Override
        public Collection<Class<?>> getMapTypes() {
            return Collections.singleton(io.vavr.collection.Map.class);
        }

        @Override
        public Collection<Class<?>> getCollectionTypes() {
            return Arrays.asList(Seq.class, Set.class);
        }

        @Override
        public Collection<Class<?>> getAllowedPaginationReturnTypes() {
            return Collections.singleton(Seq.class);
        }

        @Override
        public void registerConvertersIn(ConverterRegistry registry) {
            registry.addConverter((GenericConverter)JavaToVavrCollectionConverter.INSTANCE);
            registry.addConverter((GenericConverter)VavrToJavaCollectionConverter.INSTANCE);
        }

        @Override
        public Function<Object, Object> toJavaNativeCollection() {
            return source -> source instanceof Traversable ? VavrToJavaCollectionConverter.INSTANCE.convert(source, TypeDescriptor.forObject((Object)source), OBJECT_DESCRIPTOR) : source;
        }

        private static enum JavaToVavrCollectionConverter implements ConditionalGenericConverter
        {
            INSTANCE;

            private static final java.util.Set<GenericConverter.ConvertiblePair> CONVERTIBLE_PAIRS;

            @NonNull
            public java.util.Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
                return CONVERTIBLE_PAIRS;
            }

            public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
                if (sourceType.isCollection() && io.vavr.collection.Map.class.isAssignableFrom(targetType.getType())) {
                    return false;
                }
                return !sourceType.isMap() || io.vavr.collection.Map.class.isAssignableFrom(targetType.getType()) || targetType.getType().equals(Traversable.class);
            }

            @Nullable
            public Object convert(@Nullable Object source, TypeDescriptor sourceDescriptor, TypeDescriptor targetDescriptor) {
                Class targetType = targetDescriptor.getType();
                if (Seq.class.isAssignableFrom(targetType)) {
                    return io.vavr.collection.List.ofAll((Iterable)((Iterable)source));
                }
                if (Set.class.isAssignableFrom(targetType)) {
                    return LinkedHashSet.ofAll((Iterable)((Iterable)source));
                }
                if (io.vavr.collection.Map.class.isAssignableFrom(targetType)) {
                    return LinkedHashMap.ofAll((Map)((Map)source));
                }
                if (source instanceof List) {
                    return io.vavr.collection.List.ofAll((Iterable)((Iterable)source));
                }
                if (source instanceof java.util.Set) {
                    return LinkedHashSet.ofAll((Iterable)((Iterable)source));
                }
                if (source instanceof Map) {
                    return LinkedHashMap.ofAll((Map)((Map)source));
                }
                return source;
            }

            static {
                HashSet<GenericConverter.ConvertiblePair> pairs = new HashSet<GenericConverter.ConvertiblePair>();
                pairs.add(new GenericConverter.ConvertiblePair(Collection.class, Traversable.class));
                pairs.add(new GenericConverter.ConvertiblePair(Map.class, Traversable.class));
                CONVERTIBLE_PAIRS = Collections.unmodifiableSet(pairs);
            }
        }

        private static enum VavrToJavaCollectionConverter implements ConditionalGenericConverter
        {
            INSTANCE;

            private static final TypeDescriptor TRAVERSAL_TYPE;

            @NonNull
            public java.util.Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
                return COLLECTIONS_AND_MAP.stream().map(it -> new GenericConverter.ConvertiblePair(Traversable.class, it)).collect(Collectors.toSet());
            }

            public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
                return sourceType.isAssignableTo(TRAVERSAL_TYPE) && COLLECTIONS_AND_MAP.contains(targetType.getType());
            }

            @Nullable
            public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
                if (source == null) {
                    return null;
                }
                if (source instanceof Seq) {
                    return ((Seq)source).asJava();
                }
                if (source instanceof io.vavr.collection.Map) {
                    return ((io.vavr.collection.Map)source).toJavaMap();
                }
                if (source instanceof Set) {
                    return ((Set)source).toJavaSet();
                }
                throw new IllegalArgumentException("Unsupported Vavr collection " + source.getClass());
            }

            static {
                TRAVERSAL_TYPE = TypeDescriptor.valueOf(Traversable.class);
            }
        }
    }

    private static class SearchableTypes {
        private static final BiPredicate<Class<?>, Class<?>> EQUALS = (left, right) -> left.equals(right);
        private static final BiPredicate<Class<?>, Class<?>> IS_ASSIGNABLE = (left, right) -> left.isAssignableFrom((Class<?>)right);
        private static final Function<Class<?>, Boolean> IS_NOT_NULL = it -> it != null;
        private final Collection<Class<?>> types;

        public SearchableTypes(java.util.Set<Class<?>> types, Class<?> ... additional) {
            ArrayList all = new ArrayList(Arrays.asList(additional));
            all.addAll(types);
            this.types = all;
        }

        public boolean hasSuperTypeFor(Class<?> type) {
            Assert.notNull(type, (String)"Type must not be null");
            return this.isOneOf(type, IS_ASSIGNABLE, IS_NOT_NULL);
        }

        public boolean has(Class<?> type) {
            Assert.notNull(type, (String)"Type must not be null");
            return this.isOneOf(type, EQUALS, IS_NOT_NULL);
        }

        public Class<?> getSuperType(Class<?> type) {
            Assert.notNull(type, (String)"Type must not be null");
            Supplier<String> message = () -> String.format("Type %s not contained in candidates %s", type, this.types);
            return this.isOneOf(type, (l, r) -> l.isAssignableFrom((Class<?>)r), SearchableTypes.rejectNull(message));
        }

        private <T> T isOneOf(Class<?> type, BiPredicate<Class<?>, Class<?>> matcher, Function<Class<?>, T> resultMapper) {
            for (Class<?> candidate : this.types) {
                if (!matcher.test(candidate, type)) continue;
                return resultMapper.apply(candidate);
            }
            return resultMapper.apply(null);
        }

        private static Function<Class<?>, Class<?>> rejectNull(Supplier<String> message) {
            Assert.notNull(message, (String)"Message must not be null");
            return candidate -> {
                if (candidate == null) {
                    throw new IllegalArgumentException((String)message.get());
                }
                return candidate;
            };
        }
    }
}

