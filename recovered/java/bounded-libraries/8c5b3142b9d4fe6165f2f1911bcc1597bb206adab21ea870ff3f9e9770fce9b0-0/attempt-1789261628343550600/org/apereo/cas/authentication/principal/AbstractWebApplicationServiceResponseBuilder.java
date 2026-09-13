/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Response
 *  org.apereo.cas.authentication.principal.Response$ResponseType
 *  org.apereo.cas.authentication.principal.ResponseBuilder
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.CasModelRegisteredService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.web.UrlValidator
 */
package org.apereo.cas.authentication.principal;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.DefaultResponse;
import org.apereo.cas.authentication.principal.Response;
import org.apereo.cas.authentication.principal.ResponseBuilder;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.CasModelRegisteredService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.UrlValidator;

public abstract class AbstractWebApplicationServiceResponseBuilder
implements ResponseBuilder<WebApplicationService> {
    private static final long serialVersionUID = -4584738964007702423L;
    protected final ServicesManager servicesManager;
    private final UrlValidator urlValidator;
    private int order;

    protected Response buildRedirect(WebApplicationService service, Map<String, String> parameters) {
        return DefaultResponse.getRedirectResponse(this.determineServiceResponseUrl(service), parameters);
    }

    protected String determineServiceResponseUrl(WebApplicationService service) {
        CasModelRegisteredService casService;
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)service);
        if (registeredService instanceof CasModelRegisteredService && StringUtils.isNotBlank((CharSequence)(casService = (CasModelRegisteredService)registeredService).getRedirectUrl()) && this.getUrlValidator().isValid(casService.getRedirectUrl())) {
            return casService.getRedirectUrl();
        }
        return CollectionUtils.firstElement(service.getAttributes().get(Service.class.getName())).map(Object::toString).orElseGet(() -> ((WebApplicationService)service).getOriginalUrl());
    }

    protected Response buildHeader(WebApplicationService service, Map<String, String> parameters) {
        return DefaultResponse.getHeaderResponse(this.determineServiceResponseUrl(service), parameters);
    }

    protected Response buildPost(WebApplicationService service, Map<String, String> parameters) {
        return DefaultResponse.getPostResponse(this.determineServiceResponseUrl(service), parameters);
    }

    protected Response.ResponseType getWebApplicationServiceResponseType(WebApplicationService finalService) {
        HttpServletRequest request = HttpRequestUtils.getHttpServletRequestFromRequestAttributes();
        String methodRequest = Optional.ofNullable(request).map(httpServletRequest -> httpServletRequest.getParameter("method")).orElse(null);
        Function func = FunctionUtils.doIf(StringUtils::isBlank, t -> {
            RegisteredService registeredService = this.servicesManager.findServiceBy((Service)finalService);
            if (registeredService != null) {
                return registeredService.getResponseType();
            }
            return null;
        }, f -> methodRequest);
        String method = (String)func.apply(methodRequest);
        if (StringUtils.isBlank((CharSequence)method)) {
            return Response.ResponseType.REDIRECT;
        }
        return Response.ResponseType.valueOf((String)method.toUpperCase());
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public UrlValidator getUrlValidator() {
        return this.urlValidator;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    protected AbstractWebApplicationServiceResponseBuilder(ServicesManager servicesManager, UrlValidator urlValidator) {
        this.servicesManager = servicesManager;
        this.urlValidator = urlValidator;
    }
}

