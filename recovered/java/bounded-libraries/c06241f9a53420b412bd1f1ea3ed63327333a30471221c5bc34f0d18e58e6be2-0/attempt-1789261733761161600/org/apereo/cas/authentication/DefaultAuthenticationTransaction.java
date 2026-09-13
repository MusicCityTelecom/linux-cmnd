/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Service;

public class DefaultAuthenticationTransaction
implements AuthenticationTransaction {
    private static final long serialVersionUID = 6213904009424725484L;
    private final Service service;
    private final Collection<Credential> credentials;
    private final Collection<Authentication> authentications = new ArrayList<Authentication>();

    @Override
    @CanIgnoreReturnValue
    public AuthenticationTransaction collect(Collection<Authentication> authentications) {
        this.authentications.addAll(authentications);
        return this;
    }

    @Override
    public Optional<Credential> getPrimaryCredential() {
        return Objects.requireNonNull(this.credentials).stream().findFirst();
    }

    @Override
    public boolean hasCredentialOfType(Class<? extends Credential> type) {
        return Objects.requireNonNull(this.credentials).stream().anyMatch(type::isInstance);
    }

    @Override
    @Generated
    public Service getService() {
        return this.service;
    }

    @Override
    @Generated
    public Collection<Credential> getCredentials() {
        return this.credentials;
    }

    @Override
    @Generated
    public Collection<Authentication> getAuthentications() {
        return this.authentications;
    }

    @Generated
    public DefaultAuthenticationTransaction(Service service, Collection<Credential> credentials) {
        this.service = service;
        this.credentials = credentials;
    }

    @Generated
    public String toString() {
        return "DefaultAuthenticationTransaction(service=" + this.service + ", credentials=" + this.credentials + ", authentications=" + this.authentications + ")";
    }

    @Generated
    private DefaultAuthenticationTransaction() {
        this.service = null;
        this.credentials = null;
    }
}

