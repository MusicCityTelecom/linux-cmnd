/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URL;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceProxyPolicy;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RefuseRegisteredServiceProxyPolicy
implements RegisteredServiceProxyPolicy {
    private static final long serialVersionUID = -5718445151129901484L;

    @JsonIgnore
    public boolean isAllowedToProxy() {
        return false;
    }

    @JsonIgnore
    public boolean isAllowedProxyCallbackUrl(RegisteredService registeredService, URL pgtUrl) {
        return false;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RefuseRegisteredServiceProxyPolicy)) {
            return false;
        }
        RefuseRegisteredServiceProxyPolicy other = (RefuseRegisteredServiceProxyPolicy)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RefuseRegisteredServiceProxyPolicy;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

