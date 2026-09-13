/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.PropertyNamingStrategy
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.ser.FilterProvider
 *  com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
 *  com.fasterxml.jackson.databind.ser.std.StdSerializer
 *  com.fasterxml.jackson.dataformat.yaml.YAMLMapper
 *  de.cronn.reflection.util.PropertyUtils
 *  de.cronn.reflection.util.TypedPropertyGetter
 *  lombok.Generated
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.springframework.beans.factory.config.YamlProcessor$ResolutionMethod
 *  org.springframework.beans.factory.config.YamlPropertiesFactoryBean
 *  org.springframework.core.io.ByteArrayResource
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import de.cronn.reflection.util.PropertyUtils;
import de.cronn.reflection.util.TypedPropertyGetter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.util.function.FunctionUtils;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public final class CasCoreConfigurationUtils {
    public static <T, V> String getPropertyName(Class<T> clazz, TypedPropertyGetter<T, V> supplier) {
        return PropertyUtils.getPropertyName(clazz, supplier);
    }

    public static Map<String, Object> loadYamlProperties(Resource ... resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResolutionMethod(YamlProcessor.ResolutionMethod.OVERRIDE);
        factory.setResources(resource);
        factory.setSingleton(true);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    public static Map<String, Object> asMap(Serializable properties, FilterProvider filterProvider) {
        return (Map)FunctionUtils.doUnchecked(() -> {
            try (StringWriter writer = new StringWriter();){
                YAMLMapper mapper = new YAMLMapper();
                mapper.setFilterProvider(filterProvider);
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
                SimpleModule module = new SimpleModule();
                module.addSerializer(Resource.class, (JsonSerializer)new ResourceSerializer());
                mapper.registerModule((Module)module);
                mapper.writeValue((Writer)writer, (Object)properties);
                ByteArrayResource resource = new ByteArrayResource(writer.toString().getBytes(StandardCharsets.UTF_8));
                Map<String, Object> map = CasCoreConfigurationUtils.loadYamlProperties(new Resource[]{resource});
                return map;
            }
        });
    }

    public static Map<String, Object> asMap(Serializable withHolder) {
        return CasCoreConfigurationUtils.asMap(withHolder, (FilterProvider)new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    @Generated
    private CasCoreConfigurationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static class ResourceSerializer
    extends StdSerializer<Resource> {
        private static final long serialVersionUID = 7971411664567411958L;

        ResourceSerializer() {
            this(null);
        }

        ResourceSerializer(Class<Resource> t) {
            super(t);
        }

        public void serialize(Resource value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value instanceof ClassPathResource) {
                jgen.writeString("classpath:" + value.getFilename());
            } else {
                jgen.writeString(value.getURI().toString());
            }
        }
    }
}

