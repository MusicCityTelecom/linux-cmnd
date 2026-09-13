/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.deser.BeanDeserializerBase
 *  com.fasterxml.jackson.databind.deser.SettableBeanProperty
 *  com.fasterxml.jackson.databind.deser.ValueInstantiator
 *  com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 */
package com.fasterxml.jackson.dataformat.xml.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

public class XmlTextDeserializer
extends DelegatingDeserializer {
    private static final long serialVersionUID = 1L;
    protected final int _xmlTextPropertyIndex;
    protected final SettableBeanProperty _xmlTextProperty;
    protected final ValueInstantiator _valueInstantiator;

    public XmlTextDeserializer(BeanDeserializerBase delegate, SettableBeanProperty prop) {
        super((JsonDeserializer)delegate);
        this._xmlTextProperty = prop;
        this._xmlTextPropertyIndex = prop.getPropertyIndex();
        this._valueInstantiator = delegate.getValueInstantiator();
    }

    public XmlTextDeserializer(BeanDeserializerBase delegate, int textPropIndex) {
        super((JsonDeserializer)delegate);
        this._xmlTextPropertyIndex = textPropIndex;
        this._valueInstantiator = delegate.getValueInstantiator();
        this._xmlTextProperty = delegate.findProperty(textPropIndex);
    }

    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee0) {
        throw new IllegalStateException("Internal error: should never get called");
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return new XmlTextDeserializer(this._verifyDeserType(this._delegatee), this._xmlTextPropertyIndex);
    }

    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
            Object bean = this._valueInstantiator.createUsingDefault(ctxt);
            this._xmlTextProperty.deserializeAndSet(p, ctxt, bean);
            return bean;
        }
        return this._delegatee.deserialize(p, ctxt);
    }

    public Object deserialize(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
        if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
            this._xmlTextProperty.deserializeAndSet(p, ctxt, bean);
            return bean;
        }
        return this._delegatee.deserialize(p, ctxt, bean);
    }

    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return this._delegatee.deserializeWithType(p, ctxt, typeDeserializer);
    }

    protected BeanDeserializerBase _verifyDeserType(JsonDeserializer<?> deser) {
        if (!(deser instanceof BeanDeserializerBase)) {
            throw new IllegalArgumentException("Can not change delegate to be of type " + deser.getClass().getName());
        }
        return (BeanDeserializerBase)deser;
    }
}

