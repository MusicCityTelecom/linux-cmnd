/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.BeanDescription
 *  com.fasterxml.jackson.databind.DeserializationConfig
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.Deserializers$Base
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.CollectionType
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.fasterxml.jackson.databind.type.MapType
 *  com.fasterxml.jackson.databind.type.ReferenceType
 *  com.google.common.base.Optional
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.BoundType
 *  com.google.common.collect.EnumBiMap
 *  com.google.common.collect.EnumHashBiMap
 *  com.google.common.collect.EnumMultiset
 *  com.google.common.collect.ForwardingListMultimap
 *  com.google.common.collect.ForwardingSetMultimap
 *  com.google.common.collect.ForwardingSortedSetMultimap
 *  com.google.common.collect.HashBiMap
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.HashMultiset
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.common.collect.ImmutableCollection
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableListMultimap
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMultiset
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSetMultimap
 *  com.google.common.collect.ImmutableSortedMap
 *  com.google.common.collect.ImmutableSortedMultiset
 *  com.google.common.collect.ImmutableSortedSet
 *  com.google.common.collect.LinkedHashMultimap
 *  com.google.common.collect.LinkedHashMultiset
 *  com.google.common.collect.LinkedListMultimap
 *  com.google.common.collect.ListMultimap
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Multiset
 *  com.google.common.collect.Range
 *  com.google.common.collect.RangeSet
 *  com.google.common.collect.SetMultimap
 *  com.google.common.collect.SortedMultiset
 *  com.google.common.collect.SortedSetMultimap
 *  com.google.common.collect.Table
 *  com.google.common.collect.TreeMultimap
 *  com.google.common.collect.TreeMultiset
 *  com.google.common.hash.HashCode
 *  com.google.common.net.HostAndPort
 *  com.google.common.net.InternetDomainName
 */
package com.fasterxml.jackson.datatype.guava;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.datatype.guava.deser.GuavaOptionalDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.HashCodeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.HashMultisetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.HostAndPortDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableBiMapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableListDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableMapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableMultisetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableSetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableSortedMapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableSortedMultisetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.ImmutableSortedSetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.InternetDomainNameDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.LinkedHashMultisetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.RangeDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.RangeSetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.TreeMultisetDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.multimap.list.ArrayListMultimapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.multimap.list.LinkedListMultimapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.multimap.set.HashMultimapDeserializer;
import com.fasterxml.jackson.datatype.guava.deser.multimap.set.LinkedHashMultimapDeserializer;
import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.BoundType;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.ForwardingSetMultimap;
import com.google.common.collect.ForwardingSortedSetMultimap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;
import com.google.common.hash.HashCode;
import com.google.common.net.HostAndPort;
import com.google.common.net.InternetDomainName;
import java.io.Serializable;

