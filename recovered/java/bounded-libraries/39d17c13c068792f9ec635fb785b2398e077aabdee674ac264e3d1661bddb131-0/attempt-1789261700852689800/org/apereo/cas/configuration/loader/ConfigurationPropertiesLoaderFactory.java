/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.loader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.loader.BaseConfigurationPropertiesLoader;
import org.apereo.cas.configuration.loader.GroovyConfigurationPropertiesLoader;
import org.apereo.cas.configuration.loader.SimpleConfigurationPropertiesLoader;
import org.apereo.cas.configuration.loader.YamlConfigurationPropertiesLoader;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

public class ConfigurationPropertiesLoaderFactory {
    private final CipherExecutor<String, String> configurationCipherExecutor;
    private final Environment environment;

    public BaseConfigurationPropertiesLoader getLoader(Resource resource, String name) {
        String filename = StringUtils.defaultString((String)resource.getFilename()).toLowerCase();
        if (filename.endsWith(".properties")) {
            return new SimpleConfigurationPropertiesLoader(this.configurationCipherExecutor, name, resource);
        }
        if (filename.endsWith(".groovy")) {
            return new GroovyConfigurationPropertiesLoader(this.configurationCipherExecutor, name, ConfigurationPropertiesLoaderFactory.getApplicationProfiles(this.environment), resource);
        }
        if (filename.endsWith(".yaml") || filename.endsWith(".yml")) {
            return new YamlConfigurationPropertiesLoader(this.configurationCipherExecutor, name, resource);
        }
        throw new IllegalArgumentException("Unable to determine configuration loader for " + resource);
    }

    public static List<String> getApplicationProfiles(Environment environment) {
        return Arrays.stream(environment.getActiveProfiles()).collect(Collectors.toList());
    }

    @Generated
    public ConfigurationPropertiesLoaderFactory(CipherExecutor<String, String> configurationCipherExecutor, Environment environment) {
        this.configurationCipherExecutor = configurationCipherExecutor;
        this.environment = environment;
    }
}

