/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer
 *  com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter
 *  com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
 *  com.fasterxml.jackson.databind.util.NameTransformer
 */
package com.fasterxml.jackson.dataformat.xml.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.dataformat.xml.ser.UnwrappingXmlBeanSerializer;
import com.fasterxml.jackson.dataformat.xml.ser.XmlBeanSerializerBase;
import java.io.IOException;
import java.util.Set;

public class XmlBeanSerializer
extends XmlBeanSerializerBase {
    private static final long serialVersionUID = 1L;

    public XmlBeanSerializer(BeanSerializerBase src) {
        super(src);
    }

    public XmlBeanSerializer(XmlBeanSerializerBase src, ObjectIdWriter objectIdWriter, Object filterId) {
        super(src, objectIdWriter, filterId);
    }

    public XmlBeanSerializer(XmlBeanSerializerBase src, ObjectIdWriter objectIdWriter) {
        super(src, objectIdWriter);
    }

    public XmlBeanSerializer(XmlBeanSerializerBase src, Set<String> toIgnore, Set<String> toInclude) {
        super(src, toIgnore, toInclude);
    }

    protected XmlBeanSerializer(XmlBeanSerializerBase src, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        super(src, properties, filteredProperties);
    }

    public JsonSerializer<Object> unwrappingSerializer(NameTransformer unwrapper) {
        return new UnwrappingXmlBeanSerializer((XmlBeanSerializerBase)this, unwrapper);
    }

    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new XmlBeanSerializer((XmlBeanSerializerBase)this, objectIdWriter, this._propertyFilterId);
    }

    public BeanSerializerBase withFilterId(Object filterId) {
        return new XmlBeanSerializer((XmlBeanSerializerBase)this, this._objectIdWriter, filterId);
    }

    protected BeanSerializerBase withByNameInclusion(Set<String> toIgnore, Set<String> toInclude) {
        return new XmlBeanSerializer((XmlBeanSerializerBase)this, toIgnore, toInclude);
    }

    protected BeanSerializerBase withProperties(BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        return new XmlBeanSerializer((XmlBeanSerializerBase)this, properties, filteredProperties);
    }

    protected BeanSerializerBase asArraySerializer() {
        if (this._objectIdWriter == null && this._anyGetterWriter == null && this._propertyFilterId == null) {
            return new BeanAsArraySerializer((BeanSerializerBase)this);
        }
        return this;
    }

    public void serialize(Object bean, JsonGenerator g, SerializerProvider provider) throws IOException {
        if (this._objectIdWriter != null) {
            this._serializeWithObjectId(bean, g, provider, true);
            return;
        }
        g.writeStartObject();
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(bean, g, provider);
        } else {
            this.serializeFields(bean, g, provider);
        }
        g.writeEndObject();
    }

    public String toString() {
        return "XmlBeanSerializer for " + this.handledType().getName();
    }
}

