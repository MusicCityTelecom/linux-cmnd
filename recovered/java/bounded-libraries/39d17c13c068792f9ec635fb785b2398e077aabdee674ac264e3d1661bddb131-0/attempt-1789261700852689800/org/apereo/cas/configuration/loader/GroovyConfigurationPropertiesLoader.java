/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.util.ConfigObject
 *  groovy.util.ConfigSlurper
 *  lombok.Generated
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.loader;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Generated;
import org.apereo.cas.configuration.loader.BaseConfigurationPropertiesLoader;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class GroovyConfigurationPropertiesLoader
extends BaseConfigurationPropertiesLoader {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyConfigurationPropertiesLoader.class);
    private final List<String> applicationProfiles;

    public GroovyConfigurationPropertiesLoader(CipherExecutor<String, String> configurationCipherExecutor, String name, List<String> applicationProfiles, Resource resource) {
        super(configurationCipherExecutor, name, resource);
        this.applicationProfiles = applicationProfiles;
    }

    @Override
    public PropertySource load() {
        LinkedHashMap properties = new LinkedHashMap();
        ConfigSlurper slurper = new ConfigSlurper();
        this.applicationProfiles.forEach(Unchecked.consumer(profile -> {
            slurper.setEnvironment(profile);
            slurper.registerConditionalBlock("profiles", profile);
            Map bindings = CollectionUtils.wrap((String)"profile", (Object)profile, (String)"logger", (Object)LOGGER);
            slurper.setBinding(bindings);
            ConfigObject groovyConfig = slurper.parse(this.getResource().getURL());
            Properties pp = groovyConfig.toProperties();
            LOGGER.debug("Found settings [{}] in Groovy file [{}]", pp.keySet(), (Object)this.getResource());
            properties.putAll(pp);
        }));
        return this.finalizeProperties(this.decryptProperties(properties));
    }
}

