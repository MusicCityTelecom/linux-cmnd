/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.logging;

import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.logging.LoggingFeature;

@Priority(value=2000)
public final class LoggingFeatureAutoDiscoverable
implements AutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        Map<String, Object> properties;
        if (!context.getConfiguration().isRegistered(LoggingFeature.class) && (this.commonPropertyConfigured(properties = context.getConfiguration().getProperties()) || context.getConfiguration().getRuntimeType() == RuntimeType.CLIENT && this.clientConfigured(properties) || context.getConfiguration().getRuntimeType() == RuntimeType.SERVER && this.serverConfigured(properties))) {
            context.register(LoggingFeature.class);
        }
    }

    private boolean commonPropertyConfigured(Map properties) {
        return properties.containsKey("jersey.config.logging.logger.name") || properties.containsKey("jersey.config.logging.logger.level") || properties.containsKey("jersey.config.logging.verbosity") || properties.containsKey("jersey.config.logging.entity.maxSize");
    }

    private boolean clientConfigured(Map properties) {
        return properties.containsKey("jersey.config.client.logging.logger.name") || properties.containsKey("jersey.config.client.logging.logger.level") || properties.containsKey("jersey.config.client.logging.verbosity") || properties.containsKey("jersey.config.client.logging.entity.maxSize");
    }

    private boolean serverConfigured(Map properties) {
        return properties.containsKey("jersey.config.server.logging.logger.name") || properties.containsKey("jersey.config.server.logging.logger.level") || properties.containsKey("jersey.config.server.logging.verbosity") || properties.containsKey("jersey.config.server.logging.entity.maxSize");
    }
}

