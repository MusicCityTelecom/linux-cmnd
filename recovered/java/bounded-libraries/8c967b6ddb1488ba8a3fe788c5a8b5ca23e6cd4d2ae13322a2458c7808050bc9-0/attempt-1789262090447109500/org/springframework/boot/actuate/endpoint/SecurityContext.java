/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import java.security.Principal;

public interface SecurityContext {
    public static final SecurityContext NONE = new SecurityContext(){

        @Override
        public Principal getPrincipal() {
            return null;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }
    };

    public Principal getPrincipal();

    public boolean isUserInRole(String var1);
}

