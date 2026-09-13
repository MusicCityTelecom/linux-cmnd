/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.loader;

import java.io.InputStream;
import java.util.Properties;
import lombok.Generated;
import org.apereo.cas.configuration.loader.BaseConfigurationPropertiesLoader;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class SimpleConfigurationPropertiesLoader
extends BaseConfigurationPropertiesLoader {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigurationPropertiesLoader.class);

    public SimpleConfigurationPropertiesLoader(CipherExecutor<String, String> configurationCipherExecutor, String name, Resource resource) {
        super(configurationCipherExecutor, name, resource);
    }

    @Override
    public PropertySource load() {
        Properties props = new Properties();
        try (InputStream is = this.getResource().getInputStream();){
            LOGGER.debug("Located CAS configuration file at [{}]", (Object)this.getResource());
            props.load(is);
            LOGGER.debug("Found settings [{}] in file [{}]", props.keySet(), (Object)this.getResource());
            props.putAll(this.decryptProperties(props));
        }
        catch (Exception e) {
            LoggingUtils.warn((Logger)LOGGER, (Throwable)e);
        }
        return this.finalizeProperties(props);
    }
}

