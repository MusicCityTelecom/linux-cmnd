/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.message.filtering.CommonScopeProvider;
import org.glassfish.jersey.message.filtering.DefaultEntityProcessor;
import org.glassfish.jersey.message.filtering.EntityFilteringBinder;
import org.glassfish.jersey.message.filtering.EntityFilteringProcessor;
import org.glassfish.jersey.message.filtering.EntityFilteringScopeResolver;
import org.glassfish.jersey.message.filtering.SecurityEntityFilteringFeature;
import org.glassfish.jersey.message.filtering.SelectableEntityFilteringFeature;
import org.glassfish.jersey.message.filtering.ServerScopeProvider;

public final class EntityFilteringFeature
implements Feature {
    public static final String ENTITY_FILTERING_SCOPE = "jersey.config.entityFiltering.scope";

    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (!config.isRegistered(EntityFilteringProcessor.class)) {
            if (!config.isRegistered(EntityFilteringBinder.class)) {
                context.register(new EntityFilteringBinder());
            }
            context.register(EntityFilteringProcessor.class);
            if (!config.isRegistered(DefaultEntityProcessor.class)) {
                context.register(DefaultEntityProcessor.class);
            }
            context.register(EntityFilteringScopeResolver.class);
            if (RuntimeType.SERVER == config.getRuntimeType()) {
                context.register(ServerScopeProvider.class);
            } else {
                context.register(CommonScopeProvider.class);
            }
            return true;
        }
        return false;
    }

    public static boolean enabled(Configuration config) {
        return config.isRegistered(EntityFilteringFeature.class) || config.isRegistered(SecurityEntityFilteringFeature.class) || config.isRegistered(SelectableEntityFilteringFeature.class);
    }
}

