/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.WebBasedRegisteredService
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.web.servlet.LocaleResolver
 *  org.springframework.web.servlet.i18n.LocaleChangeInterceptor
 *  org.springframework.web.servlet.support.RequestContextUtils
 */
package org.apereo.cas.web.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.WebBasedRegisteredService;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

public class CasLocaleChangeInterceptor
extends LocaleChangeInterceptor {
    protected final ObjectProvider<CasConfigurationProperties> casProperties;
    protected final ObjectProvider<ArgumentExtractor> argumentExtractor;
    protected final ObjectProvider<ServicesManager> servicesManager;
    private List<String> supportedFlows = new ArrayList<String>();

    protected static void configureLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver((HttpServletRequest)request);
        if (localeResolver != null) {
            localeResolver.setLocale(request, response, locale);
            request.setAttribute(Locale.class.getName(), (Object)locale);
        }
    }

    private static boolean isLocaleConfigured(HttpServletRequest request) {
        return request.getAttribute(Locale.class.getName()) == null;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        boolean match;
        String newLocale;
        Locale locale;
        WebBasedRegisteredService webRegisteredService;
        RegisteredService registeredService;
        String requestUrl = request.getRequestURL().toString();
        if (((CasConfigurationProperties)this.casProperties.getObject()).getLocale().isForceDefaultLocale()) {
            Locale locale2 = Locale.forLanguageTag(((CasConfigurationProperties)this.casProperties.getObject()).getLocale().getDefaultValue());
            CasLocaleChangeInterceptor.configureLocale(request, response, locale2);
            return true;
        }
        WebApplicationService service = ((ArgumentExtractor)this.argumentExtractor.getObject()).extractService(request);
        if (service != null && (registeredService = ((ServicesManager)this.servicesManager.getObject()).findServiceBy((Service)service)) instanceof WebBasedRegisteredService && StringUtils.isNotBlank((CharSequence)(webRegisteredService = (WebBasedRegisteredService)registeredService).getLocale())) {
            locale = Locale.forLanguageTag(SpringExpressionLanguageValueResolver.getInstance().resolve(webRegisteredService.getLocale()));
            CasLocaleChangeInterceptor.configureLocale(request, response, locale);
        }
        if ((newLocale = request.getParameter(this.getParamName())) != null) {
            newLocale = newLocale.replace('_', '-');
            Locale locale3 = Locale.forLanguageTag(newLocale);
            CasLocaleChangeInterceptor.configureLocale(request, response, locale3);
        }
        if (request.getLocale() != null && CasLocaleChangeInterceptor.isLocaleConfigured(request) && (match = this.supportedFlows.stream().anyMatch(flowId -> requestUrl.contains("/" + flowId)))) {
            locale = RequestContextUtils.getLocale((HttpServletRequest)request);
            CasLocaleChangeInterceptor.configureLocale(request, response, locale);
        }
        return true;
    }

    @Generated
    public CasLocaleChangeInterceptor(ObjectProvider<CasConfigurationProperties> casProperties, ObjectProvider<ArgumentExtractor> argumentExtractor, ObjectProvider<ServicesManager> servicesManager) {
        this.casProperties = casProperties;
        this.argumentExtractor = argumentExtractor;
        this.servicesManager = servicesManager;
    }

    @Generated
    public void setSupportedFlows(List<String> supportedFlows) {
        this.supportedFlows = supportedFlows;
    }
}

