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
 *  com.google.common.base.Optional
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.google.common.base.Optional;

public class GuavaUnwrappingOptionalBeanPropertyWriter
extends UnwrappingBeanPropertyWriter {
    private static final long serialVersionUID = 1L;

    public GuavaUnwrappingOptionalBeanPropertyWriter(BeanPropertyWriter base, NameTransformer transformer) {
        super(base, transformer);
    }

    protected GuavaUnwrappingOptionalBeanPropertyWriter(UnwrappingBeanPropertyWriter base, NameTransformer transformer, SerializedString name) {
        super(base, transformer, name);
    }

    protected UnwrappingBeanPropertyWriter _new(NameTransformer transformer, SerializedString newName) {
        return new GuavaUnwrappingOptionalBeanPropertyWriter(this, transformer, newName);
    }

    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        Object value;
        if (this._nullSerializer == null && ((value = this.get(bean)) == null || Optional.absent().equals(value))) {
            return;
        }
        super.serializeAsField(bean, gen, prov);
    }
}

