/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.ticket.AuthenticationAwareTicket
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.services.BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.ticket.AuthenticationAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy
extends BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy {
    private static final long serialVersionUID = -5923946898337761319L;

    public AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy(TimeUnit timeUnit, long timeValue, int order) {
        super(timeUnit, timeValue, order);
    }

    @Override
    protected ZonedDateTime determineInitialDateTime(RegisteredService registeredService, AuthenticationAwareTicket ticketState) {
        return ticketState.getAuthentication().getAuthenticationDate();
    }

    @Override
    @Generated
    public String toString() {
        return "AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy(super=" + super.toString() + ")";
    }

    @Generated
    public AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy)) {
            return false;
        }
        AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy other = (AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

