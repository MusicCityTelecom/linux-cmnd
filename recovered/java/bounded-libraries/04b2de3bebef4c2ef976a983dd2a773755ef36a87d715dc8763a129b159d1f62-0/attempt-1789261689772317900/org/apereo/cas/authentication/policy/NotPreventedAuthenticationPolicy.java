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
 *  org.apereo.cas.authentication.PreventedException
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
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.policy.AtLeastOneCredentialValidatedAuthenticationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class NotPreventedAuthenticationPolicy
extends AtLeastOneCredentialValidatedAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(NotPreventedAuthenticationPolicy.class);
    private static final long serialVersionUID = -591956246302374794L;

    public NotPreventedAuthenticationPolicy() {
        super(true);
    }

    @Override
    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authentication, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) throws Exception {
        boolean fail = authentication.getFailures().values().stream().anyMatch(failure -> failure.getClass().isAssignableFrom(PreventedException.class));
        if (fail) {
            LOGGER.warn("Authentication policy has failed given at least one authentication failure is found to prevent authentication");
            return AuthenticationPolicyExecutionResult.failure();
        }
        return super.isSatisfiedBy(authentication, authenticationHandlers, applicationContext, assertion);
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NotPreventedAuthenticationPolicy)) {
            return false;
        }
        NotPreventedAuthenticationPolicy other = (NotPreventedAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof NotPreventedAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

