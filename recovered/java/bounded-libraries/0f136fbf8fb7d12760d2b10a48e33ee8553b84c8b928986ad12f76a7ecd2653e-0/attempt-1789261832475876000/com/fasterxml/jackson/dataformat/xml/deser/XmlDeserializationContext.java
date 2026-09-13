/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationConfig
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.InjectableValues
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
 *  com.fasterxml.jackson.databind.deser.DeserializerFactory
 */
package com.fasterxml.jackson.dataformat.xml.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import java.io.IOException;

public class XmlDeserializationContext
extends DefaultDeserializationContext {
    private static final long serialVersionUID = 1L;

    public XmlDeserializationContext(DeserializerFactory df) {
        super(df, null);
    }

    private XmlDeserializationContext(XmlDeserializationContext src, DeserializationConfig config, JsonParser p, InjectableValues values) {
        super((DefaultDeserializationContext)src, config, p, values);
    }

    private XmlDeserializationContext(XmlDeserializationContext src) {
        super((DefaultDeserializationContext)src);
    }

    private XmlDeserializationContext(XmlDeserializationContext src, DeserializerFactory factory) {
        super((DefaultDeserializationContext)src, factory);
    }

    private XmlDeserializationContext(XmlDeserializationContext src, DeserializationConfig config) {
        super((DefaultDeserializationContext)src, config);
    }

    public XmlDeserializationContext copy() {
        return new XmlDeserializationContext(this);
    }

    public DefaultDeserializationContext createInstance(DeserializationConfig config, JsonParser p, InjectableValues values) {
        return new XmlDeserializationContext(this, config, p, values);
    }

    public DefaultDeserializationContext createDummyInstance(DeserializationConfig config) {
        return new XmlDeserializationContext(this, config);
    }

    public DefaultDeserializationContext with(DeserializerFactory factory) {
        return new XmlDeserializationContext(this, factory);
    }

    public Object readRootValue(JsonParser p, JavaType valueType, JsonDeserializer<Object> deser, Object valueToUpdate) throws IOException {
        if (this._config.useRootWrapping()) {
            return this._unwrapAndDeserialize(p, valueType, deser, valueToUpdate);
        }
        if (valueToUpdate == null) {
            return deser.deserialize(p, (DeserializationContext)this);
        }
        return deser.deserialize(p, (DeserializationContext)this, valueToUpdate);
    }

    public String extractScalarFromObject(JsonParser p, JsonDeserializer<?> deser, Class<?> scalarType) throws IOException {
        String text = "";
        while (p.nextToken() == JsonToken.FIELD_NAME) {
            String propName = p.currentName();
            JsonToken t = p.nextToken();
            if (t == JsonToken.VALUE_STRING) {
                if (!propName.equals("")) continue;
                text = p.getText();
                continue;
            }
            p.skipChildren();
        }
        return text;
    }
}

