/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Service;

public interface AuthenticationTransaction
extends Serializable {
    public AuthenticationTransaction collect(Collection<Authentication> var1);

    public Service getService();

    public Collection<Credential> getCredentials();

    public Collection<Authentication> getAuthentications();

    default public Optional<Credential> getPrimaryCredential() {
        return this.getCredentials().stream().findFirst();
    }

    default public boolean hasCredentialOfType(Class<? extends Credential> type) {
        return this.getCredentials().stream().anyMatch(type::isInstance);
    }

    default public <T extends Credential> Collection<T> getCredentialsOfType(Class<T> type) {
        return this.getCredentials().stream().filter(type::isInstance).map(c -> c).collect(Collectors.toList());
    }
}

