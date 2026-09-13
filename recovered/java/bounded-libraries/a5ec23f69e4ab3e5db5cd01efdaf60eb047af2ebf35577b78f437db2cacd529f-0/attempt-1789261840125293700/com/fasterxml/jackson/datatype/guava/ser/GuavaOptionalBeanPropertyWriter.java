/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.PropertyName
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.util.NameTransformer
 *  com.google.common.base.Optional
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.datatype.guava.ser.GuavaUnwrappingOptionalBeanPropertyWriter;
import com.google.common.base.Optional;

public class GuavaOptionalBeanPropertyWriter
extends BeanPropertyWriter {
    private static final long serialVersionUID = 1L;

    protected GuavaOptionalBeanPropertyWriter(BeanPropertyWriter base) {
        super(base);
    }

    protected GuavaOptionalBeanPropertyWriter(BeanPropertyWriter base, PropertyName newName) {
        super(base, newName);
    }

    protected BeanPropertyWriter _new(PropertyName newName) {
        return new GuavaOptionalBeanPropertyWriter(this, newName);
    }

    public BeanPropertyWriter unwrappingWriter(NameTransformer unwrapper) {
        return new GuavaUnwrappingOptionalBeanPropertyWriter(this, unwrapper);
    }

    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        Object value;
        if (this._nullSerializer == null && ((value = this.get(bean)) == null || Optional.absent().equals(value))) {
            return;
        }
        super.serializeAsField(bean, gen, prov);
    }
}

