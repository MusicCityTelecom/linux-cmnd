/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.UrlResource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

final class AutoConfigurationMetadataLoader {
    protected static final String PATH = "META-INF/spring-autoconfigure-metadata.properties";

    private AutoConfigurationMetadataLoader() {
    }

    static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
        return AutoConfigurationMetadataLoader.loadMetadata(classLoader, PATH);
    }

    static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
        try {
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources(path) : ClassLoader.getSystemResources(path);
            Properties properties = new Properties();
            while (urls.hasMoreElements()) {
                properties.putAll((Map<?, ?>)PropertiesLoaderUtils.loadProperties((Resource)new UrlResource(urls.nextElement())));
            }
            return AutoConfigurationMetadataLoader.loadMetadata(properties);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load @ConditionalOnClass location [" + path + "]", ex);
        }
    }

    static AutoConfigurationMetadata loadMetadata(Properties properties) {
        return new PropertiesAutoConfigurationMetadata(properties);
    }

    private static class PropertiesAutoConfigurationMetadata
    implements AutoConfigurationMetadata {
        private final Properties properties;

        PropertiesAutoConfigurationMetadata(Properties properties) {
            this.properties = properties;
        }

        @Override
        public boolean wasProcessed(String className) {
            return this.properties.containsKey(className);
        }

        @Override
        public Integer getInteger(String className, String key) {
            return this.getInteger(className, key, null);
        }

        @Override
        public Integer getInteger(String className, String key, Integer defaultValue) {
            String value = this.get(className, key);
            return value != null ? Integer.valueOf(value) : defaultValue;
        }

        @Override
        public Set<String> getSet(String className, String key) {
            return this.getSet(className, key, null);
        }

        @Override
        public Set<String> getSet(String className, String key, Set<String> defaultValue) {
            String value = this.get(className, key);
            return value != null ? StringUtils.commaDelimitedListToSet((String)value) : defaultValue;
        }

        @Override
        public String get(String className, String key) {
            return this.get(className, key, null);
        }

        @Override
        public String get(String className, String key, String defaultValue) {
            String value = this.properties.getProperty(className + "." + key);
            return value != null ? value : defaultValue;
        }
    }
}

