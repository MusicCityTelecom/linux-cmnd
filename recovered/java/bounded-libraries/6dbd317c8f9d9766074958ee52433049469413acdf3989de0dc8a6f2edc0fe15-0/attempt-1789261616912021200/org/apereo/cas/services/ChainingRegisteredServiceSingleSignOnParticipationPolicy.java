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
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.util.model.TriStateBoolean;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(value={"order"})
public class ChainingRegisteredServiceSingleSignOnParticipationPolicy
implements RegisteredServiceSingleSignOnParticipationPolicy {
    private static final long serialVersionUID = -2923946898337761319L;
    private List<RegisteredServiceSingleSignOnParticipationPolicy> policies = new ArrayList<RegisteredServiceSingleSignOnParticipationPolicy>(0);

    public void addPolicy(@NonNull RegisteredServiceSingleSignOnParticipationPolicy policy) {
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.policies.add(policy);
    }

    public void addPolicies(@NonNull List<RegisteredServiceSingleSignOnParticipationPolicy> policy) {
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.policies.addAll(policy);
    }

    public void addPolicies(RegisteredServiceSingleSignOnParticipationPolicy ... policy) {
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.policies.addAll(Arrays.stream(policy).collect(Collectors.toList()));
    }

    @JsonIgnore
    public TriStateBoolean getCreateCookieOnRenewedAuthentication() {
        boolean result = this.policies.stream().filter(p -> p.getCreateCookieOnRenewedAuthentication() != null).allMatch(p -> p.getCreateCookieOnRenewedAuthentication().isTrue() || p.getCreateCookieOnRenewedAuthentication().isUndefined());
        return TriStateBoolean.fromBoolean((boolean)result);
    }

    public boolean shouldParticipateInSso(RegisteredService registeredService, AuthenticationAwareTicket ticketState) {
        return this.policies.stream().allMatch(p -> p.shouldParticipateInSso(registeredService, ticketState));
    }

    @Generated
    public String toString() {
        return "ChainingRegisteredServiceSingleSignOnParticipationPolicy(super=" + super.toString() + ", policies=" + this.policies + ")";
    }

    @Generated
    public List<RegisteredServiceSingleSignOnParticipationPolicy> getPolicies() {
        return this.policies;
    }

    @Generated
    public void setPolicies(List<RegisteredServiceSingleSignOnParticipationPolicy> policies) {
        this.policies = policies;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChainingRegisteredServiceSingleSignOnParticipationPolicy)) {
            return false;
        }
        ChainingRegisteredServiceSingleSignOnParticipationPolicy other = (ChainingRegisteredServiceSingleSignOnParticipationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        List<RegisteredServiceSingleSignOnParticipationPolicy> this$policies = this.policies;
        List<RegisteredServiceSingleSignOnParticipationPolicy> other$policies = other.policies;
        return !(this$policies == null ? other$policies != null : !((Object)this$policies).equals(other$policies));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ChainingRegisteredServiceSingleSignOnParticipationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<RegisteredServiceSingleSignOnParticipationPolicy> $policies = this.policies;
        result = result * 59 + ($policies == null ? 43 : ((Object)$policies).hashCode());
        return result;
    }

    @Generated
    public ChainingRegisteredServiceSingleSignOnParticipationPolicy() {
    }
}

