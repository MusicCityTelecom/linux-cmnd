/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.util.Set;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface AuthenticationPolicyResolver
extends Ordered {
    public Set<AuthenticationPolicy> resolve(AuthenticationTransaction var1);

    default public int getOrder() {
        return Integer.MIN_VALUE;
    }

    default public boolean supports(AuthenticationTransaction transaction) {
        return transaction != null;
    }
}

