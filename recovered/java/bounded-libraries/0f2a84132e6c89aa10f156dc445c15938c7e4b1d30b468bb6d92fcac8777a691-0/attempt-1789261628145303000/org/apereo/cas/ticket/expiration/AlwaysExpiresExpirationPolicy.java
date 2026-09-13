/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 */
package org.apereo.cas.ticket.expiration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;
import org.apereo.cas.ticket.expiration.AbstractCasExpirationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AlwaysExpiresExpirationPolicy
extends AbstractCasExpirationPolicy {
    public static final ExpirationPolicy INSTANCE = new AlwaysExpiresExpirationPolicy();
    private static final long serialVersionUID = 3836547698242303540L;

    @Override
    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        return true;
    }

    @JsonIgnore
    public Long getTimeToLive() {
        return 0L;
    }

    @JsonIgnore
    public Long getTimeToIdle() {
        return 0L;
    }

    @Generated
    public AlwaysExpiresExpirationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AlwaysExpiresExpirationPolicy)) {
            return false;
        }
        AlwaysExpiresExpirationPolicy other = (AlwaysExpiresExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AlwaysExpiresExpirationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

