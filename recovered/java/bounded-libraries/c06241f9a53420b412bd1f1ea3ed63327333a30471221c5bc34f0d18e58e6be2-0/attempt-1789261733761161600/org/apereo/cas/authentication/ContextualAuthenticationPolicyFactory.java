/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.ContextualAuthenticationPolicy;

@FunctionalInterface
public interface ContextualAuthenticationPolicyFactory<T> {
    public ContextualAuthenticationPolicy<T> createPolicy(T var1);
}

