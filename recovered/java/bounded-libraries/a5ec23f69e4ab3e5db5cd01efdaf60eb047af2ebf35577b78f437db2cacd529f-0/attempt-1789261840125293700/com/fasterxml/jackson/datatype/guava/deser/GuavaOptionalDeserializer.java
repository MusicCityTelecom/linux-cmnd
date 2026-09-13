/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.deser.ValueInstantiator
 *  com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 *  com.google.common.base.Optional
 */
package com.fasterxml.jackson.datatype.guava.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.google.common.base.Optional;

public class GuavaOptionalDeserializer
extends ReferenceTypeDeserializer<Optional<?>> {
    private static final long serialVersionUID = 1L;

    public GuavaOptionalDeserializer(JavaType fullType, ValueInstantiator inst, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(fullType, inst, typeDeser, deser);
    }

    public GuavaOptionalDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new GuavaOptionalDeserializer(this._fullType, this._valueInstantiator, typeDeser, valueDeser);
    }

    public Optional<?> getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return Optional.fromNullable((Object)this._valueDeserializer.getNullValue(ctxt));
    }

    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return this.getEmptyValue(ctxt);
    }

    public Optional<?> referenceValue(Object contents) {
        return Optional.fromNullable((Object)contents);
    }

    public Object getReferenced(Optional<?> reference) {
        return reference.get();
    }

    public Optional<?> updateReference(Optional<?> reference, Object contents) {
        return Optional.fromNullable((Object)contents);
    }
}

