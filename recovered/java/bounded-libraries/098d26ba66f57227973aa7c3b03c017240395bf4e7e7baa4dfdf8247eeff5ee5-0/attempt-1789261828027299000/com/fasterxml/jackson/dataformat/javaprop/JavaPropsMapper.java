/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.cfg.CoercionAction
 *  com.fasterxml.jackson.databind.cfg.CoercionInputShape
 *  com.fasterxml.jackson.databind.cfg.MapperBuilder
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsGenerator;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsParser;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.PackageVersion;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class JavaPropsMapper
extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public JavaPropsMapper() {
        this(new JavaPropsFactory());
    }

    public JavaPropsMapper(JavaPropsFactory f) {
        super((JsonFactory)f);
        this.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.coercionConfigDefaults().setAcceptBlankAsEmpty(Boolean.TRUE).setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsEmpty);
    }

    protected JavaPropsMapper(JavaPropsMapper src) {
        super((ObjectMapper)src);
    }

    public static Builder builder() {
        return new Builder(new JavaPropsMapper());
    }

    public static Builder builder(JavaPropsFactory streamFactory) {
        return new Builder(new JavaPropsMapper(streamFactory));
    }

    public JavaPropsMapper copy() {
        this._checkInvalidCopy(JavaPropsMapper.class);
        return new JavaPropsMapper(this);
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public JavaPropsFactory getFactory() {
        return (JavaPropsFactory)this._jsonFactory;
    }

    public <T> T readPropertiesAs(Properties props, JavaPropsSchema schema, Class<T> valueType) throws IOException {
        JavaPropsParser p = this.getFactory().createParser((Map<?, ?>)props);
        p.setSchema(schema);
        return (T)this.readValue((JsonParser)p, valueType);
    }

    public <T> T readPropertiesAs(Properties props, JavaPropsSchema schema, JavaType valueType) throws IOException {
        JavaPropsParser p = this.getFactory().createParser((Map<?, ?>)props);
        p.setSchema(schema);
        return (T)this.readValue((JsonParser)p, valueType);
    }

    public <T> T readPropertiesAs(Properties props, Class<T> valueType) throws IOException {
        return this.readPropertiesAs(props, JavaPropsSchema.emptySchema(), valueType);
    }

    public <T> T readPropertiesAs(Properties props, JavaType valueType) throws IOException {
        return this.readPropertiesAs(props, JavaPropsSchema.emptySchema(), valueType);
    }

    public <T> T readMapAs(Map<String, String> map, JavaPropsSchema schema, Class<T> valueType) throws IOException {
        JavaPropsParser p = this.getFactory().createParser(map);
        p.setSchema(schema);
        return (T)this.readValue((JsonParser)p, valueType);
    }

    public <T> T readMapAs(Map<String, String> map, JavaPropsSchema schema, JavaType valueType) throws IOException {
        JavaPropsParser p = this.getFactory().createParser(map);
        p.setSchema(schema);
        return (T)this.readValue((JsonParser)p, valueType);
    }

    public <T> T readMapAs(Map<String, String> map, Class<T> valueType) throws IOException {
        return this.readMapAs(map, JavaPropsSchema.emptySchema(), valueType);
    }

    public <T> T readMapAs(Map<String, String> map, JavaType valueType) throws IOException {
        return this.readMapAs(map, JavaPropsSchema.emptySchema(), valueType);
    }

    public <T> T readSystemPropertiesAs(JavaPropsSchema schema, Class<T> valueType) throws IOException {
        return this.readPropertiesAs(System.getProperties(), schema, valueType);
    }

    public <T> T readSystemPropertiesAs(JavaPropsSchema schema, JavaType valueType) throws IOException {
        return this.readPropertiesAs(System.getProperties(), schema, valueType);
    }

    public <T> T readEnvVariablesAs(JavaPropsSchema schema, Class<T> valueType) throws IOException {
        return this.readPropertiesAs(this._env(), schema, valueType);
    }

    public <T> T readEnvVariablesAs(JavaPropsSchema schema, JavaType valueType) throws IOException {
        return this.readPropertiesAs(this._env(), schema, valueType);
    }

    protected Properties _env() {
        Properties props = new Properties();
        props.putAll(System.getenv());
        return props;
    }

    public void writeValue(Map<?, ?> target, Object value) throws IOException {
        if (target == null) {
            throw new IllegalArgumentException("Can not pass `null` target");
        }
        try (JavaPropsGenerator g = this.getFactory().createGenerator(target, null);){
            this.writeValue((JsonGenerator)g, value);
        }
    }

    public void writeValue(Map<?, ?> target, Object value, JavaPropsSchema schema) throws IOException {
        if (target == null) {
            throw new IllegalArgumentException("Can not pass `null` target");
        }
        try (JavaPropsGenerator g = this.getFactory().createGenerator(target, schema);){
            if (schema != null) {
                g.setSchema(schema);
            }
            this.writeValue((JsonGenerator)g, value);
        }
    }

    @Deprecated
    public void writeValue(Properties targetProps, Object value) throws IOException {
        this.writeValue((Map<?, ?>)targetProps, value);
    }

    @Deprecated
    public void writeValue(Properties targetProps, Object value, JavaPropsSchema schema) throws IOException {
        this.writeValue((Map<?, ?>)targetProps, value, schema);
    }

    public Properties writeValueAsProperties(Object value) throws IOException {
        Properties props = new Properties();
        this.writeValue(props, value);
        return props;
    }

    public Properties writeValueAsProperties(Object value, JavaPropsSchema schema) throws IOException {
        Properties props = new Properties();
        this.writeValue(props, value, schema);
        return props;
    }

    public Map<String, String> writeValueAsMap(Object value) throws IOException {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        this.writeValue(map, value);
        return map;
    }

    public Map<String, String> writeValueAsMap(Object value, JavaPropsSchema schema) throws IOException {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        this.writeValue(map, value, schema);
        return map;
    }

    public static class Builder
    extends MapperBuilder<JavaPropsMapper, Builder> {
        public Builder(JavaPropsMapper m) {
            super((ObjectMapper)m);
        }
    }
}

