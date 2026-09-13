/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Generated;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class HardTimeoutExpirationPolicy
extends AbstractCasExpirationPolicy {
    private static final long serialVersionUID = 6728077010285422290L;
    private long timeToKillInSeconds;

    @JsonCreator
    public HardTimeoutExpirationPolicy(@JsonProperty(value="timeToLive") long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        if (ticketState == null) {
            return true;
        }
        ZonedDateTime expiringTime = ticketState.getCreationTime().plus(this.timeToKillInSeconds, ChronoUnit.SECONDS);
        boolean expired = expiringTime.isBefore(ZonedDateTime.now(this.getClock()));
        return expired || super.isExpired(ticketState);
    }

    public Long getTimeToLive() {
        return this.timeToKillInSeconds;
    }

    @JsonIgnore
    public Long getTimeToIdle() {
        return 0L;
    }

    @Generated
    public static HardTimeoutExpirationPolicyBuilder builder() {
        return new HardTimeoutExpirationPolicyBuilder();
    }

    @Generated
    public HardTimeoutExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HardTimeoutExpirationPolicy)) {
            return false;
        }
        HardTimeoutExpirationPolicy other = (HardTimeoutExpirationPolicy)o;
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
        return other instanceof HardTimeoutExpirationPolicy;
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
        return "HardTimeoutExpirationPolicy(super=" + super.toString() + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
    }

    @Generated
    public static class HardTimeoutExpirationPolicyBuilder {
        @Generated
        private long timeToKillInSeconds;

        @Generated
        HardTimeoutExpirationPolicyBuilder() {
        }

        @Generated
        public HardTimeoutExpirationPolicyBuilder timeToKillInSeconds(long timeToKillInSeconds) {
            this.timeToKillInSeconds = timeToKillInSeconds;
            return this;
        }

        @Generated
        public HardTimeoutExpirationPolicy build() {
            return new HardTimeoutExpirationPolicy(this.timeToKillInSeconds);
        }

        @Generated
        public String toString() {
            return "HardTimeoutExpirationPolicy.HardTimeoutExpirationPolicyBuilder(timeToKillInSeconds=" + this.timeToKillInSeconds + ")";
        }
    }
}

