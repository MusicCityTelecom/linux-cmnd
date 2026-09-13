/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.TicketGrantingTicketAwareTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface TicketGrantingTicket
extends TicketGrantingTicketAwareTicket {
    public static final String PREFIX = "TGT";

    public ServiceTicket grantServiceTicket(String var1, Service var2, ExpirationPolicy var3, boolean var4, ServiceTicketSessionTrackingPolicy var5);

    public Map<String, Service> getProxyGrantingTickets();

    public void removeAllServices();

    public boolean isRoot();

    public TicketGrantingTicket getRoot();

    public List<Authentication> getChainedAuthentications();

    public Service getProxiedBy();

    default public Collection<String> getDescendantTickets() {
        return new HashSet<String>(0);
    }
}

