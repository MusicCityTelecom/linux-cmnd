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
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.util.model.TriStateBoolean;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DefaultRegisteredServiceSingleSignOnParticipationPolicy
implements RegisteredServiceSingleSignOnParticipationPolicy {
    private static final long serialVersionUID = -1223944598337761319L;
    private TriStateBoolean createCookieOnRenewedAuthentication;

    public boolean shouldParticipateInSso(RegisteredService registeredService, AuthenticationAwareTicket ticketState) {
        return true;
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceSingleSignOnParticipationPolicy(createCookieOnRenewedAuthentication=" + this.createCookieOnRenewedAuthentication + ")";
    }

    @Generated
    public TriStateBoolean getCreateCookieOnRenewedAuthentication() {
        return this.createCookieOnRenewedAuthentication;
    }

    @Generated
    public DefaultRegisteredServiceSingleSignOnParticipationPolicy setCreateCookieOnRenewedAuthentication(TriStateBoolean createCookieOnRenewedAuthentication) {
        this.createCookieOnRenewedAuthentication = createCookieOnRenewedAuthentication;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceSingleSignOnParticipationPolicy() {
    }

    @Generated
    public DefaultRegisteredServiceSingleSignOnParticipationPolicy(TriStateBoolean createCookieOnRenewedAuthentication) {
        this.createCookieOnRenewedAuthentication = createCookieOnRenewedAuthentication;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceSingleSignOnParticipationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceSingleSignOnParticipationPolicy other = (DefaultRegisteredServiceSingleSignOnParticipationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        TriStateBoolean this$createCookieOnRenewedAuthentication = this.createCookieOnRenewedAuthentication;
        TriStateBoolean other$createCookieOnRenewedAuthentication = other.createCookieOnRenewedAuthentication;
        return !(this$createCookieOnRenewedAuthentication == null ? other$createCookieOnRenewedAuthentication != null : !this$createCookieOnRenewedAuthentication.equals(other$createCookieOnRenewedAuthentication));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceSingleSignOnParticipationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        TriStateBoolean $createCookieOnRenewedAuthentication = this.createCookieOnRenewedAuthentication;
        result = result * 59 + ($createCookieOnRenewedAuthentication == null ? 43 : $createCookieOnRenewedAuthentication.hashCode());
        return result;
    }
}

