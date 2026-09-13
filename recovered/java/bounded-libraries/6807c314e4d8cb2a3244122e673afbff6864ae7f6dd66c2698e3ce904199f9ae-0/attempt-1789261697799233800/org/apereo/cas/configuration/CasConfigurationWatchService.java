/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager
 *  org.apereo.cas.support.events.AbstractCasEvent
 *  org.apereo.cas.support.events.config.CasConfigurationCreatedEvent
 *  org.apereo.cas.support.events.config.CasConfigurationDeletedEvent
 *  org.apereo.cas.support.events.config.CasConfigurationModifiedEvent
 *  org.apereo.cas.util.function.ComposableFunction
 *  org.apereo.cas.util.io.FileWatcherService
 *  org.apereo.cas.util.io.PathWatcherService
 *  org.apereo.cas.util.spring.CasEventListener
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.configuration;

import java.io.Closeable;
import java.io.File;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.cas.support.events.config.CasConfigurationCreatedEvent;
import org.apereo.cas.support.events.config.CasConfigurationDeletedEvent;
import org.apereo.cas.support.events.config.CasConfigurationModifiedEvent;
import org.apereo.cas.util.function.ComposableFunction;
import org.apereo.cas.util.io.FileWatcherService;
import org.apereo.cas.util.io.PathWatcherService;
import org.apereo.cas.util.spring.CasEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class CasConfigurationWatchService
implements Closeable,
CasEventListener,
InitializingBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasConfigurationWatchService.class);
    private final ComposableFunction<File, AbstractCasEvent> createConfigurationCreatedEvent = file -> new CasConfigurationCreatedEvent((Object)this, file.toPath());
    private final ComposableFunction<File, AbstractCasEvent> createConfigurationModifiedEvent = file -> new CasConfigurationModifiedEvent((Object)this, file.toPath());
    private final ComposableFunction<File, AbstractCasEvent> createConfigurationDeletedEvent = file -> new CasConfigurationDeletedEvent((Object)this, file.toPath());
    private final CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager;
    private final ConfigurableApplicationContext applicationContext;
    private PathWatcherService configurationDirectoryWatch;
    private FileWatcherService configurationFileWatch;

    @Override
    public void close() {
        this.closeWatchServices();
    }

    public void initialize() {
        this.watchConfigurationDirectoryIfNeeded();
        this.watchConfigurationFileIfNeeded();
    }

    public void afterPropertiesSet() throws Exception {
        this.initialize();
    }

    private void watchConfigurationFileIfNeeded() {
        ConfigurableEnvironment environment = this.applicationContext.getEnvironment();
        File configFile = this.configurationPropertiesEnvironmentManager.getStandaloneProfileConfigurationFile((Environment)environment);
        if (configFile != null && configFile.exists()) {
            LOGGER.debug("Starting to watch configuration file [{}]", (Object)configFile);
            this.configurationFileWatch = new FileWatcherService(configFile, this.createConfigurationCreatedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)), this.createConfigurationModifiedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)), this.createConfigurationDeletedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)));
            this.configurationFileWatch.start(configFile.getName());
        }
    }

    private void watchConfigurationDirectoryIfNeeded() {
        ConfigurableEnvironment environment = this.applicationContext.getEnvironment();
        File configDirectory = this.configurationPropertiesEnvironmentManager.getStandaloneProfileConfigurationDirectory((Environment)environment);
        if (configDirectory != null && configDirectory.exists()) {
            LOGGER.debug("Starting to watch configuration directory [{}]", (Object)configDirectory);
            this.configurationDirectoryWatch = new PathWatcherService(configDirectory.toPath(), this.createConfigurationCreatedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)), this.createConfigurationModifiedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)), this.createConfigurationDeletedEvent.andNext(arg_0 -> ((ConfigurableApplicationContext)this.applicationContext).publishEvent(arg_0)));
            this.configurationDirectoryWatch.start(configDirectory.getName());
        }
    }

    private void closeWatchServices() {
        if (this.configurationDirectoryWatch != null) {
            this.configurationDirectoryWatch.close();
        }
        if (this.configurationFileWatch != null) {
            this.configurationFileWatch.close();
        }
    }

    @Generated
    public CasConfigurationWatchService(CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager, ConfigurableApplicationContext applicationContext) {
        this.configurationPropertiesEnvironmentManager = configurationPropertiesEnvironmentManager;
        this.applicationContext = applicationContext;
    }
}

