/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceConsentPolicy
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.services.consent;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceConsentPolicy;
import org.apereo.cas.util.model.TriStateBoolean;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceConsentPolicy
implements RegisteredServiceConsentPolicy {
    private static final long serialVersionUID = -2771506941879419063L;
    private TriStateBoolean status = TriStateBoolean.UNDEFINED;
    private Set<String> excludedAttributes;
    private Set<String> includeOnlyAttributes;
    private int order;
    private Set<String> excludedServices;

    public DefaultRegisteredServiceConsentPolicy(Set<String> excludedAttributes, Set<String> includeOnlyAttributes) {
        this.excludedAttributes = excludedAttributes;
        this.includeOnlyAttributes = includeOnlyAttributes;
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceConsentPolicy(status=" + this.status + ", excludedAttributes=" + this.excludedAttributes + ", includeOnlyAttributes=" + this.includeOnlyAttributes + ", order=" + this.order + ", excludedServices=" + this.excludedServices + ")";
    }

    @Generated
    public TriStateBoolean getStatus() {
        return this.status;
    }

    @Generated
    public Set<String> getExcludedAttributes() {
        return this.excludedAttributes;
    }

    @Generated
    public Set<String> getIncludeOnlyAttributes() {
        return this.includeOnlyAttributes;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public Set<String> getExcludedServices() {
        return this.excludedServices;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy setStatus(TriStateBoolean status) {
        this.status = status;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy setExcludedAttributes(Set<String> excludedAttributes) {
        this.excludedAttributes = excludedAttributes;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy setIncludeOnlyAttributes(Set<String> includeOnlyAttributes) {
        this.includeOnlyAttributes = includeOnlyAttributes;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy setExcludedServices(Set<String> excludedServices) {
        this.excludedServices = excludedServices;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceConsentPolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceConsentPolicy)) {
            return false;
        }
        DefaultRegisteredServiceConsentPolicy other = (DefaultRegisteredServiceConsentPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        TriStateBoolean this$status = this.status;
        TriStateBoolean other$status = other.status;
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        Set<String> this$excludedAttributes = this.excludedAttributes;
        Set<String> other$excludedAttributes = other.excludedAttributes;
        if (this$excludedAttributes == null ? other$excludedAttributes != null : !((Object)this$excludedAttributes).equals(other$excludedAttributes)) {
            return false;
        }
        Set<String> this$includeOnlyAttributes = this.includeOnlyAttributes;
        Set<String> other$includeOnlyAttributes = other.includeOnlyAttributes;
        if (this$includeOnlyAttributes == null ? other$includeOnlyAttributes != null : !((Object)this$includeOnlyAttributes).equals(other$includeOnlyAttributes)) {
            return false;
        }
        Set<String> this$excludedServices = this.excludedServices;
        Set<String> other$excludedServices = other.excludedServices;
        return !(this$excludedServices == null ? other$excludedServices != null : !((Object)this$excludedServices).equals(other$excludedServices));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceConsentPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        TriStateBoolean $status = this.status;
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Set<String> $excludedAttributes = this.excludedAttributes;
        result = result * 59 + ($excludedAttributes == null ? 43 : ((Object)$excludedAttributes).hashCode());
        Set<String> $includeOnlyAttributes = this.includeOnlyAttributes;
        result = result * 59 + ($includeOnlyAttributes == null ? 43 : ((Object)$includeOnlyAttributes).hashCode());
        Set<String> $excludedServices = this.excludedServices;
        result = result * 59 + ($excludedServices == null ? 43 : ((Object)$excludedServices).hashCode());
        return result;
    }
}

