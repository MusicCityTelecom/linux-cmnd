/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class NeverExpiresExpirationPolicy
extends AbstractCasExpirationPolicy {
    public static final ExpirationPolicy INSTANCE = new NeverExpiresExpirationPolicy();
    private static final long serialVersionUID = 3833747698242303540L;

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        return false;
    }

    @JsonIgnore
    public Long getTimeToLive() {
        return Integer.MAX_VALUE;
    }

    @JsonIgnore
    public Long getTimeToIdle() {
        return Integer.MAX_VALUE;
    }

    @Generated
    public NeverExpiresExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NeverExpiresExpirationPolicy)) {
            return false;
        }
        NeverExpiresExpirationPolicy other = (NeverExpiresExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof NeverExpiresExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "NeverExpiresExpirationPolicy(super=" + super.toString() + ")";
    }
}

