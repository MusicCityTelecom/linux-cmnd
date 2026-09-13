/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationResultBuilderFactory
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.AuthenticationTransactionFactory
 *  org.apereo.cas.authentication.AuthenticationTransactionManager
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.AuthenticationTransactionFactory;
import org.apereo.cas.authentication.AuthenticationTransactionManager;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.Service;

public class DefaultAuthenticationSystemSupport
implements AuthenticationSystemSupport {
    private final AuthenticationTransactionManager authenticationTransactionManager;
    private final PrincipalElectionStrategy principalElectionStrategy;
    private final AuthenticationResultBuilderFactory authenticationResultBuilderFactory;
    private final AuthenticationTransactionFactory authenticationTransactionFactory;

    public AuthenticationResultBuilder handleInitialAuthenticationTransaction(Service service, Credential ... credential) throws AuthenticationException {
        AuthenticationResultBuilder builder = this.authenticationResultBuilderFactory.newBuilder();
        if (credential != null) {
            Stream.of(credential).filter(Objects::nonNull).forEach(arg_0 -> ((AuthenticationResultBuilder)builder).collect(arg_0));
        }
        return this.handleAuthenticationTransaction(service, builder, credential);
    }

    public AuthenticationResultBuilder establishAuthenticationContextFromInitial(Authentication authentication, Credential credentials) {
        return this.establishAuthenticationContextFromInitial(authentication).collect(credentials);
    }

    public AuthenticationResultBuilder establishAuthenticationContextFromInitial(Authentication authentication) {
        return this.authenticationResultBuilderFactory.newBuilder().collect(authentication);
    }

    public AuthenticationResultBuilder handleAuthenticationTransaction(Service service, AuthenticationResultBuilder authenticationResultBuilder, Credential ... credentials) throws AuthenticationException {
        AuthenticationTransaction transaction = this.authenticationTransactionFactory.newTransaction(service, credentials);
        transaction.collect((Collection)authenticationResultBuilder.getAuthentications());
        this.authenticationTransactionManager.handle(transaction, authenticationResultBuilder);
        return authenticationResultBuilder;
    }

    public AuthenticationResult finalizeAllAuthenticationTransactions(@NonNull AuthenticationResultBuilder authenticationResultBuilder, Service service) {
        if (authenticationResultBuilder == null) {
            throw new NullPointerException("authenticationResultBuilder is marked non-null but is null");
        }
        return authenticationResultBuilder.build(this.principalElectionStrategy, service);
    }

    public AuthenticationResult finalizeAuthenticationTransaction(Service service, Credential ... credential) throws AuthenticationException {
        return this.finalizeAllAuthenticationTransactions(this.handleInitialAuthenticationTransaction(service, credential), service);
    }

    @Generated
    public AuthenticationTransactionManager getAuthenticationTransactionManager() {
        return this.authenticationTransactionManager;
    }

    @Generated
    public PrincipalElectionStrategy getPrincipalElectionStrategy() {
        return this.principalElectionStrategy;
    }

    @Generated
    public AuthenticationResultBuilderFactory getAuthenticationResultBuilderFactory() {
        return this.authenticationResultBuilderFactory;
    }

    @Generated
    public AuthenticationTransactionFactory getAuthenticationTransactionFactory() {
        return this.authenticationTransactionFactory;
    }

    @Generated
    public DefaultAuthenticationSystemSupport(AuthenticationTransactionManager authenticationTransactionManager, PrincipalElectionStrategy principalElectionStrategy, AuthenticationResultBuilderFactory authenticationResultBuilderFactory, AuthenticationTransactionFactory authenticationTransactionFactory) {
        this.authenticationTransactionManager = authenticationTransactionManager;
        this.principalElectionStrategy = principalElectionStrategy;
        this.authenticationResultBuilderFactory = authenticationResultBuilderFactory;
        this.authenticationTransactionFactory = authenticationTransactionFactory;
    }
}

