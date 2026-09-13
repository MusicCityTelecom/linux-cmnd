/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.databind.json.JsonMapper$Builder
 *  com.fasterxml.jackson.datatype.jdk8.Jdk8Module
 *  com.fasterxml.jackson.datatype.joda.JodaModule
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *  com.fasterxml.jackson.module.kotlin.KotlinModule
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
import org.springframework.integration.support.json.AbstractJacksonJsonObjectMapper;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class Jackson2JsonObjectMapper
extends AbstractJacksonJsonObjectMapper<JsonNode, JsonParser, JavaType> {
    private static final boolean JDK8_MODULE_PRESENT = ClassUtils.isPresent((String)"com.fasterxml.jackson.datatype.jdk8.Jdk8Module", null);
    private static final boolean JAVA_TIME_MODULE_PRESENT = ClassUtils.isPresent((String)"com.fasterxml.jackson.datatype.jsr310.JavaTimeModule", null);
    private static final boolean JODA_MODULE_PRESENT = ClassUtils.isPresent((String)"com.fasterxml.jackson.datatype.joda.JodaModule", null);
    private static final boolean KOTLIN_MODULE_PRESENT = ClassUtils.isPresent((String)"kotlin.Unit", null) && ClassUtils.isPresent((String)"com.fasterxml.jackson.module.kotlin.KotlinModule", null);
    private final ObjectMapper objectMapper;

    public Jackson2JsonObjectMapper() {
        this.objectMapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).build();
        this.registerWellKnownModulesIfAvailable();
    }

    public Jackson2JsonObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull((Object)objectMapper, (String)"objectMapper must not be null");
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public String toJson(Object value) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(value);
    }

    @Override
    public void toJson(Object value, Writer writer) throws IOException {
        this.objectMapper.writeValue(writer, value);
    }

    @Override
    public JsonNode toJsonNode(Object json) throws IOException {
        block8: {
            try {
                if (json instanceof String) {
                    return this.objectMapper.readTree((String)json);
                }
                if (json instanceof byte[]) {
                    return this.objectMapper.readTree((byte[])json);
                }
                if (json instanceof File) {
                    return this.objectMapper.readTree((File)json);
                }
                if (json instanceof URL) {
                    return this.objectMapper.readTree((URL)json);
                }
                if (json instanceof InputStream) {
                    return this.objectMapper.readTree((InputStream)json);
                }
                if (json instanceof Reader) {
                    return this.objectMapper.readTree((Reader)json);
                }
            }
            catch (JsonParseException e) {
                if (json instanceof String || json instanceof byte[]) break block8;
                throw e;
            }
        }
        return this.objectMapper.valueToTree(json);
    }

    @Override
    protected <T> T fromJson(Object json, JavaType type) throws IOException {
        if (json instanceof String) {
            return (T)this.objectMapper.readValue((String)json, type);
        }
        if (json instanceof byte[]) {
            return (T)this.objectMapper.readValue((byte[])json, type);
        }
        if (json instanceof File) {
            return (T)this.objectMapper.readValue((File)json, type);
        }
        if (json instanceof URL) {
            return (T)this.objectMapper.readValue((URL)json, type);
        }
        if (json instanceof InputStream) {
            return (T)this.objectMapper.readValue((InputStream)json, type);
        }
        if (json instanceof Reader) {
            return (T)this.objectMapper.readValue((Reader)json, type);
        }
        throw new IllegalArgumentException("'json' argument must be an instance of: " + SUPPORTED_JSON_TYPES + " , but gotten: " + json.getClass());
    }

    @Override
    public <T> T fromJson(JsonParser parser, Type valueType) throws IOException {
        return (T)this.objectMapper.readValue(parser, this.constructType(valueType));
    }

    @Override
    protected JavaType extractJavaType(Map<String, Object> javaTypes) {
        JavaType classType = (JavaType)this.createJavaType(javaTypes, "json__TypeId__");
        if (!classType.isContainerType() || classType.isArrayType()) {
            return classType;
        }
        JavaType contentClassType = (JavaType)this.createJavaType(javaTypes, "json__ContentTypeId__");
        if (classType.getKeyType() == null) {
            return this.objectMapper.getTypeFactory().constructCollectionType(classType.getRawClass(), contentClassType);
        }
        JavaType keyClassType = (JavaType)this.createJavaType(javaTypes, "json__KeyTypeId__");
        return this.objectMapper.getTypeFactory().constructMapType(classType.getRawClass(), keyClassType, contentClassType);
    }

    @Override
    protected JavaType constructType(Type type) {
        return this.objectMapper.constructType(type);
    }

    private void registerWellKnownModulesIfAvailable() {
        if (JDK8_MODULE_PRESENT) {
            this.objectMapper.registerModule(Jdk8ModuleProvider.MODULE);
        }
        if (JAVA_TIME_MODULE_PRESENT) {
            this.objectMapper.registerModule(JavaTimeModuleProvider.MODULE);
        }
        if (JODA_MODULE_PRESENT) {
            this.objectMapper.registerModule(JodaModuleProvider.MODULE);
        }
        if (KOTLIN_MODULE_PRESENT) {
            this.objectMapper.registerModule(KotlinModuleProvider.MODULE);
        }
    }

    private static final class Jdk8ModuleProvider {
        static final Module MODULE = new Jdk8Module();

        private Jdk8ModuleProvider() {
        }
    }

    private static final class JavaTimeModuleProvider {
        static final Module MODULE = new JavaTimeModule();

        private JavaTimeModuleProvider() {
        }
    }

    private static final class JodaModuleProvider {
        static final Module MODULE = new JodaModule();

        private JodaModuleProvider() {
        }
    }

    private static final class KotlinModuleProvider {
        static final Module MODULE = new KotlinModule();

        private KotlinModuleProvider() {
        }
    }
}

