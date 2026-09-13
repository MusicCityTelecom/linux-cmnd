/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.util.ArrayList;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import org.glassfish.jersey.internal.inject.AnnotationLiteral;

public final class SecurityAnnotations {
    public static RolesAllowed rolesAllowed(String ... roles) {
        ArrayList<String> list = new ArrayList<String>(roles.length);
        for (String role : roles) {
            if (role == null) continue;
            list.add(role);
        }
        return new RolesAllowedImpl(list.toArray(new String[list.size()]));
    }

    public static PermitAll permitAll() {
        return new PermitAllImpl();
    }

    public static DenyAll denyAll() {
        return new DenyAllImpl();
    }

    private SecurityAnnotations() {
    }

    private static class PermitAllImpl
    extends AnnotationLiteral<PermitAll>
    implements PermitAll {
        private PermitAllImpl() {
        }
    }

    private static final class DenyAllImpl
    extends AnnotationLiteral<DenyAll>
    implements DenyAll {
        private DenyAllImpl() {
        }
    }

    private static final class RolesAllowedImpl
    extends AnnotationLiteral<RolesAllowed>
    implements RolesAllowed {
        private final String[] roles;

        private RolesAllowedImpl(String[] roles) {
            this.roles = roles;
        }

        @Override
        public String[] value() {
            return this.roles;
        }
    }
}

