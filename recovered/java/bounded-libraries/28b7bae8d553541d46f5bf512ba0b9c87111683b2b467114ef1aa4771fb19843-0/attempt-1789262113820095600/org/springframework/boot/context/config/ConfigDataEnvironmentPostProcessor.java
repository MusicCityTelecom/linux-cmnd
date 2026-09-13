/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironment;
import org.springframework.boot.context.config.ConfigDataEnvironmentUpdateListener;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.config.UseLegacyConfigProcessingException;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.log.LogMessage;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ConfigDataEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    public static final int ORDER = -2147483638;
    public static final String ON_LOCATION_NOT_FOUND_PROPERTY = "spring.config.on-not-found";
    private final DeferredLogFactory logFactory;
    private final Log logger;
    private final ConfigurableBootstrapContext bootstrapContext;
    private final ConfigDataEnvironmentUpdateListener environmentUpdateListener;

    public ConfigDataEnvironmentPostProcessor(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext) {
        this(logFactory, bootstrapContext, null);
    }

    public ConfigDataEnvironmentPostProcessor(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, ConfigDataEnvironmentUpdateListener environmentUpdateListener) {
        this.logFactory = logFactory;
        this.logger = logFactory.getLog(this.getClass());
        this.bootstrapContext = bootstrapContext;
        this.environmentUpdateListener = environmentUpdateListener;
    }

    public int getOrder() {
        return -2147483638;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        this.postProcessEnvironment(environment, application.getResourceLoader(), application.getAdditionalProfiles());
    }

    void postProcessEnvironment(ConfigurableEnvironment environment, ResourceLoader resourceLoader, Collection<String> additionalProfiles) {
        try {
            this.logger.trace((Object)"Post-processing environment to add config data");
            resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader();
            this.getConfigDataEnvironment(environment, resourceLoader, additionalProfiles).processAndApply();
        }
        catch (UseLegacyConfigProcessingException ex) {
            this.logger.debug((Object)LogMessage.format((String)"Switching to legacy config file processing [%s]", (Object)ex.getConfigurationProperty()));
            this.configureAdditionalProfiles(environment, additionalProfiles);
            this.postProcessUsingLegacyApplicationListener(environment, resourceLoader);
        }
    }

    ConfigDataEnvironment getConfigDataEnvironment(ConfigurableEnvironment environment, ResourceLoader resourceLoader, Collection<String> additionalProfiles) {
        return new ConfigDataEnvironment(this.logFactory, this.bootstrapContext, environment, resourceLoader, additionalProfiles, this.environmentUpdateListener);
    }

    private void configureAdditionalProfiles(ConfigurableEnvironment environment, Collection<String> additionalProfiles) {
        if (!CollectionUtils.isEmpty(additionalProfiles)) {
            LinkedHashSet<String> profiles = new LinkedHashSet<String>(additionalProfiles);
            profiles.addAll(Arrays.asList(environment.getActiveProfiles()));
            environment.setActiveProfiles(StringUtils.toStringArray(profiles));
        }
    }

    private void postProcessUsingLegacyApplicationListener(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
        this.getLegacyListener().addPropertySources(environment, resourceLoader);
    }

    LegacyConfigFileApplicationListener getLegacyListener() {
        return new LegacyConfigFileApplicationListener(this.logFactory.getLog(ConfigFileApplicationListener.class));
    }

    public static void applyTo(ConfigurableEnvironment environment) {
        ConfigDataEnvironmentPostProcessor.applyTo(environment, null, null, Collections.emptyList());
    }

    public static void applyTo(ConfigurableEnvironment environment, ResourceLoader resourceLoader, ConfigurableBootstrapContext bootstrapContext, String ... additionalProfiles) {
        ConfigDataEnvironmentPostProcessor.applyTo(environment, resourceLoader, bootstrapContext, Arrays.asList(additionalProfiles));
    }

    public static void applyTo(ConfigurableEnvironment environment, ResourceLoader resourceLoader, ConfigurableBootstrapContext bootstrapContext, Collection<String> additionalProfiles) {
        DeferredLogFactory logFactory = Supplier::get;
        bootstrapContext = bootstrapContext != null ? bootstrapContext : new DefaultBootstrapContext();
        ConfigDataEnvironmentPostProcessor postProcessor = new ConfigDataEnvironmentPostProcessor(logFactory, bootstrapContext);
        postProcessor.postProcessEnvironment(environment, resourceLoader, additionalProfiles);
    }

    public static void applyTo(ConfigurableEnvironment environment, ResourceLoader resourceLoader, ConfigurableBootstrapContext bootstrapContext, Collection<String> additionalProfiles, ConfigDataEnvironmentUpdateListener environmentUpdateListener) {
        DeferredLogFactory logFactory = Supplier::get;
        bootstrapContext = bootstrapContext != null ? bootstrapContext : new DefaultBootstrapContext();
        ConfigDataEnvironmentPostProcessor postProcessor = new ConfigDataEnvironmentPostProcessor(logFactory, bootstrapContext, environmentUpdateListener);
        postProcessor.postProcessEnvironment(environment, resourceLoader, additionalProfiles);
    }

    static class LegacyConfigFileApplicationListener
    extends ConfigFileApplicationListener {
        LegacyConfigFileApplicationListener(Log logger) {
            super(logger);
        }

        @Override
        public void addPropertySources(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
            super.addPropertySources(environment, resourceLoader);
        }
    }
}

