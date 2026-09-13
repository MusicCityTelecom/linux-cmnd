/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.ExpirationPolicyBuilder
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.ServiceTicketFactory
 *  org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.function.FunctionUtils
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
import org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.ExpirationPolicyBuilder;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketFactory;
import org.apereo.cas.ticket.ServiceTicketSessionTrackingPolicy;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServiceTicketFactory
implements ServiceTicketFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceTicketFactory.class);
    private final ExpirationPolicyBuilder<ServiceTicket> ticketExpirationPolicy;
    private final Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService;
    private final ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy;
    private final CipherExecutor<String, String> cipherExecutor;
    private final UniqueTicketIdGenerator defaultServiceTicketIdGenerator = new DefaultUniqueTicketIdGenerator();
    private final ServicesManager servicesManager;

    public <T extends Ticket> T create(TicketGrantingTicket ticketGrantingTicket, Service service, boolean credentialProvided, Class<T> clazz) {
        String ticketId = this.produceTicketIdentifier(service, ticketGrantingTicket, credentialProvided);
        String result = (String)FunctionUtils.doIf((boolean)this.cipherExecutor.isEnabled(), () -> {
            LOGGER.trace("Attempting to encode service ticket [{}]", (Object)ticketId);
            String encoded = (String)this.cipherExecutor.encode((Object)ticketId);
            LOGGER.debug("Encoded service ticket id [{}]", (Object)encoded);
            return encoded;
        }, () -> ticketId).get();
        return this.produceTicket(ticketGrantingTicket, service, credentialProvided, result, clazz);
    }

    public Class<? extends Ticket> getTicketType() {
        return ServiceTicket.class;
    }

    protected <T extends Ticket> T produceTicket(TicketGrantingTicket ticketGrantingTicket, Service service, boolean credentialProvided, String ticketId, Class<T> clazz) {
        ExpirationPolicy expirationPolicyToUse = this.determineExpirationPolicyForService(service);
        ServiceTicket result = ticketGrantingTicket.grantServiceTicket(ticketId, service, expirationPolicyToUse, credentialProvided, this.serviceTicketSessionTrackingPolicy);
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    protected String produceTicketIdentifier(Service service, TicketGrantingTicket ticketGrantingTicket, boolean credentialProvided) {
        String uniqueTicketIdGenKey = service.getClass().getName();
        UniqueTicketIdGenerator serviceTicketUniqueTicketIdGenerator = null;
        if (this.uniqueTicketIdGeneratorsForService != null && !this.uniqueTicketIdGeneratorsForService.isEmpty()) {
            LOGGER.debug("Looking up service ticket id generator for [{}]", (Object)uniqueTicketIdGenKey);
            serviceTicketUniqueTicketIdGenerator = this.uniqueTicketIdGeneratorsForService.get(uniqueTicketIdGenKey);
        }
        if (serviceTicketUniqueTicketIdGenerator == null) {
            serviceTicketUniqueTicketIdGenerator = this.defaultServiceTicketIdGenerator;
            LOGGER.debug("Service ticket id generator not found for [{}]. Using the default generator.", (Object)uniqueTicketIdGenKey);
        }
        return serviceTicketUniqueTicketIdGenerator.getNewTicketId("ST");
    }

    private ExpirationPolicy determineExpirationPolicyForService(Service service) {
        CasModelRegisteredService registeredService = (CasModelRegisteredService)this.servicesManager.findServiceBy(service, CasModelRegisteredService.class);
        if (registeredService != null && registeredService.getServiceTicketExpirationPolicy() != null) {
            RegisteredServiceServiceTicketExpirationPolicy policy = registeredService.getServiceTicketExpirationPolicy();
            long count = policy.getNumberOfUses();
            String ttl = policy.getTimeToLive();
            if (count > 0L && StringUtils.isNotBlank((CharSequence)ttl)) {
                return new MultiTimeUseOrTimeoutExpirationPolicy.ServiceTicketExpirationPolicy(count, Beans.newDuration((String)ttl).getSeconds());
            }
        }
        return this.ticketExpirationPolicy.buildTicketExpirationPolicy();
    }

    @Generated
    public DefaultServiceTicketFactory(ExpirationPolicyBuilder<ServiceTicket> ticketExpirationPolicy, Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService, ServiceTicketSessionTrackingPolicy serviceTicketSessionTrackingPolicy, CipherExecutor<String, String> cipherExecutor, ServicesManager servicesManager) {
        this.ticketExpirationPolicy = ticketExpirationPolicy;
        this.uniqueTicketIdGeneratorsForService = uniqueTicketIdGeneratorsForService;
        this.serviceTicketSessionTrackingPolicy = serviceTicketSessionTrackingPolicy;
        this.cipherExecutor = cipherExecutor;
        this.servicesManager = servicesManager;
    }

    @Generated
    public ExpirationPolicyBuilder<ServiceTicket> getTicketExpirationPolicy() {
        return this.ticketExpirationPolicy;
    }

    @Generated
    public Map<String, UniqueTicketIdGenerator> getUniqueTicketIdGeneratorsForService() {
        return this.uniqueTicketIdGeneratorsForService;
    }

    @Generated
    public ServiceTicketSessionTrackingPolicy getServiceTicketSessionTrackingPolicy() {
        return this.serviceTicketSessionTrackingPolicy;
    }

    @Generated
    public CipherExecutor<String, String> getCipherExecutor() {
        return this.cipherExecutor;
    }

    @Generated
    public UniqueTicketIdGenerator getDefaultServiceTicketIdGenerator() {
        return this.defaultServiceTicketIdGenerator;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }
}

