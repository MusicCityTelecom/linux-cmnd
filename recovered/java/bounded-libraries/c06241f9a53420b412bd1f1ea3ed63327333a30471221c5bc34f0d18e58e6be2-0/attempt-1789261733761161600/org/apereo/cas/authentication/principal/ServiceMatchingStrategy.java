/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.principal;

import org.apereo.cas.authentication.principal.Service;

@FunctionalInterface
public interface ServiceMatchingStrategy {
    public boolean matches(Service var1, Service var2);

    public static ServiceMatchingStrategy alwaysMatches() {
        return (service, matchService) -> true;
    }

    public static ServiceMatchingStrategy neverMatches() {
        return (service, matchService) -> false;
    }
}

