/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceAuthenticationPolicy
implements RegisteredServiceAuthenticationPolicy {
    private static final long serialVersionUID = -6777133646772207331L;
    private Set<String> requiredAuthenticationHandlers = new HashSet<String>();
    private Set<String> excludedAuthenticationHandlers = new HashSet<String>();
    private RegisteredServiceAuthenticationPolicyCriteria criteria;

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceAuthenticationPolicy(requiredAuthenticationHandlers=" + this.requiredAuthenticationHandlers + ", excludedAuthenticationHandlers=" + this.excludedAuthenticationHandlers + ", criteria=" + this.criteria + ")";
    }

    @Generated
    public Set<String> getRequiredAuthenticationHandlers() {
        return this.requiredAuthenticationHandlers;
    }

    @Generated
    public Set<String> getExcludedAuthenticationHandlers() {
        return this.excludedAuthenticationHandlers;
    }

    @Generated
    public RegisteredServiceAuthenticationPolicyCriteria getCriteria() {
        return this.criteria;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceAuthenticationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceAuthenticationPolicy other = (DefaultRegisteredServiceAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Set<String> this$requiredAuthenticationHandlers = this.requiredAuthenticationHandlers;
        Set<String> other$requiredAuthenticationHandlers = other.requiredAuthenticationHandlers;
        if (this$requiredAuthenticationHandlers == null ? other$requiredAuthenticationHandlers != null : !((Object)this$requiredAuthenticationHandlers).equals(other$requiredAuthenticationHandlers)) {
            return false;
        }
        Set<String> this$excludedAuthenticationHandlers = this.excludedAuthenticationHandlers;
        Set<String> other$excludedAuthenticationHandlers = other.excludedAuthenticationHandlers;
        if (this$excludedAuthenticationHandlers == null ? other$excludedAuthenticationHandlers != null : !((Object)this$excludedAuthenticationHandlers).equals(other$excludedAuthenticationHandlers)) {
            return false;
        }
        RegisteredServiceAuthenticationPolicyCriteria this$criteria = this.criteria;
        RegisteredServiceAuthenticationPolicyCriteria other$criteria = other.criteria;
        return !(this$criteria == null ? other$criteria != null : !this$criteria.equals(other$criteria));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceAuthenticationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Set<String> $requiredAuthenticationHandlers = this.requiredAuthenticationHandlers;
        result = result * 59 + ($requiredAuthenticationHandlers == null ? 43 : ((Object)$requiredAuthenticationHandlers).hashCode());
        Set<String> $excludedAuthenticationHandlers = this.excludedAuthenticationHandlers;
        result = result * 59 + ($excludedAuthenticationHandlers == null ? 43 : ((Object)$excludedAuthenticationHandlers).hashCode());
        RegisteredServiceAuthenticationPolicyCriteria $criteria = this.criteria;
        result = result * 59 + ($criteria == null ? 43 : $criteria.hashCode());
        return result;
    }

    @Generated
    public DefaultRegisteredServiceAuthenticationPolicy setRequiredAuthenticationHandlers(Set<String> requiredAuthenticationHandlers) {
        this.requiredAuthenticationHandlers = requiredAuthenticationHandlers;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAuthenticationPolicy setExcludedAuthenticationHandlers(Set<String> excludedAuthenticationHandlers) {
        this.excludedAuthenticationHandlers = excludedAuthenticationHandlers;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceAuthenticationPolicy setCriteria(RegisteredServiceAuthenticationPolicyCriteria criteria) {
        this.criteria = criteria;
        return this;
    }
}

