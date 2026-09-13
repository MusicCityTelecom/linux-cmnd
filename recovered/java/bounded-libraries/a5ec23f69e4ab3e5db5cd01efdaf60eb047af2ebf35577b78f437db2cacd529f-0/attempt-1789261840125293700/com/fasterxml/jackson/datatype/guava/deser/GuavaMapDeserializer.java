/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer
 *  com.fasterxml.jackson.databind.deser.NullValueProvider
 *  com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.fasterxml.jackson.databind.type.LogicalType
 *  com.fasterxml.jackson.databind.util.AccessPattern
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

public abstract class GuavaMapDeserializer<T>
extends ContainerDeserializerBase<T>
implements ContextualDeserializer {
    private static final long serialVersionUID = 2L;
    protected KeyDeserializer _keyDeserializer;
    protected JsonDeserializer<?> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    protected GuavaMapDeserializer(JavaType type, KeyDeserializer keyDeser, JsonDeserializer<?> valueDeser, TypeDeserializer valueTypeDeser, NullValueProvider nuller) {
        super(type, nuller, null);
        this._keyDeserializer = keyDeser;
        this._valueDeserializer = valueDeser;
        this._valueTypeDeserializer = valueTypeDeser;
    }

    public abstract GuavaMapDeserializer<T> withResolved(KeyDeserializer var1, JsonDeserializer<?> var2, TypeDeserializer var3, NullValueProvider var4);

    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    public LogicalType logicalType() {
        return LogicalType.Map;
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        KeyDeserializer keyDeser = this._keyDeserializer;
        JsonDeserializer valueDeser = this._valueDeserializer;
        TypeDeserializer valueTypeDeser = this._valueTypeDeserializer;
        if (keyDeser == null) {
            keyDeser = ctxt.findKeyDeserializer(this._containerType.getKeyType(), property);
        } else if (keyDeser instanceof ContextualKeyDeserializer) {
            keyDeser = ((ContextualKeyDeserializer)keyDeser).createContextual(ctxt, property);
        }
        JavaType vt = this._containerType.getContentType();
        valueDeser = valueDeser == null ? ctxt.findContextualValueDeserializer(vt, property) : ctxt.handleSecondaryContextualization(valueDeser, property, vt);
        if (valueTypeDeser != null) {
            valueTypeDeser = valueTypeDeser.forProperty(property);
        }
        NullValueProvider nuller = this.findContentNullProvider(ctxt, property, valueDeser);
        if (this._keyDeserializer == keyDeser && this._valueDeserializer == valueDeser && this._valueTypeDeserializer == valueTypeDeser && this._nullProvider == nuller) {
            return this;
        }
        return this.withResolved(keyDeser, valueDeser, valueTypeDeser, nuller);
    }

    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromObject(p, ctxt);
    }

    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        }
        if (t != JsonToken.FIELD_NAME && t != JsonToken.END_OBJECT) {
            return (T)ctxt.handleUnexpectedToken(this._containerType.getRawClass(), p);
        }
        return this._deserializeEntries(p, ctxt);
    }

    public abstract AccessPattern getEmptyAccessPattern();

    public abstract Object getEmptyValue(DeserializationContext var1) throws JsonMappingException;

    protected abstract T _deserializeEntries(JsonParser var1, DeserializationContext var2) throws IOException;
}

