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
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Duration;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ThrottledUseAndTimeoutExpirationPolicy
extends AbstractCasExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ThrottledUseAndTimeoutExpirationPolicy.class);
    private static final long serialVersionUID = 205979491183779408L;
    private long timeToKillInSeconds;
    private long timeInBetweenUsesInSeconds;

    @JsonCreator
    public ThrottledUseAndTimeoutExpirationPolicy(@JsonProperty(value="timeToLive") long timeToKillInSeconds, @JsonProperty(value="timeToIdle") long timeInBetweenUsesInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        this.timeInBetweenUsesInSeconds = timeInBetweenUsesInSeconds;
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        LOGGER.trace("Checking validity of ticket [{}]", (Object)ticketState);
        ZonedDateTime lastTimeUsed = ticketState.getLastTimeUsed();
        ZonedDateTime currentTime = ZonedDateTime.now(this.getClock());
        LOGGER.trace("Current time is [{}]. Ticket last used time is [{}]", (Object)currentTime, (Object)lastTimeUsed);
        long margin = Duration.between(lastTimeUsed, currentTime).toSeconds();
        LOGGER.trace("The duration in seconds between current time and last used time is [{}]", (Object)margin);
        if (ticketState.getCountOfUses() == 0 && margin < this.timeToKillInSeconds) {
            LOGGER.debug("Valid [{}]: Usage count is zero and number of seconds since ticket usage time [{}] is less than [{}]", new Object[]{ticketState, margin, this.timeToKillInSeconds});
            return super.isExpired(ticketState);
        }
        if (margin >= this.timeToKillInSeconds) {
            LOGGER.debug("Expired [{}]: number of seconds since ticket usage time [{}] is greater than or equal to [{}]", new Object[]{ticketState, margin, this.timeToKillInSeconds});
            return true;
        }
        if (margin > 0L && margin <= this.timeInBetweenUsesInSeconds) {
            LOGGER.warn("Expired [{}]: number of seconds since ticket usage time [{}] is less than or equal to time in between uses in seconds [{}]", new Object[]{ticketState, margin, this.timeInBetweenUsesInSeconds});
            return true;
        }
        return super.isExpired(ticketState);
    }

    public Long getTimeToLive() {
        return this.timeToKillInSeconds;
    }

    public Long getTimeToIdle() {
        return this.timeInBetweenUsesInSeconds;
    }

    @Generated
    public void setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
    }

    @Generated
    public void setTimeInBetweenUsesInSeconds(long timeInBetweenUsesInSeconds) {
        this.timeInBetweenUsesInSeconds = timeInBetweenUsesInSeconds;
    }

    @Generated
    public ThrottledUseAndTimeoutExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ThrottledUseAndTimeoutExpirationPolicy)) {
            return false;
        }
        ThrottledUseAndTimeoutExpirationPolicy other = (ThrottledUseAndTimeoutExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.timeToKillInSeconds != other.timeToKillInSeconds) {
            return false;
        }
        return this.timeInBetweenUsesInSeconds == other.timeInBetweenUsesInSeconds;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ThrottledUseAndTimeoutExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $timeToKillInSeconds = this.timeToKillInSeconds;
        result = result * 59 + (int)($timeToKillInSeconds >>> 32 ^ $timeToKillInSeconds);
        long $timeInBetweenUsesInSeconds = this.timeInBetweenUsesInSeconds;
        result = result * 59 + (int)($timeInBetweenUsesInSeconds >>> 32 ^ $timeInBetweenUsesInSeconds);
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "ThrottledUseAndTimeoutExpirationPolicy(super=" + super.toString() + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ", timeInBetweenUsesInSeconds=" + this.timeInBetweenUsesInSeconds + ")";
    }
}

