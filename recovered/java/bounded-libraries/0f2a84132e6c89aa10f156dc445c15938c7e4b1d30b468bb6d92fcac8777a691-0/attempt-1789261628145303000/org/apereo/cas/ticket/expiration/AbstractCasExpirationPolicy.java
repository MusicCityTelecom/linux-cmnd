/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.TicketGrantingTicketAwareTicket
 */
package org.apereo.cas.ticket.expiration;

import java.time.Clock;
import java.util.UUID;
import lombok.Generated;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;

public abstract class AbstractCasExpirationPolicy
implements ExpirationPolicy {
    private static final long serialVersionUID = 8042104336580063690L;
    private String name;
    private Clock clock = Clock.systemUTC();

    protected AbstractCasExpirationPolicy() {
        this.name = this.getClass().getSimpleName() + "-" + UUID.randomUUID();
    }

    public boolean isExpired(TicketGrantingTicketAwareTicket ticketState) {
        TicketGrantingTicket tgt = ticketState.getTicketGrantingTicket();
        return tgt != null && tgt.isExpired();
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Clock getClock() {
        return this.clock;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Generated
    public String toString() {
        return "AbstractCasExpirationPolicy(name=" + this.name + ", clock=" + this.clock + ")";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractCasExpirationPolicy)) {
            return false;
        }
        AbstractCasExpirationPolicy other = (AbstractCasExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Clock this$clock = this.clock;
        Clock other$clock = other.clock;
        return !(this$clock == null ? other$clock != null : !((Object)this$clock).equals(other$clock));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractCasExpirationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Clock $clock = this.clock;
        result = result * 59 + ($clock == null ? 43 : ((Object)$clock).hashCode());
        return result;
    }
}

