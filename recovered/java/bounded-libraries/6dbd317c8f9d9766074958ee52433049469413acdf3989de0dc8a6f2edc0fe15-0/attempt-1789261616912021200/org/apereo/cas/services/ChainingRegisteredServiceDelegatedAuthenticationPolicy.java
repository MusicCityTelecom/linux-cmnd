/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceChainOperatorTypes
 *  org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceChainOperatorTypes;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(value={"order"})
public class ChainingRegisteredServiceDelegatedAuthenticationPolicy
implements RegisteredServiceDelegatedAuthenticationPolicy {
    private static final long serialVersionUID = -2127874606493954025L;
    private List<RegisteredServiceDelegatedAuthenticationPolicy> strategies = new ArrayList<RegisteredServiceDelegatedAuthenticationPolicy>();
    private RegisteredServiceChainOperatorTypes operator = RegisteredServiceChainOperatorTypes.AND;

    public void addStrategy(@NonNull RegisteredServiceDelegatedAuthenticationPolicy policy) {
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.strategies.add(policy);
    }

    @JsonIgnore
    public Collection<String> getAllowedProviders() {
        return this.strategies.stream().map(RegisteredServiceDelegatedAuthenticationPolicy::getAllowedProviders).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @JsonIgnore
    public String getSelectionStrategy() {
        return this.strategies.stream().map(RegisteredServiceDelegatedAuthenticationPolicy::getSelectionStrategy).filter(StringUtils::isNotBlank).findFirst().orElse("");
    }

    @JsonIgnore
    public boolean isExclusive() {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(RegisteredServiceDelegatedAuthenticationPolicy::isExclusive);
        }
        return this.strategies.stream().allMatch(RegisteredServiceDelegatedAuthenticationPolicy::isExclusive);
    }

    @JsonIgnore
    public boolean isPermitUndefined() {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(RegisteredServiceDelegatedAuthenticationPolicy::isPermitUndefined);
        }
        return this.strategies.stream().allMatch(RegisteredServiceDelegatedAuthenticationPolicy::isPermitUndefined);
    }

    @JsonIgnore
    public boolean isProviderAllowed(String provider, RegisteredService registeredService) {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(policy -> policy.isProviderAllowed(provider, registeredService));
        }
        return this.strategies.stream().allMatch(policy -> policy.isProviderAllowed(provider, registeredService));
    }

    @JsonIgnore
    public boolean isProviderRequired() {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(RegisteredServiceDelegatedAuthenticationPolicy::isProviderRequired);
        }
        return this.strategies.stream().allMatch(RegisteredServiceDelegatedAuthenticationPolicy::isProviderRequired);
    }

    @Generated
    public List<RegisteredServiceDelegatedAuthenticationPolicy> getStrategies() {
        return this.strategies;
    }

    @Generated
    public RegisteredServiceChainOperatorTypes getOperator() {
        return this.operator;
    }

    @Generated
    public void setStrategies(List<RegisteredServiceDelegatedAuthenticationPolicy> strategies) {
        this.strategies = strategies;
    }

    @Generated
    public void setOperator(RegisteredServiceChainOperatorTypes operator) {
        this.operator = operator;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChainingRegisteredServiceDelegatedAuthenticationPolicy)) {
            return false;
        }
        ChainingRegisteredServiceDelegatedAuthenticationPolicy other = (ChainingRegisteredServiceDelegatedAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<RegisteredServiceDelegatedAuthenticationPolicy> this$strategies = this.strategies;
        List<RegisteredServiceDelegatedAuthenticationPolicy> other$strategies = other.strategies;
        if (this$strategies == null ? other$strategies != null : !((Object)this$strategies).equals(other$strategies)) {
            return false;
        }
        RegisteredServiceChainOperatorTypes this$operator = this.operator;
        RegisteredServiceChainOperatorTypes other$operator = other.operator;
        return !(this$operator == null ? other$operator != null : !this$operator.equals(other$operator));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ChainingRegisteredServiceDelegatedAuthenticationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<RegisteredServiceDelegatedAuthenticationPolicy> $strategies = this.strategies;
        result = result * 59 + ($strategies == null ? 43 : ((Object)$strategies).hashCode());
        RegisteredServiceChainOperatorTypes $operator = this.operator;
        result = result * 59 + ($operator == null ? 43 : $operator.hashCode());
        return result;
    }

    @Generated
    public ChainingRegisteredServiceDelegatedAuthenticationPolicy() {
    }
}

