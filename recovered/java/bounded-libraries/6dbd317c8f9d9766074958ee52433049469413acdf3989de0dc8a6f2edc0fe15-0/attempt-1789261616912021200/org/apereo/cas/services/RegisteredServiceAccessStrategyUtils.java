/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.PrincipalException
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceExpirationPolicy
 *  org.apereo.cas.services.UnauthorizedServiceException
 *  org.apereo.cas.services.UnauthorizedServiceForPrincipalException
 *  org.apereo.cas.services.UnauthorizedSsoServiceException
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.Generated;
import org.apereo.cas.authentication.PrincipalException;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceExpirationPolicy;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.services.UnauthorizedServiceForPrincipalException;
import org.apereo.cas.services.UnauthorizedSsoServiceException;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegisteredServiceAccessStrategyUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceAccessStrategyUtils.class);

    public static void ensureServiceAccessIsAllowed(RegisteredService registeredService) {
        RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(registeredService != null ? registeredService.getName() : "", registeredService);
    }

    public static void ensureServiceAccessIsAllowed(String service, RegisteredService registeredService) {
        if (registeredService == null) {
            LOGGER.warn("Unauthorized Service Access. Service [{}] is not registered in service registry.", (Object)service);
            throw new UnauthorizedServiceException("screen.service.error.message", "Service " + service + " is not found in service registry.");
        }
        if (!registeredService.getAccessStrategy().isServiceAccessAllowed()) {
            String msg = String.format("Unauthorized Service Access. Service [%s] is not enabled in service registry. You should review the service access strategy to evaluate the conditions and policies required for service access.", service);
            throw new UnauthorizedServiceException("screen.service.error.message", msg);
        }
        if (!RegisteredServiceAccessStrategyUtils.ensureServiceIsNotExpired(registeredService)) {
            String msg = String.format("Expired service access is denied. Service [%s] has been expired", service);
            throw new UnauthorizedServiceException("screen.service.expired.message", msg);
        }
    }

    public static void ensureServiceAccessIsAllowed(Service service, RegisteredService registeredService) {
        RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(service != null ? service.getId() : "unknown", registeredService);
    }

    public static boolean ensureServiceIsNotExpired(RegisteredService registeredService) {
        return RegisteredServiceAccessStrategyUtils.getRegisteredServiceExpirationPolicyPredicate().test(registeredService);
    }

    public static void ensureServiceSsoAccessIsAllowed(RegisteredService registeredService, Service service, TicketGrantingTicket ticketGrantingTicket) {
        RegisteredServiceAccessStrategyUtils.ensureServiceSsoAccessIsAllowed(registeredService, service, ticketGrantingTicket, false);
    }

    public static void ensureServiceSsoAccessIsAllowed(RegisteredService registeredService, Service service, TicketGrantingTicket ticketGrantingTicket, boolean credentialsProvided) {
        if (!registeredService.getAccessStrategy().isServiceAccessAllowedForSso()) {
            LOGGER.debug("Service [{}] is configured to not use SSO", (Object)service.getId());
            if (ticketGrantingTicket.getProxiedBy() != null) {
                LOGGER.warn("Service [{}] is not allowed to use SSO for proxying.", (Object)service.getId());
                throw new UnauthorizedSsoServiceException();
            }
            if (ticketGrantingTicket.getCountOfUses() > 0 && !credentialsProvided) {
                LOGGER.warn("Service [{}] is not allowed to use SSO. The ticket-granting ticket [{}] is not proxied and it's been used at least once. The authentication request must provide credentials before access can be granted", (Object)ticketGrantingTicket.getId(), (Object)service.getId());
                throw new UnauthorizedSsoServiceException();
            }
        }
        LOGGER.debug("Current authentication via ticket [{}] allows service [{}] to participate in the existing SSO session", (Object)ticketGrantingTicket.getId(), (Object)service.getId());
    }

    public static boolean ensurePrincipalAccessIsAllowedForService(Service service, RegisteredService registeredService, String principalId, Map<String, List<Object>> attributes) {
        RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(service, registeredService);
        LOGGER.trace("Checking access strategy for service [{}], requested by [{}] with attributes [{}].", new Object[]{service != null ? service.getId() : "unknown", principalId, attributes});
        if (!registeredService.getAccessStrategy().doPrincipalAttributesAllowServiceAccess(principalId, attributes)) {
            LOGGER.warn("Cannot grant access to service [{}]; it is not authorized for use by [{}].", (Object)(service != null ? service.getId() : "unknown"), (Object)principalId);
            HashMap<String, UnauthorizedServiceForPrincipalException> handlerErrors = new HashMap<String, UnauthorizedServiceForPrincipalException>();
            String message = String.format("Cannot grant service access to %s", principalId);
            UnauthorizedServiceForPrincipalException exception = new UnauthorizedServiceForPrincipalException(message, registeredService, principalId, attributes);
            handlerErrors.put(UnauthorizedServiceForPrincipalException.class.getSimpleName(), exception);
            throw new PrincipalException("screen.service.error.message", handlerErrors, new HashMap(0));
        }
        return true;
    }

    public static Predicate<RegisteredService> getRegisteredServiceExpirationPolicyPredicate() {
        return service -> {
            if (service == null) {
                return false;
            }
            RegisteredServiceExpirationPolicy policy = service.getExpirationPolicy();
            return policy == null || !policy.isExpired();
        };
    }

    @Generated
    private RegisteredServiceAccessStrategyUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

