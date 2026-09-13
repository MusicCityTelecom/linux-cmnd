/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import javax.annotation.Priority;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.media.sse.SseFeature;

@Priority(value=2000)
public final class SseAutoDiscoverable
implements ForcedAutoDiscoverable {
    @Override
    public void configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (context.getConfiguration().isRegistered(SseFeature.class)) {
            return;
        }
        if (!PropertiesHelper.getValue(config.getProperties(), config.getRuntimeType(), "jersey.config.media.sse.disable", Boolean.FALSE, Boolean.class, null).booleanValue()) {
            context.register(SseFeature.class);
        }
    }
}

