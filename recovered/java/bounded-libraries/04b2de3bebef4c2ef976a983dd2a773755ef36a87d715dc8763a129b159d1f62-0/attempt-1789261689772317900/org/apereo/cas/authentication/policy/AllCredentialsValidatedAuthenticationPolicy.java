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
public class AllCredentialsValidatedAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AllCredentialsValidatedAuthenticationPolicy.class);
    private static final long serialVersionUID = 6112280265093249844L;

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authn, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) {
        LOGGER.debug("Successful authentications: [{}], credentials: [{}]", authn.getSuccesses().keySet(), (Object)authn.getCredentials());
        if (authn.getSuccesses().size() != authn.getCredentials().size()) {
            LOGGER.warn("Number of successful authentications, [{}], does not match the number of provided credentials, [{}].", (Object)authn.getSuccesses().size(), (Object)authn.getCredentials().size());
            return AuthenticationPolicyExecutionResult.failure();
        }
        LOGGER.debug("Authentication policy is satisfied.");
        return AuthenticationPolicyExecutionResult.success();
    }

    @Generated
    public AllCredentialsValidatedAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllCredentialsValidatedAuthenticationPolicy)) {
            return false;
        }
        AllCredentialsValidatedAuthenticationPolicy other = (AllCredentialsValidatedAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AllCredentialsValidatedAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

