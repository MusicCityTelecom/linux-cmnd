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
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.Assert
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class MultiTimeUseOrTimeoutExpirationPolicy
extends AbstractCasExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTimeUseOrTimeoutExpirationPolicy.class);
    private static final long serialVersionUID = -5704993954986738308L;
    @JsonProperty(value="timeToLive")
    private long timeToKillInSeconds;
    @JsonProperty(value="numberOfUses")
    private long numberOfUses;

    @JsonCreator
    public MultiTimeUseOrTimeoutExpirationPolicy(@JsonProperty(value="numberOfUses") long numberOfUses, @JsonProperty(value="timeToLive") long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        this.numberOfUses = numberOfUses;
        Assert.isTrue((numberOfUses > 0L ? 1 : 0) != 0, (String)"numberOfUses must be greater than 0.");
        Assert.isTrue((timeToKillInSeconds > 0L ? 1 : 0) != 0, (String)"timeToKillInSeconds must be greater than 0.");
    }

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        if (ticketState == null) {
            LOGGER.debug("Ticket state is null for [{}]. Ticket has expired.", (Object)this.getClass().getSimpleName());
            return true;
        }
        int countUses = ticketState.getCountOfUses();
        if ((long)countUses >= this.numberOfUses) {
            LOGGER.debug("Ticket usage count [{}] is greater than or equal to [{}]. Ticket [{}] has expired", new Object[]{countUses, this.numberOfUses, ticketState.getId()});
            return true;
        }
        ZonedDateTime systemTime = ZonedDateTime.now(this.getClock());
        ZonedDateTime creationTime = ticketState.getCreationTime();
        ZonedDateTime expiringTime = creationTime.plus(this.timeToKillInSeconds, ChronoUnit.SECONDS);
        if (expiringTime.isBefore(systemTime)) {
            LOGGER.debug("Ticket [{}] has expired; difference between current time [{}] and ticket creation time [{}] is greater than or equal to [{}].", new Object[]{ticketState.getId(), systemTime, creationTime, this.timeToKillInSeconds});
            return true;
        }
        return super.isExpired(ticketState);
    }

    public Long getTimeToLive() {
        return this.timeToKillInSeconds;
    }

    @JsonIgnore
    public Long getTimeToIdle() {
        return 0L;
    }

    @Generated
    public MultiTimeUseOrTimeoutExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MultiTimeUseOrTimeoutExpirationPolicy)) {
            return false;
        }
        MultiTimeUseOrTimeoutExpirationPolicy other = (MultiTimeUseOrTimeoutExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (this.timeToKillInSeconds != other.timeToKillInSeconds) {
            return false;
        }
        return this.numberOfUses == other.numberOfUses;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof MultiTimeUseOrTimeoutExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        long $timeToKillInSeconds = this.timeToKillInSeconds;
        result = result * 59 + (int)($timeToKillInSeconds >>> 32 ^ $timeToKillInSeconds);
        long $numberOfUses = this.numberOfUses;
        result = result * 59 + (int)($numberOfUses >>> 32 ^ $numberOfUses);
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "MultiTimeUseOrTimeoutExpirationPolicy(super=" + super.toString() + ", timeToKillInSeconds=" + this.timeToKillInSeconds + ", numberOfUses=" + this.numberOfUses + ")";
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    public static class TransientSessionTicketExpirationPolicy
    extends MultiTimeUseOrTimeoutExpirationPolicy {
        private static final long serialVersionUID = -5814201080268311070L;

        @JsonCreator
        public TransientSessionTicketExpirationPolicy(@JsonProperty(value="numberOfUses") long numberOfUses, @JsonProperty(value="timeToLive") long timeToKillInSeconds) {
            super(numberOfUses, timeToKillInSeconds);
        }

        @Override
        @Generated
        public String toString() {
            return "MultiTimeUseOrTimeoutExpirationPolicy.TransientSessionTicketExpirationPolicy(super=" + super.toString() + ")";
        }
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    public static class ServiceTicketExpirationPolicy
    extends MultiTimeUseOrTimeoutExpirationPolicy {
        private static final long serialVersionUID = -5814201080268311070L;

        @JsonCreator
        public ServiceTicketExpirationPolicy(@JsonProperty(value="numberOfUses") long numberOfUses, @JsonProperty(value="timeToLive") long timeToKillInSeconds) {
            super(numberOfUses, timeToKillInSeconds);
        }

        @Override
        @Generated
        public String toString() {
            return "MultiTimeUseOrTimeoutExpirationPolicy.ServiceTicketExpirationPolicy(super=" + super.toString() + ")";
        }
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    public static class ProxyTicketExpirationPolicy
    extends MultiTimeUseOrTimeoutExpirationPolicy {
        private static final long serialVersionUID = -5814201080268311070L;

        @JsonCreator
        public ProxyTicketExpirationPolicy(@JsonProperty(value="numberOfUses") long numberOfUses, @JsonProperty(value="timeToLive") long timeToKillInSeconds) {
            super(numberOfUses, timeToKillInSeconds);
        }

        @Override
        @Generated
        public String toString() {
            return "MultiTimeUseOrTimeoutExpirationPolicy.ProxyTicketExpirationPolicy(super=" + super.toString() + ")";
        }
    }
}

