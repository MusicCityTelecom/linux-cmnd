/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.type.WritableTypeId
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.jsontype.TypeSerializer
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  com.google.common.cache.Cache
 */
package com.fasterxml.jackson.datatype.guava.ser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.cache.Cache;
import java.io.IOException;

public class CacheSerializer
extends StdSerializer<Cache<?, ?>> {
    private static final long serialVersionUID = 1L;

    public CacheSerializer() {
        super(Cache.class, false);
    }

    public boolean isEmpty(SerializerProvider prov, Cache<?, ?> value) {
        return true;
    }

    public void serialize(Cache<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(value);
        this._writeContents(value, gen, provider);
        gen.writeEndObject();
    }

    public void serializeWithType(Cache<?, ?> value, JsonGenerator gen, SerializerProvider ctxt, TypeSerializer typeSer) throws IOException {
        gen.assignCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.START_OBJECT));
        this._writeContents(value, gen, ctxt);
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }

    protected void _writeContents(Cache<?, ?> value, JsonGenerator g, SerializerProvider ctxt) throws JacksonException {
    }
}

