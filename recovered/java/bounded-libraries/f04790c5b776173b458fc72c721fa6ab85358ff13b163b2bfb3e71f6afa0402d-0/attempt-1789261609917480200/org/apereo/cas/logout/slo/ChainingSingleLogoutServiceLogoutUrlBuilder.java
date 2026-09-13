/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder
 *  org.apereo.cas.logout.slo.SingleLogoutUrl
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.logout.slo;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.logout.slo.SingleLogoutUrl;
import org.apereo.cas.services.RegisteredService;

public class ChainingSingleLogoutServiceLogoutUrlBuilder
implements SingleLogoutServiceLogoutUrlBuilder {
    private final List<SingleLogoutServiceLogoutUrlBuilder> singleLogoutServiceLogoutUrlBuilders;

    public Collection<SingleLogoutUrl> determineLogoutUrl(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        return this.singleLogoutServiceLogoutUrlBuilders.stream().sorted(Comparator.comparing(SingleLogoutServiceLogoutUrlBuilder::getOrder)).filter(builder -> builder.supports(registeredService, singleLogoutService, httpRequest)).map(builder -> builder.determineLogoutUrl(registeredService, singleLogoutService, httpRequest)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean supports(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        return this.singleLogoutServiceLogoutUrlBuilders.stream().anyMatch(builder -> builder.supports(registeredService, singleLogoutService, httpRequest));
    }

    public boolean isServiceAuthorized(WebApplicationService service, Optional<HttpServletRequest> httpRequest, Optional<HttpServletResponse> httpResponse) {
        return this.singleLogoutServiceLogoutUrlBuilders.stream().anyMatch(builder -> builder.isServiceAuthorized(service, httpRequest, httpResponse));
    }

    @Generated
    public ChainingSingleLogoutServiceLogoutUrlBuilder(List<SingleLogoutServiceLogoutUrlBuilder> singleLogoutServiceLogoutUrlBuilders) {
        this.singleLogoutServiceLogoutUrlBuilders = singleLogoutServiceLogoutUrlBuilders;
    }
}

