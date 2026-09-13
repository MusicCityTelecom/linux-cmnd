/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceServiceTicketExpirationPolicy
implements RegisteredServiceServiceTicketExpirationPolicy {
    private static final long serialVersionUID = -6745109870746310448L;
    private long numberOfUses;
    private String timeToLive;

    @Generated
    public long getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public String getTimeToLive() {
        return this.timeToLive;
    }

    @Generated
    public DefaultRegisteredServiceServiceTicketExpirationPolicy setNumberOfUses(long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceServiceTicketExpirationPolicy setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceServiceTicketExpirationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceServiceTicketExpirationPolicy other = (DefaultRegisteredServiceServiceTicketExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.numberOfUses != other.numberOfUses) {
            return false;
        }
        String this$timeToLive = this.timeToLive;
        String other$timeToLive = other.timeToLive;
        return !(this$timeToLive == null ? other$timeToLive != null : !this$timeToLive.equals(other$timeToLive));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceServiceTicketExpirationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $numberOfUses = this.numberOfUses;
        result = result * 59 + (int)($numberOfUses >>> 32 ^ $numberOfUses);
        String $timeToLive = this.timeToLive;
        result = result * 59 + ($timeToLive == null ? 43 : $timeToLive.hashCode());
        return result;
    }

    @Generated
    public DefaultRegisteredServiceServiceTicketExpirationPolicy(long numberOfUses, String timeToLive) {
        this.numberOfUses = numberOfUses;
        this.timeToLive = timeToLive;
    }

    @Generated
    public DefaultRegisteredServiceServiceTicketExpirationPolicy() {
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceServiceTicketExpirationPolicy(numberOfUses=" + this.numberOfUses + ", timeToLive=" + this.timeToLive + ")";
    }
}

