/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider
 *  com.fasterxml.jackson.databind.deser.std.StdDeserializer
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.LogicalType
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.LinkedListMultimap
 *  com.google.common.collect.ListMultimap
 *  com.google.common.collect.Multimap
 */
package com.fasterxml.jackson.datatype.guava.deser.multimap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class GuavaMultimapDeserializer<T extends Multimap<Object, Object>>
extends StdDeserializer<T>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    private static final List<String> METHOD_NAMES = ImmutableList.of((Object)"copyOf", (Object)"create");
    private final MapLikeType type;
    private final KeyDeserializer keyDeserializer;
    private final TypeDeserializer elementTypeDeserializer;
    private final JsonDeserializer<?> elementDeserializer;
    private final NullValueProvider nullProvider;
    private final boolean skipNullValues;
    private final Method creatorMethod;

    public GuavaMultimapDeserializer(MapLikeType type, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) {
        this(type, keyDeserializer, elementTypeDeserializer, elementDeserializer, GuavaMultimapDeserializer.findTransformer(type.getRawClass()), null);
    }

    public GuavaMultimapDeserializer(MapLikeType type, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer, Method creatorMethod, NullValueProvider nvp) {
        super((JavaType)type);
        this.type = type;
        this.keyDeserializer = keyDeserializer;
        this.elementTypeDeserializer = elementTypeDeserializer;
        this.elementDeserializer = elementDeserializer;
        this.creatorMethod = creatorMethod;
        this.nullProvider = nvp;
        this.skipNullValues = nvp == null ? false : NullsConstantProvider.isSkipper((NullValueProvider)nvp);
    }

    private static Method findTransformer(Class<?> rawType) {
        Method m2;
        if (rawType == LinkedListMultimap.class || rawType == ListMultimap.class || rawType == Multimap.class) {
            return null;
        }
        for (String methodName : METHOD_NAMES) {
            try {
                m2 = rawType.getMethod(methodName, Multimap.class);
                if (m2 == null) continue;
                return m2;
            }
            catch (NoSuchMethodException m2) {
            }
        }
        for (String methodName : METHOD_NAMES) {
            try {
                m2 = rawType.getMethod(methodName, Multimap.class);
                if (m2 == null) continue;
                return m2;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
        }
        return null;
    }

    protected abstract T createMultimap();

    public LogicalType logicalType() {
        return LogicalType.Map;
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        KeyDeserializer kd = this.keyDeserializer;
        if (kd == null) {
            kd = ctxt.findKeyDeserializer(this.type.getKeyType(), property);
        }
        JsonDeserializer valueDeser = this.elementDeserializer;
        JavaType vt = this.type.getContentType();
        valueDeser = valueDeser == null ? ctxt.findContextualValueDeserializer(vt, property) : ctxt.handleSecondaryContextualization(valueDeser, property, vt);
        TypeDeserializer vtd = this.elementTypeDeserializer;
        if (vtd != null) {
            vtd = vtd.forProperty(property);
        }
        return this._createContextual(this.type, kd, vtd, valueDeser, this.creatorMethod, this.findContentNullProvider(ctxt, property, valueDeser));
    }

    protected abstract JsonDeserializer<?> _createContextual(MapLikeType var1, KeyDeserializer var2, TypeDeserializer var3, JsonDeserializer<?> var4, Method var5, NullValueProvider var6);

    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            return this.deserializeFromSingleValue(p, ctxt);
        }
        return this.deserializeContents(p, ctxt);
    }

    private T deserializeContents(JsonParser p, DeserializationContext ctxt) throws IOException {
        T multimap = this.createMultimap();
        this.expect(p, JsonToken.START_OBJECT);
        while (p.nextToken() != JsonToken.END_OBJECT) {
            Object key = this.keyDeserializer != null ? this.keyDeserializer.deserializeKey(p.getCurrentName(), ctxt) : p.getCurrentName();
            p.nextToken();
            this.expect(p, JsonToken.START_ARRAY);
            while (p.nextToken() != JsonToken.END_ARRAY) {
                Object value;
                if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
                    if (this.skipNullValues) continue;
                    value = this.nullProvider.getNullValue(ctxt);
                } else {
                    value = this.elementTypeDeserializer != null ? this.elementDeserializer.deserializeWithType(p, ctxt, this.elementTypeDeserializer) : this.elementDeserializer.deserialize(p, ctxt);
                }
                multimap.put(key, value);
            }
        }
        if (this.creatorMethod == null) {
            return multimap;
        }
        try {
            Multimap map = (Multimap)this.creatorMethod.invoke(null, multimap);
            return (T)map;
        }
        catch (InvocationTargetException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
        catch (IllegalArgumentException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
        catch (IllegalAccessException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
    }

    private T deserializeFromSingleValue(JsonParser p, DeserializationContext ctxt) throws IOException {
        T multimap = this.createMultimap();
        this.expect(p, JsonToken.START_OBJECT);
        while (p.nextToken() != JsonToken.END_OBJECT) {
            Object value;
            Object key = this.keyDeserializer != null ? this.keyDeserializer.deserializeKey(p.getCurrentName(), ctxt) : p.getCurrentName();
            p.nextToken();
            if (p.currentToken() == JsonToken.START_ARRAY) {
                while (p.nextToken() != JsonToken.END_ARRAY) {
                    value = this.getCurrentTokenValue(p, ctxt);
                    multimap.put(key, value);
                }
                continue;
            }
            value = this.getCurrentTokenValue(p, ctxt);
            multimap.put(key, value);
        }
        if (this.creatorMethod == null) {
            return multimap;
        }
        try {
            Multimap map = (Multimap)this.creatorMethod.invoke(null, multimap);
            return (T)map;
        }
        catch (InvocationTargetException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
        catch (IllegalArgumentException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
        catch (IllegalAccessException e) {
            throw new JsonMappingException((Closeable)p, "Could not map to " + this.type, this._peel(e));
        }
    }

    private Object getCurrentTokenValue(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        if (this.elementTypeDeserializer != null) {
            return this.elementDeserializer.deserializeWithType(p, ctxt, this.elementTypeDeserializer);
        }
        return this.elementDeserializer.deserialize(p, ctxt);
    }

    private void expect(JsonParser p, JsonToken token) throws IOException {
        if (p.getCurrentToken() != token) {
            throw new JsonMappingException((Closeable)p, "Expecting " + token + ", found " + p.getCurrentToken(), p.getCurrentLocation());
        }
    }

    private Throwable _peel(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }
}

