/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.converter;

import com.google.gson.Gson;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.messaging.converter.AbstractJsonMessageConverter;
import org.springframework.util.Assert;

public class GsonMessageConverter
extends AbstractJsonMessageConverter {
    private Gson gson;

    public GsonMessageConverter() {
        this.gson = new Gson();
    }

    public GsonMessageConverter(Gson gson) {
        Assert.notNull((Object)gson, (String)"A Gson instance is required");
        this.gson = gson;
    }

    public void setGson(Gson gson) {
        Assert.notNull((Object)gson, (String)"A Gson instance is required");
        this.gson = gson;
    }

    public Gson getGson() {
        return this.gson;
    }

    @Override
    protected Object fromJson(Reader reader, Type resolvedType) {
        return this.getGson().fromJson(reader, resolvedType);
    }

    @Override
    protected Object fromJson(String payload, Type resolvedType) {
        return this.getGson().fromJson(payload, resolvedType);
    }

    @Override
    protected void toJson(Object payload, Type resolvedType, Writer writer) {
        if (resolvedType instanceof ParameterizedType) {
            this.getGson().toJson(payload, resolvedType, (Appendable)writer);
        } else {
            this.getGson().toJson(payload, (Appendable)writer);
        }
    }

    @Override
    protected String toJson(Object payload, Type resolvedType) {
        if (resolvedType instanceof ParameterizedType) {
            return this.getGson().toJson(payload, resolvedType);
        }
        return this.getGson().toJson(payload);
    }
}

