/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.ticket.AuthenticationAwareTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface TicketGrantingTicketAwareTicket
extends AuthenticationAwareTicket {
    public TicketGrantingTicket getTicketGrantingTicket();
}

