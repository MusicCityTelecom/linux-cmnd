/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.ticket.ProxyTicketProperties
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 */
package org.apereo.cas.ticket.expiration.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.ticket.ProxyTicketProperties;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;
import org.apereo.cas.ticket.proxy.ProxyTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ProxyTicketExpirationPolicyBuilder
implements ExpirationPolicyBuilder<ProxyTicket> {
    private static final long serialVersionUID = -2597980180617072826L;
    protected final CasConfigurationProperties casProperties;

    public ExpirationPolicy buildTicketExpirationPolicy() {
        return this.toProxyTicketExpirationPolicy();
    }

    public ExpirationPolicy toProxyTicketExpirationPolicy() {
        ProxyTicketProperties pt = this.casProperties.getTicket().getPt();
        return new MultiTimeUseOrTimeoutExpirationPolicy.ProxyTicketExpirationPolicy(pt.getNumberOfUses(), pt.getTimeToKillInSeconds());
    }

    @Generated
    public ProxyTicketExpirationPolicyBuilder(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Generated
    public String toString() {
        return "ProxyTicketExpirationPolicyBuilder(casProperties=" + this.casProperties + ")";
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }
}

