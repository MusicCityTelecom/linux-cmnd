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
 *  org.apereo.cas.authentication.policy.RequiredAuthenticationHandlerAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.policy.RequiredAuthenticationHandlerAuthenticationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
implements RegisteredServiceAuthenticationPolicyCriteria {
    private static final long serialVersionUID = -7298017804877275864L;

    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService registeredService) {
        Set handlers = registeredService.getAuthenticationPolicy().getRequiredAuthenticationHandlers();
        return new RequiredAuthenticationHandlerAuthenticationPolicy(handlers, false);
    }

    @Generated
    public String toString() {
        return "AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria()";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria)) {
            return false;
        }
        AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria other = (AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

