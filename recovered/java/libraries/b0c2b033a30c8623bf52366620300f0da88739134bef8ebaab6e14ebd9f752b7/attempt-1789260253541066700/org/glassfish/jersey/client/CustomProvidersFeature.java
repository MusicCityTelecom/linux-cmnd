/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.Collection;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class CustomProvidersFeature
implements Feature {
    private final Collection<Class<?>> providers;

    public CustomProvidersFeature(Collection<Class<?>> providers) {
        this.providers = providers;
    }

    @Override
    public boolean configure(FeatureContext context) {
        for (Class<?> provider : this.providers) {
            context.register(provider);
        }
        return true;
    }
}

