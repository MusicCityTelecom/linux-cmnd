/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.CentralAuthenticationService
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.ticket.TicketValidator
 *  org.apereo.cas.ticket.TicketValidator$ValidationResult
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.validation.Assertion
 *  org.apereo.cas.validation.AuthenticationAttributeReleasePolicy
 */
package org.apereo.cas.util;

import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.TicketValidator;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.AuthenticationAttributeReleasePolicy;

public class InternalTicketValidator
implements TicketValidator {
    private final CentralAuthenticationService centralAuthenticationService;
    private final ServiceFactory<WebApplicationService> webApplicationServiceFactory;
    private final AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy;
    private final ServicesManager servicesManager;

    public TicketValidator.ValidationResult validate(String ticketId, String serviceId) {
        WebApplicationService service = (WebApplicationService)this.webApplicationServiceFactory.createService(serviceId);
        Assertion assertion = this.centralAuthenticationService.validateServiceTicket(ticketId, (Service)service);
        Authentication authentication = assertion.getPrimaryAuthentication();
        Principal principal = authentication.getPrincipal();
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)service);
        Map authenticationAttributes = this.authenticationAttributeReleasePolicy.getAuthenticationAttributesForRelease(authentication, assertion, new HashMap(0), registeredService);
        Map context = CollectionUtils.wrap((String)Authentication.class.getName(), (Object)assertion.getOriginalAuthentication(), (String)Assertion.class.getName(), (Object)assertion, (String)RegisteredService.class.getName(), (Object)registeredService);
        context.putAll(assertion.getContext());
        return TicketValidator.ValidationResult.builder().principal(principal).service((Service)service).attributes(authenticationAttributes).context(context).build();
    }

    @Generated
    public InternalTicketValidator(CentralAuthenticationService centralAuthenticationService, ServiceFactory<WebApplicationService> webApplicationServiceFactory, AuthenticationAttributeReleasePolicy authenticationAttributeReleasePolicy, ServicesManager servicesManager) {
        this.centralAuthenticationService = centralAuthenticationService;
        this.webApplicationServiceFactory = webApplicationServiceFactory;
        this.authenticationAttributeReleasePolicy = authenticationAttributeReleasePolicy;
        this.servicesManager = servicesManager;
    }
}

