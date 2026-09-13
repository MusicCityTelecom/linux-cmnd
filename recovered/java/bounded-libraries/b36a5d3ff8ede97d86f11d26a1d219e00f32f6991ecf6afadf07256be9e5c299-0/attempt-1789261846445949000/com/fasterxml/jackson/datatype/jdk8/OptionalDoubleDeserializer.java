/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.cfg.CoercionAction
 *  com.fasterxml.jackson.databind.type.LogicalType
 */
package com.fasterxml.jackson.datatype.jdk8;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.datatype.jdk8.BaseScalarOptionalDeserializer;
import java.io.IOException;
import java.util.OptionalDouble;

class OptionalDoubleDeserializer
extends BaseScalarOptionalDeserializer<OptionalDouble> {
    private static final long serialVersionUID = 1L;
    static final OptionalDoubleDeserializer INSTANCE = new OptionalDoubleDeserializer();

    public OptionalDoubleDeserializer() {
        super(OptionalDouble.class, OptionalDouble.empty());
    }

    public LogicalType logicalType() {
        return LogicalType.Float;
    }

    public OptionalDouble deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
            return OptionalDouble.of(p.getDoubleValue());
        }
        switch (p.currentTokenId()) {
            case 6: {
                String text = p.getText();
                Double specialValue = this._checkDoubleSpecialValue(text);
                if (specialValue != null) {
                    return OptionalDouble.of(specialValue);
                }
                CoercionAction act = this._checkFromStringCoercion(ctxt, text);
                if (act == CoercionAction.AsNull || act == CoercionAction.AsEmpty) {
                    return (OptionalDouble)this._empty;
                }
                text = text.trim();
                return OptionalDouble.of(this._parseDoublePrimitive(ctxt, text));
            }
            case 7: {
                return OptionalDouble.of(p.getDoubleValue());
            }
            case 11: {
                return (OptionalDouble)this.getNullValue(ctxt);
            }
            case 3: {
                return (OptionalDouble)this._deserializeFromArray(p, ctxt);
            }
        }
        return (OptionalDouble)ctxt.handleUnexpectedToken(this.getValueType(ctxt), p);
    }
}

