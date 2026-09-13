/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  org.springframework.boot.context.properties.bind.PlaceholdersResolver
 *  org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySources
 *  org.springframework.boot.origin.Origin
 *  org.springframework.boot.origin.OriginLookup
 *  org.springframework.core.env.CompositePropertySource
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.StandardEnvironment
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.PropertyPlaceholderHelper
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.env;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.springframework.boot.actuate.endpoint.SanitizableData;
import org.springframework.boot.actuate.endpoint.Sanitizer;
import org.springframework.boot.actuate.endpoint.SanitizingFunction;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.context.properties.bind.PlaceholdersResolver;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

@Endpoint(id="env")
public class EnvironmentEndpoint {
    private final Sanitizer sanitizer;
    private final Environment environment;

    public EnvironmentEndpoint(Environment environment) {
        this(environment, Collections.emptyList());
    }

    public EnvironmentEndpoint(Environment environment, Iterable<SanitizingFunction> sanitizingFunctions) {
        this.environment = environment;
        this.sanitizer = new Sanitizer(sanitizingFunctions);
    }

    public void setKeysToSanitize(String ... keysToSanitize) {
        this.sanitizer.setKeysToSanitize(keysToSanitize);
    }

    public void keysToSanitize(String ... keysToSanitize) {
        this.sanitizer.keysToSanitize(keysToSanitize);
    }

    @ReadOperation
    public EnvironmentDescriptor environment(@Nullable String pattern) {
        if (StringUtils.hasText((String)pattern)) {
            return this.getEnvironmentDescriptor(Pattern.compile(pattern).asPredicate());
        }
        return this.getEnvironmentDescriptor(name -> true);
    }

    @ReadOperation
    public EnvironmentEntryDescriptor environmentEntry(@Selector String toMatch) {
        return this.getEnvironmentEntryDescriptor(toMatch);
    }

    private EnvironmentDescriptor getEnvironmentDescriptor(Predicate<String> propertyNamePredicate) {
        PlaceholdersResolver resolver = this.getResolver();
        ArrayList propertySources = new ArrayList();
        this.getPropertySourcesAsMap().forEach((sourceName, source) -> {
            if (source instanceof EnumerablePropertySource) {
                propertySources.add(this.describeSource((String)sourceName, (EnumerablePropertySource<?>)((EnumerablePropertySource)source), resolver, propertyNamePredicate));
            }
        });
        return new EnvironmentDescriptor(Arrays.asList(this.environment.getActiveProfiles()), propertySources);
    }

    private EnvironmentEntryDescriptor getEnvironmentEntryDescriptor(String propertyName) {
        Map<String, PropertyValueDescriptor> descriptors = this.getPropertySourceDescriptors(propertyName);
        PropertySummaryDescriptor summary = this.getPropertySummaryDescriptor(descriptors);
        return new EnvironmentEntryDescriptor(summary, Arrays.asList(this.environment.getActiveProfiles()), this.toPropertySourceDescriptors(descriptors));
    }

    private List<PropertySourceEntryDescriptor> toPropertySourceDescriptors(Map<String, PropertyValueDescriptor> descriptors) {
        ArrayList<PropertySourceEntryDescriptor> result = new ArrayList<PropertySourceEntryDescriptor>();
        descriptors.forEach((name, property) -> result.add(new PropertySourceEntryDescriptor((String)name, (PropertyValueDescriptor)property)));
        return result;
    }

    private PropertySummaryDescriptor getPropertySummaryDescriptor(Map<String, PropertyValueDescriptor> descriptors) {
        for (Map.Entry<String, PropertyValueDescriptor> entry : descriptors.entrySet()) {
            if (entry.getValue() == null) continue;
            return new PropertySummaryDescriptor(entry.getKey(), entry.getValue().getValue());
        }
        return null;
    }

    private Map<String, PropertyValueDescriptor> getPropertySourceDescriptors(String propertyName) {
        LinkedHashMap<String, PropertyValueDescriptor> propertySources = new LinkedHashMap<String, PropertyValueDescriptor>();
        PlaceholdersResolver resolver = this.getResolver();
        this.getPropertySourcesAsMap().forEach((sourceName, source) -> {
            PropertyValueDescriptor cfr_ignored_0 = propertySources.put((String)sourceName, source.containsProperty(propertyName) ? this.describeValueOf(propertyName, (PropertySource<?>)source, resolver) : null);
        });
        return propertySources;
    }

    private PropertySourceDescriptor describeSource(String sourceName, EnumerablePropertySource<?> source, PlaceholdersResolver resolver, Predicate<String> namePredicate) {
        LinkedHashMap properties = new LinkedHashMap();
        Stream.of(source.getPropertyNames()).filter(namePredicate).forEach(name -> properties.put(name, this.describeValueOf((String)name, (PropertySource<?>)source, resolver)));
        return new PropertySourceDescriptor(sourceName, properties);
    }

    private PropertyValueDescriptor describeValueOf(String name, PropertySource<?> source, PlaceholdersResolver resolver) {
        Object resolved = resolver.resolvePlaceholders(source.getProperty(name));
        Origin origin = source instanceof OriginLookup ? ((OriginLookup)source).getOrigin((Object)name) : null;
        Object sanitizedValue = this.sanitize(source, name, resolved);
        return new PropertyValueDescriptor(this.stringifyIfNecessary(sanitizedValue), origin);
    }

    private PlaceholdersResolver getResolver() {
        return new PropertySourcesPlaceholdersSanitizingResolver((Iterable<PropertySource<?>>)this.getPropertySources(), this.sanitizer);
    }

