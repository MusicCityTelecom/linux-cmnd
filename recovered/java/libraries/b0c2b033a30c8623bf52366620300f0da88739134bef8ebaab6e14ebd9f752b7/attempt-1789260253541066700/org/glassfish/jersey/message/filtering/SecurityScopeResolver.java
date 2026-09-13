/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.inject.Singleton;
import org.glassfish.jersey.message.filtering.SecurityHelper;
import org.glassfish.jersey.message.filtering.spi.ScopeResolver;

@Singleton
final class SecurityScopeResolver
implements ScopeResolver {
    SecurityScopeResolver() {
    }

    @Override
    public Set<String> resolve(Annotation[] annotations) {
        return SecurityHelper.getFilteringScopes(annotations);
    }
}

