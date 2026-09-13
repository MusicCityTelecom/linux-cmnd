/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceConsentPolicy
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.services.consent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceConsentPolicy;
import org.apereo.cas.util.model.TriStateBoolean;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ChainingRegisteredServiceConsentPolicy
implements RegisteredServiceConsentPolicy {
    private static final long serialVersionUID = -2949244688986345692L;
    private final List<RegisteredServiceConsentPolicy> policies = new ArrayList<RegisteredServiceConsentPolicy>(0);

    public void addPolicies(Collection<RegisteredServiceConsentPolicy> policy) {
        if (this.policies.addAll(policy.stream().filter(BeanSupplier::isNotProxy).collect(Collectors.toList()))) {
            AnnotationAwareOrderComparator.sortIfNecessary(this.policies);
        }
    }

    public void addPolicy(RegisteredServiceConsentPolicy policy) {
        if (BeanSupplier.isNotProxy((Object)policy)) {
            this.policies.add(policy);
            AnnotationAwareOrderComparator.sortIfNecessary(this.policies);
        }
    }

    @JsonIgnore
    public TriStateBoolean getStatus() {
        if (this.policies.stream().filter(BeanSupplier::isNotProxy).anyMatch(policy -> policy.getStatus().isTrue())) {
            return TriStateBoolean.TRUE;
        }
        if (this.policies.stream().filter(BeanSupplier::isNotProxy).allMatch(policy -> policy.getStatus().isFalse())) {
            return TriStateBoolean.FALSE;
        }
        return TriStateBoolean.UNDEFINED;
    }

    @JsonIgnore
    public Set<String> getExcludedAttributes() {
        return this.policies.stream().filter(BeanSupplier::isNotProxy).map(RegisteredServiceConsentPolicy::getExcludedAttributes).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @JsonIgnore
    public Set<String> getExcludedServices() {
        return this.policies.stream().filter(BeanSupplier::isNotProxy).map(RegisteredServiceConsentPolicy::getExcludedServices).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @JsonIgnore
    public Set<String> getIncludeOnlyAttributes() {
        return this.policies.stream().filter(BeanSupplier::isNotProxy).map(RegisteredServiceConsentPolicy::getIncludeOnlyAttributes).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @JsonIgnore
    public int size() {
        return this.policies.size();
    }

    @Generated
    public String toString() {
        return "ChainingRegisteredServiceConsentPolicy(policies=" + this.policies + ")";
    }

    @Generated
    public List<RegisteredServiceConsentPolicy> getPolicies() {
        return this.policies;
    }

    @Generated
    public ChainingRegisteredServiceConsentPolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChainingRegisteredServiceConsentPolicy)) {
            return false;
        }
        ChainingRegisteredServiceConsentPolicy other = (ChainingRegisteredServiceConsentPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<RegisteredServiceConsentPolicy> this$policies = this.policies;
        List<RegisteredServiceConsentPolicy> other$policies = other.policies;
        return !(this$policies == null ? other$policies != null : !((Object)this$policies).equals(other$policies));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ChainingRegisteredServiceConsentPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<RegisteredServiceConsentPolicy> $policies = this.policies;
        result = result * 59 + ($policies == null ? 43 : ((Object)$policies).hashCode());
        return result;
    }
}

