/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.ticket.AuthenticationAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class NeverRegisteredServiceSingleSignOnParticipationPolicy
implements RegisteredServiceSingleSignOnParticipationPolicy {
    private static final long serialVersionUID = -1123946898337761319L;

    public boolean shouldParticipateInSso(RegisteredService registeredService, AuthenticationAwareTicket ticketState) {
        return false;
    }

    @Generated
    public String toString() {
        return "NeverRegisteredServiceSingleSignOnParticipationPolicy(super=" + super.toString() + ")";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NeverRegisteredServiceSingleSignOnParticipationPolicy)) {
            return false;
        }
        NeverRegisteredServiceSingleSignOnParticipationPolicy other = (NeverRegisteredServiceSingleSignOnParticipationPolicy)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof NeverRegisteredServiceSingleSignOnParticipationPolicy;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }

    @Generated
    public NeverRegisteredServiceSingleSignOnParticipationPolicy() {
    }
}

