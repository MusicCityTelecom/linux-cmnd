/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@AutoConfiguration
@ConditionalOnClass(value={Gson.class})
@EnableConfigurationProperties(value={GsonProperties.class})
public class GsonAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {
        GsonBuilder builder = new GsonBuilder();
        customizers.forEach(c -> c.customize(builder));
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    public Gson gson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }

    @Bean
    public StandardGsonBuilderCustomizer standardGsonBuilderCustomizer(GsonProperties gsonProperties) {
        return new StandardGsonBuilderCustomizer(gsonProperties);
    }

    static final class StandardGsonBuilderCustomizer
    implements GsonBuilderCustomizer,
    Ordered {
        private final GsonProperties properties;

        StandardGsonBuilderCustomizer(GsonProperties properties) {
            this.properties = properties;
        }

        public int getOrder() {
            return 0;
        }

        @Override
        public void customize(GsonBuilder builder) {
            GsonProperties properties = this.properties;
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(properties::getGenerateNonExecutableJson).toCall(() -> ((GsonBuilder)builder).generateNonExecutableJson());
            map.from(properties::getExcludeFieldsWithoutExposeAnnotation).toCall(() -> ((GsonBuilder)builder).excludeFieldsWithoutExposeAnnotation());
            map.from(properties::getSerializeNulls).whenTrue().toCall(() -> ((GsonBuilder)builder).serializeNulls());
            map.from(properties::getEnableComplexMapKeySerialization).toCall(() -> ((GsonBuilder)builder).enableComplexMapKeySerialization());
            map.from(properties::getDisableInnerClassSerialization).toCall(() -> ((GsonBuilder)builder).disableInnerClassSerialization());
            map.from(properties::getLongSerializationPolicy).to(arg_0 -> ((GsonBuilder)builder).setLongSerializationPolicy(arg_0));
            map.from(properties::getFieldNamingPolicy).to(arg_0 -> ((GsonBuilder)builder).setFieldNamingPolicy(arg_0));
            map.from(properties::getPrettyPrinting).toCall(() -> ((GsonBuilder)builder).setPrettyPrinting());
            map.from(properties::getLenient).toCall(() -> ((GsonBuilder)builder).setLenient());
            map.from(properties::getDisableHtmlEscaping).toCall(() -> ((GsonBuilder)builder).disableHtmlEscaping());
            map.from(properties::getDateFormat).to(arg_0 -> ((GsonBuilder)builder).setDateFormat(arg_0));
        }
    }
}

