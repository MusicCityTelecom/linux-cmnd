/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.Optional;
import org.apereo.cas.authentication.Authentication;

public interface ContextualAuthenticationPolicy<T> {
    public T getContext();

    default public Optional<String> getCode() {
        return Optional.empty();
    }

    public boolean isSatisfiedBy(Authentication var1) throws Exception;
}

