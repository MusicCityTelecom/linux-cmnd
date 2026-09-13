/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy
implements RegisteredServiceProxyGrantingTicketExpirationPolicy {
    private static final long serialVersionUID = 1122553887352573119L;
    private long maxTimeToLiveInSeconds;

    @Generated
    public long getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy setMaxTimeToLiveInSeconds(long maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy other = (DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.maxTimeToLiveInSeconds == other.maxTimeToLiveInSeconds;
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy;
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
    public DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy(long maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
    }

    @Generated
    public DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy() {
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy(maxTimeToLiveInSeconds=" + this.maxTimeToLiveInSeconds + ")";
    }
}

