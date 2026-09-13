/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 *  org.yaml.snakeyaml.error.YAMLException
 */
package org.apereo.cas.configuration.loader;

import java.util.Map;
import java.util.Properties;
import lombok.Generated;
import org.apereo.cas.configuration.CasCoreConfigurationUtils;
import org.apereo.cas.configuration.loader.BaseConfigurationPropertiesLoader;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.error.YAMLException;

public class YamlConfigurationPropertiesLoader
extends BaseConfigurationPropertiesLoader {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(YamlConfigurationPropertiesLoader.class);

    public YamlConfigurationPropertiesLoader(CipherExecutor<String, String> configurationCipherExecutor, String name, Resource resource) {
        super(configurationCipherExecutor, name, resource);
    }

    @Override
    public PropertySource load() {
        Properties props = new Properties();
        if (ResourceUtils.doesResourceExist((Resource)this.getResource())) {
            try {
                Map<String, Object> pp = CasCoreConfigurationUtils.loadYamlProperties(this.getResource());
                if (pp.isEmpty()) {
                    LOGGER.debug("No properties were located inside [{}]", (Object)this.getResource());
                } else {
                    LOGGER.info("Found settings [{}] in YAML file [{}]", pp.keySet(), (Object)this.getResource());
                    props.putAll(this.decryptProperties(pp));
                }
            }
            catch (YAMLException e) {
                LOGGER.warn("Error parsing yaml configuration in [{}]: [{}]", (Object)this.getResource(), (Object)e.getMessage());
                throw e;
            }
        }
        return this.finalizeProperties(props);
    }
}

