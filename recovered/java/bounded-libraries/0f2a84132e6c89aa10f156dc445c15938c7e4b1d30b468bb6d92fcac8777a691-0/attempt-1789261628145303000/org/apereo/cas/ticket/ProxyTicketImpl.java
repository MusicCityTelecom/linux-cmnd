/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ServiceTicketImpl;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ProxyTicketImpl
extends ServiceTicketImpl
implements ProxyTicket {
    private static final long serialVersionUID = -4469960563289285371L;

    @JsonCreator
    public ProxyTicketImpl(@JsonProperty(value="id") String id, @JsonProperty(value="ticketGrantingTicket") TicketGrantingTicket ticket, @JsonProperty(value="service") Service service, @JsonProperty(value="credentialProvided") boolean credentialProvided, @JsonProperty(value="expirationPolicy") ExpirationPolicy policy) {
        super(id, ticket, service, credentialProvided, policy);
    }

    @Override
    public String getPrefix() {
        return "PT";
    }

    @Generated
    public ProxyTicketImpl() {
    }
}

