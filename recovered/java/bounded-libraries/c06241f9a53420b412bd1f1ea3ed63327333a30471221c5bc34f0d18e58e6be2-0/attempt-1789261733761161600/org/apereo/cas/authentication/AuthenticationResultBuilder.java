/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.Service;

public interface AuthenticationResultBuilder
extends Serializable {
    public Set<Authentication> getAuthentications();

    public Optional<Authentication> getInitialAuthentication();

    public Optional<Credential> getInitialCredential();

    public AuthenticationResultBuilder collect(Authentication var1);

    public AuthenticationResultBuilder collect(Collection<Authentication> var1);

    public AuthenticationResultBuilder collect(Credential var1);

    public AuthenticationResultBuilder collect(CredentialMetaData var1);

    public AuthenticationResult build(PrincipalElectionStrategy var1);

    public AuthenticationResult build(PrincipalElectionStrategy var1, Service var2);
}

