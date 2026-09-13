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
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.services.RegisteredServiceAccessStrategy
 *  org.apereo.cas.services.RegisteredServiceChainOperatorTypes
 *  org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.services.ChainingRegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceAccessStrategy;
import org.apereo.cas.services.RegisteredServiceChainOperatorTypes;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;
import org.apereo.services.persondir.support.merger.IAttributeMerger;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(value={"order"})
public class ChainingRegisteredServiceAccessStrategy
implements RegisteredServiceAccessStrategy {
    private static final long serialVersionUID = 5018603912161923218L;
    private List<RegisteredServiceAccessStrategy> strategies = new ArrayList<RegisteredServiceAccessStrategy>();
    private RegisteredServiceChainOperatorTypes operator = RegisteredServiceChainOperatorTypes.AND;
    private URI unauthorizedRedirectUrl;

    public void addStrategy(@NonNull RegisteredServiceAccessStrategy policy) {
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.strategies.add(policy);
    }

    public void addStrategies(RegisteredServiceAccessStrategy ... policies) {
        Arrays.stream(policies).forEach(this::addStrategy);
    }

    @JsonIgnore
    public boolean isServiceAccessAllowed() {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(RegisteredServiceAccessStrategy::isServiceAccessAllowed);
        }
        return this.strategies.stream().allMatch(RegisteredServiceAccessStrategy::isServiceAccessAllowed);
    }

    @JsonIgnore
    public void setServiceAccessAllowed(boolean enabled) {
        this.strategies.forEach(strategy -> strategy.setServiceAccessAllowed(enabled));
    }

    @JsonIgnore
    public boolean isServiceAccessAllowedForSso() {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(RegisteredServiceAccessStrategy::isServiceAccessAllowedForSso);
        }
        return this.strategies.stream().allMatch(RegisteredServiceAccessStrategy::isServiceAccessAllowedForSso);
    }

    public boolean doPrincipalAttributesAllowServiceAccess(String principal, Map<String, Object> attributes) {
        if (this.operator == RegisteredServiceChainOperatorTypes.OR) {
            return this.strategies.stream().anyMatch(strategy -> strategy.doPrincipalAttributesAllowServiceAccess(principal, attributes));
        }
        return this.strategies.stream().allMatch(strategy -> strategy.doPrincipalAttributesAllowServiceAccess(principal, attributes));
    }

    @JsonIgnore
    public RegisteredServiceDelegatedAuthenticationPolicy getDelegatedAuthenticationPolicy() {
        ChainingRegisteredServiceDelegatedAuthenticationPolicy policy = new ChainingRegisteredServiceDelegatedAuthenticationPolicy();
        policy.setOperator(this.operator);
        this.strategies.stream().map(RegisteredServiceAccessStrategy::getDelegatedAuthenticationPolicy).forEach(policy::addStrategy);
        return policy;
    }

    @JsonIgnore
    public Map<String, Set<String>> getRequiredAttributes() {
        LinkedHashMap results = new LinkedHashMap();
        IAttributeMerger merger = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)PrincipalAttributesCoreProperties.MergingStrategyTypes.MULTIVALUED);
        this.strategies.forEach(strategy -> {
            Map<String, ArrayList> requiredAttributes = strategy.getRequiredAttributes().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, mapper -> new ArrayList((Collection)mapper.getValue())));
            merger.mergeAttributes((Map)results, requiredAttributes);
        });
        return results.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, mapper -> new LinkedHashSet((Collection)mapper.getValue())));
    }

    @Generated
    public List<RegisteredServiceAccessStrategy> getStrategies() {
        return this.strategies;
    }

    @Generated
    public RegisteredServiceChainOperatorTypes getOperator() {
        return this.operator;
    }

    @Generated
    public URI getUnauthorizedRedirectUrl() {
        return this.unauthorizedRedirectUrl;
    }

    @Generated
    public void setStrategies(List<RegisteredServiceAccessStrategy> strategies) {
        this.strategies = strategies;
    }

    @Generated
    public void setOperator(RegisteredServiceChainOperatorTypes operator) {
        this.operator = operator;
    }

    @Generated
    public void setUnauthorizedRedirectUrl(URI unauthorizedRedirectUrl) {
        this.unauthorizedRedirectUrl = unauthorizedRedirectUrl;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChainingRegisteredServiceAccessStrategy)) {
            return false;
        }
        ChainingRegisteredServiceAccessStrategy other = (ChainingRegisteredServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<RegisteredServiceAccessStrategy> this$strategies = this.strategies;
        List<RegisteredServiceAccessStrategy> other$strategies = other.strategies;
        if (this$strategies == null ? other$strategies != null : !((Object)this$strategies).equals(other$strategies)) {
            return false;
        }
        RegisteredServiceChainOperatorTypes this$operator = this.operator;
        RegisteredServiceChainOperatorTypes other$operator = other.operator;
        if (this$operator == null ? other$operator != null : !this$operator.equals(other$operator)) {
            return false;
        }
        URI this$unauthorizedRedirectUrl = this.unauthorizedRedirectUrl;
        URI other$unauthorizedRedirectUrl = other.unauthorizedRedirectUrl;
        return !(this$unauthorizedRedirectUrl == null ? other$unauthorizedRedirectUrl != null : !((Object)this$unauthorizedRedirectUrl).equals(other$unauthorizedRedirectUrl));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ChainingRegisteredServiceAccessStrategy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<RegisteredServiceAccessStrategy> $strategies = this.strategies;
        result = result * 59 + ($strategies == null ? 43 : ((Object)$strategies).hashCode());
        RegisteredServiceChainOperatorTypes $operator = this.operator;
        result = result * 59 + ($operator == null ? 43 : $operator.hashCode());
        URI $unauthorizedRedirectUrl = this.unauthorizedRedirectUrl;
        result = result * 59 + ($unauthorizedRedirectUrl == null ? 43 : ((Object)$unauthorizedRedirectUrl).hashCode());
        return result;
    }

    @Generated
    public ChainingRegisteredServiceAccessStrategy() {
    }
}

