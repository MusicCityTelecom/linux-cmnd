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
public class RequiredAuthenticationHandlerAuthenticationPolicy
extends BaseAuthenticationHandlerAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredAuthenticationHandlerAuthenticationPolicy.class);
    private static final long serialVersionUID = -3871692225877293627L;

    public RequiredAuthenticationHandlerAuthenticationPolicy(String requiredHandlerNames) {
        super(requiredHandlerNames);
    }

    public RequiredAuthenticationHandlerAuthenticationPolicy(Set<String> handlerNames, boolean tryAll) {
        super(handlerNames, tryAll);
    }

    @Override
    public AuthenticationPolicyExecutionResult isSatisfiedByInternal(Authentication authn) {
        boolean credsOk;
        LOGGER.debug("Examining authentication successes for authentication handler [{}]", this.getHandlerNames());
        if (!this.getHandlerNames().isEmpty() && !(credsOk = authn.getSuccesses().keySet().stream().anyMatch(s -> this.getHandlerNames().contains(s)))) {
            LOGGER.info("Required authentication handler(s) [{}] is not present in the list of successful authentications [{}]", this.getHandlerNames(), authn.getSuccesses().keySet());
            return AuthenticationPolicyExecutionResult.failure();
        }
        LOGGER.trace("Authentication policy is satisfied");
        return AuthenticationPolicyExecutionResult.success();
    }

    @Generated
    public RequiredAuthenticationHandlerAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RequiredAuthenticationHandlerAuthenticationPolicy)) {
            return false;
        }
        RequiredAuthenticationHandlerAuthenticationPolicy other = (RequiredAuthenticationHandlerAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RequiredAuthenticationHandlerAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

