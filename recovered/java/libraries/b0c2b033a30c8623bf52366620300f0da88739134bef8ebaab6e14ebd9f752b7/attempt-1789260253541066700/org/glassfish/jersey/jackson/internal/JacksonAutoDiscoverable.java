/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal;

import javax.annotation.Priority;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.jackson.JacksonFeature;

@Priority(value=2000)
public class JacksonAutoDiscoverable
implements AutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(JacksonFeature.class)) {
            context.register(JacksonFeature.class);
        }
    }
}