public class GuavaDeserializers
extends Deserializers.Base
implements Serializable {
    static final long serialVersionUID = 1L;
    protected BoundType _defaultBoundType;

    public GuavaDeserializers() {
        this(null);
    }

    public GuavaDeserializers(BoundType defaultBoundType) {
        this._defaultBoundType = defaultBoundType;
    }

    public JsonDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        Class raw = type.getRawClass();
        if (ImmutableCollection.class.isAssignableFrom(raw)) {
            if (ImmutableList.class.isAssignableFrom(raw)) {
                return new ImmutableListDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            if (ImmutableMultiset.class.isAssignableFrom(raw)) {
                if (ImmutableSortedMultiset.class.isAssignableFrom(raw)) {
                    this.requireCollectionOfComparableElements(type, "ImmutableSortedMultiset");
                    return new ImmutableSortedMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
                }
                return new ImmutableMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            if (ImmutableSet.class.isAssignableFrom(raw)) {
                if (ImmutableSortedSet.class.isAssignableFrom(raw)) {
                    this.requireCollectionOfComparableElements(type, "ImmutableSortedSet");
                    return new ImmutableSortedSetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
                }
                return new ImmutableSetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            return new ImmutableListDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
        }
        if (Multiset.class.isAssignableFrom(raw)) {
            if (SortedMultiset.class.isAssignableFrom(raw)) {
                if (TreeMultiset.class.isAssignableFrom(raw)) {
                    return new TreeMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
                }
                return new TreeMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            if (LinkedHashMultiset.class.isAssignableFrom(raw)) {
                return new LinkedHashMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            if (HashMultiset.class.isAssignableFrom(raw)) {
                return new HashMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
            }
            if (EnumMultiset.class.isAssignableFrom(raw)) {
                // empty if block
            }
            return new HashMultisetDeserializer((JavaType)type, elementDeserializer, elementTypeDeserializer, null, null);
        }
        return null;
    }

    private void requireCollectionOfComparableElements(CollectionType actualType, String targetType) {
        Class elemType = actualType.getContentType().getRawClass();
        if (!Comparable.class.isAssignableFrom(elemType)) {
            throw new IllegalArgumentException("Can not handle " + targetType + " with elements that are not Comparable<?> (" + elemType.getName() + ")");
        }
    }

    public JsonDeserializer<?> findMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer valueTypeDeserializer, JsonDeserializer<?> valueDeserializer) throws JsonMappingException {
        Class raw = type.getRawClass();
        if (ImmutableMap.class.isAssignableFrom(raw)) {
            if (ImmutableSortedMap.class.isAssignableFrom(raw)) {
                return new ImmutableSortedMapDeserializer((JavaType)type, keyDeserializer, valueDeserializer, valueTypeDeserializer, null);
            }
            if (ImmutableBiMap.class.isAssignableFrom(raw)) {
                return new ImmutableBiMapDeserializer((JavaType)type, keyDeserializer, valueDeserializer, valueTypeDeserializer, null);
            }
            return new ImmutableMapDeserializer((JavaType)type, keyDeserializer, valueDeserializer, valueTypeDeserializer, null);
        }
        if (BiMap.class.isAssignableFrom(raw)) {
            if (EnumBiMap.class.isAssignableFrom(raw)) {
                // empty if block
            }
            if (EnumHashBiMap.class.isAssignableFrom(raw)) {
                // empty if block
            }
            if (HashBiMap.class.isAssignableFrom(raw)) {
                // empty if block
            }
        }
        return null;
    }

    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        Class raw = type.getRawClass();
        if (ListMultimap.class.isAssignableFrom(raw)) {
            if (ImmutableListMultimap.class.isAssignableFrom(raw)) {
                // empty if block
            }
            if (ArrayListMultimap.class.isAssignableFrom(raw)) {
                return new ArrayListMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            }
            if (LinkedListMultimap.class.isAssignableFrom(raw)) {
                return new LinkedListMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            }
            if (ForwardingListMultimap.class.isAssignableFrom(raw)) {
                // empty if block
            }
            return new ArrayListMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
        }
        if (SetMultimap.class.isAssignableFrom(raw)) {
            if (SortedSetMultimap.class.isAssignableFrom(raw)) {
                if (TreeMultimap.class.isAssignableFrom(raw)) {
                    // empty if block
                }
                if (ForwardingSortedSetMultimap.class.isAssignableFrom(raw)) {
                    // empty if block
                }
            }
            if (ImmutableSetMultimap.class.isAssignableFrom(raw)) {
                return new LinkedHashMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            }
            if (HashMultimap.class.isAssignableFrom(raw)) {
                return new HashMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            }
            if (LinkedHashMultimap.class.isAssignableFrom(raw)) {
                return new LinkedHashMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            }
            if (ForwardingSetMultimap.class.isAssignableFrom(raw)) {
                // empty if block
            }
            return new LinkedHashMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
        }
        if (Multimap.class.isAssignableFrom(raw)) {
            return new LinkedListMultimapDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
        }
        if (Table.class.isAssignableFrom(raw)) {
            // empty if block
        }
        return null;
    }

    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer, JsonDeserializer<?> contentDeserializer) {
        if (refType.hasRawClass(Optional.class)) {
            return new GuavaOptionalDeserializer((JavaType)refType, null, contentTypeDeserializer, contentDeserializer);
        }
        return null;
    }

    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) {
        if (type.hasRawClass(RangeSet.class)) {
            return new RangeSetDeserializer();
        }
        if (type.hasRawClass(Range.class)) {
            return new RangeDeserializer(this._defaultBoundType, type);
        }
        if (type.hasRawClass(HostAndPort.class)) {
            return HostAndPortDeserializer.std;
        }
        if (type.hasRawClass(InternetDomainName.class)) {
            return InternetDomainNameDeserializer.std;
        }
        if (type.hasRawClass(HashCode.class)) {
            return HashCodeDeserializer.std;
        }
        return null;
    }

    public boolean hasDeserializerFor(DeserializationConfig config, Class<?> valueType) {
        if (valueType.getName().startsWith("com.google.")) {
            return valueType == Optional.class || valueType == RangeSet.class || valueType == HostAndPort.class || valueType == InternetDomainName.class || valueType == HashCode.class || Multiset.class.isAssignableFrom(valueType) || Multimap.class.isAssignableFrom(valueType) || ImmutableCollection.class.isAssignableFrom(valueType) || ImmutableMap.class.isAssignableFrom(valueType) || BiMap.class.isAssignableFrom(valueType);
        }
        return false;
    }
}

