/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeFilter
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.services.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class RegisteredServiceChainingAttributeFilter
implements RegisteredServiceAttributeFilter {
    private static final long serialVersionUID = 903015750234610128L;
    private List<RegisteredServiceAttributeFilter> filters = new ArrayList<RegisteredServiceAttributeFilter>(0);

    public Map<String, List<Object>> filter(Map<String, List<Object>> givenAttributes) {
        AnnotationAwareOrderComparator.sort(this.filters);
        HashMap<String, List<Object>> attributes = new HashMap<String, List<Object>>();
        this.filters.forEach(policy -> attributes.putAll(policy.filter(givenAttributes)));
        return attributes;
    }

    @Generated
    public String toString() {
        return "RegisteredServiceChainingAttributeFilter(filters=" + this.filters + ")";
    }

    @Generated
    public void setFilters(List<RegisteredServiceAttributeFilter> filters) {
        this.filters = filters;
    }

    @Generated
    public RegisteredServiceChainingAttributeFilter() {
    }

    @Generated
    public List<RegisteredServiceAttributeFilter> getFilters() {
        return this.filters;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegisteredServiceChainingAttributeFilter)) {
            return false;
        }
        RegisteredServiceChainingAttributeFilter other = (RegisteredServiceChainingAttributeFilter)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<RegisteredServiceAttributeFilter> this$filters = this.filters;
        List<RegisteredServiceAttributeFilter> other$filters = other.filters;
        return !(this$filters == null ? other$filters != null : !((Object)this$filters).equals(other$filters));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegisteredServiceChainingAttributeFilter;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<RegisteredServiceAttributeFilter> $filters = this.filters;
        result = result * 59 + ($filters == null ? 43 : ((Object)$filters).hashCode());
        return result;
    }
}

