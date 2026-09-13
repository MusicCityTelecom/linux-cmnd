/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ServiceTicket
extends TicketGrantingTicketAwareTicket {
    public static final String PREFIX = "ST";

    public Service getService();
}

