/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.ticket.ServiceTicketProperties
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.ServiceTicket
 */
package org.apereo.cas.ticket.expiration.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.ticket.ServiceTicketProperties;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ServiceTicketExpirationPolicyBuilder
implements ExpirationPolicyBuilder<ServiceTicket> {
    private static final long serialVersionUID = -3597980180617072826L;
    protected final CasConfigurationProperties casProperties;

    public ExpirationPolicy buildTicketExpirationPolicy() {
        return this.toServiceTicketExpirationPolicy();
    }

    public ExpirationPolicy toServiceTicketExpirationPolicy() {
        ServiceTicketProperties st = this.casProperties.getTicket().getSt();
        return new MultiTimeUseOrTimeoutExpirationPolicy.ServiceTicketExpirationPolicy(st.getNumberOfUses(), st.getTimeToKillInSeconds());
    }

    @Generated
    public ServiceTicketExpirationPolicyBuilder(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Generated
    public String toString() {
        return "ServiceTicketExpirationPolicyBuilder(casProperties=" + this.casProperties + ")";
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }
}

