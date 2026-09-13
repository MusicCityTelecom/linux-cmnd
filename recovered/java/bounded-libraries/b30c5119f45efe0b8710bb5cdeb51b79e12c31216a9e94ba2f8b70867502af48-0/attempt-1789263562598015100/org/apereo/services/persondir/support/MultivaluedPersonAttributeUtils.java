/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 */
package org.apereo.services.persondir.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;

public final class MultivaluedPersonAttributeUtils {
    public static Map<String, Set<String>> parseAttributeToAttributeMapping(Map<String, ? extends Object> mapping) {
        if (mapping == null) {
            return new HashMap<String, Set<String>>();
        }
        LinkedHashMap mappedAttributesBuilder = new LinkedHashMap();
        for (Map.Entry<String, ? extends Object> mappingEntry : mapping.entrySet()) {
            String sourceAttrName = mappingEntry.getKey();
            Validate.notNull((Object)sourceAttrName, (String)"attribute name can not be null in Map", (Object[])new Object[0]);
            Object mappedAttribute = mappingEntry.getValue();
            if (mappedAttribute == null) {
                mappedAttributesBuilder.put(sourceAttrName, null);
                continue;
            }
            if (mappedAttribute instanceof String) {
                HashSet<String> mappedSet = new HashSet<String>();
                mappedSet.add(mappedAttribute.toString());
                mappedAttributesBuilder.put(sourceAttrName, mappedSet);
                continue;
            }
            if (mappedAttribute instanceof Collection) {
                Collection sourceSet = (Collection)mappedAttribute;
                LinkedHashSet<String> mappedSet = new LinkedHashSet<String>();
                for (Object sourceObj : sourceSet) {
                    if (sourceObj != null) {
                        mappedSet.add(sourceObj.toString());
                        continue;
                    }
                    mappedSet.add(null);
                }
                mappedAttributesBuilder.put(sourceAttrName, mappedSet);
                continue;
            }
            throw new IllegalArgumentException("Invalid mapped type. key='" + sourceAttrName + "', value type='" + mappedAttribute.getClass().getName() + "'");
        }
        return new HashMap<String, Set<String>>(mappedAttributesBuilder);
    }

    public static <K, V> void addResult(Map<K, List<V>> results, K key, Object value) {
        Validate.notNull(results, (String)"Cannot add a result to a null map.", (Object[])new Object[0]);
        Validate.notNull(key, (String)"Cannot add a result with a null key.", (Object[])new Object[0]);
        if (value == null) {
            return;
        }
        List<V> currentValue = results.get(key);
        if (currentValue == null) {
            currentValue = new LinkedList<V>();
            results.put(key, currentValue);
        }
        if (value instanceof List) {
            currentValue.addAll((List)value);
        } else {
            currentValue.add(value);
        }
    }

    public static <T> Collection<T> flattenCollection(Collection<? extends Object> source) {
        Validate.notNull(source, (String)"Cannot flatten a null collection.", (Object[])new Object[0]);
        LinkedList<Object> result = new LinkedList<Object>();
        for (Object object : source) {
            if (object instanceof Collection) {
                Collection<T> flatCollection = MultivaluedPersonAttributeUtils.flattenCollection((Collection)object);
                result.addAll(flatCollection);
                continue;
            }
            result.add(object);
        }
        return result;
    }

    public static Map<String, List<Object>> toMultivaluedMap(Map<String, Object> seed) {
        Validate.notNull(seed, (String)"seed can not be null", (Object[])new Object[0]);
        LinkedHashMap<String, List<Object>> multiSeed = new LinkedHashMap<String, List<Object>>(seed.size());
        for (Map.Entry<String, Object> seedEntry : seed.entrySet()) {
            String seedName = seedEntry.getKey();
            Object seedValue = seedEntry.getValue();
            if (seedValue instanceof List) {
                multiSeed.put(seedName, (List)seedValue);
                continue;
            }
            multiSeed.put(seedName, Collections.singletonList(seedValue));
        }
        return multiSeed;
    }

    public static Map<String, List<Object>> stuffAttributesIntoListValues(Map<String, ?> personAttributesMap, IPersonAttributeDaoFilter filter) {
        HashMap<String, List<Object>> personAttributes = new HashMap<String, List<Object>>();
        for (Map.Entry<String, ?> stringObjectEntry : personAttributesMap.entrySet()) {
            Object value = stringObjectEntry.getValue();
            if (value instanceof List) {
                personAttributes.put(stringObjectEntry.getKey(), (List)value);
                continue;
            }
            personAttributes.put(stringObjectEntry.getKey(), new ArrayList<Object>(Arrays.asList(value)));
        }
        return personAttributes;
    }

    private MultivaluedPersonAttributeUtils() {
    }
}

