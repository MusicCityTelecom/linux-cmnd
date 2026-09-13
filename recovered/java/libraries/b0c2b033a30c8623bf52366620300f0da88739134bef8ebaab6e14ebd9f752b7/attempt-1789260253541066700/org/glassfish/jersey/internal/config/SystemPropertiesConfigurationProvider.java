/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.config;

import java.util.Map;
import org.glassfish.jersey.internal.config.SystemPropertiesConfigurationModel;
import org.glassfish.jersey.spi.ExternalConfigurationModel;
import org.glassfish.jersey.spi.ExternalConfigurationProvider;

class SystemPropertiesConfigurationProvider
implements ExternalConfigurationProvider {
    private final SystemPropertiesConfigurationModel model = new SystemPropertiesConfigurationModel();

    SystemPropertiesConfigurationProvider() {
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.model.getProperties();
    }

    @Override
    public ExternalConfigurationModel getConfiguration() {
        return this.model;
    }

    @Override
    public ExternalConfigurationModel merge(ExternalConfigurationModel input) {
        return input == null ? this.model : this.model.mergeProperties(input.getProperties());
    }
}

