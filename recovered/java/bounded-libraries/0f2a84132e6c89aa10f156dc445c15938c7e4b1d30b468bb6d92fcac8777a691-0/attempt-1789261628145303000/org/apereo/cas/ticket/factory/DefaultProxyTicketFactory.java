/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyTicketExpirationPolicy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.proxy.ProxyGrantingTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicket
 *  org.apereo.cas.ticket.proxy.ProxyTicketFactory
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.factory;

import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.services.CasModelRegisteredService;
import org.apereo.cas.services.RegisteredServiceProxyTicketExpirationPolicy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyTicket;
import org.apereo.cas.ticket.proxy.ProxyTicketFactory;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProxyTicketFactory
implements ProxyTicketFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProxyTicketFactory.class);
    private final UniqueTicketIdGenerator defaultTicketIdGenerator = new DefaultUniqueTicketIdGenerator();
    private final ExpirationPolicyBuilder<ProxyTicket> proxyTicketExpirationPolicy;
    private final Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService;
    private final CipherExecutor<String, String> cipherExecutor;
    private final ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy;
    private final ServicesManager servicesManager;

    public <T extends Ticket> T create(ProxyGrantingTicket proxyGrantingTicket, Service service, Class<T> clazz) {
        String ticketId = this.produceTicketIdentifier(service);
        return this.produceTicket(proxyGrantingTicket, service, ticketId, clazz);
    }

    private ExpirationPolicy determineExpirationPolicyForService(Service service) {
        CasModelRegisteredService registeredService = (CasModelRegisteredService)this.servicesManager.findServiceBy(service, CasModelRegisteredService.class);
        if (registeredService != null && registeredService.getProxyTicketExpirationPolicy() != null) {
            RegisteredServiceProxyTicketExpirationPolicy policy = registeredService.getProxyTicketExpirationPolicy();
            long count = policy.getNumberOfUses();
            String ttl = policy.getTimeToLive();
            if (count > 0L && StringUtils.isNotBlank((CharSequence)ttl)) {
                return new MultiTimeUseOrTimeoutExpirationPolicy.ProxyTicketExpirationPolicy(count, Beans.newDuration((String)ttl).getSeconds());
            }
        }
        return this.proxyTicketExpirationPolicy.buildTicketExpirationPolicy();
    }

    protected <T extends Ticket> T produceTicket(ProxyGrantingTicket proxyGrantingTicket, Service service, String ticketId, Class<T> clazz) {
        ExpirationPolicy expirationPolicyToUse = this.determineExpirationPolicyForService(service);
        ProxyTicket result = proxyGrantingTicket.grantProxyTicket(ticketId, service, expirationPolicyToUse, this.serviceTicketSessionTrackingPolicy);
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    protected String produceTicketIdentifier(Service service) {
        String uniqueTicketIdGenKey = service.getClass().getName();
        LOGGER.debug("Looking up ticket id generator for [{}]", (Object)uniqueTicketIdGenKey);
        UniqueTicketIdGenerator generator = this.uniqueTicketIdGeneratorsForService.get(uniqueTicketIdGenKey);
        if (generator == null) {
            generator = this.defaultTicketIdGenerator;
            LOGGER.debug("Ticket id generator not found for [{}]. Using the default generator...", (Object)uniqueTicketIdGenKey);
        }
        String ticketId = generator.getNewTicketId("PT");
        if (this.cipherExecutor == null || !this.cipherExecutor.isEnabled()) {
            return ticketId;
        }
        LOGGER.trace("Attempting to encode proxy ticket [{}]", (Object)ticketId);
        String encodedId = (String)this.cipherExecutor.encode((Object)ticketId);
        LOGGER.debug("Encoded proxy ticket id [{}]", (Object)encodedId);
        return encodedId;
    }

    public Class<? extends Ticket> getTicketType() {
        return ProxyTicket.class;
    }

    @Generated
    public DefaultProxyTicketFactory(ExpirationPolicyBuilder<ProxyTicket> proxyTicketExpirationPolicy, Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService, CipherExecutor<String, String> cipherExecutor, ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy, ServicesManager servicesManager) {
        this.proxyTicketExpirationPolicy = proxyTicketExpirationPolicy;
        this.uniqueTicketIdGeneratorsForService = uniqueTicketIdGeneratorsForService;
        this.cipherExecutor = cipherExecutor;
        this.serviceTicketSessionTrackingPolicy = serviceTicketSessionTrackingPolicy;
        this.servicesManager = servicesManager;
    }
}

