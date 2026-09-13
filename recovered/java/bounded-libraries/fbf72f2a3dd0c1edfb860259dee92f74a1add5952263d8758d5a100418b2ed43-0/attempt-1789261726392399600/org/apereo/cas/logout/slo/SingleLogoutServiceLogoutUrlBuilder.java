/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.logout.slo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.slo.SingleLogoutUrl;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

public interface SingleLogoutServiceLogoutUrlBuilder
extends Ordered {
    default public Collection<SingleLogoutUrl> determineLogoutUrl(RegisteredService registeredService, WebApplicationService singleLogoutService) {
        return this.determineLogoutUrl(registeredService, singleLogoutService, Optional.empty());
    }

    default public Collection<SingleLogoutUrl> determineLogoutUrl(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        return new ArrayList<SingleLogoutUrl>(0);
    }

    default public String getName() {
        return this.getClass().getName();
    }

    public boolean supports(RegisteredService var1, WebApplicationService var2, Optional<HttpServletRequest> var3);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public boolean isServiceAuthorized(WebApplicationService var1, Optional<HttpServletRequest> var2, Optional<HttpServletResponse> var3);
}

