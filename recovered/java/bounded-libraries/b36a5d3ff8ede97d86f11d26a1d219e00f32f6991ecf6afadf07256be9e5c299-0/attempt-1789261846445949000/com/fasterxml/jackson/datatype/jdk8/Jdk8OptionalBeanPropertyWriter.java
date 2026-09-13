/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.PropertyName
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.util.NameTransformer
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8UnwrappingOptionalBeanPropertyWriter;

public class Jdk8OptionalBeanPropertyWriter
extends BeanPropertyWriter {
    private static final long serialVersionUID = 1L;
    protected final Object _empty;

    protected Jdk8OptionalBeanPropertyWriter(BeanPropertyWriter base, Object empty) {
        super(base);
        this._empty = empty;
    }

    protected Jdk8OptionalBeanPropertyWriter(Jdk8OptionalBeanPropertyWriter base, PropertyName newName) {
        super((BeanPropertyWriter)base, newName);
        this._empty = base._empty;
    }

    protected BeanPropertyWriter _new(PropertyName newName) {
        return new Jdk8OptionalBeanPropertyWriter(this, newName);
    }

    public BeanPropertyWriter unwrappingWriter(NameTransformer unwrapper) {
        return new Jdk8UnwrappingOptionalBeanPropertyWriter(this, unwrapper, this._empty);
    }

    public void serializeAsField(Object bean, JsonGenerator g, SerializerProvider prov) throws Exception {
        Object value;
        if (this._nullSerializer == null && ((value = this.get(bean)) == null || value.equals(this._empty))) {
            return;
        }
        super.serializeAsField(bean, g, prov);
    }
}

