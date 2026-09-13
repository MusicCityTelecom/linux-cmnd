/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.context.properties.source;

import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.PropertyMapper;
import org.springframework.util.ObjectUtils;

final class DefaultPropertyMapper
implements PropertyMapper {
    public static final PropertyMapper INSTANCE = new DefaultPropertyMapper();
    private LastMapping<ConfigurationPropertyName, List<String>> lastMappedConfigurationPropertyName;
    private LastMapping<String, ConfigurationPropertyName> lastMappedPropertyName;

    private DefaultPropertyMapper() {
    }

    @Override
    public List<String> map(ConfigurationPropertyName configurationPropertyName) {
        LastMapping<ConfigurationPropertyName, List<String>> last = this.lastMappedConfigurationPropertyName;
        if (last != null && last.isFrom(configurationPropertyName)) {
            return last.getMapping();
        }
        String convertedName = configurationPropertyName.toString();
        List<String> mapping = Collections.singletonList(convertedName);
        this.lastMappedConfigurationPropertyName = new LastMapping<ConfigurationPropertyName, List<String>>(configurationPropertyName, mapping);
        return mapping;
    }

    @Override
    public ConfigurationPropertyName map(String propertySourceName) {
        LastMapping<String, ConfigurationPropertyName> last = this.lastMappedPropertyName;
        if (last != null && last.isFrom(propertySourceName)) {
            return last.getMapping();
        }
        ConfigurationPropertyName mapping = this.tryMap(propertySourceName);
        this.lastMappedPropertyName = new LastMapping<String, ConfigurationPropertyName>(propertySourceName, mapping);
        return mapping;
    }

    private ConfigurationPropertyName tryMap(String propertySourceName) {
        try {
            ConfigurationPropertyName convertedName = ConfigurationPropertyName.adapt(propertySourceName, '.');
            if (!convertedName.isEmpty()) {
                return convertedName;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ConfigurationPropertyName.EMPTY;
    }

    private static class LastMapping<T, M> {
        private final T from;
        private final M mapping;

        LastMapping(T from, M mapping) {
            this.from = from;
            this.mapping = mapping;
        }

        boolean isFrom(T from) {
            return ObjectUtils.nullSafeEquals(from, this.from);
        }

        M getMapping() {
            return this.mapping;
        }
    }
}

