/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.ServiceTicket;

@FunctionalInterface
public interface ServiceTicketSessionTrackingPolicy {
    public static final String BEAN_NAME = "serviceTicketSessionTrackingPolicy";

    public void track(AuthenticatedServicesAwareTicketGrantingTicket var1, ServiceTicket var2);
}

