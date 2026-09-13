/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySources
 *  org.springframework.boot.info.InfoProperties
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.info;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.info.InfoProperties;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public abstract class InfoPropertiesInfoContributor<T extends InfoProperties>
implements InfoContributor {
    private static final Bindable<Map<String, Object>> STRING_OBJECT_MAP = Bindable.mapOf(String.class, Object.class);
    private final T properties;
    private final Mode mode;

    protected InfoPropertiesInfoContributor(T properties, Mode mode) {
        this.properties = properties;
        this.mode = mode;
    }

    protected final T getProperties() {
        return this.properties;
    }

    protected final Mode getMode() {
        return this.mode;
    }

    protected abstract PropertySource<?> toSimplePropertySource();

    protected Map<String, Object> generateContent() {
        Map<String, Object> content = this.extractContent(this.toPropertySource());
        this.postProcessContent(content);
        return content;
    }

    protected Map<String, Object> extractContent(PropertySource<?> propertySource) {
        return (Map)new Binder(ConfigurationPropertySources.from(propertySource)).bind("", STRING_OBJECT_MAP).orElseGet(LinkedHashMap::new);
    }

    protected void postProcessContent(Map<String, Object> content) {
    }

    protected PropertySource<?> toPropertySource() {
        if (this.mode.equals((Object)Mode.FULL)) {
            return this.properties.toPropertySource();
        }
        return this.toSimplePropertySource();
    }

    protected void copyIfSet(Properties target, String key) {
        String value = this.properties.get(key);
        if (StringUtils.hasText((String)value)) {
            target.put(key, value);
        }
    }

    protected void replaceValue(Map<String, Object> content, String key, Object value) {
        if (content.containsKey(key) && value != null) {
            content.put(key, value);
        }
    }

    protected Map<String, Object> getNestedMap(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return Collections.emptyMap();
        }
        return (Map)value;
    }

    public static enum Mode {
        FULL,
        SIMPLE;

    }
}

