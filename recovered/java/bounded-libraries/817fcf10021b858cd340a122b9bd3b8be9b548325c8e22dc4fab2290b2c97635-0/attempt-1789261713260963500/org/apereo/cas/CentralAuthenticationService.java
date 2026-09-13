/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.AbstractTicketException
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketFactory
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 *  org.apereo.cas.validation.Assertion
 */
package org.apereo.cas;

import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;
import org.apereo.cas.validation.Assertion;

public interface CentralAuthenticationService {
    public static final String BEAN_NAME = "centralAuthenticationService";
    public static final String NAMESPACE = CentralAuthenticationService.class.getPackage().getName();

    public TicketGrantingTicket createTicketGrantingTicket(AuthenticationResult var1) throws AuthenticationException, AbstractTicketException;

    public ServiceTicket grantServiceTicket(String var1, Service var2, AuthenticationResult var3) throws AuthenticationException, AbstractTicketException;

    public ProxyTicket grantProxyTicket(String var1, Service var2) throws AbstractTicketException;

    public Assertion validateServiceTicket(String var1, Service var2) throws AbstractTicketException;

    public ProxyGrantingTicket createProxyGrantingTicket(String var1, AuthenticationResult var2) throws AuthenticationException, AbstractTicketException;

    public TicketFactory getTicketFactory();
}

