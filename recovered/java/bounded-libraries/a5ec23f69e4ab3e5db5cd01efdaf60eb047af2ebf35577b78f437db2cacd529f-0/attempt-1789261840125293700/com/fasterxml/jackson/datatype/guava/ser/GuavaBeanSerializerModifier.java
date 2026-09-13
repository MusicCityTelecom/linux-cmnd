/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.BeanDescription
 *  com.fasterxml.jackson.databind.SerializationConfig
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.ser.BeanSerializerModifier
 *  com.google.common.base.Optional
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.guava.ser.GuavaOptionalBeanPropertyWriter;
import com.google.common.base.Optional;
import java.io.Serializable;
import java.util.List;

public class GuavaBeanSerializerModifier
extends BeanSerializerModifier
implements Serializable {
    static final long serialVersionUID = 1L;

    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (int i = 0; i < beanProperties.size(); ++i) {
            BeanPropertyWriter writer = beanProperties.get(i);
            if (!Optional.class.isAssignableFrom(writer.getType().getRawClass())) continue;
            beanProperties.set(i, new GuavaOptionalBeanPropertyWriter(writer));
        }
        return beanProperties;
    }
}

