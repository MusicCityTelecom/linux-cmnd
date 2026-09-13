/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurablePropertyResolver
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.StandardEnvironment
 */
package org.springframework.boot;

import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

class ApplicationEnvironment
extends StandardEnvironment {
    ApplicationEnvironment() {
    }

    protected String doGetActiveProfilesProperty() {
        return null;
    }

    protected String doGetDefaultProfilesProperty() {
        return null;
    }

    protected ConfigurablePropertyResolver createPropertyResolver(MutablePropertySources propertySources) {
        return ConfigurationPropertySources.createPropertyResolver(propertySources);
    }
}

