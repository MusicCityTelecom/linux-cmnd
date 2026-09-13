/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy
implements RegisteredServiceTicketGrantingTicketExpirationPolicy {
    private static final long serialVersionUID = 1122553887352573119L;
    private long maxTimeToLiveInSeconds;

    public Optional<ExpirationPolicy> toExpirationPolicy() {
        if (this.getMaxTimeToLiveInSeconds() > 0L) {
            return Optional.of(new HardTimeoutExpirationPolicy(this.getMaxTimeToLiveInSeconds()));
        }
        return Optional.empty();
    }

    @Generated
    public long getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy setMaxTimeToLiveInSeconds(long maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy other = (DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.maxTimeToLiveInSeconds == other.maxTimeToLiveInSeconds;
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $maxTimeToLiveInSeconds = this.maxTimeToLiveInSeconds;
        result = result * 59 + (int)($maxTimeToLiveInSeconds >>> 32 ^ $maxTimeToLiveInSeconds);
        return result;
    }

    @Generated
    public DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy(long maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
    }

    @Generated
    public DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy() {
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy(maxTimeToLiveInSeconds=" + this.maxTimeToLiveInSeconds + ")";
    }
}

