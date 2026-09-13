/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.services.RegisteredServiceChainingAttributeReleasePolicy
 *  org.apereo.cas.services.RegisteredServiceConsentPolicy
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.services;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.principal.ChainingPrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.RegisteredServiceChainingAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceConsentPolicy;
import org.apereo.cas.services.consent.ChainingRegisteredServiceConsentPolicy;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class ChainingAttributeReleasePolicy
implements RegisteredServiceChainingAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ChainingAttributeReleasePolicy.class);
    private static final long serialVersionUID = 3795054936775326709L;
    private List<RegisteredServiceAttributeReleasePolicy> policies = new ArrayList<RegisteredServiceAttributeReleasePolicy>(0);
    private PrincipalAttributesCoreProperties.MergingStrategyTypes mergingPolicy = PrincipalAttributesCoreProperties.MergingStrategyTypes.REPLACE;
    private int order;

    public RegisteredServiceConsentPolicy getConsentPolicy() {
        ChainingRegisteredServiceConsentPolicy chainingConsentPolicy = new ChainingRegisteredServiceConsentPolicy();
        LinkedHashSet newConsentPolicies = this.policies.stream().map(policy -> policy.getConsentPolicy().getPolicies()).flatMap(Collection::stream).sorted(AnnotationAwareOrderComparator.INSTANCE).collect(Collectors.toCollection(LinkedHashSet::new));
        chainingConsentPolicy.addPolicies(newConsentPolicies);
        return chainingConsentPolicy;
    }

    public RegisteredServicePrincipalAttributesRepository getPrincipalAttributesRepository() {
        List<RegisteredServicePrincipalAttributesRepository> repositories = this.policies.stream().sorted(AnnotationAwareOrderComparator.INSTANCE).map(RegisteredServiceAttributeReleasePolicy::getPrincipalAttributesRepository).sorted((Comparator<RegisteredServicePrincipalAttributesRepository>)AnnotationAwareOrderComparator.INSTANCE).collect(Collectors.toList());
        return new ChainingPrincipalAttributesRepository(repositories);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Map<String, List<Object>> getAttributes(RegisteredServiceAttributeReleasePolicyContext context) {
        try {
            IAttributeMerger merger = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)this.mergingPolicy);
            HashMap<String, List<Object>> attributes = new HashMap<String, List<Object>>();
            this.policies.stream().sorted(AnnotationAwareOrderComparator.INSTANCE).forEach(policy -> {
                LOGGER.trace("Fetching attributes from policy [{}] for principal [{}]", (Object)policy.getName(), (Object)context.getPrincipal().getId());
                Map policyAttributes = policy.getAttributes(context);
                HashMap results = new HashMap(merger.mergeAttributes((Map)attributes, policyAttributes));
                LOGGER.trace("Attributes that remain, after the merge with attribute policy results, are [{}]", results);
                attributes.clear();
                attributes.putAll(results);
                context.getReleasingAttributes().clear();
                context.getReleasingAttributes().putAll(attributes);
            });
            HashMap<String, List<Object>> hashMap = attributes;
            return hashMap;
        }
        finally {
            context.getReleasingAttributes().clear();
        }
    }

    public Map<String, List<Object>> getConsentableAttributes(RegisteredServiceAttributeReleasePolicyContext context) {
        IAttributeMerger merger = CoreAuthenticationUtils.getAttributeMerger((PrincipalAttributesCoreProperties.MergingStrategyTypes)this.mergingPolicy);
        HashMap<String, List<Object>> attributes = new HashMap<String, List<Object>>();
        this.policies.stream().sorted(AnnotationAwareOrderComparator.INSTANCE).forEach(policy -> {
            LOGGER.trace("Fetching consentable attributes from policy [{}] for principal [{}]", (Object)policy.getName(), (Object)context.getPrincipal().getId());
            Map policyAttributes = policy.getConsentableAttributes(context);
            merger.mergeAttributes((Map)attributes, policyAttributes);
            LOGGER.trace("Attributes that remain, after the merge with consentable attribute policy results, are [{}]", (Object)attributes);
        });
        return attributes;
    }

    @CanIgnoreReturnValue
    public RegisteredServiceChainingAttributeReleasePolicy addPolicies(RegisteredServiceAttributeReleasePolicy ... policies) {
        this.policies.addAll(Arrays.stream(policies).collect(Collectors.toList()));
        return this;
    }

    public int size() {
        return this.policies.size();
    }

    @Generated
    public String toString() {
        return "ChainingAttributeReleasePolicy(policies=" + this.policies + ", mergingPolicy=" + this.mergingPolicy + ", order=" + this.order + ")";
    }

    @Generated
    public void setPolicies(List<RegisteredServiceAttributeReleasePolicy> policies) {
        this.policies = policies;
    }

    @Generated
    public void setMergingPolicy(PrincipalAttributesCoreProperties.MergingStrategyTypes mergingPolicy) {
        this.mergingPolicy = mergingPolicy;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public List<RegisteredServiceAttributeReleasePolicy> getPolicies() {
        return this.policies;
    }

    @Generated
    public PrincipalAttributesCoreProperties.MergingStrategyTypes getMergingPolicy() {
        return this.mergingPolicy;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChainingAttributeReleasePolicy)) {
            return false;
        }
        ChainingAttributeReleasePolicy other = (ChainingAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        List<RegisteredServiceAttributeReleasePolicy> this$policies = this.policies;
        List<RegisteredServiceAttributeReleasePolicy> other$policies = other.policies;
        if (this$policies == null ? other$policies != null : !((Object)this$policies).equals(other$policies)) {
            return false;
        }
        PrincipalAttributesCoreProperties.MergingStrategyTypes this$mergingPolicy = this.mergingPolicy;
        PrincipalAttributesCoreProperties.MergingStrategyTypes other$mergingPolicy = other.mergingPolicy;
        return !(this$mergingPolicy == null ? other$mergingPolicy != null : !this$mergingPolicy.equals(other$mergingPolicy));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ChainingAttributeReleasePolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        List<RegisteredServiceAttributeReleasePolicy> $policies = this.policies;
        result = result * 59 + ($policies == null ? 43 : ((Object)$policies).hashCode());
        PrincipalAttributesCoreProperties.MergingStrategyTypes $mergingPolicy = this.mergingPolicy;
        result = result * 59 + ($mergingPolicy == null ? 43 : $mergingPolicy.hashCode());
        return result;
    }
}

