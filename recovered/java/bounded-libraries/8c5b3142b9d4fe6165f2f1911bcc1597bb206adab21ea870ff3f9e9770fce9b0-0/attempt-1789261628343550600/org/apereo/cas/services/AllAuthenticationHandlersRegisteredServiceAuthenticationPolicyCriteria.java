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
 *  org.apereo.cas.authentication.policy.AllAuthenticationHandlersSucceededAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.policy.AllAuthenticationHandlersSucceededAuthenticationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
implements RegisteredServiceAuthenticationPolicyCriteria {
    private static final long serialVersionUID = -2905826778096374574L;

    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService registeredService) {
        return new AllAuthenticationHandlersSucceededAuthenticationPolicy();
    }

    @Generated
    public String toString() {
        return "AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria()";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria)) {
            return false;
        }
        AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria other = (AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

