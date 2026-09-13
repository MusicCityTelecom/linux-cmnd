/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Feature
 *  com.fasterxml.jackson.annotation.JsonFormat$Value
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.type.WritableTypeId
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.BeanDescription
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.introspect.AnnotatedMember
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
 *  com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.ContainerSerializer
 *  com.fasterxml.jackson.databind.ser.ContextualSerializer
 *  com.fasterxml.jackson.databind.ser.PropertyFilter
 *  com.fasterxml.jackson.databind.ser.PropertyWriter
 *  com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap
 *  com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap$SerializerAndMapResult
 *  com.fasterxml.jackson.databind.ser.std.MapProperty
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.google.common.collect.Multimap
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultimapSerializer
extends ContainerSerializer<Multimap<?, ?>>
implements ContextualSerializer {
    private static final long serialVersionUID = 1L;
    private final MapLikeType _type;
    private final BeanProperty _property;
    private final JsonSerializer<Object> _keySerializer;
    private final TypeSerializer _valueTypeSerializer;
    private final JsonSerializer<Object> _valueSerializer;
    protected final Set<String> _ignoredEntries;
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final Object _filterId;
    protected final boolean _sortKeys;

    public MultimapSerializer(MapLikeType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer vts, JsonSerializer<Object> valueSerializer, Set<String> ignoredEntries, Object filterId) {
        super(type.getRawClass(), false);
        this._type = type;
        this._property = null;
        this._keySerializer = keySerializer;
        this._valueTypeSerializer = vts;
        this._valueSerializer = valueSerializer;
        this._ignoredEntries = ignoredEntries;
        this._filterId = filterId;
        this._sortKeys = false;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
    }

    protected MultimapSerializer(MultimapSerializer src, BeanProperty property, JsonSerializer<?> keySerializer, TypeSerializer vts, JsonSerializer<?> valueSerializer, Set<String> ignoredEntries, Object filterId, boolean sortKeys) {
        super((ContainerSerializer)src);
        this._type = src._type;
        this._property = property;
        this._keySerializer = keySerializer;
        this._valueTypeSerializer = vts;
        this._valueSerializer = valueSerializer;
        this._dynamicValueSerializers = src._dynamicValueSerializers;
        this._ignoredEntries = ignoredEntries;
        this._filterId = filterId;
        this._sortKeys = sortKeys;
    }

    protected MultimapSerializer withResolved(BeanProperty property, JsonSerializer<?> keySer, TypeSerializer vts, JsonSerializer<?> valueSer, Set<String> ignored, Object filterId, boolean sortKeys) {
        return new MultimapSerializer(this, property, keySer, vts, valueSer, ignored, filterId, sortKeys);
    }

    protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSer) {
        return new MultimapSerializer(this, this._property, this._keySerializer, typeSer, this._valueSerializer, this._ignoredEntries, this._filterId, this._sortKeys);
    }

    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        Boolean B;
        JsonFormat.Value format;
        JsonSerializer valueSer = this._valueSerializer;
        if (valueSer == null) {
            JavaType valueType = this._type.getContentType();
            if (valueType.isFinal()) {
                valueSer = provider.findValueSerializer(valueType, property);
            }
        } else if (valueSer instanceof ContextualSerializer) {
            valueSer = ((ContextualSerializer)valueSer).createContextual(provider, property);
        }
        AnnotationIntrospector intr = provider.getAnnotationIntrospector();
        AnnotatedMember propertyAcc = property == null ? null : property.getMember();
        JsonSerializer keySer = null;
        Object filterId = this._filterId;
        if (propertyAcc != null && intr != null) {
            Object serDef = intr.findKeySerializer((Annotated)propertyAcc);
            if (serDef != null) {
                keySer = provider.serializerInstance((Annotated)propertyAcc, serDef);
            }
            if ((serDef = intr.findContentSerializer((Annotated)propertyAcc)) != null) {
                valueSer = provider.serializerInstance((Annotated)propertyAcc, serDef);
            }
            filterId = intr.findFilterId((Annotated)propertyAcc);
        }
        if (valueSer == null) {
            valueSer = this._valueSerializer;
        }
        if ((valueSer = this.findContextualConvertingSerializer(provider, property, valueSer)) == null) {
            JavaType valueType = this._type.getContentType();
            if (valueType.useStaticType()) {
                valueSer = provider.findValueSerializer(valueType, property);
            }
        } else {
            valueSer = provider.handleSecondaryContextualization(valueSer, property);
        }
        if (keySer == null) {
            keySer = this._keySerializer;
        }
        keySer = keySer == null ? provider.findKeySerializer(this._type.getKeyType(), property) : provider.handleSecondaryContextualization(keySer, property);
        TypeSerializer typeSer = this._valueTypeSerializer;
        if (typeSer != null) {
            typeSer = typeSer.forProperty(property);
        }
        Set<String> ignored = this._ignoredEntries;
        boolean sortKeys = false;
        if (intr != null && propertyAcc != null) {
            Boolean b;
            Set newIgnored;
            JsonIgnoreProperties.Value ignorals = intr.findPropertyIgnoralByName((MapperConfig)provider.getConfig(), (Annotated)propertyAcc);
            if (ignorals != null && (newIgnored = ignorals.findIgnoredForSerialization()) != null && !newIgnored.isEmpty()) {
                ignored = ignored == null ? new HashSet<String>() : new HashSet<String>(ignored);
                for (String str : newIgnored) {
                    ignored.add(str);
                }
            }
            boolean bl = sortKeys = (b = intr.findSerializationSortAlphabetically((Annotated)propertyAcc)) != null && b != false;
        }
        if ((format = this.findFormatOverrides(provider, property, this.handledType())) != null && (B = format.getFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES)) != null) {
            sortKeys = B;
        }
        return this.withResolved(property, keySer, typeSer, valueSer, ignored, filterId, sortKeys);
    }

    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    public JavaType getContentType() {
        return this._type.getContentType();
    }

    public boolean hasSingleElement(Multimap<?, ?> map) {
        return map.size() == 1;
    }

    public boolean isEmpty(SerializerProvider prov, Multimap<?, ?> value) {
        return value.isEmpty();
    }

    public void serialize(Multimap<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.setCurrentValue(value);
        if (!value.isEmpty()) {
            if (this._filterId != null) {
                this.serializeFilteredFields(value, gen, provider);
            } else {
                this.serializeFields(value, gen, provider);
            }
        }
        gen.writeEndObject();
    }

    public void serializeWithType(Multimap<?, ?> value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        gen.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
        if (!value.isEmpty()) {
            if (this._filterId != null) {
                this.serializeFilteredFields(value, gen, provider);
            } else {
                this.serializeFields(value, gen, provider);
            }
        }
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }

    private final void serializeFields(Multimap<?, ?> mmap, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<String> ignored = this._ignoredEntries;
        PropertySerializerMap serializers = this._dynamicValueSerializers;
        for (Map.Entry entry : mmap.asMap().entrySet()) {
            Object key = entry.getKey();
            if (ignored != null && ignored.contains(key)) continue;
            if (key == null) {
                provider.findNullKeySerializer(this._type.getKeyType(), this._property).serialize(null, gen, provider);
            } else {
                this._keySerializer.serialize(key, gen, provider);
            }
            gen.writeStartArray();
            for (Object vv : (Collection)entry.getValue()) {
                Class<?> cc;
                if (vv == null) {
                    provider.defaultSerializeNull(gen);
                    continue;
                }
                JsonSerializer<Object> valueSer = this._valueSerializer;
                if (valueSer == null && (valueSer = serializers.serializerFor(cc = vv.getClass())) == null) {
                    valueSer = this._findAndAddDynamic(serializers, cc, provider);
                    serializers = this._dynamicValueSerializers;
                }
                if (this._valueTypeSerializer == null) {
                    valueSer.serialize(vv, gen, provider);
                    continue;
                }
                valueSer.serializeWithType(vv, gen, provider, this._valueTypeSerializer);
            }
            gen.writeEndArray();
        }
    }

    private final void serializeFilteredFields(Multimap<?, ?> mmap, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<String> ignored = this._ignoredEntries;
        PropertyFilter filter = this.findPropertyFilter(provider, this._filterId, mmap);
        MapProperty prop = new MapProperty(this._valueTypeSerializer, this._property);
        for (Map.Entry entry : mmap.asMap().entrySet()) {
            Object key = entry.getKey();
            if (ignored != null && ignored.contains(key)) continue;
            Collection value = (Collection)entry.getValue();
            JsonSerializer<Object> valueSer = value == null ? provider.getDefaultNullValueSerializer() : this._valueSerializer;
            prop.reset(key, (Object)value, this._keySerializer, valueSer);
            try {
                filter.serializeAsField(mmap, gen, provider, (PropertyWriter)prop);
            }
            catch (Exception e) {
                String keyDesc = "" + key;
                this.wrapAndThrow(provider, e, value, keyDesc);
            }
        }
    }

    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JsonMapFormatVisitor v2;
        JsonMapFormatVisitor jsonMapFormatVisitor = v2 = visitor == null ? null : visitor.expectMapFormat(typeHint);
        if (v2 != null) {
            v2.keyFormat(this._keySerializer, this._type.getKeyType());
            JsonSerializer<Object> valueSer = this._valueSerializer;
            final JavaType vt = this._type.getContentType();
            final SerializerProvider prov = visitor.getProvider();
            if (valueSer == null) {
                valueSer = this._findAndAddDynamic(this._dynamicValueSerializers, vt, prov);
            }
            final JsonSerializer<Object> valueSer2 = valueSer;
            v2.valueFormat(new JsonFormatVisitable(){
                final JavaType arrayType;
                {
                    this.arrayType = prov.getTypeFactory().constructArrayType(vt);
                }

                public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper v3, JavaType hint3) throws JsonMappingException {
                    JsonArrayFormatVisitor v4 = v3.expectArrayFormat(this.arrayType);
                    if (v4 != null) {
                        v4.itemsFormat((JsonFormatVisitable)valueSer2, vt);
                    }
                }
            }, vt);
        }
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicValueSerializers = result.map;
        }
        return result.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicValueSerializers = result.map;
        }
        return result.serializer;
    }
}

