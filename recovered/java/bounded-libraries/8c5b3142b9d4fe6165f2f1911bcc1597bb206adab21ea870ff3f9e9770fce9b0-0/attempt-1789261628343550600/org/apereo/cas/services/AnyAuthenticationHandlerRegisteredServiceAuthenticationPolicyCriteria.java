/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.policy.AtLeastOneCredentialValidatedAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.policy.AtLeastOneCredentialValidatedAuthenticationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria
implements RegisteredServiceAuthenticationPolicyCriteria {
    private static final long serialVersionUID = -2905826778096374574L;
    private boolean tryAll;

    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService registeredService) {
        return new AtLeastOneCredentialValidatedAuthenticationPolicy(this.tryAll);
    }

    @Generated
    public String toString() {
        return "AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria(tryAll=" + this.tryAll + ")";
    }

    @Generated
    public boolean isTryAll() {
        return this.tryAll;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria)) {
            return false;
        }
        AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria other = (AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.tryAll == other.tryAll;
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.tryAll ? 79 : 97);
        return result;
    }

    @Generated
    public AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria setTryAll(boolean tryAll) {
        this.tryAll = tryAll;
        return this;
    }
}

