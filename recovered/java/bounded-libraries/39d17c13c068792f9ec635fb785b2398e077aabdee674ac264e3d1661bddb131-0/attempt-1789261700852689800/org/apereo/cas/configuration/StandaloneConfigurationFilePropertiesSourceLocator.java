/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.env.CompositePropertySource
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 */
package org.apereo.cas.configuration;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator;
import org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Order(value=-2147483648)
public class StandaloneConfigurationFilePropertiesSourceLocator
implements CasConfigurationPropertiesSourceLocator {
    private final CasConfigurationPropertiesEnvironmentManager casConfigurationPropertiesEnvironmentManager;
    private final ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory;

    @Override
    public Optional<PropertySource<?>> locate(Environment environment, ResourceLoader resourceLoader) {
        CompositePropertySource compositePropertySource = new CompositePropertySource(this.getClass().getSimpleName());
        File configFile = this.casConfigurationPropertiesEnvironmentManager.getStandaloneProfileConfigurationFile(environment);
        if (configFile != null) {
            PropertySource<Map<String, Object>> sourceStandalone = this.loadSettingsFromStandaloneConfigFile(configFile);
            compositePropertySource.addPropertySource(sourceStandalone);
            return Optional.of(compositePropertySource);
        }
        return Optional.empty();
    }

    private PropertySource<Map<String, Object>> loadSettingsFromStandaloneConfigFile(File configFile) {
        return this.configurationPropertiesLoaderFactory.getLoader((Resource)new FileSystemResource(configFile), "standaloneConfigurationFileProperties").load();
    }

    @Generated
    public StandaloneConfigurationFilePropertiesSourceLocator(CasConfigurationPropertiesEnvironmentManager casConfigurationPropertiesEnvironmentManager, ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory) {
        this.casConfigurationPropertiesEnvironmentManager = casConfigurationPropertiesEnvironmentManager;
        this.configurationPropertiesLoaderFactory = configurationPropertiesLoaderFactory;
    }
}

