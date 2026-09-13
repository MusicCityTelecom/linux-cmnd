/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnAllAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    private static final long serialVersionUID = 5519257723778012771L;
    @JsonProperty
    private Set<String> excludedAttributes;

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> resolvedAttributes) {
        if (this.excludedAttributes != null) {
            this.excludedAttributes.forEach(resolvedAttributes::remove);
        }
        return resolvedAttributes;
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnAllAttributeReleasePolicy(super=" + super.toString() + ", excludedAttributes=" + this.excludedAttributes + ")";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnAllAttributeReleasePolicy)) {
            return false;
        }
        ReturnAllAttributeReleasePolicy other = (ReturnAllAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Set<String> this$excludedAttributes = this.excludedAttributes;
        Set<String> other$excludedAttributes = other.excludedAttributes;
        return !(this$excludedAttributes == null ? other$excludedAttributes != null : !((Object)this$excludedAttributes).equals(other$excludedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnAllAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Set<String> $excludedAttributes = this.excludedAttributes;
        result = result * 59 + ($excludedAttributes == null ? 43 : ((Object)$excludedAttributes).hashCode());
        return result;
    }

    @Generated
    public Set<String> getExcludedAttributes() {
        return this.excludedAttributes;
    }

    @JsonProperty
    @Generated
    public ReturnAllAttributeReleasePolicy setExcludedAttributes(Set<String> excludedAttributes) {
        this.excludedAttributes = excludedAttributes;
        return this;
    }

    @Generated
    public ReturnAllAttributeReleasePolicy() {
    }
}

