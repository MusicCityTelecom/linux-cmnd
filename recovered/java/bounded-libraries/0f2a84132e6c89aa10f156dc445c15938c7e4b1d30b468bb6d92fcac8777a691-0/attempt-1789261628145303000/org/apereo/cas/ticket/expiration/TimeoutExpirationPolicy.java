/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Generated;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TimeoutExpirationPolicy
extends AbstractCasExpirationPolicy {
    private static final long serialVersionUID = -7636642464326939536L;
    private long timeToKillInSeconds;

    @JsonCreator
    public TimeoutExpirationPolicy(@JsonProperty(value="timeToIdle") long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        ZonedDateTime expirationTime;
        if (ticketState == null) {
            return true;
        }
        ZonedDateTime now = ZonedDateTime.now(this.getClock());
        boolean expired = now.isAfter(expirationTime = ticketState.getLastTimeUsed().plus(this.timeToKillInSeconds, ChronoUnit.SECONDS));
        return expired || super.isExpired(ticketState);
    }

    @JsonIgnore
    public Long getTimeToLive() {
        return Long.MAX_VALUE;
    }

    public Long getTimeToIdle() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public static TimeoutExpirationPolicyBuilder builder() {
        return new TimeoutExpirationPolicyBuilder();
    }

    @Generated
    public TimeoutExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimeoutExpirationPolicy)) {
            return false;
        }
        TimeoutExpirationPolicy other = (TimeoutExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.timeToKillInSeconds == other.timeToKillInSeconds;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof TimeoutExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $timeToKillInSeconds = this.timeToKillInSeconds;
        result = result * 59 + (int)($timeToKillInSeconds >>> 32 ^ $timeToKillInSeconds);
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "TimeoutExpirationPolicy(super=" + super.toString() + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
    }

    @Generated
    public static class TimeoutExpirationPolicyBuilder {
        @Generated
        private long timeToKillInSeconds;

        @Generated
        TimeoutExpirationPolicyBuilder() {
        }

        @Generated
        public TimeoutExpirationPolicyBuilder timeToKillInSeconds(long timeToKillInSeconds) {
            this.timeToKillInSeconds = timeToKillInSeconds;
            return this;
        }

        @Generated
        public TimeoutExpirationPolicy build() {
            return new TimeoutExpirationPolicy(this.timeToKillInSeconds);
        }

        @Generated
        public String toString() {
            return "TimeoutExpirationPolicy.TimeoutExpirationPolicyBuilder(timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
        }
    }
}

