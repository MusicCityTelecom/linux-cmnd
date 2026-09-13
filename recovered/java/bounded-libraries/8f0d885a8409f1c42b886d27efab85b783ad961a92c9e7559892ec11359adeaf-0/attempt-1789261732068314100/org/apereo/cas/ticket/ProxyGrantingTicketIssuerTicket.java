/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.Authentication
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ProxyGrantingTicketIssuerTicket
extends Ticket {
    public ProxyGrantingTicket grantProxyGrantingTicket(String var1, Authentication var2, ExpirationPolicy var3) throws AbstractTicketException;
}

