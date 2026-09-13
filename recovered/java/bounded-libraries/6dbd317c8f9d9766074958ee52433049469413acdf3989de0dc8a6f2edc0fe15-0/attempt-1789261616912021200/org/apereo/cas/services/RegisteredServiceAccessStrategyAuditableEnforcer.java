/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.audit.AuditableContext
 *  org.apereo.cas.audit.AuditableExecutionResult
 *  org.apereo.cas.audit.BaseAuditableExecution
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.PrincipalException
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.UnauthorizedServiceException
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.services;

import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.audit.AuditableContext;
import org.apereo.cas.audit.AuditableExecutionResult;
import org.apereo.cas.audit.BaseAuditableExecution;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.PrincipalException;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAccessStrategyUtils;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.apereo.inspektr.audit.annotation.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class RegisteredServiceAccessStrategyAuditableEnforcer
extends BaseAuditableExecution {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceAccessStrategyAuditableEnforcer.class);
    private final WatchableGroovyScriptResource accessStrategyScriptResource;

    public RegisteredServiceAccessStrategyAuditableEnforcer(CasConfigurationProperties casProperties) {
        Resource location = casProperties.getAccessStrategy().getGroovy().getLocation();
        this.accessStrategyScriptResource = location != null ? new WatchableGroovyScriptResource(location) : null;
    }

    private static Optional<AuditableExecutionResult> byServiceTicketAndAuthnResultAndRegisteredService(AuditableContext context) {
        Optional providedRegisteredService = context.getRegisteredService();
        if (context.getServiceTicket().isPresent() && context.getAuthenticationResult().isPresent() && providedRegisteredService.isPresent()) {
            AuditableExecutionResult result = AuditableExecutionResult.of((AuditableContext)context);
            try {
                ServiceTicket serviceTicket = (ServiceTicket)context.getServiceTicket().orElseThrow();
                Authentication authResult = ((AuthenticationResult)context.getAuthenticationResult().orElseThrow()).getAuthentication();
                RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(serviceTicket.getService(), (RegisteredService)providedRegisteredService.get(), authResult.getPrincipal().getId(), CollectionUtils.merge((Map[])new Map[]{authResult.getAttributes(), authResult.getPrincipal().getAttributes()}));
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private static Optional<AuditableExecutionResult> byServiceAndRegisteredServiceAndTicketGrantingTicket(AuditableContext context) {
        Optional providedService = context.getService();
        Optional providedRegisteredService = context.getRegisteredService();
        Optional ticketGrantingTicket = context.getTicketGrantingTicket();
        if (providedService.isPresent() && providedRegisteredService.isPresent() && ticketGrantingTicket.isPresent()) {
            RegisteredService registeredService = (RegisteredService)providedRegisteredService.get();
            Service service = (Service)providedService.get();
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService(registeredService).service(service).ticketGrantingTicket((TicketGrantingTicket)ticketGrantingTicket.get()).build();
            try {
                Authentication authResult = ((TicketGrantingTicket)ticketGrantingTicket.get()).getRoot().getAuthentication();
                RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service, registeredService, authResult.getPrincipal().getId(), CollectionUtils.merge((Map[])new Map[]{authResult.getAttributes(), authResult.getPrincipal().getAttributes()}));
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private static Optional<AuditableExecutionResult> byRegisteredService(AuditableContext context) {
        Optional providedRegisteredService = context.getRegisteredService();
        if (providedRegisteredService.isPresent()) {
            RegisteredService registeredService = (RegisteredService)providedRegisteredService.get();
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService(registeredService).service((Service)context.getService().orElse(null)).authentication((Authentication)context.getAuthentication().orElse(null)).build();
            try {
                RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(registeredService);
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private static Optional<AuditableExecutionResult> byServiceAndRegisteredService(AuditableContext context) {
        Optional providedService = context.getService();
        Optional providedRegisteredService = context.getRegisteredService();
        if (providedService.isPresent() && providedRegisteredService.isPresent()) {
            RegisteredService registeredService = (RegisteredService)providedRegisteredService.get();
            Service service = (Service)providedService.get();
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService(registeredService).service(service).build();
            try {
                RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(service, registeredService);
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    public static Optional<AuditableExecutionResult> byServiceAndRegisteredServiceAndPrincipal(AuditableContext context) {
        Optional providedService = context.getService();
        Optional providedRegisteredService = context.getRegisteredService();
        Optional providedPrincipal = context.getPrincipal();
        if (providedService.isPresent() && providedRegisteredService.isPresent() && providedPrincipal.isPresent()) {
            RegisteredService registeredService = (RegisteredService)providedRegisteredService.get();
            Service service = (Service)providedService.get();
            Principal principal = (Principal)providedPrincipal.get();
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService(registeredService).service(service).build();
            try {
                RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service, registeredService, principal.getId(), principal.getAttributes());
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private static Optional<AuditableExecutionResult> byServiceAndRegisteredServiceAndAuthentication(AuditableContext context) {
        Optional providedService = context.getService();
        Optional providedRegisteredService = context.getRegisteredService();
        Optional providedAuthn = context.getAuthentication();
        if (providedService.isPresent() && providedRegisteredService.isPresent() && providedAuthn.isPresent()) {
            RegisteredService registeredService = (RegisteredService)providedRegisteredService.get();
            Service service = (Service)providedService.get();
            Authentication authentication = (Authentication)providedAuthn.get();
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService(registeredService).service(service).authentication(authentication).build();
            try {
                RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service, registeredService, authentication.getPrincipal().getId(), CollectionUtils.merge((Map[])new Map[]{authentication.getAttributes(), authentication.getPrincipal().getAttributes()}));
            }
            catch (PrincipalException | UnauthorizedServiceException e) {
                result.setException((RuntimeException)e);
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }

    @Audit(action="SERVICE_ACCESS_ENFORCEMENT", actionResolverName="SERVICE_ACCESS_ENFORCEMENT_ACTION_RESOLVER", resourceResolverName="SERVICE_ACCESS_ENFORCEMENT_RESOURCE_RESOLVER")
    public AuditableExecutionResult execute(AuditableContext context) {
        return this.byExternalGroovyScript(context).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byServiceTicketAndAuthnResultAndRegisteredService(context)).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byServiceAndRegisteredServiceAndTicketGrantingTicket(context)).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byServiceAndRegisteredServiceAndPrincipal(context)).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byServiceAndRegisteredServiceAndAuthentication(context)).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byServiceAndRegisteredService(context)).or(() -> RegisteredServiceAccessStrategyAuditableEnforcer.byRegisteredService(context)).orElseGet(() -> {
            AuditableExecutionResult result = AuditableExecutionResult.builder().registeredService((RegisteredService)context.getRegisteredService().orElse(null)).service((Service)context.getService().orElse(null)).authentication((Authentication)context.getAuthentication().orElse(null)).build();
            result.setException((RuntimeException)((Object)new UnauthorizedServiceException("screen.service.error.message", "Service unauthorized")));
            return result;
        });
    }

    protected Optional<AuditableExecutionResult> byExternalGroovyScript(AuditableContext context) {
        if (this.accessStrategyScriptResource != null) {
            Object[] args = new Object[]{context, LOGGER};
            return Optional.ofNullable((AuditableExecutionResult)this.accessStrategyScriptResource.execute(args, AuditableExecutionResult.class, true));
        }
        return Optional.empty();
    }
}

