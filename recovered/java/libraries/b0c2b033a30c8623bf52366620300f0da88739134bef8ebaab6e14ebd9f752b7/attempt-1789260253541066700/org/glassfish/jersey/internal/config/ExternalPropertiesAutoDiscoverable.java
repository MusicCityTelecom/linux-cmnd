/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.config;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.config.ExternalPropertiesConfigurationFeature;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

@ConstrainedTo(value=RuntimeType.CLIENT)
@Priority(value=2000)
public class ExternalPropertiesAutoDiscoverable
implements AutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(ExternalPropertiesConfigurationFeature.class)) {
            context.register(ExternalPropertiesConfigurationFeature.class);
        }
    }
}

