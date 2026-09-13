/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.principal.Principal;

@FunctionalInterface
public interface PrincipalElectionStrategyConflictResolver {
    public static PrincipalElectionStrategyConflictResolver last() {
        return (principals, attributes) -> ((Principal)principals.get(principals.size() - 1)).getId();
    }

    public static PrincipalElectionStrategyConflictResolver first() {
        return (principals, attributes) -> ((Principal)principals.get(0)).getId();
    }

    public String resolve(List<Principal> var1, Map<String, List<Object>> var2);
}

