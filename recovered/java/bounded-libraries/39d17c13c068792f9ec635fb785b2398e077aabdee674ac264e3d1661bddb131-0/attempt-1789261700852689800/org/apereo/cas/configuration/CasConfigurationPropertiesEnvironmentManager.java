/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.support.RelaxedPropertyNames
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.RelaxedPropertyNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class CasConfigurationPropertiesEnvironmentManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasConfigurationPropertiesEnvironmentManager.class);
    public static final String PROPERTY_CAS_STANDALONE_CONFIGURATION_FILE = "cas.standalone.configuration-file";
    public static final String PROPERTY_CAS_STANDALONE_CONFIGURATION_DIRECTORY = "cas.standalone.configuration-directory";
    private static final File[] DEFAULT_CAS_CONFIG_DIRECTORIES = new File[]{new File("/etc/cas/config"), new File("/opt/cas/config"), new File("/var/cas/config")};
    private final ConfigurationPropertiesBindingPostProcessor binder;

    public static ApplicationContext rebindCasConfigurationProperties(ConfigurationPropertiesBindingPostProcessor binder, ApplicationContext applicationContext) {
        CasConfigurationProperties config = (CasConfigurationProperties)applicationContext.getBean(CasConfigurationProperties.class);
        String name = String.format("%s-%s", "cas", config.getClass().getName());
        binder.postProcessBeforeInitialization((Object)config, name);
        Object bean = applicationContext.getAutowireCapableBeanFactory().initializeBean((Object)config, name);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
        LOGGER.debug("Reloaded CAS configuration [{}]", (Object)name);
        return applicationContext;
    }

    public ApplicationContext rebindCasConfigurationProperties(ApplicationContext applicationContext) {
        return CasConfigurationPropertiesEnvironmentManager.rebindCasConfigurationProperties(this.binder, applicationContext);
    }

    public File getStandaloneProfileConfigurationDirectory(Environment environment) {
        LinkedHashSet<String> values = new LinkedHashSet<String>(RelaxedPropertyNames.forCamelCase((String)PROPERTY_CAS_STANDALONE_CONFIGURATION_DIRECTORY).getValues());
        values.add(PROPERTY_CAS_STANDALONE_CONFIGURATION_DIRECTORY);
        File file = values.stream().map(key -> (File)environment.getProperty(key, File.class)).filter(Objects::nonNull).findFirst().orElse(null);
        if (file != null && file.exists()) {
            LOGGER.trace("Received standalone configuration directory [{}]", (Object)file);
            return file;
        }
        return Arrays.stream(DEFAULT_CAS_CONFIG_DIRECTORIES).filter(File::exists).findFirst().orElse(null);
    }

    public File getStandaloneProfileConfigurationFile(Environment environment) {
        LinkedHashSet<String> values = new LinkedHashSet<String>(RelaxedPropertyNames.forCamelCase((String)PROPERTY_CAS_STANDALONE_CONFIGURATION_FILE).getValues());
        values.add(PROPERTY_CAS_STANDALONE_CONFIGURATION_FILE);
        return values.stream().map(key -> (File)environment.getProperty(key, File.class)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public String getApplicationName(Environment environment) {
        return environment.getProperty("spring.application.name", "cas");
    }

    public String getConfigurationName(Environment environment) {
        return environment.getProperty("spring.config.name", "cas");
    }

    @Generated
    public CasConfigurationPropertiesEnvironmentManager(ConfigurationPropertiesBindingPostProcessor binder) {
        this.binder = binder;
    }

    @Generated
    public ConfigurationPropertiesBindingPostProcessor getBinder() {
        return this.binder;
    }
}

