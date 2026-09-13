/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.time.Clock;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ExpirationPolicy
extends Serializable {
    public boolean isExpired(TicketGrantingTicketAwareTicket var1);

    default public Long getTimeToLive(Ticket ticketState) {
        return this.getTimeToLive();
    }

    public Long getTimeToLive();

    public Long getTimeToIdle();

    public String getName();

    @JsonIgnore
    public Clock getClock();
}

