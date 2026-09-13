/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 */
package org.apereo.cas.ticket.proxy;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;

public interface ProxyGrantingTicketFactory
extends TicketFactory {
    public <T extends ProxyGrantingTicket> T create(ServiceTicket var1, Authentication var2, Class<T> var3) throws AbstractTicketException;
}

