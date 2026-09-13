/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket.proxy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ProxyGrantingTicket
extends TicketGrantingTicket {
    public static final String PROXY_GRANTING_TICKET_PREFIX = "PGT";
    public static final String PROXY_GRANTING_TICKET_IOU_PREFIX = "PGTIOU";

    public ProxyTicket grantProxyTicket(String var1, Service var2, ExpirationPolicy var3, ServiceTicketSessionTrackingPolicy var4);
}

