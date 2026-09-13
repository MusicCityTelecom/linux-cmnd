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
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.policy.BaseAuthenticationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AllAuthenticationHandlersSucceededAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AllAuthenticationHandlersSucceededAuthenticationPolicy.class);
    private static final long serialVersionUID = 8901190843828760737L;

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authn, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successful authentications: [{}], current authentication handlers [{}]", authn.getSuccesses().keySet(), (Object)authenticationHandlers.stream().map(AuthenticationHandler::getName).collect(Collectors.joining(",")));
        }
        if (authn.getSuccesses().size() != authenticationHandlers.size()) {
            LOGGER.warn("Number of successful authentications, [{}], does not match the number of authentication handlers, [{}].", (Object)authn.getSuccesses().size(), (Object)authenticationHandlers.size());
            return AuthenticationPolicyExecutionResult.failure();
        }
        return AuthenticationPolicyExecutionResult.success((!authn.getSuccesses().isEmpty() ? 1 : 0) != 0);
    }

    @Generated
    public AllAuthenticationHandlersSucceededAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllAuthenticationHandlersSucceededAuthenticationPolicy)) {
            return false;
        }
        AllAuthenticationHandlersSucceededAuthenticationPolicy other = (AllAuthenticationHandlersSucceededAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AllAuthenticationHandlersSucceededAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

