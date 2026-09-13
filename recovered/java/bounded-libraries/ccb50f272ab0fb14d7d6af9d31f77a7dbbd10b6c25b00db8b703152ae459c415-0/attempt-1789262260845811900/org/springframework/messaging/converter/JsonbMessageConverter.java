/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.json.bind.Jsonb
 *  javax.json.bind.JsonbBuilder
 *  javax.json.bind.JsonbConfig
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.springframework.messaging.converter.AbstractJsonMessageConverter;
import org.springframework.util.Assert;

public class JsonbMessageConverter
extends AbstractJsonMessageConverter {
    private Jsonb jsonb;

    public JsonbMessageConverter() {
        this.jsonb = JsonbBuilder.create();
    }

    public JsonbMessageConverter(JsonbConfig config) {
        this.jsonb = JsonbBuilder.create((JsonbConfig)config);
    }

    public JsonbMessageConverter(Jsonb jsonb) {
        Assert.notNull((Object)jsonb, (String)"A Jsonb instance is required");
        this.jsonb = jsonb;
    }

    public void setJsonb(Jsonb jsonb) {
        Assert.notNull((Object)jsonb, (String)"A Jsonb instance is required");
        this.jsonb = jsonb;
    }

    public Jsonb getJsonb() {
        return this.jsonb;
    }

    @Override
    protected Object fromJson(Reader reader, Type resolvedType) {
        return this.getJsonb().fromJson(reader, resolvedType);
    }

    @Override
    protected Object fromJson(String payload, Type resolvedType) {
        return this.getJsonb().fromJson(payload, resolvedType);
    }

    @Override
    protected void toJson(Object payload, Type resolvedType, Writer writer) {
        if (resolvedType instanceof ParameterizedType) {
            this.getJsonb().toJson(payload, resolvedType, writer);
        } else {
            this.getJsonb().toJson(payload, writer);
        }
    }

    @Override
    protected String toJson(Object payload, Type resolvedType) {
        if (resolvedType instanceof ParameterizedType) {
            return this.getJsonb().toJson(payload, resolvedType);
        }
        return this.getJsonb().toJson(payload);
    }
}

