/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.policy.BaseAuthenticationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AtLeastOneCredentialValidatedAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AtLeastOneCredentialValidatedAuthenticationPolicy.class);
    private static final long serialVersionUID = -7484490540437793931L;
    private final boolean tryAll;

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authn, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) throws Exception {
        if (this.tryAll) {
            boolean match = authenticationHandlers.stream().allMatch(handler -> authn.getSuccesses().containsKey(handler.getName()));
            if (!match) {
                LOGGER.warn("Authentication handlers qualified to handle this transaction, [{}], have not all completed a successful authentication event. Successful authentication events recorded currently are [{}]", authenticationHandlers, authn.getSuccesses().keySet());
                return AuthenticationPolicyExecutionResult.failure();
            }
            LOGGER.debug("Authentication policy is satisfied with all authentication transactions");
            return AuthenticationPolicyExecutionResult.success((!authn.getSuccesses().isEmpty() ? 1 : 0) != 0);
        }
        if (!authn.getSuccesses().isEmpty()) {
            LOGGER.debug("Authentication policy is satisfied having found at least one authentication transactions");
            return AuthenticationPolicyExecutionResult.success();
        }
        LOGGER.warn("Authentication policy has failed to find a successful authentication transaction");
        return AuthenticationPolicyExecutionResult.failure();
    }

    @Generated
    public AtLeastOneCredentialValidatedAuthenticationPolicy() {
        this.tryAll = false;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AtLeastOneCredentialValidatedAuthenticationPolicy)) {
            return false;
        }
        AtLeastOneCredentialValidatedAuthenticationPolicy other = (AtLeastOneCredentialValidatedAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.tryAll == other.tryAll;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AtLeastOneCredentialValidatedAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + (this.tryAll ? 79 : 97);
        return result;
    }

    @Generated
    public boolean isTryAll() {
        return this.tryAll;
    }

    @Generated
    public AtLeastOneCredentialValidatedAuthenticationPolicy(boolean tryAll) {
        this.tryAll = tryAll;
    }
}

