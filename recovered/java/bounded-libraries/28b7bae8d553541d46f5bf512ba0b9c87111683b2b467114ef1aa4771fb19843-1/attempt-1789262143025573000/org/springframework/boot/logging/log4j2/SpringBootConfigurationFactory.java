/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.core.LoggerContext
 *  org.apache.logging.log4j.core.config.Configuration
 *  org.apache.logging.log4j.core.config.ConfigurationFactory
 *  org.apache.logging.log4j.core.config.ConfigurationSource
 *  org.apache.logging.log4j.core.config.DefaultConfiguration
 *  org.apache.logging.log4j.core.config.Order
 *  org.apache.logging.log4j.core.config.plugins.Plugin
 */
package org.springframework.boot.logging.log4j2;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;

@Plugin(name="SpringBootConfigurationFactory", category="ConfigurationFactory")
@Order(value=0)
public class SpringBootConfigurationFactory
extends ConfigurationFactory {
    private static final String[] TYPES = new String[]{".springboot"};

    protected String[] getSupportedTypes() {
        return TYPES;
    }

    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        if (source == null || source == ConfigurationSource.NULL_SOURCE) {
            return null;
        }
        return new DefaultConfiguration();
    }
}

