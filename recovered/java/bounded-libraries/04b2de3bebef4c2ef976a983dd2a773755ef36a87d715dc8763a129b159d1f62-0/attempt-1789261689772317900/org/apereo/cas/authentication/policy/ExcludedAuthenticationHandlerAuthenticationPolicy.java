/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.policy.BaseAuthenticationHandlerAuthenticationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ExcludedAuthenticationHandlerAuthenticationPolicy
extends BaseAuthenticationHandlerAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcludedAuthenticationHandlerAuthenticationPolicy.class);
    private static final long serialVersionUID = -3871692225877293627L;

    public ExcludedAuthenticationHandlerAuthenticationPolicy(Set<String> handlerNames, boolean tryAll) {
        super(handlerNames, tryAll);
    }

    @Override
    public AuthenticationPolicyExecutionResult isSatisfiedByInternal(Authentication authn) {
        boolean credsOk;
        if (!this.getHandlerNames().isEmpty() && (credsOk = authn.getSuccesses().keySet().stream().anyMatch(s -> this.getHandlerNames().contains(s)))) {
            LOGGER.warn("Excluded authentication handler(s) [{}] found in authentication attempt", this.getHandlerNames());
            return AuthenticationPolicyExecutionResult.failure();
        }
        LOGGER.trace("Authentication policy is satisfied");
        return AuthenticationPolicyExecutionResult.success();
    }

    @Generated
    public ExcludedAuthenticationHandlerAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExcludedAuthenticationHandlerAuthenticationPolicy)) {
            return false;
        }
        ExcludedAuthenticationHandlerAuthenticationPolicy other = (ExcludedAuthenticationHandlerAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ExcludedAuthenticationHandlerAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

