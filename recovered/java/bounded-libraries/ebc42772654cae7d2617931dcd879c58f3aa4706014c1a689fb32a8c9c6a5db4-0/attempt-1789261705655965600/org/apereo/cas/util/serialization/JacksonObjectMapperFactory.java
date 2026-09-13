/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.PropertyAccessor
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.InjectableValues
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.ObjectMapper$DefaultTyping
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.cfg.MapperBuilder
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.dataformat.xml.XmlFactory
 *  com.fasterxml.jackson.dataformat.xml.XmlMapper
 *  com.fasterxml.jackson.dataformat.yaml.YAMLFactory
 *  com.fasterxml.jackson.dataformat.yaml.YAMLMapper
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *  lombok.Generated
 *  org.apereo.cas.util.serialization.JacksonObjectMapperCustomizer
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.util.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.util.serialization.JacksonInjectableValueSupplier;
import org.apereo.cas.util.serialization.JacksonObjectMapperCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class JacksonObjectMapperFactory {
    private final boolean defaultTypingEnabled;
    private final boolean failOnUnknownProperties;
    private final boolean singleValueAsArray;
    private final boolean singleArrayElementUnwrapped;
    private final boolean writeDatesAsTimestamps;
    private final boolean defaultViewInclusion;
    private final boolean useWrapperNameAsProperty;
    private final Map<String, Object> injectableValues;
    private final JsonFactory jsonFactory;

    public static void configure(ConfigurableApplicationContext applicationContext, ObjectMapper objectMapper) {
        objectMapper.registerModule((Module)new JavaTimeModule());
        ArrayList serializers = new ArrayList(applicationContext.getBeansOfType(JacksonObjectMapperCustomizer.class).values());
        AnnotationAwareOrderComparator.sort(serializers);
        Map<String, Object> injectedValues = serializers.stream().map(JacksonObjectMapperCustomizer::getInjectableValues).flatMap(entry -> entry.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        objectMapper.setInjectableValues((InjectableValues)new JacksonInjectableValueSupplier(() -> injectedValues));
    }

    public ObjectMapper toObjectMapper() {
        MapperBuilder<?, ?> mapper = this.determineMapperInstance();
        return this.initialize(mapper);
    }

    private MapperBuilder<?, ?> determineMapperInstance() {
        if (this.jsonFactory instanceof YAMLFactory) {
            return YAMLMapper.builder((YAMLFactory)((YAMLFactory)this.jsonFactory));
        }
        if (this.jsonFactory instanceof XmlFactory) {
            return XmlMapper.builder((XmlFactory)((XmlFactory)this.jsonFactory));
        }
        return JsonMapper.builder((JsonFactory)this.jsonFactory);
    }

    protected ObjectMapper initialize(MapperBuilder<?, ?> mapper) {
        ObjectMapper obm = mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, this.isSingleArrayElementUnwrapped()).configure(MapperFeature.DEFAULT_VIEW_INCLUSION, this.isDefaultViewInclusion()).configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, this.isUseWrapperNameAsProperty()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, this.isFailOnUnknownProperties()).configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true).configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, false).configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, this.isSingleValueAsArray()).disable(new DeserializationFeature[]{DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE}).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, this.isWriteDatesAsTimestamps()).build();
        obm.setInjectableValues((InjectableValues)new JacksonInjectableValueSupplier(this::getInjectableValues)).setSerializationInclusion(JsonInclude.Include.NON_DEFAULT).setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC).setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC).setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC).findAndRegisterModules();
        if (this.isDefaultTypingEnabled()) {
            obm.activateDefaultTyping(obm.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        }
        return obm;
    }

    @Generated
    private static boolean $default$defaultViewInclusion() {
        return true;
    }

    @Generated
    private static boolean $default$useWrapperNameAsProperty() {
        return false;
    }

    @Generated
    private static Map<String, Object> $default$injectableValues() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    protected JacksonObjectMapperFactory(JacksonObjectMapperFactoryBuilder<?, ?> b) {
        this.defaultTypingEnabled = b.defaultTypingEnabled;
        this.failOnUnknownProperties = b.failOnUnknownProperties;
        this.singleValueAsArray = b.singleValueAsArray;
        this.singleArrayElementUnwrapped = b.singleArrayElementUnwrapped;
        this.writeDatesAsTimestamps = b.writeDatesAsTimestamps;
        this.defaultViewInclusion = b.defaultViewInclusion$set ? b.defaultViewInclusion$value : JacksonObjectMapperFactory.$default$defaultViewInclusion();
        this.useWrapperNameAsProperty = b.useWrapperNameAsProperty$set ? b.useWrapperNameAsProperty$value : JacksonObjectMapperFactory.$default$useWrapperNameAsProperty();
        this.injectableValues = b.injectableValues$set ? b.injectableValues$value : JacksonObjectMapperFactory.$default$injectableValues();
        this.jsonFactory = b.jsonFactory;
    }

    @Generated
    public static JacksonObjectMapperFactoryBuilder<?, ?> builder() {
        return new JacksonObjectMapperFactoryBuilderImpl();
    }

    @Generated
    public boolean isDefaultTypingEnabled() {
        return this.defaultTypingEnabled;
    }

    @Generated
    public boolean isFailOnUnknownProperties() {
        return this.failOnUnknownProperties;
    }

    @Generated
    public boolean isSingleValueAsArray() {
        return this.singleValueAsArray;
    }

    @Generated
    public boolean isSingleArrayElementUnwrapped() {
        return this.singleArrayElementUnwrapped;
    }

    @Generated
    public boolean isWriteDatesAsTimestamps() {
        return this.writeDatesAsTimestamps;
    }

    @Generated
    public boolean isDefaultViewInclusion() {
        return this.defaultViewInclusion;
    }

    @Generated
    public boolean isUseWrapperNameAsProperty() {
        return this.useWrapperNameAsProperty;
    }

    @Generated
    public Map<String, Object> getInjectableValues() {
        return this.injectableValues;
    }

    @Generated
    public JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    @Generated
    private static final class JacksonObjectMapperFactoryBuilderImpl
    extends JacksonObjectMapperFactoryBuilder<JacksonObjectMapperFactory, JacksonObjectMapperFactoryBuilderImpl> {
        @Generated
        private JacksonObjectMapperFactoryBuilderImpl() {
        }

        @Override
        @Generated
        protected JacksonObjectMapperFactoryBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public JacksonObjectMapperFactory build() {
            return new JacksonObjectMapperFactory(this);
        }
    }

    @Generated
    public static abstract class JacksonObjectMapperFactoryBuilder<C extends JacksonObjectMapperFactory, B extends JacksonObjectMapperFactoryBuilder<C, B>> {
        @Generated
        private boolean defaultTypingEnabled;
        @Generated
        private boolean failOnUnknownProperties;
        @Generated
        private boolean singleValueAsArray;
        @Generated
        private boolean singleArrayElementUnwrapped;
        @Generated
        private boolean writeDatesAsTimestamps;
        @Generated
        private boolean defaultViewInclusion$set;
        @Generated
        private boolean defaultViewInclusion$value;
        @Generated
        private boolean useWrapperNameAsProperty$set;
        @Generated
        private boolean useWrapperNameAsProperty$value;
        @Generated
        private boolean injectableValues$set;
        @Generated
        private Map<String, Object> injectableValues$value;
        @Generated
        private JsonFactory jsonFactory;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B defaultTypingEnabled(boolean defaultTypingEnabled) {
            this.defaultTypingEnabled = defaultTypingEnabled;
            return this.self();
        }

        @Generated
        public B failOnUnknownProperties(boolean failOnUnknownProperties) {
            this.failOnUnknownProperties = failOnUnknownProperties;
            return this.self();
        }

        @Generated
        public B singleValueAsArray(boolean singleValueAsArray) {
            this.singleValueAsArray = singleValueAsArray;
            return this.self();
        }

        @Generated
        public B singleArrayElementUnwrapped(boolean singleArrayElementUnwrapped) {
            this.singleArrayElementUnwrapped = singleArrayElementUnwrapped;
            return this.self();
        }

        @Generated
        public B writeDatesAsTimestamps(boolean writeDatesAsTimestamps) {
            this.writeDatesAsTimestamps = writeDatesAsTimestamps;
            return this.self();
        }

        @Generated
        public B defaultViewInclusion(boolean defaultViewInclusion) {
            this.defaultViewInclusion$value = defaultViewInclusion;
            this.defaultViewInclusion$set = true;
            return this.self();
        }

        @Generated
        public B useWrapperNameAsProperty(boolean useWrapperNameAsProperty) {
            this.useWrapperNameAsProperty$value = useWrapperNameAsProperty;
            this.useWrapperNameAsProperty$set = true;
            return this.self();
        }

        @Generated
        public B injectableValues(Map<String, Object> injectableValues) {
            this.injectableValues$value = injectableValues;
            this.injectableValues$set = true;
            return this.self();
        }

        @Generated
        public B jsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this.self();
        }

        @Generated
        public String toString() {
            return "JacksonObjectMapperFactory.JacksonObjectMapperFactoryBuilder(defaultTypingEnabled=" + this.defaultTypingEnabled + ", failOnUnknownProperties=" + this.failOnUnknownProperties + ", singleValueAsArray=" + this.singleValueAsArray + ", singleArrayElementUnwrapped=" + this.singleArrayElementUnwrapped + ", writeDatesAsTimestamps=" + this.writeDatesAsTimestamps + ", defaultViewInclusion$value=" + this.defaultViewInclusion$value + ", useWrapperNameAsProperty$value=" + this.useWrapperNameAsProperty$value + ", injectableValues$value=" + this.injectableValues$value + ", jsonFactory=" + this.jsonFactory + ")";
        }
    }
}

