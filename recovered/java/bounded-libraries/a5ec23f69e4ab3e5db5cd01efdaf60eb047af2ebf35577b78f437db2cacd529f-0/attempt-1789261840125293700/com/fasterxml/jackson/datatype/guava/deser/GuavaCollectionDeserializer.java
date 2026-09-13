/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Feature
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.LogicalType
 *  com.fasterxml.jackson.databind.util.AccessPattern
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.util.Collection;

public abstract class GuavaCollectionDeserializer<T>
extends ContainerDeserializerBase<T>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    protected final JsonDeserializer<?> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    protected GuavaCollectionDeserializer(JavaType selfType, JsonDeserializer<?> deser, TypeDeserializer typeDeser, NullValueProvider nuller, Boolean unwrapSingle) {
        super(selfType, nuller, unwrapSingle);
        this._valueTypeDeserializer = typeDeser;
        this._valueDeserializer = deser;
    }

    public abstract GuavaCollectionDeserializer<T> withResolved(JsonDeserializer<?> var1, TypeDeserializer var2, NullValueProvider var3, Boolean var4);

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Boolean unwrapSingle = this.findFormatFeature(ctxt, property, Collection.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonDeserializer valueDeser = this._valueDeserializer;
        TypeDeserializer valueTypeDeser = this._valueTypeDeserializer;
        if (valueDeser == null) {
            valueDeser = ctxt.findContextualValueDeserializer(this._containerType.getContentType(), property);
        }
        if (valueTypeDeser != null) {
            valueTypeDeser = valueTypeDeser.forProperty(property);
        }
        NullValueProvider nuller = this.findContentNullProvider(ctxt, property, valueDeser);
        if (unwrapSingle != this._unwrapSingle || nuller != this._nullProvider || valueDeser != this._valueDeserializer || valueTypeDeser != this._valueTypeDeserializer) {
            return this.withResolved(valueDeser, valueTypeDeser, nuller, unwrapSingle);
        }
        return this;
    }

    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    public LogicalType logicalType() {
        return LogicalType.Collection;
    }

    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(p, ctxt);
    }

    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.isExpectedStartArrayToken()) {
            return this._deserializeContents(p, ctxt);
        }
        if (ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            return this._deserializeFromSingleValue(p, ctxt);
        }
        return (T)ctxt.handleUnexpectedToken(this._valueClass, p);
    }

    public abstract AccessPattern getEmptyAccessPattern();

    public abstract Object getEmptyValue(DeserializationContext var1) throws JsonMappingException;

    protected abstract T _deserializeContents(JsonParser var1, DeserializationContext var2) throws IOException;

    protected T _deserializeFromSingleValue(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object value;
        JsonDeserializer<?> valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            if (this._skipNullValues) {
                return this._createEmpty(ctxt);
            }
            value = this._nullProvider.getNullValue(ctxt);
        } else {
            value = typeDeser == null ? valueDes.deserialize(p, ctxt) : valueDes.deserializeWithType(p, ctxt, typeDeser);
        }
        return this._createWithSingleElement(ctxt, value);
    }

    protected abstract T _createEmpty(DeserializationContext var1);

    protected abstract T _createWithSingleElement(DeserializationContext var1, Object var2);
}

