/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer
 *  com.fasterxml.jackson.databind.type.ReferenceType
 *  com.fasterxml.jackson.databind.util.NameTransformer
 *  com.google.common.base.Optional
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.google.common.base.Optional;

public class GuavaOptionalSerializer
extends ReferenceTypeSerializer<Optional<?>> {
    private static final long serialVersionUID = 1L;

    public GuavaOptionalSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> ser) {
        super(fullType, staticTyping, vts, ser);
    }

    public GuavaOptionalSerializer(GuavaOptionalSerializer base, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper, Object suppressableValue, boolean suppressNulls) {
        super((ReferenceTypeSerializer)base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
    }

    protected ReferenceTypeSerializer<Optional<?>> withResolved(BeanProperty prop, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper) {
        if (this._property == prop && this._valueTypeSerializer == vts && this._valueSerializer == valueSer && this._unwrapper == unwrapper) {
            return this;
        }
        return new GuavaOptionalSerializer(this, prop, vts, valueSer, unwrapper, this._suppressableValue, this._suppressNulls);
    }

    public ReferenceTypeSerializer<Optional<?>> withContentInclusion(Object suppressableValue, boolean suppressNulls) {
        return new GuavaOptionalSerializer(this, this._property, this._valueTypeSerializer, this._valueSerializer, this._unwrapper, suppressableValue, suppressNulls);
    }

    protected boolean _isValuePresent(Optional<?> value) {
        return value.isPresent();
    }

    protected Object _getReferenced(Optional<?> value) {
        return value.get();
    }

    protected Object _getReferencedIfPresent(Optional<?> value) {
        return value.isPresent() ? value.get() : null;
    }
}

