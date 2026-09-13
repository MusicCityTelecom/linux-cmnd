/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.io.SerializedString
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter
 *  com.fasterxml.jackson.databind.util.NameTransformer
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public class Jdk8UnwrappingOptionalBeanPropertyWriter
extends UnwrappingBeanPropertyWriter {
    private static final long serialVersionUID = 1L;
    protected final Object _empty;

    public Jdk8UnwrappingOptionalBeanPropertyWriter(BeanPropertyWriter base, NameTransformer transformer, Object empty) {
        super(base, transformer);
        this._empty = empty;
    }

    protected Jdk8UnwrappingOptionalBeanPropertyWriter(Jdk8UnwrappingOptionalBeanPropertyWriter base, NameTransformer transformer, SerializedString name) {
        super((UnwrappingBeanPropertyWriter)base, transformer, name);
        this._empty = base._empty;
    }

    protected UnwrappingBeanPropertyWriter _new(NameTransformer transformer, SerializedString newName) {
        return new Jdk8UnwrappingOptionalBeanPropertyWriter(this, transformer, newName);
    }

    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        Object value;
        if (this._nullSerializer == null && ((value = this.get(bean)) == null || value.equals(this._empty))) {
            return;
        }
        super.serializeAsField(bean, gen, prov);
    }
}

