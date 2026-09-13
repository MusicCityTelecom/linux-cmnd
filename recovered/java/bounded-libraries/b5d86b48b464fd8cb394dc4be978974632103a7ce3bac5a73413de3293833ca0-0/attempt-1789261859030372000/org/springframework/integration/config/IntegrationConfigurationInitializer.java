/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 */
package org.springframework.integration.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@FunctionalInterface
public interface IntegrationConfigurationInitializer {
    public void initialize(ConfigurableListableBeanFactory var1) throws BeansException;
}

