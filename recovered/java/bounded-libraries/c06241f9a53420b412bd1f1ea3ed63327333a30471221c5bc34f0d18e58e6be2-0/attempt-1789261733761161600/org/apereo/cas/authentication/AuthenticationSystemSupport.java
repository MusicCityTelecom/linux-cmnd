/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.AuthenticationTransactionFactory;
import org.apereo.cas.authentication.AuthenticationTransactionManager;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.Service;

public interface AuthenticationSystemSupport {
    public static final String BEAN_NAME = "defaultAuthenticationSystemSupport";

    public AuthenticationTransactionManager getAuthenticationTransactionManager();

    public PrincipalElectionStrategy getPrincipalElectionStrategy();

    public AuthenticationTransactionFactory getAuthenticationTransactionFactory();

    public AuthenticationResultBuilderFactory getAuthenticationResultBuilderFactory();

    public AuthenticationResultBuilder establishAuthenticationContextFromInitial(Authentication var1, Credential var2);

    public AuthenticationResultBuilder establishAuthenticationContextFromInitial(Authentication var1);

    public AuthenticationResultBuilder handleInitialAuthenticationTransaction(Service var1, Credential ... var2) throws AuthenticationException;

    public AuthenticationResultBuilder handleAuthenticationTransaction(Service var1, AuthenticationResultBuilder var2, Credential ... var3) throws AuthenticationException;

    public AuthenticationResult finalizeAllAuthenticationTransactions(AuthenticationResultBuilder var1, Service var2);

    public AuthenticationResult finalizeAuthenticationTransaction(Service var1, Credential ... var2) throws AuthenticationException;

    default public AuthenticationResult finalizeAuthenticationTransaction(Service service, Collection<Credential> credentials) throws AuthenticationException {
        return this.finalizeAuthenticationTransaction(service, (Credential[])credentials.toArray(Credential[]::new));
    }

    default public AuthenticationResult finalizeAuthenticationTransaction(Credential ... credentials) throws AuthenticationException {
        return this.finalizeAuthenticationTransaction((Service)null, credentials);
    }
}

