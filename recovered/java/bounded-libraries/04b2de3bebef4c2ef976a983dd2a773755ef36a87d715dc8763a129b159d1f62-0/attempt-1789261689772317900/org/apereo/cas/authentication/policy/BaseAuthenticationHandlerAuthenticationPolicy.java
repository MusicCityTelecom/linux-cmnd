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
 *  org.springframework.util.StringUtils
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
import org.springframework.util.StringUtils;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseAuthenticationHandlerAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAuthenticationHandlerAuthenticationPolicy.class);
    private static final long serialVersionUID = -3871692225877293627L;
    private Set<String> handlerNames;
    private boolean tryAll;

    protected BaseAuthenticationHandlerAuthenticationPolicy(String requiredHandlerNames) {
        this(StringUtils.commaDelimitedListToSet((String)requiredHandlerNames), false);
    }

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authn, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) {
        boolean credsOk = true;
        int sum = authn.getSuccesses().size() + authn.getFailures().size();
        if (this.tryAll) {
            boolean bl = credsOk = authn.getCredentials().size() == sum;
        }
        if (!credsOk) {
            LOGGER.warn("Number of provided credentials [{}] does not match the sum of authentication successes and failures [{}]. Successful authentication handlers are [{}]", new Object[]{authn.getCredentials().size(), sum, authn.getSuccesses().keySet()});
            return AuthenticationPolicyExecutionResult.failure();
        }
        return this.isSatisfiedByInternal(authn);
    }

    abstract AuthenticationPolicyExecutionResult isSatisfiedByInternal(Authentication var1);

    @Generated
    protected BaseAuthenticationHandlerAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseAuthenticationHandlerAuthenticationPolicy)) {
            return false;
        }
        BaseAuthenticationHandlerAuthenticationPolicy other = (BaseAuthenticationHandlerAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.tryAll != other.tryAll) {
            return false;
        }
        Set<String> this$handlerNames = this.handlerNames;
        Set<String> other$handlerNames = other.handlerNames;
        return !(this$handlerNames == null ? other$handlerNames != null : !((Object)this$handlerNames).equals(other$handlerNames));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseAuthenticationHandlerAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + (this.tryAll ? 79 : 97);
        Set<String> $handlerNames = this.handlerNames;
        result = result * 59 + ($handlerNames == null ? 43 : ((Object)$handlerNames).hashCode());
        return result;
    }

    @Generated
    public void setHandlerNames(Set<String> handlerNames) {
        this.handlerNames = handlerNames;
    }

    @Generated
    public void setTryAll(boolean tryAll) {
        this.tryAll = tryAll;
    }

    @Generated
    public Set<String> getHandlerNames() {
        return this.handlerNames;
    }

    @Generated
    public boolean isTryAll() {
        return this.tryAll;
    }

    @Generated
    protected BaseAuthenticationHandlerAuthenticationPolicy(Set<String> handlerNames, boolean tryAll) {
        this.handlerNames = handlerNames;
        this.tryAll = tryAll;
    }
}

