/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.message.filtering.SecurityHelper;
import org.glassfish.jersey.message.filtering.ServerScopeProvider;

@Singleton
@ConstrainedTo(value=RuntimeType.SERVER)
final class SecurityServerScopeProvider
extends ServerScopeProvider {
    @Context
    private SecurityContext securityContext;

    @Inject
    public SecurityServerScopeProvider(Configuration config, InjectionManager injectionManager) {
        super(config, injectionManager);
    }

    @Override
    public Set<String> getFilteringScopes(Annotation[] entityAnnotations, boolean defaultIfNotFound) {
        Set<String> filteringScope = super.getFilteringScopes(entityAnnotations, false);
        if (filteringScope.isEmpty()) {
            filteringScope = new HashSet<String>();
            for (String role : SecurityHelper.getProcessedRoles()) {
                if (!this.securityContext.isUserInRole(role)) continue;
                filteringScope.add(SecurityHelper.getRolesAllowedScope(role));
            }
        }
        return this.returnFilteringScopes(filteringScope, defaultIfNotFound);
    }
}

