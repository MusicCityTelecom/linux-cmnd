/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.AbstractTicketException
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.ProxyGrantingTicketIssuerTicket
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicketFactory
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.factory;

import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.services.CasModelRegisteredService;
import org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ProxyGrantingTicketIssuerTicket;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicketFactory;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProxyGrantingTicketFactory
implements ProxyGrantingTicketFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProxyGrantingTicketFactory.class);
    protected final UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator;
    protected final ExpirationPolicyBuilder<ProxyGrantingTicket> ticketGrantingTicketExpirationPolicy;
    protected final CipherExecutor<String, String> cipherExecutor;
    protected final ServicesManager servicesManager;

    public <T extends ProxyGrantingTicket> T create(ServiceTicket serviceTicket, Authentication authentication, Class<T> clazz) throws AbstractTicketException {
        String pgtId = this.produceTicketIdentifier();
        return this.produceTicket(serviceTicket, authentication, pgtId, clazz);
    }

    public Class<? extends Ticket> getTicketType() {
        return ProxyGrantingTicket.class;
    }

    protected <T extends ProxyGrantingTicket> T produceTicket(ServiceTicket serviceTicket, Authentication authentication, String pgtId, Class<T> clazz) {
        ProxyGrantingTicketIssuerTicket pgtIssuer;
        RegisteredServiceProxyGrantingTicketExpirationPolicy proxyGrantingTicketExpirationPolicy = this.getProxyGrantingTicketExpirationPolicy(serviceTicket);
        ProxyGrantingTicket result = this.produceTicketWithAdequateExpirationPolicy(proxyGrantingTicketExpirationPolicy, pgtIssuer = (ProxyGrantingTicketIssuerTicket)serviceTicket, authentication, pgtId);
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    protected RegisteredServiceProxyGrantingTicketExpirationPolicy getProxyGrantingTicketExpirationPolicy(ServiceTicket serviceTicket) {
        CasModelRegisteredService service = (CasModelRegisteredService)this.servicesManager.findServiceBy(serviceTicket.getService(), CasModelRegisteredService.class);
        if (service != null) {
            return service.getProxyGrantingTicketExpirationPolicy();
        }
        return null;
    }

    protected ProxyGrantingTicket produceTicketWithAdequateExpirationPolicy(RegisteredServiceProxyGrantingTicketExpirationPolicy servicePgtPolicy, ProxyGrantingTicketIssuerTicket serviceTicket, Authentication authentication, String pgtId) {
        if (servicePgtPolicy != null) {
            LOGGER.trace("Overriding proxy-granting ticket policy with the specific policy: [{}]", (Object)servicePgtPolicy);
            return serviceTicket.grantProxyGrantingTicket(pgtId, authentication, (ExpirationPolicy)new HardTimeoutExpirationPolicy(servicePgtPolicy.getMaxTimeToLiveInSeconds()));
        }
        LOGGER.trace("Using default ticket-granting ticket policy for proxy-granting ticket");
        return serviceTicket.grantProxyGrantingTicket(pgtId, authentication, this.ticketGrantingTicketExpirationPolicy.buildTicketExpirationPolicy());
    }

    protected String produceTicketIdentifier() {
        String pgtId = this.ticketGrantingTicketUniqueTicketIdGenerator.getNewTicketId("PGT");
        if (this.cipherExecutor == null || !this.cipherExecutor.isEnabled()) {
            return pgtId;
        }
        LOGGER.debug("Attempting to encode proxy-granting ticket [{}]", (Object)pgtId);
        String pgtEncoded = (String)this.cipherExecutor.encode((Object)pgtId);
        LOGGER.debug("Encoded proxy-granting ticket id [{}]", (Object)pgtEncoded);
        return pgtEncoded;
    }

    @Generated
    public DefaultProxyGrantingTicketFactory(UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator, ExpirationPolicyBuilder<ProxyGrantingTicket> ticketGrantingTicketExpirationPolicy, CipherExecutor<String, String> cipherExecutor, ServicesManager servicesManager) {
        this.ticketGrantingTicketUniqueTicketIdGenerator = ticketGrantingTicketUniqueTicketIdGenerator;
        this.ticketGrantingTicketExpirationPolicy = ticketGrantingTicketExpirationPolicy;
        this.cipherExecutor = cipherExecutor;
        this.servicesManager = servicesManager;
    }
}

