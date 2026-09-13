/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apereo.cas.util.model.TriStateBoolean;
import org.springframework.core.Ordered;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceConsentPolicy
extends Serializable,
Ordered {
    default public TriStateBoolean getStatus() {
        return TriStateBoolean.UNDEFINED;
    }

    default public Set<String> getExcludedServices() {
        return null;
    }

    default public Set<String> getExcludedAttributes() {
        return new LinkedHashSet<String>(0);
    }

    default public Set<String> getIncludeOnlyAttributes() {
        return new LinkedHashSet<String>(0);
    }

    default public int getOrder() {
        return 0;
    }

    @JsonIgnore
    default public int size() {
        return 0;
    }

    @JsonIgnore
    default public List<RegisteredServiceConsentPolicy> getPolicies() {
        return List.of(this);
    }
}

