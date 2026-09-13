/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.fasterxml.jackson.annotation.JsonCreator$Mode
 *  com.fasterxml.jackson.annotation.PropertyAccessor
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.PropertyNamingStrategies
 *  com.fasterxml.jackson.databind.PropertyNamingStrategy
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.cfg.ConstructorDetector
 *  com.fasterxml.jackson.module.paramnames.ParameterNamesModule
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.jackson.JsonComponentModule
 *  org.springframework.boot.jackson.JsonMixinModule
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Primary
 *  org.springframework.context.annotation.Scope
 *  org.springframework.core.Ordered
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

@AutoConfiguration
@ConditionalOnClass(value={ObjectMapper.class})
public class JacksonAutoConfiguration {
    private static final Map<?, Boolean> FEATURE_DEFAULTS;

    @Bean
    public JsonComponentModule jsonComponentModule() {
        return new JsonComponentModule();
    }

    @Bean
    public JsonMixinModule jsonMixinModule(ApplicationContext context) {
        List<Object> packages = AutoConfigurationPackages.has((BeanFactory)context) ? AutoConfigurationPackages.get((BeanFactory)context) : Collections.emptyList();
        return new JsonMixinModule(context, packages);
    }

    static {
        HashMap<SerializationFeature, Boolean> featureDefaults = new HashMap<SerializationFeature, Boolean>();
        featureDefaults.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        featureDefaults.put(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        FEATURE_DEFAULTS = Collections.unmodifiableMap(featureDefaults);
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Jackson2ObjectMapperBuilder.class})
    @EnableConfigurationProperties(value={JacksonProperties.class})
    static class Jackson2ObjectMapperBuilderCustomizerConfiguration {
        Jackson2ObjectMapperBuilderCustomizerConfiguration() {
        }

        @Bean
        StandardJackson2ObjectMapperBuilderCustomizer standardJacksonObjectMapperBuilderCustomizer(ApplicationContext applicationContext, JacksonProperties jacksonProperties) {
            return new StandardJackson2ObjectMapperBuilderCustomizer(applicationContext, jacksonProperties);
        }

        static final class StandardJackson2ObjectMapperBuilderCustomizer
        implements Jackson2ObjectMapperBuilderCustomizer,
        Ordered {
            private final ApplicationContext applicationContext;
            private final JacksonProperties jacksonProperties;

            StandardJackson2ObjectMapperBuilderCustomizer(ApplicationContext applicationContext, JacksonProperties jacksonProperties) {
                this.applicationContext = applicationContext;
                this.jacksonProperties = jacksonProperties;
            }

            public int getOrder() {
                return 0;
            }

            @Override
            public void customize(Jackson2ObjectMapperBuilder builder) {
                if (this.jacksonProperties.getDefaultPropertyInclusion() != null) {
                    builder.serializationInclusion(this.jacksonProperties.getDefaultPropertyInclusion());
                }
                if (this.jacksonProperties.getTimeZone() != null) {
                    builder.timeZone(this.jacksonProperties.getTimeZone());
                }
                this.configureFeatures(builder, FEATURE_DEFAULTS);
                this.configureVisibility(builder, this.jacksonProperties.getVisibility());
                this.configureFeatures(builder, this.jacksonProperties.getDeserialization());
                this.configureFeatures(builder, this.jacksonProperties.getSerialization());
                this.configureFeatures(builder, this.jacksonProperties.getMapper());
                this.configureFeatures(builder, this.jacksonProperties.getParser());
                this.configureFeatures(builder, this.jacksonProperties.getGenerator());
                this.configureDateFormat(builder);
                this.configurePropertyNamingStrategy(builder);
                this.configureModules(builder);
                this.configureLocale(builder);
                this.configureDefaultLeniency(builder);
                this.configureConstructorDetector(builder);
            }

            private void configureFeatures(Jackson2ObjectMapperBuilder builder, Map<?, Boolean> features) {
                features.forEach((feature, value) -> {
                    if (value != null) {
                        if (value.booleanValue()) {
                            builder.featuresToEnable(new Object[]{feature});
                        } else {
                            builder.featuresToDisable(new Object[]{feature});
                        }
                    }
                });
            }

            private void configureVisibility(Jackson2ObjectMapperBuilder builder, Map<PropertyAccessor, JsonAutoDetect.Visibility> visibilities) {
                visibilities.forEach((arg_0, arg_1) -> ((Jackson2ObjectMapperBuilder)builder).visibility(arg_0, arg_1));
            }

            private void configureDateFormat(Jackson2ObjectMapperBuilder builder) {
                String dateFormat = this.jacksonProperties.getDateFormat();
                if (dateFormat != null) {
                    try {
                        Class dateFormatClass = ClassUtils.forName((String)dateFormat, null);
                        builder.dateFormat((DateFormat)BeanUtils.instantiateClass((Class)dateFormatClass));
                    }
                    catch (ClassNotFoundException ex) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                        TimeZone timeZone = this.jacksonProperties.getTimeZone();
                        if (timeZone == null) {
                            timeZone = new ObjectMapper().getSerializationConfig().getTimeZone();
                        }
                        simpleDateFormat.setTimeZone(timeZone);
                        builder.dateFormat((DateFormat)simpleDateFormat);
                    }
                }
            }

