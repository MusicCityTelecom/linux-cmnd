/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ProxyTicketImpl;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ProxyGrantingTicketImpl
extends TicketGrantingTicketImpl
implements ProxyGrantingTicket {
    private static final long serialVersionUID = -8126909926138945649L;

    public ProxyGrantingTicketImpl(String id, Authentication authentication, ExpirationPolicy policy) {
        super(id, authentication, policy);
    }

    @JsonCreator
    public ProxyGrantingTicketImpl(@JsonProperty(value="id") String id, @JsonProperty(value="proxiedBy") Service proxiedBy, @JsonProperty(value="ticketGrantingTicket") TicketGrantingTicket parentTicketGrantingTicket, @JsonProperty(value="authentication") Authentication authentication, @JsonProperty(value="expirationPolicy") ExpirationPolicy expirationPolicy) {
        super(id, proxiedBy, parentTicketGrantingTicket, authentication, expirationPolicy);
    }

    public ProxyTicket grantProxyTicket(String id, Service service, ExpirationPolicy expirationPolicy, ServiceTicketSessionTrackingPolicy trackingPolicy) {
        ProxyTicketImpl serviceTicket = new ProxyTicketImpl(id, (TicketGrantingTicket)this, service, false, expirationPolicy);
        trackingPolicy.track((AuthenticatedServicesAwareTicketGrantingTicket)this, (ServiceTicket)serviceTicket);
        return serviceTicket;
    }

    @Override
    public String getPrefix() {
        return "PGT";
    }

    @Generated
    public ProxyGrantingTicketImpl() {
    }
}

