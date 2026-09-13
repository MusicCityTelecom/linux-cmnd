/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.ticket.TransientSessionTicketProperties
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.TransientSessionTicket
 */
package org.apereo.cas.ticket.expiration.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.ticket.TransientSessionTicketProperties;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.TransientSessionTicket;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TransientSessionTicketExpirationPolicyBuilder
implements ExpirationPolicyBuilder<TransientSessionTicket> {
    private static final long serialVersionUID = -1587980180617072826L;
    protected final CasConfigurationProperties casProperties;

    public ExpirationPolicy buildTicketExpirationPolicy() {
        return this.toTransientSessionTicketExpirationPolicy();
    }

    public ExpirationPolicy toTransientSessionTicketExpirationPolicy() {
        TransientSessionTicketProperties tst = this.casProperties.getTicket().getTst();
        return new MultiTimeUseOrTimeoutExpirationPolicy.TransientSessionTicketExpirationPolicy(tst.getNumberOfUses(), tst.getTimeToKillInSeconds());
    }

    @Generated
    public TransientSessionTicketExpirationPolicyBuilder(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Generated
    public String toString() {
        return "TransientSessionTicketExpirationPolicyBuilder(casProperties=" + this.casProperties + ")";
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }
}

