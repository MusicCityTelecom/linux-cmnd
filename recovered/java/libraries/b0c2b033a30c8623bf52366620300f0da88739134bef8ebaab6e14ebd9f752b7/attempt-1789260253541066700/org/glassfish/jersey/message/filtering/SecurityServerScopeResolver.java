/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.message.filtering.SecurityHelper;
import org.glassfish.jersey.message.filtering.spi.ScopeResolver;

@Singleton
@Priority(value=4100)
@ConstrainedTo(value=RuntimeType.SERVER)
final class SecurityServerScopeResolver
implements ScopeResolver {
    @Context
    private SecurityContext securityContext;

    SecurityServerScopeResolver() {
    }

    @Override
    public Set<String> resolve(Annotation[] annotations) {
        return SecurityHelper.getFilteringScopes(this.securityContext, annotations);
    }
}

