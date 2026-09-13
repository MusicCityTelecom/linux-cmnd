/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.jaxb;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdapterConverter
extends StdConverter<Object, Object> {
    protected final JavaType _inputType;
    protected final JavaType _targetType;
    protected final XmlAdapter<Object, Object> _adapter;
    protected final boolean _forSerialization;

    public AdapterConverter(XmlAdapter<?, ?> adapter, JavaType inType, JavaType outType, boolean ser) {
        this._adapter = adapter;
        this._inputType = inType;
        this._targetType = outType;
        this._forSerialization = ser;
    }

    @Override
    public Object convert(Object value) {
        try {
            if (this._forSerialization) {
                return this._adapter.marshal(value);
            }
            return this._adapter.unmarshal(value);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return this._inputType;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return this._targetType;
    }
}

