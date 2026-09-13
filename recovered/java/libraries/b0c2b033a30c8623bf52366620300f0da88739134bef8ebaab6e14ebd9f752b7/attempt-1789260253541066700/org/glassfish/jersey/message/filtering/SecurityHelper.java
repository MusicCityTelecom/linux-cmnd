/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.message.filtering.spi.FilteringHelper;

final class SecurityHelper {
    private static final Set<String> roles = new HashSet<String>();

    static Set<String> getFilteringScopes(Annotation[] annotations) {
        return SecurityHelper.getFilteringScopes(null, annotations);
    }

    static Set<String> getFilteringScopes(SecurityContext securityContext, Annotation[] annotations) {
        if (annotations.length == 0) {
            return Collections.emptySet();
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof RolesAllowed) {
                HashSet<String> bindings = new HashSet<String>();
                for (String role : ((RolesAllowed)annotation).value()) {
                    if (securityContext != null && !securityContext.isUserInRole(role)) continue;
                    bindings.add(SecurityHelper.getRolesAllowedScope(role));
                }
                return bindings;
            }
            if (annotation instanceof PermitAll) {
                return FilteringHelper.getDefaultFilteringScope();
            }
            if (!(annotation instanceof DenyAll)) continue;
            return null;
        }
        return Collections.emptySet();
    }

    static String getRolesAllowedScope(String role) {
        roles.add(role);
        return RolesAllowed.class.getName() + "_" + role;
    }

    static Set<String> getProcessedRoles() {
        return roles;
    }

    private SecurityHelper() {
    }
}

