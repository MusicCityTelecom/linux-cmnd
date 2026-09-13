/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 */
package org.apereo.cas.ticket.expiration.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ProxyGrantingTicketExpirationPolicyBuilder
implements ExpirationPolicyBuilder<ProxyGrantingTicket> {
    private static final long serialVersionUID = -1597980180617072826L;
    protected final ExpirationPolicyBuilder<TicketGrantingTicket> ticketGrantingTicketExpirationPolicyBuilder;
    protected final CasConfigurationProperties casProperties;

    public ExpirationPolicy buildTicketExpirationPolicy() {
        return this.ticketGrantingTicketExpirationPolicyBuilder.buildTicketExpirationPolicy();
    }

    @Generated
    public ProxyGrantingTicketExpirationPolicyBuilder(ExpirationPolicyBuilder<TicketGrantingTicket> ticketGrantingTicketExpirationPolicyBuilder, CasConfigurationProperties casProperties) {
        this.ticketGrantingTicketExpirationPolicyBuilder = ticketGrantingTicketExpirationPolicyBuilder;
        this.casProperties = casProperties;
    }

    @Generated
    public String toString() {
        return "ProxyGrantingTicketExpirationPolicyBuilder(ticketGrantingTicketExpirationPolicyBuilder=" + this.ticketGrantingTicketExpirationPolicyBuilder + ", casProperties=" + this.casProperties + ")";
    }

    @Generated
    public ExpirationPolicyBuilder<TicketGrantingTicket> getTicketGrantingTicketExpirationPolicyBuilder() {
        return this.ticketGrantingTicketExpirationPolicyBuilder;
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }
}

