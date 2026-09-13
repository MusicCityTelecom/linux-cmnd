/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.BeanDescription
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationConfig
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.introspect.AnnotatedMethod
 *  com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.databind.json.JsonMapper$Builder
 *  com.fasterxml.jackson.databind.ser.BeanPropertyWriter
 *  com.fasterxml.jackson.databind.ser.BeanSerializerFactory
 *  com.fasterxml.jackson.databind.ser.BeanSerializerModifier
 *  com.fasterxml.jackson.databind.ser.FilterProvider
 *  com.fasterxml.jackson.databind.ser.PropertyWriter
 *  com.fasterxml.jackson.databind.ser.SerializerFactory
 *  com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
 *  com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.boot.context.properties.BoundConfigurationProperties
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBean
 *  org.springframework.boot.context.properties.ConstructorBinding
 *  org.springframework.boot.context.properties.bind.Name
 *  org.springframework.boot.context.properties.source.ConfigurationProperty
 *  org.springframework.boot.context.properties.source.ConfigurationPropertyName
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySource
 *  org.springframework.boot.origin.Origin
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.DefaultParameterNameDiscoverer
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.context.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.SanitizableData;
import org.springframework.boot.actuate.endpoint.Sanitizer;
import org.springframework.boot.actuate.endpoint.SanitizingFunction;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.context.properties.BoundConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.KotlinDetector;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@Endpoint(id="configprops")
public class ConfigurationPropertiesReportEndpoint
implements ApplicationContextAware {
    private static final String CONFIGURATION_PROPERTIES_FILTER_ID = "configurationPropertiesFilter";
    private final Sanitizer sanitizer;
    private ApplicationContext context;
    private ObjectMapper objectMapper;

    public ConfigurationPropertiesReportEndpoint() {
        this(Collections.emptyList());
    }

    public ConfigurationPropertiesReportEndpoint(Iterable<SanitizingFunction> sanitizingFunctions) {
        this.sanitizer = new Sanitizer(sanitizingFunctions);
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public void setKeysToSanitize(String ... keysToSanitize) {
        this.sanitizer.setKeysToSanitize(keysToSanitize);
    }

    public void keysToSanitize(String ... keysToSanitize) {
        this.sanitizer.keysToSanitize(keysToSanitize);
    }

    @ReadOperation
    public ApplicationConfigurationProperties configurationProperties() {
        return this.extract(this.context, bean -> true);
    }

    @ReadOperation
    public ApplicationConfigurationProperties configurationPropertiesWithPrefix(@Selector String prefix) {
        return this.extract(this.context, bean -> bean.getAnnotation().prefix().startsWith(prefix));
    }

    private ApplicationConfigurationProperties extract(ApplicationContext context, Predicate<ConfigurationPropertiesBean> beanFilterPredicate) {
        ObjectMapper mapper = this.getObjectMapper();
        HashMap<String, ContextConfigurationProperties> contexts = new HashMap<String, ContextConfigurationProperties>();
        for (ApplicationContext target = context; target != null; target = target.getParent()) {
            contexts.put(target.getId(), this.describeBeans(mapper, target, beanFilterPredicate));
        }
        return new ApplicationConfigurationProperties(contexts);
    }

    private ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            JsonMapper.Builder builder = JsonMapper.builder();
            this.configureJsonMapper(builder);
            this.objectMapper = builder.build();
            this.configureObjectMapper(this.objectMapper);
        }
        return this.objectMapper;
    }

    @Deprecated
    protected void configureObjectMapper(ObjectMapper mapper) {
    }

    protected void configureJsonMapper(JsonMapper.Builder builder) {
        builder.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        builder.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        builder.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        JsonMapper.builder();
        builder.configure(MapperFeature.USE_STD_BEAN_NAMING, true);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        this.applyConfigurationPropertiesFilter(builder);
        this.applySerializationModifier(builder);
        builder.addModule((Module)new JavaTimeModule());
    }

    private void applyConfigurationPropertiesFilter(JsonMapper.Builder builder) {
        builder.annotationIntrospector((AnnotationIntrospector)new ConfigurationPropertiesAnnotationIntrospector());
        builder.filterProvider((FilterProvider)new SimpleFilterProvider().setDefaultFilter((SimpleBeanPropertyFilter)new ConfigurationPropertiesPropertyFilter()));
    }

    private void applySerializationModifier(JsonMapper.Builder builder) {
        SerializerFactory factory = BeanSerializerFactory.instance.withSerializerModifier((BeanSerializerModifier)new GenericSerializerModifier());
        builder.serializerFactory(factory);
    }

    private ContextConfigurationProperties describeBeans(ObjectMapper mapper, ApplicationContext context, Predicate<ConfigurationPropertiesBean> beanFilterPredicate) {
        Map beans = ConfigurationPropertiesBean.getAll((ApplicationContext)context);
        Map<String, ConfigurationPropertiesBeanDescriptor> descriptors = beans.values().stream().filter(beanFilterPredicate).collect(Collectors.toMap(ConfigurationPropertiesBean::getName, bean -> this.describeBean(mapper, (ConfigurationPropertiesBean)bean)));
        return new ContextConfigurationProperties(descriptors, context.getParent() != null ? context.getParent().getId() : null);
    }

    private ConfigurationPropertiesBeanDescriptor describeBean(ObjectMapper mapper, ConfigurationPropertiesBean bean) {
        String prefix = bean.getAnnotation().prefix();
        Map<String, Object> serialized = this.safeSerialize(mapper, bean.getInstance(), prefix);
        Map<String, Object> properties = this.sanitize(prefix, serialized);
        Map<String, Object> inputs = this.getInputs(prefix, serialized);
        return new ConfigurationPropertiesBeanDescriptor(prefix, properties, inputs);
    }

    private Map<String, Object> safeSerialize(ObjectMapper mapper, Object bean, String prefix) {
        try {
            return new HashMap<String, Object>((Map)mapper.convertValue(bean, Map.class));
        }
        catch (Exception ex) {
            return new HashMap<String, Object>(Collections.singletonMap("error", "Cannot serialize '" + prefix + "'"));
        }
    }

    private Map<String, Object> sanitize(String prefix, Map<String, Object> map) {
        map.forEach((key, value) -> {
            String qualifiedKey = this.getQualifiedKey(prefix, (String)key);
            if (value instanceof Map) {
                map.put((String)key, this.sanitize(qualifiedKey, (Map)value));
            } else if (value instanceof List) {
                map.put((String)key, this.sanitize(qualifiedKey, (List)value));
            } else {
                map.put((String)key, this.sanitizeWithPropertySourceIfPresent(qualifiedKey, value));
            }
        });
        return map;
    }

    private Object sanitizeWithPropertySourceIfPresent(String qualifiedKey, Object value) {
        ConfigurationPropertyName currentName = this.getCurrentName(qualifiedKey);
        ConfigurationProperty candidate = this.getCandidate(currentName);
        PropertySource<?> propertySource = this.getPropertySource(candidate);
        if (propertySource != null) {
            SanitizableData data = new SanitizableData(propertySource, qualifiedKey, value);
            return this.sanitizer.sanitize(data);
        }
        SanitizableData data = new SanitizableData(null, qualifiedKey, value);
        return this.sanitizer.sanitize(data);
    }

    private PropertySource<?> getPropertySource(ConfigurationProperty configurationProperty) {
        if (configurationProperty == null) {
            return null;
        }
        ConfigurationPropertySource source = configurationProperty.getSource();
        Object underlyingSource = source != null ? source.getUnderlyingSource() : null;
        return underlyingSource instanceof PropertySource ? (PropertySource)underlyingSource : null;
    }

    private ConfigurationPropertyName getCurrentName(String qualifiedKey) {
        return ConfigurationPropertyName.adapt((CharSequence)qualifiedKey, (char)'.');
    }

    private ConfigurationProperty getCandidate(ConfigurationPropertyName currentName) {
        BoundConfigurationProperties bound = BoundConfigurationProperties.get((ApplicationContext)this.context);
        if (bound == null) {
            return null;
        }
        ConfigurationProperty candidate = bound.get(currentName);
        if (candidate == null && currentName.isLastElementIndexed()) {
            candidate = bound.get(currentName.chop(currentName.getNumberOfElements() - 1));
        }
        return candidate;
    }

    private List<Object> sanitize(String prefix, List<Object> list) {
        ArrayList<Object> sanitized = new ArrayList<Object>();
        int index = 0;
        for (Object item : list) {
            String name = prefix + "[" + index++ + "]";
            if (item instanceof Map) {
                sanitized.add(this.sanitize(name, (Map)item));
                continue;
            }
            if (item instanceof List) {
                sanitized.add(this.sanitize(name, (List)item));
                continue;
            }
            sanitized.add(this.sanitizeWithPropertySourceIfPresent(name, item));
        }
        return sanitized;
    }

    private Map<String, Object> getInputs(String prefix, Map<String, Object> map) {
        LinkedHashMap<String, Object> augmented = new LinkedHashMap<String, Object>(map);
        map.forEach((key, value) -> {
            String qualifiedKey = this.getQualifiedKey(prefix, (String)key);
            if (value instanceof Map) {
                augmented.put((String)key, this.getInputs(qualifiedKey, (Map)value));
            } else if (value instanceof List) {
                augmented.put((String)key, this.getInputs(qualifiedKey, (List)value));
            } else {
                augmented.put((String)key, this.applyInput(qualifiedKey));
            }
        });
        return augmented;
    }

    private List<Object> getInputs(String prefix, List<Object> list) {
        ArrayList<Object> augmented = new ArrayList<Object>();
        int index = 0;
        for (Object item : list) {
            String name = prefix + "[" + index++ + "]";
            if (item instanceof Map) {
                augmented.add(this.getInputs(name, (Map)item));
                continue;
            }
            if (item instanceof List) {
                augmented.add(this.getInputs(name, (List)item));
                continue;
            }
            augmented.add(this.applyInput(name));
        }
        return augmented;
    }

    private Map<String, Object> applyInput(String qualifiedKey) {
        ConfigurationPropertyName currentName = this.getCurrentName(qualifiedKey);
        ConfigurationProperty candidate = this.getCandidate(currentName);
        PropertySource<?> propertySource = this.getPropertySource(candidate);
        if (propertySource != null) {
            Object value = this.stringifyIfNecessary(candidate.getValue());
            SanitizableData data = new SanitizableData(propertySource, currentName.toString(), value);
            return this.getInput(candidate, this.sanitizer.sanitize(data));
        }
        return Collections.emptyMap();
    }

    private Map<String, Object> getInput(ConfigurationProperty candidate, Object sanitizedValue) {
        LinkedHashMap<String, Object> input = new LinkedHashMap<String, Object>();
        Origin origin = Origin.from((Object)candidate);
        List originParents = Origin.parentsFrom((Object)candidate);
        input.put("value", sanitizedValue);
        input.put("origin", origin != null ? origin.toString() : "none");
        if (!originParents.isEmpty()) {
            input.put("originParents", originParents.stream().map(Object::toString).toArray(String[]::new));
        }
        return input;
    }

    private Object stringifyIfNecessary(Object value) {
        if (value == null || value.getClass().isPrimitive()) {
            return value;
        }
        if (CharSequence.class.isAssignableFrom(value.getClass())) {
            return value.toString();
        }
        return "Complex property value " + value.getClass().getName();
    }

    private String getQualifiedKey(String prefix, String key) {
        return (prefix.isEmpty() ? prefix : prefix + ".") + key;
    }

    public static final class ConfigurationPropertiesBeanDescriptor {
        private final String prefix;
        private final Map<String, Object> properties;
        private final Map<String, Object> inputs;

        private ConfigurationPropertiesBeanDescriptor(String prefix, Map<String, Object> properties, Map<String, Object> inputs) {
            this.prefix = prefix;
            this.properties = properties;
            this.inputs = inputs;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public Map<String, Object> getProperties() {
            return this.properties;
        }

        public Map<String, Object> getInputs() {
            return this.inputs;
        }
    }

    public static final class ContextConfigurationProperties {
        private final Map<String, ConfigurationPropertiesBeanDescriptor> beans;
        private final String parentId;

        private ContextConfigurationProperties(Map<String, ConfigurationPropertiesBeanDescriptor> beans, String parentId) {
            this.beans = beans;
            this.parentId = parentId;
        }

        public Map<String, ConfigurationPropertiesBeanDescriptor> getBeans() {
            return this.beans;
        }

        public String getParentId() {
            return this.parentId;
        }
    }

    public static final class ApplicationConfigurationProperties {
        private final Map<String, ContextConfigurationProperties> contexts;

        private ApplicationConfigurationProperties(Map<String, ContextConfigurationProperties> contexts) {
            this.contexts = contexts;
        }

        public Map<String, ContextConfigurationProperties> getContexts() {
            return this.contexts;
        }
    }

    protected static class GenericSerializerModifier
    extends BeanSerializerModifier {
        private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

        protected GenericSerializerModifier() {
        }

        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            ArrayList<BeanPropertyWriter> result = new ArrayList<BeanPropertyWriter>();
            Class beanClass = beanDesc.getType().getRawClass();
            Constructor<?> bindConstructor = this.findBindConstructor(ClassUtils.getUserClass((Class)beanClass));
            for (BeanPropertyWriter writer : beanProperties) {
                if (!this.isCandidate(beanDesc, writer, bindConstructor)) continue;
                result.add(writer);
            }
            return result;
        }

        private boolean isCandidate(BeanDescription beanDesc, BeanPropertyWriter writer, Constructor<?> constructor) {
            if (constructor != null) {
                Parameter[] parameters = constructor.getParameters();
                String[] names = PARAMETER_NAME_DISCOVERER.getParameterNames(constructor);
                if (names == null) {
                    names = new String[parameters.length];
                }
                for (int i = 0; i < parameters.length; ++i) {
                    String name = MergedAnnotations.from((AnnotatedElement)parameters[i]).get(Name.class).getValue("value", String.class).orElse(names[i] != null ? names[i] : parameters[i].getName());
                    if (!name.equals(writer.getName())) continue;
                    return true;
                }
            }
            return this.isReadable(beanDesc, writer);
        }

        private boolean isReadable(BeanDescription beanDesc, BeanPropertyWriter writer) {
            Class parentType = beanDesc.getType().getRawClass();
            Class type = writer.getType().getRawClass();
            AnnotatedMethod setter = this.findSetter(beanDesc, writer);
            return setter != null || ClassUtils.getPackageName((Class)parentType).equals(ClassUtils.getPackageName((Class)type)) || Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type);
        }

        private AnnotatedMethod findSetter(BeanDescription beanDesc, BeanPropertyWriter writer) {
            Class type;
            String name = "set" + this.determineAccessorSuffix(writer.getName());
            AnnotatedMethod setter = beanDesc.findMethod(name, new Class[]{type = writer.getType().getRawClass()});
            if (setter == null && type.equals(Boolean.TYPE)) {
                setter = beanDesc.findMethod(name, new Class[]{Boolean.class});
            }
            return setter;
        }

        private String determineAccessorSuffix(String propertyName) {
            if (propertyName.length() > 1 && Character.isUpperCase(propertyName.charAt(1))) {
                return propertyName;
            }
            return StringUtils.capitalize((String)propertyName);
        }

        private Constructor<?> findBindConstructor(Class<?> type) {
            Constructor constructor;
            boolean classConstructorBinding = MergedAnnotations.from(type, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY_AND_ENCLOSING_CLASSES).isPresent(ConstructorBinding.class);
            if (KotlinDetector.isKotlinPresent() && KotlinDetector.isKotlinType(type) && (constructor = BeanUtils.findPrimaryConstructor(type)) != null) {
                return this.findBindConstructor(classConstructorBinding, constructor);
            }
            return this.findBindConstructor(classConstructorBinding, type.getDeclaredConstructors());
        }

        private Constructor<?> findBindConstructor(boolean classConstructorBinding, Constructor<?> ... candidates) {
            List candidateConstructors = Arrays.stream(candidates).filter(constructor -> constructor.getParameterCount() > 0).collect(Collectors.toList());
            List flaggedConstructors = candidateConstructors.stream().filter(candidate -> MergedAnnotations.from((AnnotatedElement)candidate).isPresent(ConstructorBinding.class)).collect(Collectors.toList());
            if (flaggedConstructors.size() == 1) {
                return (Constructor)flaggedConstructors.get(0);
            }
            if (classConstructorBinding && candidateConstructors.size() == 1) {
                return (Constructor)candidateConstructors.get(0);
            }
            return null;
        }
    }

    private static class ConfigurationPropertiesPropertyFilter
    extends SimpleBeanPropertyFilter {
        private static final Log logger = LogFactory.getLog(ConfigurationPropertiesPropertyFilter.class);

        private ConfigurationPropertiesPropertyFilter() {
        }

        protected boolean include(BeanPropertyWriter writer) {
            return this.include(writer.getFullName().getSimpleName());
        }

        protected boolean include(PropertyWriter writer) {
            return this.include(writer.getFullName().getSimpleName());
        }

        private boolean include(String name) {
            return !name.startsWith("$$");
        }

        public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
            if (writer instanceof BeanPropertyWriter) {
                try {
                    if (pojo == ((BeanPropertyWriter)writer).get(pojo)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug((Object)("Skipping '" + writer.getFullName() + "' on '" + pojo.getClass().getName() + "' as it is self-referential"));
                        }
                        return;
                    }
                }
                catch (Exception ex) {
                    if (logger.isDebugEnabled()) {
                        logger.debug((Object)("Skipping '" + writer.getFullName() + "' on '" + pojo.getClass().getName() + "' as an exception was thrown when retrieving its value"), (Throwable)ex);
                    }
                    return;
                }
            }
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    private static class ConfigurationPropertiesAnnotationIntrospector
    extends JacksonAnnotationIntrospector {
        private ConfigurationPropertiesAnnotationIntrospector() {
        }

        public Object findFilterId(Annotated a) {
            Object id = super.findFilterId(a);
            if (id == null) {
                id = ConfigurationPropertiesReportEndpoint.CONFIGURATION_PROPERTIES_FILTER_ID;
            }
            return id;
        }
    }
}

