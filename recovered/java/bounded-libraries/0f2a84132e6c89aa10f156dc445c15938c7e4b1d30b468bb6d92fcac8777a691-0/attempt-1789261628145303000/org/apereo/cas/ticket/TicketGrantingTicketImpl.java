/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicket;
import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketImpl;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.TicketGrantingTicket;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TicketGrantingTicketImpl
extends AbstractTicket
implements AuthenticatedServicesAwareTicketGrantingTicket {
    private static final long serialVersionUID = -8608149809180911599L;
    private Authentication authentication;
    private Service proxiedBy;
    private Map<String, Service> services = new HashMap<String, Service>(0);
    private TicketGrantingTicket ticketGrantingTicket;
    private Map<String, Service> proxyGrantingTickets = new HashMap<String, Service>(0);
    private Set<String> descendantTickets = new HashSet<String>(0);

    @JsonCreator
    public TicketGrantingTicketImpl(@JsonProperty(value="id") String id, @JsonProperty(value="proxiedBy") Service proxiedBy, @JsonProperty(value="ticketGrantingTicket") TicketGrantingTicket parentTicketGrantingTicket, @JsonProperty(value="authentication") @NonNull Authentication authentication, @JsonProperty(value="expirationPolicy") ExpirationPolicy policy) {
        super(id, policy);
        if (authentication == null) {
            throw new NullPointerException("authentication is marked non-null but is null");
        }
        if (parentTicketGrantingTicket != null && proxiedBy == null) {
            throw new IllegalArgumentException("Must specify proxiedBy when providing parent ticket-granting ticket");
        }
        this.ticketGrantingTicket = parentTicketGrantingTicket;
        this.authentication = authentication;
        this.proxiedBy = proxiedBy;
    }

    public TicketGrantingTicketImpl(String id, Authentication authentication, ExpirationPolicy policy) {
        this(id, null, null, authentication, policy);
    }

    public synchronized ServiceTicket grantServiceTicket(String id, Service service, ExpirationPolicy expirationPolicy, boolean credentialProvided, ServiceTicketSessionTrackingPolicy trackingPolicy) {
        ServiceTicketImpl serviceTicket = new ServiceTicketImpl(id, (TicketGrantingTicket)this, service, credentialProvided, expirationPolicy);
        trackingPolicy.track((AuthenticatedServicesAwareTicketGrantingTicket)this, (ServiceTicket)serviceTicket);
        return serviceTicket;
    }

    public void removeAllServices() {
        this.services.clear();
    }

    public boolean isRoot() {
        return this.getTicketGrantingTicket() == null;
    }

    @JsonIgnore
    @CanIgnoreReturnValue
    public TicketGrantingTicket getRoot() {
        TicketGrantingTicket parent = this.getTicketGrantingTicket();
        if (parent == null) {
            return this;
        }
        return parent.getRoot();
    }

    @JsonIgnore
    public List<Authentication> getChainedAuthentications() {
        ArrayList<Authentication> list = new ArrayList<Authentication>(2);
        list.add(this.getAuthentication());
        if (this.getTicketGrantingTicket() == null) {
            return list;
        }
        list.addAll(this.getTicketGrantingTicket().getChainedAuthentications());
        return list;
    }

    public String getPrefix() {
        return "TGT";
    }

    @Override
    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Generated
    public Service getProxiedBy() {
        return this.proxiedBy;
    }

    @Generated
    public Map<String, Service> getServices() {
        return this.services;
    }

    @Override
    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    @Generated
    public Map<String, Service> getProxyGrantingTickets() {
        return this.proxyGrantingTickets;
    }

    @Generated
    public Set<String> getDescendantTickets() {
        return this.descendantTickets;
    }

    @Generated
    public TicketGrantingTicketImpl() {
    }
}

