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
import org.glassfish.jersey.message.filtering.SecurityEntityProcessor;
import org.glassfish.jersey.message.filtering.SecurityScopeResolver;
import org.glassfish.jersey.message.filtering.SecurityServerScopeProvider;
import org.glassfish.jersey.message.filtering.SecurityServerScopeResolver;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public final class SecurityEntityFilteringFeature
implements Feature {
    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (!config.isRegistered(SecurityEntityProcessor.class)) {
            if (!config.isRegistered(RolesAllowedDynamicFeature.class)) {
                context.register(RolesAllowedDynamicFeature.class);
            }
            if (!config.isRegistered(EntityFilteringBinder.class)) {
                context.register(new EntityFilteringBinder());
            }
            context.register(SecurityEntityProcessor.class);
            if (!config.isRegistered(DefaultEntityProcessor.class)) {
                context.register(DefaultEntityProcessor.class);
            }
            context.register(SecurityScopeResolver.class);
            if (RuntimeType.SERVER.equals((Object)config.getRuntimeType())) {
                context.register(SecurityServerScopeResolver.class);
            }
            if (RuntimeType.SERVER == config.getRuntimeType()) {
                context.register(SecurityServerScopeProvider.class);
            } else {
                context.register(CommonScopeProvider.class);
            }
            return true;
        }
        return false;
    }
}

