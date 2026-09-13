/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationManager
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.AuthenticationTransactionManager
 *  org.apereo.cas.support.events.authentication.CasAuthenticationTransactionCompletedEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 */
package org.apereo.cas.authentication;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationManager;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.AuthenticationTransactionManager;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultAuthenticationTransactionManager
implements AuthenticationTransactionManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationTransactionManager.class);
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;

    @CanIgnoreReturnValue
    public AuthenticationTransactionManager handle(@NonNull AuthenticationTransaction authenticationTransaction, @NonNull AuthenticationResultBuilder authenticationResult) throws AuthenticationException {
        if (authenticationTransaction == null) {
            throw new NullPointerException("authenticationTransaction is marked non-null but is null");
        }
        if (authenticationResult == null) {
            throw new NullPointerException("authenticationResult is marked non-null but is null");
        }
        if (authenticationTransaction.getCredentials().isEmpty()) {
            LOGGER.debug("Transaction ignored since there are no credentials to authenticate");
        } else {
            Authentication authentication = this.authenticationManager.authenticate(authenticationTransaction);
            LOGGER.trace("Successful authentication; Collecting authentication result [{}]", (Object)authentication);
            this.publishEvent((ApplicationEvent)new CasAuthenticationTransactionCompletedEvent((Object)this, authentication));
            authenticationResult.collect(authentication);
        }
        return this;
    }

    private void publishEvent(ApplicationEvent event) {
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(event);
        }
    }

    @Generated
    public ApplicationEventPublisher getEventPublisher() {
        return this.eventPublisher;
    }

    @Generated
    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    @Generated
    public DefaultAuthenticationTransactionManager(ApplicationEventPublisher eventPublisher, AuthenticationManager authenticationManager) {
        this.eventPublisher = eventPublisher;
        this.authenticationManager = authenticationManager;
    }
}

