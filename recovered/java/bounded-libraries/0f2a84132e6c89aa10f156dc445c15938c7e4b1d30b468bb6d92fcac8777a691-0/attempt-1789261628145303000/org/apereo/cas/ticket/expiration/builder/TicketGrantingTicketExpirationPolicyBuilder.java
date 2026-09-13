/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.ticket.TicketGrantingTicketProperties
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.expiration.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.ticket.TicketGrantingTicketProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.expiration.AlwaysExpiresExpirationPolicy;
import org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy;
import org.apereo.cas.ticket.expiration.NeverExpiresExpirationPolicy;
import org.apereo.cas.ticket.expiration.RememberMeDelegatingExpirationPolicy;
import org.apereo.cas.ticket.expiration.ThrottledUseAndTimeoutExpirationPolicy;
import org.apereo.cas.ticket.expiration.TicketGrantingTicketExpirationPolicy;
import org.apereo.cas.ticket.expiration.TimeoutExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TicketGrantingTicketExpirationPolicyBuilder
implements ExpirationPolicyBuilder<TicketGrantingTicket> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGrantingTicketExpirationPolicyBuilder.class);
    private static final long serialVersionUID = -4197980180617072826L;
    protected final CasConfigurationProperties casProperties;

    public ExpirationPolicy buildTicketExpirationPolicy() {
        TicketGrantingTicketProperties tgt = this.casProperties.getTicket().getTgt();
        if (tgt.getRememberMe().isEnabled()) {
            ExpirationPolicy policy = this.toRememberMeTicketExpirationPolicy();
            LOGGER.debug("Final effective time-to-live of remember-me expiration policy is [{}] seconds", (Object)policy.getTimeToLive());
            return policy;
        }
        ExpirationPolicy policy = this.toTicketGrantingTicketExpirationPolicy();
        LOGGER.debug("Final effective time-to-live of ticket-granting ticket expiration policy is [{}] seconds", (Object)policy.getTimeToLive());
        return policy;
    }

    protected ExpirationPolicy toTicketGrantingTicketExpirationPolicy() {
        TicketGrantingTicketProperties tgt = this.casProperties.getTicket().getTgt();
        if (Beans.isInfinitelyDurable((String)tgt.getPrimary().getMaxTimeToLiveInSeconds()) && Beans.isInfinitelyDurable((String)tgt.getPrimary().getTimeToKillInSeconds())) {
            LOGGER.warn("Primary ticket-granting ticket expiration policy is set to NEVER expire tickets.");
            return NeverExpiresExpirationPolicy.INSTANCE;
        }
        if (Beans.isNeverDurable((String)tgt.getPrimary().getMaxTimeToLiveInSeconds()) && Beans.isNeverDurable((String)tgt.getPrimary().getTimeToKillInSeconds())) {
            LOGGER.warn("Ticket-granting ticket expiration policy is set to ALWAYS expire tickets.");
            return AlwaysExpiresExpirationPolicy.INSTANCE;
        }
        if (StringUtils.isNotBlank((CharSequence)tgt.getTimeout().getMaxTimeToLiveInSeconds())) {
            long seconds = Beans.newDuration((String)tgt.getTimeout().getMaxTimeToLiveInSeconds()).toSeconds();
            LOGGER.debug("Ticket-granting ticket expiration policy is based on a timeout of [{}] seconds", (Object)seconds);
            return new TimeoutExpirationPolicy(seconds);
        }
        if (StringUtils.isNotBlank((CharSequence)tgt.getThrottledTimeout().getTimeInBetweenUsesInSeconds()) && StringUtils.isNotBlank((CharSequence)tgt.getThrottledTimeout().getTimeToKillInSeconds())) {
            ThrottledUseAndTimeoutExpirationPolicy policy = new ThrottledUseAndTimeoutExpirationPolicy();
            long seconds = Beans.newDuration((String)tgt.getThrottledTimeout().getTimeToKillInSeconds()).toSeconds();
            long timeInBetweenSeconds = Beans.newDuration((String)tgt.getThrottledTimeout().getTimeInBetweenUsesInSeconds()).toSeconds();
            policy.setTimeToKillInSeconds(seconds);
            policy.setTimeInBetweenUsesInSeconds(timeInBetweenSeconds);
            LOGGER.debug("Ticket-granting ticket expiration policy is based on throttled timeouts");
            return policy;
        }
        if (StringUtils.isNotBlank((CharSequence)tgt.getHardTimeout().getTimeToKillInSeconds())) {
            long seconds = Beans.newDuration((String)tgt.getHardTimeout().getTimeToKillInSeconds()).toSeconds();
            LOGGER.debug("Ticket-granting ticket expiration policy is based on a hard timeout of [{}] seconds", (Object)seconds);
            return new HardTimeoutExpirationPolicy(seconds);
        }
        long maxTimePrimarySeconds = Beans.newDuration((String)tgt.getPrimary().getMaxTimeToLiveInSeconds()).toSeconds();
        long ttlPrimarySeconds = Beans.newDuration((String)tgt.getPrimary().getTimeToKillInSeconds()).toSeconds();
        LOGGER.debug("Ticket-granting ticket expiration policy is based on hard/idle timeouts of [{}]/[{}] seconds", (Object)maxTimePrimarySeconds, (Object)ttlPrimarySeconds);
        return new TicketGrantingTicketExpirationPolicy(maxTimePrimarySeconds, ttlPrimarySeconds);
    }

    public ExpirationPolicy toRememberMeTicketExpirationPolicy() {
        TicketGrantingTicketProperties tgt = this.casProperties.getTicket().getTgt();
        LOGGER.debug("Remember me expiration policy is being configured based on hard timeout of [{}] seconds", (Object)tgt.getRememberMe().getTimeToKillInSeconds());
        HardTimeoutExpirationPolicy rememberMePolicy = new HardTimeoutExpirationPolicy(tgt.getRememberMe().getTimeToKillInSeconds());
        RememberMeDelegatingExpirationPolicy p = new RememberMeDelegatingExpirationPolicy();
        p.addPolicy("REMEMBER_ME", rememberMePolicy);
        p.addPolicy("DEFAULT", this.toTicketGrantingTicketExpirationPolicy());
        return p;
    }

    @Generated
    public TicketGrantingTicketExpirationPolicyBuilder(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Generated
    public String toString() {
        return "TicketGrantingTicketExpirationPolicyBuilder(casProperties=" + this.casProperties + ")";
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }
}

