/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectReader
 *  com.fasterxml.jackson.databind.ObjectWriter
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.util.JSONPObject
 *  com.fasterxml.jackson.databind.util.JSONWrappedObject
 *  com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase
 */
package com.fasterxml.jackson.jaxrs.json;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import java.lang.annotation.Annotation;

public class JsonEndpointConfig
extends EndpointConfigBase<JsonEndpointConfig> {
    protected JSONP.Def _jsonp;

    protected JsonEndpointConfig(MapperConfig<?> config) {
        super(config);
    }

    public static JsonEndpointConfig forReading(ObjectReader reader, Annotation[] annotations) {
        return (JsonEndpointConfig)((JsonEndpointConfig)new JsonEndpointConfig((MapperConfig<?>)reader.getConfig()).add(annotations, false)).initReader(reader);
    }

    public static JsonEndpointConfig forWriting(ObjectWriter writer, Annotation[] annotations, String defaultJsonpMethod) {
        JsonEndpointConfig config = new JsonEndpointConfig((MapperConfig<?>)writer.getConfig());
        if (defaultJsonpMethod != null) {
            config._jsonp = new JSONP.Def(defaultJsonpMethod);
        }
        return (JsonEndpointConfig)((JsonEndpointConfig)config.add(annotations, true)).initWriter(writer);
    }

    protected void addAnnotation(Class<? extends Annotation> type, Annotation annotation, boolean forWriting) {
        if (type == JSONP.class) {
            if (forWriting) {
                this._jsonp = new JSONP.Def((JSONP)annotation);
            }
        } else {
            super.addAnnotation(type, annotation, forWriting);
        }
    }

    public Object modifyBeforeWrite(Object value) {
        return this.applyJSONP(value);
    }

    public Object applyJSONP(Object value) {
        if (this._jsonp != null) {
            if (this._jsonp.prefix != null || this._jsonp.suffix != null) {
                return new JSONWrappedObject(this._jsonp.prefix, this._jsonp.suffix, value);
            }
            if (this._jsonp.method != null) {
                return new JSONPObject(this._jsonp.method, value);
            }
        }
        return value;
    }
}

