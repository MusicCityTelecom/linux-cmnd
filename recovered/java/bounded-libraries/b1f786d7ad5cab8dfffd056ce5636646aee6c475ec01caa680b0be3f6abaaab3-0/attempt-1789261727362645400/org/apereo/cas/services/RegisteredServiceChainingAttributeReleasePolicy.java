/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceChainingAttributeReleasePolicy
extends RegisteredServiceAttributeReleasePolicy {
    public List<RegisteredServiceAttributeReleasePolicy> getPolicies();

    public RegisteredServiceChainingAttributeReleasePolicy addPolicies(RegisteredServiceAttributeReleasePolicy ... var1);

    @CanIgnoreReturnValue
    default public RegisteredServiceChainingAttributeReleasePolicy addPolicies(Collection<RegisteredServiceAttributeReleasePolicy> policies) {
        this.addPolicies(policies.toArray(new RegisteredServiceAttributeReleasePolicy[0]));
        return this;
    }

    public int size();
}

