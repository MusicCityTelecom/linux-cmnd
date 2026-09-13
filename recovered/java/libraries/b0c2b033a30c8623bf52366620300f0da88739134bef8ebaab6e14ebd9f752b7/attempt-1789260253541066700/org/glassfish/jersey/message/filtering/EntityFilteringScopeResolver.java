/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.inject.Singleton;
import org.glassfish.jersey.message.filtering.EntityFilteringHelper;
import org.glassfish.jersey.message.filtering.spi.ScopeResolver;

@Singleton
final class EntityFilteringScopeResolver
implements ScopeResolver {
    EntityFilteringScopeResolver() {
    }

    @Override
    public Set<String> resolve(Annotation[] annotations) {
        return EntityFilteringHelper.getFilteringScopes(annotations);
    }
}