    private Map<String, PropertySource<?>> getPropertySourcesAsMap() {
        LinkedHashMap map = new LinkedHashMap();
        for (PropertySource source : this.getPropertySources()) {
            if (ConfigurationPropertySources.isAttachedConfigurationPropertySource((PropertySource)source)) continue;
            this.extract("", map, source);
        }
        return map;
    }

    private MutablePropertySources getPropertySources() {
        if (this.environment instanceof ConfigurableEnvironment) {
            return ((ConfigurableEnvironment)this.environment).getPropertySources();
        }
        return new StandardEnvironment().getPropertySources();
    }

    private void extract(String root, Map<String, PropertySource<?>> map, PropertySource<?> source) {
        if (source instanceof CompositePropertySource) {
            for (PropertySource nest : ((CompositePropertySource)source).getPropertySources()) {
                this.extract(source.getName() + ":", map, nest);
            }
        } else {
            map.put(root + source.getName(), source);
        }
    }

    @Deprecated
    public Object sanitize(String key, Object value) {
        return this.sanitizer.sanitize(key, value);
    }

    private Object sanitize(PropertySource<?> source, String name, Object value) {
        return this.sanitizer.sanitize(new SanitizableData(source, name, value));
    }

    protected Object stringifyIfNecessary(Object value) {
        if (value == null || ClassUtils.isPrimitiveOrWrapper(value.getClass()) || Number.class.isAssignableFrom(value.getClass())) {
            return value;
        }
        if (CharSequence.class.isAssignableFrom(value.getClass())) {
            return value.toString();
        }
        return "Complex property type " + value.getClass().getName();
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public static final class PropertyValueDescriptor {
        private final Object value;
        private final String origin;
        private final String[] originParents;

        private PropertyValueDescriptor(Object value, Origin origin) {
            this.value = value;
            this.origin = origin != null ? origin.toString() : null;
            List originParents = Origin.parentsFrom((Object)origin);
            this.originParents = originParents.isEmpty() ? null : (String[])originParents.stream().map(Object::toString).toArray(String[]::new);
        }

        public Object getValue() {
            return this.value;
        }

        public String getOrigin() {
            return this.origin;
        }

        public String[] getOriginParents() {
            return this.originParents;
        }
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public static final class PropertySourceEntryDescriptor {
        private final String name;
        private final PropertyValueDescriptor property;

        private PropertySourceEntryDescriptor(String name, PropertyValueDescriptor property) {
            this.name = name;
            this.property = property;
        }

        public String getName() {
            return this.name;
        }

        public PropertyValueDescriptor getProperty() {
            return this.property;
        }
    }

    public static final class PropertySourceDescriptor {
        private final String name;
        private final Map<String, PropertyValueDescriptor> properties;

        private PropertySourceDescriptor(String name, Map<String, PropertyValueDescriptor> properties) {
            this.name = name;
            this.properties = properties;
        }

        public String getName() {
            return this.name;
        }

        public Map<String, PropertyValueDescriptor> getProperties() {
            return this.properties;
        }
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public static final class PropertySummaryDescriptor {
        private final String source;
        private final Object value;

        public PropertySummaryDescriptor(String source, Object value) {
            this.source = source;
            this.value = value;
        }

        public String getSource() {
            return this.source;
        }

        public Object getValue() {
            return this.value;
        }
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public static final class EnvironmentEntryDescriptor {
        private final PropertySummaryDescriptor property;
        private final List<String> activeProfiles;
        private final List<PropertySourceEntryDescriptor> propertySources;

        private EnvironmentEntryDescriptor(PropertySummaryDescriptor property, List<String> activeProfiles, List<PropertySourceEntryDescriptor> propertySources) {
            this.property = property;
            this.activeProfiles = activeProfiles;
            this.propertySources = propertySources;
        }

        public PropertySummaryDescriptor getProperty() {
            return this.property;
        }

        public List<String> getActiveProfiles() {
            return this.activeProfiles;
        }

        public List<PropertySourceEntryDescriptor> getPropertySources() {
            return this.propertySources;
        }
    }

    public static final class EnvironmentDescriptor {
        private final List<String> activeProfiles;
        private final List<PropertySourceDescriptor> propertySources;

        private EnvironmentDescriptor(List<String> activeProfiles, List<PropertySourceDescriptor> propertySources) {
            this.activeProfiles = activeProfiles;
            this.propertySources = propertySources;
        }

        public List<String> getActiveProfiles() {
            return this.activeProfiles;
        }

        public List<PropertySourceDescriptor> getPropertySources() {
            return this.propertySources;
        }
    }

    private static class PropertySourcesPlaceholdersSanitizingResolver
    extends PropertySourcesPlaceholdersResolver {
        private final Sanitizer sanitizer;
        private final Iterable<PropertySource<?>> sources;

        PropertySourcesPlaceholdersSanitizingResolver(Iterable<PropertySource<?>> sources, Sanitizer sanitizer) {
            super(sources, new PropertyPlaceholderHelper("${", "}", ":", true));
            this.sources = sources;
            this.sanitizer = sanitizer;
        }

        protected String resolvePlaceholder(String placeholder) {
            if (this.sources != null) {
                for (PropertySource<?> source : this.sources) {
                    Object value = source.getProperty(placeholder);
                    if (value == null) continue;
                    SanitizableData data = new SanitizableData(source, placeholder, value);
                    Object sanitized = this.sanitizer.sanitize(data);
                    return sanitized != null ? String.valueOf(sanitized) : null;
                }
            }
            return null;
        }
    }
}

