/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertiesPropertySource
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.cloud;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class CloudFoundryVcapEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    private static final String VCAP_APPLICATION = "VCAP_APPLICATION";
    private static final String VCAP_SERVICES = "VCAP_SERVICES";
    private final Log logger;
    private int order = -2147483639;

    public CloudFoundryVcapEnvironmentPostProcessor(Log logger) {
        this.logger = logger;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (CloudPlatform.CLOUD_FOUNDRY.isActive((Environment)environment)) {
            Properties properties = new Properties();
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            this.addWithPrefix(properties, this.getPropertiesFromApplication((Environment)environment, jsonParser), "vcap.application.");
            this.addWithPrefix(properties, this.getPropertiesFromServices((Environment)environment, jsonParser), "vcap.services.");
            MutablePropertySources propertySources = environment.getPropertySources();
            if (propertySources.contains("commandLineArgs")) {
                propertySources.addAfter("commandLineArgs", (PropertySource)new PropertiesPropertySource("vcap", properties));
            } else {
                propertySources.addFirst((PropertySource)new PropertiesPropertySource("vcap", properties));
            }
        }
    }

    private void addWithPrefix(Properties properties, Properties other, String prefix) {
        for (String key : other.stringPropertyNames()) {
            String prefixed = prefix + key;
            properties.setProperty(prefixed, other.getProperty(key));
        }
    }

    private Properties getPropertiesFromApplication(Environment environment, JsonParser parser) {
        Properties properties = new Properties();
        try {
            String property = environment.getProperty(VCAP_APPLICATION, "{}");
            Map<String, Object> map = parser.parseMap(property);
            this.extractPropertiesFromApplication(properties, map);
        }
        catch (Exception ex) {
            this.logger.error((Object)"Could not parse VCAP_APPLICATION", (Throwable)ex);
        }
        return properties;
    }

    private Properties getPropertiesFromServices(Environment environment, JsonParser parser) {
        Properties properties = new Properties();
        try {
            String property = environment.getProperty(VCAP_SERVICES, "{}");
            Map<String, Object> map = parser.parseMap(property);
            this.extractPropertiesFromServices(properties, map);
        }
        catch (Exception ex) {
            this.logger.error((Object)"Could not parse VCAP_SERVICES", (Throwable)ex);
        }
        return properties;
    }

    private void extractPropertiesFromApplication(Properties properties, Map<String, Object> map) {
        if (map != null) {
            this.flatten(properties, map, "");
        }
    }

    private void extractPropertiesFromServices(Properties properties, Map<String, Object> map) {
        if (map != null) {
            for (Object services : map.values()) {
                List list = (List)services;
                for (Object object : list) {
                    Map service = (Map)object;
                    String key = (String)service.get("name");
                    if (key == null) {
                        key = (String)service.get("label");
                    }
                    this.flatten(properties, service, key);
                }
            }
        }
    }

    private void flatten(Properties properties, Map<String, Object> input, String path) {
        input.forEach((key, value) -> {
            String name = this.getPropertyName(path, (String)key);
            if (value instanceof Map) {
                this.flatten(properties, (Map)value, name);
            } else if (value instanceof Collection) {
                Collection collection = (Collection)value;
                properties.put(name, StringUtils.collectionToCommaDelimitedString((Collection)collection));
                int count = 0;
                for (Object item : collection) {
                    String itemKey = "[" + count++ + "]";
                    this.flatten(properties, Collections.singletonMap(itemKey, item), name);
                }
            } else if (value instanceof String) {
                properties.put(name, value);
            } else if (value instanceof Number) {
                properties.put(name, value.toString());
            } else if (value instanceof Boolean) {
                properties.put(name, value.toString());
            } else {
                properties.put(name, value != null ? value : "");
            }
        });
    }

    private String getPropertyName(String path, String key) {
        if (!StringUtils.hasText((String)path)) {
            return key;
        }
        if (key.startsWith("[")) {
            return path + key;
        }
        return path + "." + key;
    }
}

