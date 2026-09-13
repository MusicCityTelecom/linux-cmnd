/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.BeanDescription
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.SerializationConfig
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.Serializers$Base
 *  com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer
 *  com.fasterxml.jackson.databind.ser.std.ToStringSerializer
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.fasterxml.jackson.databind.type.ReferenceType
 *  com.fasterxml.jackson.databind.util.Converter
 *  com.fasterxml.jackson.databind.util.StdConverter
 *  com.google.common.base.Optional
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheBuilderSpec
 *  com.google.common.collect.FluentIterable
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Range
 *  com.google.common.collect.RangeSet
 *  com.google.common.collect.Table
 *  com.google.common.hash.HashCode
 *  com.google.common.net.HostAndPort
 *  com.google.common.net.InternetDomainName
 */
package com.fasterxml.jackson.datatype.guava;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.fasterxml.jackson.datatype.guava.ser.CacheSerializer;
import com.fasterxml.jackson.datatype.guava.ser.GuavaOptionalSerializer;
import com.fasterxml.jackson.datatype.guava.ser.MultimapSerializer;
import com.fasterxml.jackson.datatype.guava.ser.RangeSerializer;
import com.fasterxml.jackson.datatype.guava.ser.RangeSetSerializer;
import com.fasterxml.jackson.datatype.guava.ser.TableSerializer;
import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Table;
import com.google.common.hash.HashCode;
import com.google.common.net.HostAndPort;
import com.google.common.net.InternetDomainName;
import java.io.Serializable;
import java.util.Set;

public class GuavaSerializers
extends Serializers.Base
implements Serializable {
    static final long serialVersionUID = 1L;

    public JsonSerializer<?> findReferenceSerializer(SerializationConfig config, ReferenceType refType, BeanDescription beanDesc, TypeSerializer contentTypeSerializer, JsonSerializer<Object> contentValueSerializer) {
        Class raw = refType.getRawClass();
        if (Optional.class.isAssignableFrom(raw)) {
            boolean staticTyping = contentTypeSerializer == null && config.isEnabled(MapperFeature.USE_STATIC_TYPING);
            return new GuavaOptionalSerializer(refType, staticTyping, contentTypeSerializer, contentValueSerializer);
        }
        return null;
    }

    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        Class raw = type.getRawClass();
        if (RangeSet.class.isAssignableFrom(raw)) {
            return new RangeSetSerializer();
        }
        if (Range.class.isAssignableFrom(raw)) {
            return new RangeSerializer(this._findDeclared(type, Range.class));
        }
        if (Table.class.isAssignableFrom(raw)) {
            return new TableSerializer(this._findDeclared(type, Table.class));
        }
        if (HostAndPort.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        if (InternetDomainName.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        if (CacheBuilderSpec.class.isAssignableFrom(raw) || CacheBuilder.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        if (HashCode.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        if (FluentIterable.class.isAssignableFrom(raw)) {
            JavaType iterableType = this._findDeclared(type, Iterable.class);
            return new StdDelegatingSerializer((Converter)FluentConverter.instance, iterableType, null);
        }
        if (Cache.class.isAssignableFrom(raw)) {
            return new CacheSerializer();
        }
        return super.findSerializer(config, type, beanDesc);
    }

    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig config, MapLikeType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        if (Multimap.class.isAssignableFrom(type.getRawClass())) {
            AnnotationIntrospector intr = config.getAnnotationIntrospector();
            Object filterId = intr.findFilterId((Annotated)beanDesc.getClassInfo());
            JsonIgnoreProperties.Value ignorals = config.getDefaultPropertyIgnorals(Multimap.class, beanDesc.getClassInfo());
            Set ignored = ignorals == null ? null : ignorals.getIgnored();
            return new MultimapSerializer(type, beanDesc, keySerializer, elementTypeSerializer, elementValueSerializer, ignored, filterId);
        }
        return null;
    }

    private JavaType _findDeclared(JavaType subtype, Class<?> target) {
        JavaType decl = subtype.findSuperType(target);
        if (decl == null) {
            throw new IllegalArgumentException("Strange " + target.getName() + " sub-type, " + subtype + ", can not find type parameters");
        }
        return decl;
    }

    static class FluentConverter
    extends StdConverter<Object, Iterable<?>> {
        static final FluentConverter instance = new FluentConverter();

        FluentConverter() {
        }

        public Iterable<?> convert(Object value) {
            return (Iterable)value;
        }
    }
}

