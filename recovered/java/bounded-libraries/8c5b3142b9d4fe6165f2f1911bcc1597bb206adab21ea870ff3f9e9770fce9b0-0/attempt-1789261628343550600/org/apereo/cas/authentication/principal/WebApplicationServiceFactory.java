/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apache.http.client.utils.URIBuilder
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.validation.ValidationResponseType
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.utils.URIBuilder;
import org.apereo.cas.authentication.principal.AbstractServiceFactory;
import org.apereo.cas.authentication.principal.AbstractWebApplicationService;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.validation.ValidationResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServiceFactory
extends AbstractServiceFactory<WebApplicationService> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationServiceFactory.class);
    private static final List<String> IGNORED_ATTRIBUTES_PARAMS = List.of("password", "service", "targetService", "ticket", "format");

    protected AbstractWebApplicationService newWebApplicationService(HttpServletRequest request, String serviceToUse) {
        String artifactId = Optional.ofNullable(request).map(httpServletRequest -> httpServletRequest.getParameter("ticket")).orElse(null);
        String id = WebApplicationServiceFactory.cleanupUrl(serviceToUse);
        SimpleWebApplicationServiceImpl newService = new SimpleWebApplicationServiceImpl(id, serviceToUse, artifactId);
        WebApplicationServiceFactory.determineWebApplicationFormat(request, newService);
        String source = WebApplicationServiceFactory.getSourceParameter(request, "targetService", "service");
        newService.setSource(source);
        if (request != null) {
            this.populateAttributes(newService, request);
            if (StringUtils.isNotBlank((CharSequence)source)) {
                newService.getAttributes().put(source, CollectionUtils.wrap((Object)id));
            }
        }
        return newService;
    }

    protected void populateAttributes(AbstractWebApplicationService service, HttpServletRequest request) {
        Map<String, ArrayList> attributes = request.getParameterMap().entrySet().stream().filter(entry -> !IGNORED_ATTRIBUTES_PARAMS.contains(entry.getKey())).map(entry -> Pair.of((Object)((String)entry.getKey()), (Object)((ArrayList)CollectionUtils.toCollection(entry.getValue(), ArrayList.class)))).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        attributes.putAll(this.extractQueryParameters(service));
        LOGGER.trace("Extracted attributes [{}] for service [{}]", attributes, (Object)service.getId());
        service.setAttributes(new HashMap<String, List<Object>>(attributes));
    }

    protected Map<String, List> extractQueryParameters(WebApplicationService service) {
        LinkedHashMap<String, List> attributes = new LinkedHashMap<String, List>();
        String originalUrl = service.getOriginalUrl();
        try {
            if (StringUtils.isNotBlank((CharSequence)originalUrl) && originalUrl.startsWith("http") && originalUrl.contains("?")) {
                List queryParams = (List)FunctionUtils.doUnchecked(() -> new URIBuilder(originalUrl).getQueryParams());
                queryParams.forEach(pair -> attributes.put(pair.getName(), CollectionUtils.wrapArrayList((Object[])new String[]{pair.getValue()})));
            }
        }
        catch (Exception e) {
            LOGGER.error("Unable to extract query parameters from [{}]: [{}]", (Object)originalUrl, (Object)e.getMessage());
        }
        return attributes;
    }

    private static AbstractWebApplicationService determineWebApplicationFormat(HttpServletRequest request, AbstractWebApplicationService webApplicationService) {
        String format = Optional.ofNullable(request).map(httpServletRequest -> httpServletRequest.getParameter("format")).orElse("");
        try {
            if (StringUtils.isNotBlank((CharSequence)format)) {
                ValidationResponseType formatType = ValidationResponseType.valueOf((String)Objects.requireNonNull(format).toUpperCase());
                webApplicationService.setFormat(formatType);
            }
        }
        catch (Exception e) {
            LOGGER.error("Format specified in the request [{}] is not recognized", (Object)format);
        }
        return webApplicationService;
    }

    public WebApplicationService createService(HttpServletRequest request) {
        String serviceToUse = this.getRequestedService(request);
        if (StringUtils.isBlank((CharSequence)serviceToUse)) {
            LOGGER.trace("No service is specified in the request. Skipping service creation");
            return null;
        }
        return this.newWebApplicationService(request, serviceToUse);
    }

    public WebApplicationService createService(String id) {
        HttpServletRequest request = HttpRequestUtils.getHttpServletRequestFromRequestAttributes();
        return this.newWebApplicationService(request, id);
    }

    protected String getRequestedService(HttpServletRequest request) {
        String targetService = request.getParameter("targetService");
        String service = request.getParameter("service");
        Object serviceAttribute = request.getAttribute("service");
        if (StringUtils.isNotBlank((CharSequence)targetService)) {
            return targetService;
        }
        if (StringUtils.isNotBlank((CharSequence)service)) {
            return service;
        }
        if (serviceAttribute != null) {
            if (serviceAttribute instanceof Service) {
                return ((Service)serviceAttribute).getId();
            }
            return serviceAttribute.toString();
        }
        return null;
    }
}

