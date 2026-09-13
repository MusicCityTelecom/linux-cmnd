/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.PropertiesPropertySource
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.loader;

import java.util.Map;
import java.util.Properties;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public abstract class BaseConfigurationPropertiesLoader {
    private final CipherExecutor<String, String> configurationCipherExecutor;
    private final String name;
    private final Resource resource;

    protected Map<String, Object> decryptProperties(Map properties) {
        return this.configurationCipherExecutor.decode(properties, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    protected PropertySource finalizeProperties(Map props) {
        return new MapPropertySource(this.getName(), props);
    }

    protected PropertySource finalizeProperties(Properties props) {
        return new PropertiesPropertySource(this.getName(), props);
    }

    public abstract PropertySource load();

    @Generated
    protected BaseConfigurationPropertiesLoader(CipherExecutor<String, String> configurationCipherExecutor, String name, Resource resource) {
        this.configurationCipherExecutor = configurationCipherExecutor;
        this.name = name;
        this.resource = resource;
    }

    @Generated
    public CipherExecutor<String, String> getConfigurationCipherExecutor() {
        return this.configurationCipherExecutor;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Resource getResource() {
        return this.resource;
    }
}