            private void configurePropertyNamingStrategy(Jackson2ObjectMapperBuilder builder) {
                String strategy = this.jacksonProperties.getPropertyNamingStrategy();
                if (strategy != null) {
                    try {
                        this.configurePropertyNamingStrategyClass(builder, ClassUtils.forName((String)strategy, null));
                    }
                    catch (ClassNotFoundException ex) {
                        this.configurePropertyNamingStrategyField(builder, strategy);
                    }
                }
            }

            private void configurePropertyNamingStrategyClass(Jackson2ObjectMapperBuilder builder, Class<?> propertyNamingStrategyClass) {
                builder.propertyNamingStrategy((PropertyNamingStrategy)BeanUtils.instantiateClass(propertyNamingStrategyClass));
            }

            private void configurePropertyNamingStrategyField(Jackson2ObjectMapperBuilder builder, String fieldName) {
                Field field = this.findPropertyNamingStrategyField(fieldName);
                Assert.notNull((Object)field, () -> "Constant named '" + fieldName + "' not found");
                try {
                    builder.propertyNamingStrategy((PropertyNamingStrategy)field.get(null));
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex);
                }
            }

            private Field findPropertyNamingStrategyField(String fieldName) {
                try {
                    return ReflectionUtils.findField(PropertyNamingStrategies.class, (String)fieldName, PropertyNamingStrategy.class);
                }
                catch (NoClassDefFoundError ex) {
                    return ReflectionUtils.findField(PropertyNamingStrategy.class, (String)fieldName, PropertyNamingStrategy.class);
                }
            }

            private void configureModules(Jackson2ObjectMapperBuilder builder) {
                Collection<Module> moduleBeans = StandardJackson2ObjectMapperBuilderCustomizer.getBeans((ListableBeanFactory)this.applicationContext, Module.class);
                builder.modulesToInstall(moduleBeans.toArray(new Module[0]));
            }

            private void configureLocale(Jackson2ObjectMapperBuilder builder) {
                Locale locale = this.jacksonProperties.getLocale();
                if (locale != null) {
                    builder.locale(locale);
                }
            }

            private void configureDefaultLeniency(Jackson2ObjectMapperBuilder builder) {
                Boolean defaultLeniency = this.jacksonProperties.getDefaultLeniency();
                if (defaultLeniency != null) {
                    builder.postConfigurer(objectMapper -> objectMapper.setDefaultLeniency(defaultLeniency));
                }
            }

            private void configureConstructorDetector(Jackson2ObjectMapperBuilder builder) {
                JacksonProperties.ConstructorDetectorStrategy strategy = this.jacksonProperties.getConstructorDetector();
                if (strategy != null) {
                    builder.postConfigurer(objectMapper -> {
                        switch (strategy) {
                            case USE_PROPERTIES_BASED: {
                                objectMapper.setConstructorDetector(ConstructorDetector.USE_PROPERTIES_BASED);
                                break;
                            }
                            case USE_DELEGATING: {
                                objectMapper.setConstructorDetector(ConstructorDetector.USE_DELEGATING);
                                break;
                            }
                            case EXPLICIT_ONLY: {
                                objectMapper.setConstructorDetector(ConstructorDetector.EXPLICIT_ONLY);
                                break;
                            }
                            default: {
                                objectMapper.setConstructorDetector(ConstructorDetector.DEFAULT);
                            }
                        }
                    });
                }
            }

            private static <T> Collection<T> getBeans(ListableBeanFactory beanFactory, Class<T> type) {
                return BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)beanFactory, type).values();
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Jackson2ObjectMapperBuilder.class})
    static class JacksonObjectMapperBuilderConfiguration {
        JacksonObjectMapperBuilderConfiguration() {
        }

        @Bean
        @Scope(value="prototype")
        @ConditionalOnMissingBean
        Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(ApplicationContext applicationContext, List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
            Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
            builder.applicationContext(applicationContext);
            this.customize(builder, customizers);
            return builder;
        }

        private void customize(Jackson2ObjectMapperBuilder builder, List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
            for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
                customizer.customize(builder);
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ParameterNamesModule.class})
    static class ParameterNamesModuleConfiguration {
        ParameterNamesModuleConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        ParameterNamesModule parameterNamesModule() {
            return new ParameterNamesModule(JsonCreator.Mode.DEFAULT);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Jackson2ObjectMapperBuilder.class})
    static class JacksonObjectMapperConfiguration {
        JacksonObjectMapperConfiguration() {
        }

        @Bean
        @Primary
        @ConditionalOnMissingBean
        ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
            return builder.createXmlMapper(false).build();
        }
    }
}

