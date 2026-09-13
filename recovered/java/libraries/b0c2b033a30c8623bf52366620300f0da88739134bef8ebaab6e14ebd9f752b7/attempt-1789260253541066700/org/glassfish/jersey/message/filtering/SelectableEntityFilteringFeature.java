/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.message.filtering.SelectableEntityProcessor;
import org.glassfish.jersey.message.filtering.SelectableScopeResolver;

public final class SelectableEntityFilteringFeature
implements Feature {
    public static final String QUERY_PARAM_NAME = "jersey.config.entityFiltering.selectable.query";

    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (!config.isRegistered(SelectableEntityProcessor.class)) {
            if (!config.isRegistered(EntityFilteringFeature.class)) {
                context.register(EntityFilteringFeature.class);
            }
            context.register(SelectableEntityProcessor.class);
            context.register(SelectableScopeResolver.class);
            return true;
        }
        return true;
    }
}

