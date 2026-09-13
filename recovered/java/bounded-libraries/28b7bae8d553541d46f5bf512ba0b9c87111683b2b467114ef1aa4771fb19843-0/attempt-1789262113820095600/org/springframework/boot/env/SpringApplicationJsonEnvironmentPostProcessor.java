/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.env;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SpringApplicationJsonEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    public static final String SPRING_APPLICATION_JSON_PROPERTY = "spring.application.json";
    public static final String SPRING_APPLICATION_JSON_ENVIRONMENT_VARIABLE = "SPRING_APPLICATION_JSON";
    private static final String SERVLET_ENVIRONMENT_CLASS = "org.springframework.web.context.support.StandardServletEnvironment";
    private static final Set<String> SERVLET_ENVIRONMENT_PROPERTY_SOURCES = new LinkedHashSet<String>(Arrays.asList("jndiProperties", "servletContextInitParams", "servletConfigInitParams"));
    public static final int DEFAULT_ORDER = -2147483643;
    private int order = -2147483643;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.stream().map(JsonPropertyValue::get).filter(Objects::nonNull).findFirst().ifPresent(v -> this.processJson(environment, (JsonPropertyValue)v));
    }

    private void processJson(ConfigurableEnvironment environment, JsonPropertyValue propertyValue) {
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = parser.parseMap(propertyValue.getJson());
        if (!map.isEmpty()) {
            this.addJsonPropertySource(environment, (PropertySource<?>)new JsonPropertySource(propertyValue, this.flatten(map)));
        }
    }

    private Map<String, Object> flatten(Map<String, Object> map) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        this.flatten(null, result, map);
        return result;
    }

    private void flatten(String prefix, Map<String, Object> result, Map<String, Object> map) {
        String namePrefix = prefix != null ? prefix + "." : "";
        map.forEach((key, value) -> this.extract(namePrefix + key, result, value));
    }

    private void extract(String name, Map<String, Object> result, Object value) {
        if (value instanceof Map) {
            if (CollectionUtils.isEmpty((Map)((Map)value))) {
                result.put(name, value);
                return;
            }
            this.flatten(name, result, (Map)value);
        } else if (value instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection)((Collection)value))) {
                result.put(name, value);
                return;
            }
            int index = 0;
            for (Object object : (Collection)value) {
                this.extract(name + "[" + index + "]", result, object);
                ++index;
            }
        } else {
            result.put(name, value);
        }
    }

    private void addJsonPropertySource(ConfigurableEnvironment environment, PropertySource<?> source) {
        String name;
        MutablePropertySources sources = environment.getPropertySources();
        if (sources.contains(name = this.findPropertySource(sources))) {
            sources.addBefore(name, source);
        } else {
            sources.addFirst(source);
        }
    }

    private String findPropertySource(MutablePropertySources sources) {
        PropertySource servletPropertySource;
        if (ClassUtils.isPresent((String)SERVLET_ENVIRONMENT_CLASS, null) && (servletPropertySource = (PropertySource)sources.stream().filter(source -> SERVLET_ENVIRONMENT_PROPERTY_SOURCES.contains(source.getName())).findFirst().orElse(null)) != null) {
            return servletPropertySource.getName();
        }
        return "systemProperties";
    }

    private static class JsonPropertyValue {
        private static final String[] CANDIDATES = new String[]{"spring.application.json", "SPRING_APPLICATION_JSON"};
        private final PropertySource<?> propertySource;
        private final String propertyName;
        private final String json;

        JsonPropertyValue(PropertySource<?> propertySource, String propertyName, String json) {
            this.propertySource = propertySource;
            this.propertyName = propertyName;
            this.json = json;
        }

        String getJson() {
            return this.json;
        }

        Origin getOrigin() {
            return PropertySourceOrigin.get(this.propertySource, this.propertyName);
        }

        static JsonPropertyValue get(PropertySource<?> propertySource) {
            for (String candidate : CANDIDATES) {
                Object value = propertySource.getProperty(candidate);
                if (!(value instanceof String) || !StringUtils.hasLength((String)((String)value))) continue;
                return new JsonPropertyValue(propertySource, candidate, (String)value);
            }
            return null;
        }
    }

    private static class JsonPropertySource
    extends MapPropertySource
    implements OriginLookup<String> {
        private final JsonPropertyValue propertyValue;

        JsonPropertySource(JsonPropertyValue propertyValue, Map<String, Object> source) {
            super(SpringApplicationJsonEnvironmentPostProcessor.SPRING_APPLICATION_JSON_PROPERTY, source);
            this.propertyValue = propertyValue;
        }

        @Override
        public Origin getOrigin(String key) {
            return this.propertyValue.getOrigin();
        }
    }
}

