/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.Assert
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Generated;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TicketGrantingTicketExpirationPolicy
extends AbstractCasExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGrantingTicketExpirationPolicy.class);
    private static final long serialVersionUID = 7670537200691354820L;
    private long maxTimeToLiveInSeconds;
    private long timeToKillInSeconds;

    @JsonCreator
    public TicketGrantingTicketExpirationPolicy(@JsonProperty(value="timeToLive") long maxTimeToLive, @JsonProperty(value="timeToIdle") long timeToKill) {
        this.maxTimeToLiveInSeconds = maxTimeToLive;
        this.timeToKillInSeconds = timeToKill;
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        Assert.isTrue((this.maxTimeToLiveInSeconds >= this.timeToKillInSeconds ? 1 : 0) != 0, (String)"maxTimeToLiveInSeconds must be greater than or equal to timeToKillInSeconds.");
        ZonedDateTime currentSystemTime = ZonedDateTime.now(this.getClock());
        ZonedDateTime creationTime = ticketState.getCreationTime();
        ZonedDateTime lastTimeUsed = ticketState.getLastTimeUsed();
        ZonedDateTime expirationTime = creationTime.plus(this.maxTimeToLiveInSeconds, ChronoUnit.SECONDS);
        if (currentSystemTime.isAfter(expirationTime)) {
            LOGGER.debug("Ticket is expired because the time since creation [{}] is greater than current system time [{}]", (Object)expirationTime, (Object)currentSystemTime);
            return true;
        }
        ZonedDateTime expirationTimeKill = lastTimeUsed.plus(this.timeToKillInSeconds, ChronoUnit.SECONDS);
        if (currentSystemTime.isAfter(expirationTimeKill)) {
            LOGGER.debug("Ticket is expired because the time since last use is greater than timeToKillInSeconds");
            return true;
        }
        return super.isExpired(ticketState);
    }

    public Long getTimeToLive() {
        return this.maxTimeToLiveInSeconds;
    }

    public Long getTimeToIdle() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public static TicketGrantingTicketExpirationPolicyBuilder builder() {
        return new TicketGrantingTicketExpirationPolicyBuilder();
    }

    @Generated
    public TicketGrantingTicketExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TicketGrantingTicketExpirationPolicy)) {
            return false;
        }
        TicketGrantingTicketExpirationPolicy other = (TicketGrantingTicketExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.maxTimeToLiveInSeconds != other.maxTimeToLiveInSeconds) {
            return false;
        }
        return this.timeToKillInSeconds == other.timeToKillInSeconds;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof TicketGrantingTicketExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $maxTimeToLiveInSeconds = this.maxTimeToLiveInSeconds;
        result = result * 59 + (int)($maxTimeToLiveInSeconds >>> 32 ^ $maxTimeToLiveInSeconds);
        long $timeToKillInSeconds = this.timeToKillInSeconds;
        result = result * 59 + (int)($timeToKillInSeconds >>> 32 ^ $timeToKillInSeconds);
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "TicketGrantingTicketExpirationPolicy(super=" + super.toString() + ", maxTimeToLiveInSeconds=" + this.maxTimeToLiveInSeconds + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
    }

    @Generated
    public static class TicketGrantingTicketExpirationPolicyBuilder {
        @Generated
        private long maxTimeToLiveInSeconds;
        @Generated
        private long timeToKillInSeconds;

        @Generated
        TicketGrantingTicketExpirationPolicyBuilder() {
        }

        @Generated
        public TicketGrantingTicketExpirationPolicyBuilder maxTimeToLiveInSeconds(long maxTimeToLiveInSeconds) {
            this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
            return this;
        }

        @Generated
        public TicketGrantingTicketExpirationPolicyBuilder timeToKillInSeconds(long timeToKillInSeconds) {
            this.timeToKillInSeconds = timeToKillInSeconds;
            return this;
        }

        @Generated
        public TicketGrantingTicketExpirationPolicy build() {
            return new TicketGrantingTicketExpirationPolicy(this.maxTimeToLiveInSeconds, this.timeToKillInSeconds);
        }

        @Generated
        public String toString() {
            return "TicketGrantingTicketExpirationPolicy.TicketGrantingTicketExpirationPolicyBuilder(maxTimeToLiveInSeconds=" + this.maxTimeToLiveInSeconds + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
        }
    }
}

