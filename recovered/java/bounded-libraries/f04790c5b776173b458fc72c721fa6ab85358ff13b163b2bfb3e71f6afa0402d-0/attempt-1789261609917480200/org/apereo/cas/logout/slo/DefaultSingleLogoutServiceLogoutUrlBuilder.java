/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.web.UrlValidator
 */
package org.apereo.cas.logout.slo;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.slo.BaseSingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.UrlValidator;

public class DefaultSingleLogoutServiceLogoutUrlBuilder
extends BaseSingleLogoutServiceLogoutUrlBuilder {
    public DefaultSingleLogoutServiceLogoutUrlBuilder(ServicesManager servicesManager, UrlValidator urlValidator) {
        super(servicesManager, urlValidator);
    }

    @Override
    public boolean supports(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        return super.supports(registeredService, singleLogoutService, httpRequest) && registeredService.getFriendlyName().equalsIgnoreCase("CAS Client");
    }
}

