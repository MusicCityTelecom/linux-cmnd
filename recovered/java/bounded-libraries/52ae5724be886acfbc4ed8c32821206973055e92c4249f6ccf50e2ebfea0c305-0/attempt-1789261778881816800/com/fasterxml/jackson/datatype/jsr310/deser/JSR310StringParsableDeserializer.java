/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Value
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.util.VersionUtil
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.cfg.CoercionAction
 *  com.fasterxml.jackson.databind.cfg.CoercionInputShape
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.jsontype.TypeDeserializer
 */
package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class JSR310StringParsableDeserializer
extends JSR310DeserializerBase<Object>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    protected static final int TYPE_PERIOD = 1;
    protected static final int TYPE_ZONE_ID = 2;
    protected static final int TYPE_ZONE_OFFSET = 3;
    public static final JsonDeserializer<Period> PERIOD = JSR310StringParsableDeserializer.createDeserializer(Period.class, 1);
    public static final JsonDeserializer<ZoneId> ZONE_ID = JSR310StringParsableDeserializer.createDeserializer(ZoneId.class, 2);
    public static final JsonDeserializer<ZoneOffset> ZONE_OFFSET = JSR310StringParsableDeserializer.createDeserializer(ZoneOffset.class, 3);
    protected final int _typeSelector;

    protected JSR310StringParsableDeserializer(Class<?> supportedType, int typeSelector) {
        super(supportedType);
        this._typeSelector = typeSelector;
    }

    protected JSR310StringParsableDeserializer(JSR310StringParsableDeserializer base, Boolean leniency) {
        super(base, leniency);
        this._typeSelector = base._typeSelector;
    }

    protected static <T> JsonDeserializer<T> createDeserializer(Class<T> type, int typeId) {
        return new JSR310StringParsableDeserializer(type, typeId);
    }

    protected JSR310StringParsableDeserializer withLeniency(Boolean leniency) {
        if (this._isLenient == !Boolean.FALSE.equals(leniency)) {
            return this;
        }
        return new JSR310StringParsableDeserializer(this, leniency);
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        Boolean leniency;
        JsonFormat.Value format = this.findFormatOverrides(ctxt, property, this.handledType());
        JSR310StringParsableDeserializer deser = this;
        if (format != null && format.hasLenient() && (leniency = format.getLenient()) != null) {
            deser = this.withLeniency(leniency);
        }
        return deser;
    }

    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            return this._fromString(p, ctxt, p.getText());
        }
        if (p.isExpectedStartObjectToken()) {
            return this._fromString(p, ctxt, ctxt.extractScalarFromObject(p, (JsonDeserializer)this, this.handledType()));
        }
        if (p.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return p.getEmbeddedObject();
        }
        if (p.isExpectedStartArrayToken()) {
            return this._deserializeFromArray(p, ctxt);
        }
        throw ctxt.wrongTokenException(p, this.handledType(), JsonToken.VALUE_STRING, null);
    }

    @Override
    public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer deserializer) throws IOException {
        JsonToken t = parser.getCurrentToken();
        if (t != null && t.isScalarValue()) {
            return this.deserialize(parser, context);
        }
        return deserializer.deserializeTypedFromAny(parser, context);
    }

    protected Object _fromString(JsonParser p, DeserializationContext ctxt, String string) throws IOException {
        if ((string = string.trim()).length() == 0) {
            CoercionAction act = ctxt.findCoercionAction(this.logicalType(), this._valueClass, CoercionInputShape.EmptyString);
            if (act == CoercionAction.Fail) {
                ctxt.reportInputMismatch((JsonDeserializer)this, "Cannot coerce empty String (\"\") to %s (but could if enabling coercion using `CoercionConfig`)", new Object[]{this._coercedTypeDesc()});
            }
            if (!this.isLenient()) {
                return this._failForNotLenient(p, ctxt, JsonToken.VALUE_STRING);
            }
            if (act == CoercionAction.AsEmpty) {
                return this.getEmptyValue(ctxt);
            }
            return null;
        }
        try {
            switch (this._typeSelector) {
                case 1: {
                    return Period.parse(string);
                }
                case 2: {
                    return ZoneId.of(string);
                }
                case 3: {
                    return ZoneOffset.of(string);
                }
            }
        }
        catch (DateTimeException e) {
            return this._handleDateTimeException(ctxt, e, string);
        }
        VersionUtil.throwInternal();
        return null;
    }
}

