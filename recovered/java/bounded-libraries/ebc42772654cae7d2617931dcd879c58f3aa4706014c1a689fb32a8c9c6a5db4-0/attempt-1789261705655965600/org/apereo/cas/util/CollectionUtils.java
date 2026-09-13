/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Multimap
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public final class CollectionUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionUtils.class);

    public static Optional<Object> firstElement(Object obj) {
        Set<Object> object = CollectionUtils.toCollection(obj);
        if (object.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(object.iterator().next());
    }

    public static <T> Optional<T> firstElement(Object obj, Class<T> clazz) {
        Optional<Object> result = CollectionUtils.firstElement(obj);
        result.ifPresent(value -> Assert.isTrue((boolean)clazz.isAssignableFrom(value.getClass()), () -> "Invalid element subtype"));
        return result;
    }

    public static <T extends Collection> T toCollection(Object obj, Class<T> clazz) {
        return (T)((Collection)FunctionUtils.doUnchecked(() -> {
            Set<Object> results = CollectionUtils.toCollection(obj);
            if (clazz.isInterface()) {
                throw new IllegalArgumentException("Cannot accept an interface " + clazz.getSimpleName() + " to create a new object instance");
            }
            Collection col = (Collection)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            col.addAll(results);
            return col;
        }));
    }

    public static Set<Object> toCollection(Object obj) {
        LinkedHashSet<Object> c = new LinkedHashSet<Object>();
        if (obj == null) {
            LOGGER.trace("Converting null obj to empty collection");
        } else if (obj instanceof Collection) {
            c.addAll((Collection)obj);
            LOGGER.trace("Converting multi-valued element [{}]", obj);
        } else if (obj instanceof Map) {
            Map map = (Map)obj;
            Set set = map.entrySet();
            c.addAll(set.stream().map(e -> Pair.of(e.getKey(), e.getValue())).collect(Collectors.toSet()));
        } else if (obj.getClass().isArray()) {
            if (byte[].class.isInstance(obj)) {
                c.add(obj);
            } else {
                c.addAll(Arrays.stream((Object[])obj).collect(Collectors.toSet()));
            }
            LOGGER.trace("Converting array element [{}]", obj);
        } else if (obj instanceof Iterator) {
            Iterator it = (Iterator)obj;
            while (it.hasNext()) {
                c.add(it.next());
            }
        } else if (obj instanceof Enumeration) {
            Enumeration it = (Enumeration)obj;
            while (it.hasMoreElements()) {
                c.add(it.nextElement());
            }
        } else {
            c.add(obj);
            LOGGER.trace("Converting element [{}]", obj);
        }
        return c.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static <K, V> Map<K, V> wrap(Multimap<K, V> source) {
        if (source != null && !source.isEmpty()) {
            Map inner = source.asMap();
            HashMap map = new HashMap();
            inner.forEach((k, v) -> map.put(k, CollectionUtils.wrap(v)));
            return map;
        }
        return new HashMap(0);
    }

    public static <K, V> Map<K, V> wrap(Map<K, V> source) {
        if (source != null && !source.isEmpty()) {
            return new HashMap<K, V>(source);
        }
        return new HashMap(0);
    }

    public static <K, V> Map<K, V> wrap(String key, Object value) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        if (value != null && StringUtils.isNotBlank((CharSequence)key)) {
            map.put(key, value);
        }
        return map;
    }

    public static <K extends String, V> Map<K, V> wrap(String key, Object value, String key2, Object value2) {
        Map<String, Object> m = CollectionUtils.wrap(key, value);
        m.put(key2, value2);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2);
        m.put(key3, value3);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3);
        m.put(key4, value4);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4, String key5, Object value5) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3, key4, value4);
        m.put(key5, value5);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4, String key5, Object value5, String key6, Object value6) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3, key4, value4, key5, value5);
        m.put(key6, value6);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4, String key5, Object value5, String key6, Object value6, String key7, Object value7) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3, key4, value4, key5, value5, key6, value6);
        m.put(key7, value7);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4, String key5, Object value5, String key6, Object value6, String key7, Object value7, String key8, Object value8) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3, key4, value4, key5, value5, key6, value6, key7, value7);
        m.put(key8, value8);
        return m;
    }

    public static <K, V> Map<K, V> wrap(String key, Object value, String key2, Object value2, String key3, Object value3, String key4, Object value4, String key5, Object value5, String key6, Object value6, String key7, Object value7, String key8, Object value8, String key9, Object value9) {
        Map<String, Object> m = CollectionUtils.wrap(key, value, key2, value2, key3, value3, key4, value4, key5, value5, key6, value6, key7, value7, key8, value8);
        m.put(key9, value9);
        return m;
    }

    public static <T> List<T> wrap(T source) {
        ArrayList<Object> list = new ArrayList<Object>();
        if (source != null) {
            if (source instanceof Collection) {
                Iterator it = ((Collection)source).iterator();
                while (it.hasNext()) {
                    list.add(it.next());
                }
            } else if (source.getClass().isArray()) {
                if (source.getClass().isAssignableFrom(byte[].class)) {
                    list.add(source);
                } else {
                    List elements = Arrays.stream((Object[])source).collect(Collectors.toList());
                    list.addAll(elements);
                }
            } else {
                list.add(source);
            }
        }
        return list;
    }

    public static <T> List<T> wrap(List<T> source) {
        ArrayList<T> list = new ArrayList<T>();
        if (source != null && !source.isEmpty()) {
            list.addAll(source);
        }
        return list;
    }

    public static <T> Set<T> wrap(Set<T> source) {
        LinkedHashSet<T> list = new LinkedHashSet<T>();
        if (source != null && !source.isEmpty()) {
            list.addAll(source);
        }
        return list;
    }

    public static <T> Set<T> wrapSet(T source) {
        LinkedHashSet<T> list = new LinkedHashSet<T>();
        if (source != null) {
            list.add(source);
        }
        return list;
    }

    public static <T> Set<T> wrapSet(T ... source) {
        LinkedHashSet list = new LinkedHashSet();
        CollectionUtils.addToCollection(list, source);
        return list;
    }

    public static <T> HashSet<T> wrapHashSet(T ... source) {
        HashSet list = new HashSet();
        CollectionUtils.addToCollection(list, source);
        return list;
    }

    public static <T> HashSet<T> wrapHashSet(Collection<T> source) {
        return new HashSet<T>(source);
    }

    public static <T> List<T> wrapList(T ... source) {
        ArrayList list = new ArrayList();
        CollectionUtils.addToCollection(list, source);
        return list;
    }

    public static <T> ArrayList<T> wrapArrayList(T ... source) {
        ArrayList list = new ArrayList();
        CollectionUtils.addToCollection(list, source);
        return list;
    }

    public static <T> Map<String, T> wrapLinkedHashMap(String key, T source) {
        LinkedHashMap<String, T> list = new LinkedHashMap<String, T>();
        list.put(key, source);
        return list;
    }

    public static MultiValueMap asMultiValueMap(Map innerMap) {
        return org.springframework.util.CollectionUtils.toMultiValueMap((Map)innerMap);
    }

    public static MultiValueMap asMultiValueMap(String key, Object value) {
        return org.springframework.util.CollectionUtils.toMultiValueMap(CollectionUtils.wrap(key, value));
    }

    public static MultiValueMap asMultiValueMap(String key1, Object value1, String key2, Object value2) {
        Map wrap = CollectionUtils.wrap(key1, CollectionUtils.wrapList(value1), key2, CollectionUtils.wrapList(value2));
        return org.springframework.util.CollectionUtils.toMultiValueMap(wrap);
    }

    public static Map<String, String> convertDirectedListToMap(Collection<String> inputList) {
        TreeMap<String, String> mappings = new TreeMap<String, String>();
        inputList.stream().map(s -> {
            List bits = Splitter.on((String)"->").splitToList((CharSequence)s);
            return Pair.of((Object)((String)bits.get(0)), (Object)(bits.size() > 1 ? (String)bits.get(1) : ""));
        }).forEach(p -> mappings.put((String)p.getKey(), (String)p.getValue()));
        return mappings;
    }

    public static <T> Collection<T> wrapCollection(T ... source) {
        LinkedHashSet list = new LinkedHashSet();
        CollectionUtils.addToCollection(list, source);
        return list;
    }

    private static <T> void addToCollection(Collection<T> list, T[] source) {
        if (source != null) {
            Arrays.stream(source).forEach(s -> {
                Set<Object> col = CollectionUtils.toCollection(s);
                list.addAll(col);
            });
        }
    }

    public static Map<String, Object> merge(Map<String, ?> ... attributes) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        Arrays.stream(attributes).forEach(result::putAll);
        return result;
    }

    @Generated
    private CollectionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

