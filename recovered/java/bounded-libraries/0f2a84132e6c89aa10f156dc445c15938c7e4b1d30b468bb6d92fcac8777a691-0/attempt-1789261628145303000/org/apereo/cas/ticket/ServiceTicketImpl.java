/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.AbstractTicketException
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ProxyGrantingTicketIssuerTicket
 *  org.apereo.cas.ticket.RenewableServiceTicket
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicket;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.InvalidProxyGrantingTicketForServiceTicketException;
import org.apereo.cas.ticket.ProxyGrantingTicketImpl;
import org.apereo.cas.ticket.ProxyGrantingTicketIssuerTicket;
import org.apereo.cas.ticket.RenewableServiceTicket;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class ServiceTicketImpl
extends AbstractTicket
implements ServiceTicket,
RenewableServiceTicket,
ProxyGrantingTicketIssuerTicket {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTicketImpl.class);
    private static final long serialVersionUID = -4223319704861765405L;
    @JsonProperty(value="ticketGrantingTicket")
    private TicketGrantingTicket ticketGrantingTicket;
    private Service service;
    private boolean fromNewLogin;
    private Boolean grantedTicketAlready = Boolean.FALSE;

    @JsonCreator
    public ServiceTicketImpl(@JsonProperty(value="id") @NonNull String id, @JsonProperty(value="ticketGrantingTicket") @NonNull TicketGrantingTicket ticket, @JsonProperty(value="service") @NonNull Service service, @JsonProperty(value="credentialProvided") boolean credentialProvided, @JsonProperty(value="expirationPolicy") ExpirationPolicy policy) {
        super(id, policy);
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        if (ticket == null) {
            throw new NullPointerException("ticket is marked non-null but is null");
        }
        if (service == null) {
            throw new NullPointerException("service is marked non-null but is null");
        }
        this.ticketGrantingTicket = ticket;
        this.service = service;
        this.fromNewLogin = credentialProvided || ticket.getCountOfUses() == 0;
    }

    public ProxyGrantingTicket grantProxyGrantingTicket(@NonNull String id, @NonNull Authentication authentication, ExpirationPolicy expirationPolicy) throws AbstractTicketException {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        if (authentication == null) {
            throw new NullPointerException("authentication is marked non-null but is null");
        }
        if (this.grantedTicketAlready.booleanValue()) {
            LOGGER.warn("Service ticket [{}] issued for service [{}] has already allotted a proxy-granting ticket", (Object)this.getId(), (Object)this.service.getId());
            throw new InvalidProxyGrantingTicketForServiceTicketException(this.service);
        }
        this.grantedTicketAlready = Boolean.TRUE;
        ProxyGrantingTicketImpl pgt = new ProxyGrantingTicketImpl(id, this.service, this.getTicketGrantingTicket(), authentication, expirationPolicy);
        this.getTicketGrantingTicket().getProxyGrantingTickets().put(pgt.getId(), this.service);
        return pgt;
    }

    @Override
    public Authentication getAuthentication() {
        return this.getTicketGrantingTicket().getAuthentication();
    }

    public String getPrefix() {
        return "ST";
    }

    @JsonProperty(value="ticketGrantingTicket")
    @Generated
    public void setTicketGrantingTicket(TicketGrantingTicket ticketGrantingTicket) {
        this.ticketGrantingTicket = ticketGrantingTicket;
    }

    @Generated
    public void setService(Service service) {
        this.service = service;
    }

    @Generated
    public void setFromNewLogin(boolean fromNewLogin) {
        this.fromNewLogin = fromNewLogin;
    }

    @Generated
    public void setGrantedTicketAlready(Boolean grantedTicketAlready) {
        this.grantedTicketAlready = grantedTicketAlready;
    }

    @Generated
    public ServiceTicketImpl() {
    }

    @Override
    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    @Generated
    public Service getService() {
        return this.service;
    }

    @Generated
    public boolean isFromNewLogin() {
        return this.fromNewLogin;
    }

    @Generated
    public Boolean getGrantedTicketAlready() {
        return this.grantedTicketAlready;
    }
}

