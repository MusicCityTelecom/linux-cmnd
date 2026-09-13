/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

final class UseLegacyConfigProcessingException
extends ConfigDataException {
    static final ConfigurationPropertyName PROPERTY_NAME = ConfigurationPropertyName.of("spring.config.use-legacy-processing");
    private static final Bindable<Boolean> BOOLEAN = Bindable.of(Boolean.class);
    private static final UseLegacyProcessingBindHandler BIND_HANDLER = new UseLegacyProcessingBindHandler();
    private final ConfigurationProperty configurationProperty;

    UseLegacyConfigProcessingException(ConfigurationProperty configurationProperty) {
        super("Legacy processing requested from " + configurationProperty, null);
        this.configurationProperty = configurationProperty;
    }

    ConfigurationProperty getConfigurationProperty() {
        return this.configurationProperty;
    }

    static void throwIfRequested(Binder binder) {
        try {
            binder.bind(PROPERTY_NAME, BOOLEAN, (BindHandler)BIND_HANDLER);
        }
        catch (BindException ex) {
            if (ex.getCause() instanceof UseLegacyConfigProcessingException) {
                throw (UseLegacyConfigProcessingException)ex.getCause();
            }
            throw ex;
        }
    }

    private static class UseLegacyProcessingBindHandler
    implements BindHandler {
        private UseLegacyProcessingBindHandler() {
        }

        @Override
        public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
            if (Boolean.TRUE.equals(result)) {
                throw new UseLegacyConfigProcessingException(context.getConfigurationProperty());
            }
            return result;
        }
    }
}

