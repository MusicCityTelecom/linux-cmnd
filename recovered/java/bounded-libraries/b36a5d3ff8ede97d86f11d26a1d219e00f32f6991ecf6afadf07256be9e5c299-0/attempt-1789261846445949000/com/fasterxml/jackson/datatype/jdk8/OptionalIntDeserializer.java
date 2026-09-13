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
import java.util.OptionalInt;

public class OptionalIntDeserializer
extends BaseScalarOptionalDeserializer<OptionalInt> {
    private static final long serialVersionUID = 1L;
    static final OptionalIntDeserializer INSTANCE = new OptionalIntDeserializer();

    public OptionalIntDeserializer() {
        super(OptionalInt.class, OptionalInt.empty());
    }

    public LogicalType logicalType() {
        return LogicalType.Integer;
    }

    public OptionalInt deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return OptionalInt.of(p.getIntValue());
        }
        switch (p.currentTokenId()) {
            case 6: {
                String text = p.getText();
                CoercionAction act = this._checkFromStringCoercion(ctxt, text);
                if (act == CoercionAction.AsNull || act == CoercionAction.AsEmpty) {
                    return (OptionalInt)this._empty;
                }
                text = text.trim();
                return OptionalInt.of(this._parseIntPrimitive(ctxt, text));
            }
            case 8: {
                CoercionAction act = this._checkFloatToIntCoercion(p, ctxt, this._valueClass);
                if (act == CoercionAction.AsNull || act == CoercionAction.AsEmpty) {
                    return (OptionalInt)this._empty;
                }
                return OptionalInt.of(p.getValueAsInt());
            }
            case 11: {
                return (OptionalInt)this._empty;
            }
            case 3: {
                return (OptionalInt)this._deserializeFromArray(p, ctxt);
            }
        }
        return (OptionalInt)ctxt.handleUnexpectedToken(this.getValueType(ctxt), p);
    }
}

