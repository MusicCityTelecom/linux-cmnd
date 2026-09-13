/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.OneTimeTokenAccount
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Response
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.UnauthorizedServiceException
 *  org.apereo.cas.ticket.ServiceTicket
 *  org.apereo.cas.ticket.Ticket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistrySupport
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.web.cookie.CasCookieBuilder
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.binding.message.MessageBuilder
 *  org.springframework.binding.message.MessageContext
 *  org.springframework.binding.message.MessageResolver
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.servlet.ModelAndView
 *  org.springframework.webflow.context.ExternalContextHolder
 *  org.springframework.webflow.context.servlet.ServletExternalContext
 *  org.springframework.webflow.core.collection.MutableAttributeMap
 *  org.springframework.webflow.engine.Flow
 *  org.springframework.webflow.engine.FlowVariable
 *  org.springframework.webflow.execution.Event
 *  org.springframework.webflow.execution.FlowSession
 *  org.springframework.webflow.execution.RequestContext
 *  org.springframework.webflow.execution.RequestContextHolder
 *  org.springframework.webflow.test.MockRequestContext
 */
package org.apereo.cas.web.support;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.OneTimeTokenAccount;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Response;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.cookie.CasCookieBuilder;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.FlowVariable;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.test.MockRequestContext;

public final class WebUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);
    public static final String PUBLIC_WORKSTATION_ATTRIBUTE = "publicWorkstation";
    public static final String REQUEST_SURROGATE_ACCOUNT_ATTRIBUTE = "requestSurrogateAccount";
    public static final String PARAMETER_TICKET_GRANTING_TICKET_ID = "ticketGrantingTicketId";
    private static final String PARAMETER_AUTHENTICATION = "authentication";
    private static final String PARAMETER_AUTHENTICATION_RESULT_BUILDER = "authenticationResultBuilder";
    private static final String PARAMETER_AUTHENTICATION_RESULT = "authenticationResult";
    private static final String PARAMETER_CREDENTIAL = "credential";
    private static final String PARAMETER_UNAUTHORIZED_REDIRECT_URL = "unauthorizedRedirectUrl";
    private static final String PARAMETER_SERVICE_TICKET_ID = "serviceTicketId";
    private static final String PARAMETER_LOGOUT_REQUESTS = "logoutRequests";
    private static final String PARAMETER_SERVICE_UI_METADATA = "serviceUIMetadata";

    public static Collection<Event> getResolvedEventsAsAttribute(RequestContext context) {
        return (Collection)context.getAttributes().get("resolvedAuthenticationEvents", Collection.class);
    }

    public static void putResolvedEventsAsAttribute(RequestContext context, Collection<Event> resolvedEvents) {
        context.getAttributes().put("resolvedAuthenticationEvents", resolvedEvents);
    }

    public static HttpServletRequest getHttpServletRequestFromExternalWebflowContext(RequestContext context) {
        return (HttpServletRequest)context.getExternalContext().getNativeRequest();
    }

    public static HttpServletRequest getHttpServletRequestFromExternalWebflowContext() {
        ServletExternalContext servletExternalContext = (ServletExternalContext)ExternalContextHolder.getExternalContext();
        if (servletExternalContext != null) {
            return (HttpServletRequest)servletExternalContext.getNativeRequest();
        }
        return null;
    }

    public static HttpServletResponse getHttpServletResponseFromExternalWebflowContext(RequestContext context) {
        return (HttpServletResponse)context.getExternalContext().getNativeResponse();
    }

    public static HttpServletResponse getHttpServletResponseFromExternalWebflowContext() {
        ServletExternalContext servletExternalContext = (ServletExternalContext)ExternalContextHolder.getExternalContext();
        if (servletExternalContext != null) {
            return (HttpServletResponse)servletExternalContext.getNativeResponse();
        }
        return null;
    }

    public static WebApplicationService getService(List<ArgumentExtractor> argumentExtractors, RequestContext context) {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(context);
        return HttpRequestUtils.getService(argumentExtractors, (HttpServletRequest)request);
    }

    public static WebApplicationService getService(RequestContext context) {
        return Optional.ofNullable(context).map(requestContext -> (WebApplicationService)requestContext.getFlowScope().get("service")).orElse(null);
    }

    public static RegisteredService getRegisteredService(RequestContext context) {
        return Optional.ofNullable(context).map(requestContext -> (RegisteredService)requestContext.getFlowScope().get("registeredService")).orElse(null);
    }

    public static RegisteredService getRegisteredService(HttpServletRequest request) {
        return Optional.ofNullable(request).map(requestContext -> (RegisteredService)request.getAttribute("registeredService")).orElse(null);
    }

    public static void putTicketGrantingTicket(RequestContext context, TicketGrantingTicket ticket) {
        context.getFlowScope().put("ticketGrantingTicket", (Object)ticket);
    }

    public static TicketGrantingTicket getTicketGrantingTicket(RequestContext context) {
        return (TicketGrantingTicket)context.getFlowScope().get("ticketGrantingTicket", TicketGrantingTicket.class);
    }

    public static void putTicketGrantingTicketInScopes(RequestContext context, TicketGrantingTicket ticket) {
        String ticketValue = Optional.ofNullable(ticket).map(Ticket::getId).orElse(null);
        WebUtils.putTicketGrantingTicketInScopes(context, ticketValue);
    }

    public static void putTicketGrantingTicketInScopes(RequestContext context, String ticketValue) {
        WebUtils.putTicketGrantingTicketIntoMap((MutableAttributeMap<Object>)context.getRequestScope(), ticketValue);
        WebUtils.putTicketGrantingTicketIntoMap((MutableAttributeMap<Object>)context.getFlowScope(), ticketValue);
        for (FlowSession session = context.getFlowExecutionContext().getActiveSession().getParent(); session != null; session = session.getParent()) {
            WebUtils.putTicketGrantingTicketIntoMap((MutableAttributeMap<Object>)session.getScope(), ticketValue);
        }
    }

    public static void putTicketGrantingTicketIntoMap(MutableAttributeMap<Object> map, String ticketValue) {
        FunctionUtils.doIf((boolean)StringUtils.isNotBlank((CharSequence)ticketValue), value -> map.put(PARAMETER_TICKET_GRANTING_TICKET_ID, value), value -> map.remove(PARAMETER_TICKET_GRANTING_TICKET_ID)).accept(ticketValue);
    }

    public static String getTicketGrantingTicketId(RequestContext context) {
        String tgtFromRequest = WebUtils.getTicketGrantingTicketIdFrom(context.getRequestScope());
        String tgtFromFlow = WebUtils.getTicketGrantingTicketIdFrom(context.getFlowScope());
        return Optional.ofNullable(tgtFromRequest).orElse(tgtFromFlow);
    }

    public static String getTicketGrantingTicketIdFrom(MutableAttributeMap scopeMap) {
        return (String)scopeMap.get(PARAMETER_TICKET_GRANTING_TICKET_ID);
    }

    public static void putServiceTicketInRequestScope(RequestContext context, ServiceTicket ticketValue) {
        context.getRequestScope().put(PARAMETER_SERVICE_TICKET_ID, (Object)ticketValue.getId());
    }

    public static String getServiceTicketFromRequestScope(RequestContext context) {
        return context.getRequestScope().getString(PARAMETER_SERVICE_TICKET_ID);
    }

    public static void putUnauthorizedRedirectUrlIntoFlowScope(RequestContext context, URI url) {
        context.getFlowScope().put(PARAMETER_UNAUTHORIZED_REDIRECT_URL, (Object)url);
    }

    public static URI getUnauthorizedRedirectUrlFromFlowScope(RequestContext context) {
        return (URI)context.getFlowScope().get(PARAMETER_UNAUTHORIZED_REDIRECT_URL, URI.class);
    }

    public static void putLogoutRequests(RequestContext context, List<SingleLogoutRequestContext> requests) {
        context.getFlowScope().put(PARAMETER_LOGOUT_REQUESTS, requests);
    }

    public static void putLogoutUrls(RequestContext context, Map urls) {
        context.getFlowScope().put("logoutUrls", (Object)urls);
    }

    public static List<SingleLogoutRequestContext> getLogoutRequests(RequestContext context) {
        return (List)context.getFlowScope().get(PARAMETER_LOGOUT_REQUESTS);
    }

    public static void putServiceIntoFlowScope(RequestContext context, Service service) {
        context.getFlowScope().put("service", (Object)service);
    }

    public static void putServiceIntoFlashScope(RequestContext context, Service service) {
        context.getFlashScope().put("service", (Object)service);
    }

    public static void putWarningCookie(RequestContext context, Boolean cookieValue) {
        context.getFlowScope().put("warnCookieValue", (Object)cookieValue);
    }

    public static boolean getWarningCookie(RequestContext context) {
        String val = ObjectUtils.defaultIfNull((Object)context.getFlowScope().get("warnCookieValue"), (Object)Boolean.FALSE.toString()).toString();
        return Boolean.parseBoolean(val);
    }

    public static void putRegisteredService(HttpServletRequest request, RegisteredService registeredService) {
        request.setAttribute("registeredService", (Object)registeredService);
    }

    public static void putRegisteredService(RequestContext context, RegisteredService registeredService) {
        context.getFlowScope().put("registeredService", (Object)registeredService);
    }

    public static <T extends Credential> T getCredential(RequestContext context, @NonNull Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz is marked non-null but is null");
        }
        Credential credential = WebUtils.getCredential(context);
        if (credential == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(credential.getClass())) {
            throw new ClassCastException("credential [" + credential.getId() + " is of type " + credential.getClass() + " when we were expecting " + clazz);
        }
        return (T)credential;
    }

    public static Credential getCredential(RequestContext context) {
        Credential cFromRequest = (Credential)context.getRequestScope().get(PARAMETER_CREDENTIAL);
        Credential cFromFlashScope = (Credential)context.getFlashScope().get(PARAMETER_CREDENTIAL);
        Credential cFromFlow = (Credential)context.getFlowScope().get(PARAMETER_CREDENTIAL);
        Credential cFromConversation = (Credential)context.getConversationScope().get(PARAMETER_CREDENTIAL);
        Credential credential = cFromRequest;
        if (credential == null || StringUtils.isBlank((CharSequence)credential.getId())) {
            credential = cFromFlow;
        }
        if (credential == null || StringUtils.isBlank((CharSequence)credential.getId())) {
            credential = cFromFlashScope;
        }
        if ((credential == null || StringUtils.isBlank((CharSequence)credential.getId())) && (credential = cFromConversation) != null && !StringUtils.isBlank((CharSequence)credential.getId())) {
            context.getFlowScope().put(PARAMETER_CREDENTIAL, (Object)credential);
        }
        if (credential == null) {
            FlowSession session = context.getFlowExecutionContext().getActiveSession();
            credential = (Credential)session.getScope().get(PARAMETER_CREDENTIAL, Credential.class);
        }
        if (credential != null && StringUtils.isBlank((CharSequence)credential.getId())) {
            return null;
        }
        return credential;
    }

    public static void putCredential(RequestContext context, Credential credential) {
        if (credential == null) {
            context.getRequestScope().remove(PARAMETER_CREDENTIAL);
            context.getFlowScope().remove(PARAMETER_CREDENTIAL);
            context.getConversationScope().remove(PARAMETER_CREDENTIAL);
        } else {
            WebUtils.putCredentialIntoScope((MutableAttributeMap<Object>)context.getRequestScope(), credential);
            WebUtils.putCredentialIntoScope((MutableAttributeMap<Object>)context.getFlowScope(), credential);
            WebUtils.putCredentialIntoScope((MutableAttributeMap<Object>)context.getConversationScope(), credential);
        }
    }

    public static void putCredentialIntoScope(MutableAttributeMap<Object> scope, Credential credential) {
        scope.put(PARAMETER_CREDENTIAL, (Object)credential);
    }

    public static void removeCredential(RequestContext context) {
        WebUtils.putCredential(context, null);
    }

    public static boolean isAuthenticatingAtPublicWorkstation(RequestContext ctx) {
        if (ctx.getFlowScope().contains(PUBLIC_WORKSTATION_ATTRIBUTE)) {
            LOGGER.debug("Public workstation flag detected. SSO session will be considered renewed.");
            return true;
        }
        return false;
    }

    public static void putPublicWorkstationToFlowIfRequestParameterPresent(RequestContext context) {
        if (context.getRequestParameters().contains(PUBLIC_WORKSTATION_ATTRIBUTE)) {
            context.getFlowScope().put(PUBLIC_WORKSTATION_ATTRIBUTE, (Object)Boolean.TRUE);
        }
    }

    public static void putWarnCookieIfRequestParameterPresent(CasCookieBuilder warnCookieGenerator, RequestContext context) {
        if (warnCookieGenerator != null) {
            LOGGER.trace("Evaluating request to determine if warning cookie should be generated");
            if (StringUtils.isNotBlank((CharSequence)context.getExternalContext().getRequestParameterMap().get("warn"))) {
                HttpServletResponse response = WebUtils.getHttpServletResponseFromExternalWebflowContext(context);
                warnCookieGenerator.addCookie(response, "true");
            }
        } else {
            LOGGER.trace("No warning cookie generator is defined");
        }
    }

    public static void putAuthentication(Authentication authentication, RequestContext ctx) {
        ctx.getConversationScope().put(PARAMETER_AUTHENTICATION, (Object)authentication);
    }

    public static Authentication getAuthentication(RequestContext ctx) {
        return (Authentication)ctx.getConversationScope().get(PARAMETER_AUTHENTICATION, Authentication.class);
    }

    public static void putAuthenticationResultBuilder(AuthenticationResultBuilder builder, RequestContext ctx) {
        ctx.getConversationScope().put(PARAMETER_AUTHENTICATION_RESULT_BUILDER, (Object)builder);
    }

    public static Principal getPrincipalFromRequestContext(RequestContext requestContext, TicketRegistrySupport ticketRegistrySupport) {
        String tgt = WebUtils.getTicketGrantingTicketId(requestContext);
        if (StringUtils.isBlank((CharSequence)tgt)) {
            throw new IllegalArgumentException("No ticket-granting ticket could be found in the context");
        }
        return ticketRegistrySupport.getAuthenticatedPrincipalFrom(tgt);
    }

    public static Principal getPrincipalFromRequestContext(RequestContext requestContext) {
        return (Principal)requestContext.getFlowScope().get("principal", Principal.class);
    }

    public static AuthenticationResultBuilder getAuthenticationResultBuilder(RequestContext ctx) {
        return (AuthenticationResultBuilder)ctx.getConversationScope().get(PARAMETER_AUTHENTICATION_RESULT_BUILDER, AuthenticationResultBuilder.class);
    }

    public static void putAuthenticationResult(AuthenticationResult authenticationResult, RequestContext context) {
        context.getConversationScope().put(PARAMETER_AUTHENTICATION_RESULT, (Object)authenticationResult);
    }

    public static AuthenticationResult getAuthenticationResult(RequestContext ctx) {
        return (AuthenticationResult)ctx.getConversationScope().get(PARAMETER_AUTHENTICATION_RESULT, AuthenticationResult.class);
    }

    public static String getHttpServletRequestUserAgentFromRequestContext() {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext();
        return HttpRequestUtils.getHttpServletRequestUserAgent((HttpServletRequest)request);
    }

    public static String getHttpServletRequestUserAgentFromRequestContext(RequestContext context) {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(context);
        return WebUtils.getHttpServletRequestUserAgentFromRequestContext(request);
    }

    public static String getHttpServletRequestUserAgentFromRequestContext(HttpServletRequest request) {
        return HttpRequestUtils.getHttpServletRequestUserAgent((HttpServletRequest)request);
    }

    public static GeoLocationRequest getHttpServletRequestGeoLocationFromRequestContext() {
        HttpServletRequest servletRequest = WebUtils.getHttpServletRequestFromExternalWebflowContext();
        return WebUtils.getHttpServletRequestGeoLocation(servletRequest);
    }

    public static GeoLocationRequest getHttpServletRequestGeoLocationFromRequestContext(RequestContext context) {
        HttpServletRequest servletRequest = WebUtils.getHttpServletRequestFromExternalWebflowContext(context);
        return WebUtils.getHttpServletRequestGeoLocation(servletRequest);
    }

    public static GeoLocationRequest getHttpServletRequestGeoLocation(HttpServletRequest servletRequest) {
        if (servletRequest != null) {
            return HttpRequestUtils.getHttpServletRequestGeoLocation((HttpServletRequest)servletRequest);
        }
        return null;
    }

    public static void putGeoLocationTrackingIntoFlowScope(RequestContext context, Object value) {
        context.getFlowScope().put("trackGeoLocation", value);
    }

    public static Boolean isGeoLocationTrackingIntoFlowScope(RequestContext context) {
        return (Boolean)context.getFlowScope().get("trackGeoLocation", Boolean.class);
    }

    public static void putRecaptchaPropertiesFlowScope(RequestContext context, GoogleRecaptchaProperties googleRecaptcha) {
        MutableAttributeMap flowScope = context.getFlowScope();
        if (googleRecaptcha.isEnabled()) {
            flowScope.put("recaptchaSiteKey", (Object)googleRecaptcha.getSiteKey());
            flowScope.put("recaptchaInvisible", (Object)googleRecaptcha.isInvisible());
            flowScope.put("recaptchaPosition", (Object)googleRecaptcha.getPosition());
            flowScope.put("recaptchaVersion", (Object)googleRecaptcha.getVersion().name().toLowerCase());
        }
    }

    public static String getRecaptchaSiteKey(RequestContext context) {
        MutableAttributeMap flowScope = context.getFlowScope();
        return (String)flowScope.get("recaptchaSiteKey", String.class);
    }

    public static void putStaticAuthenticationIntoFlowScope(RequestContext context, Object value) {
        context.getFlowScope().put("staticAuthentication", value);
    }

    public static void putPasswordManagementEnabled(RequestContext context, Boolean value) {
        context.getFlowScope().put("passwordManagementEnabled", (Object)value);
    }

    public static void putAccountProfileManagementEnabled(RequestContext context, Boolean value) {
        context.getFlowScope().put("accountProfileManagementEnabled", (Object)value);
    }

    public static void putSecurityQuestionsEnabled(RequestContext context, Boolean value) {
        context.getFlowScope().put("securityQuestionsEnabled", (Object)value);
    }

    public static boolean isPasswordManagementEnabled(RequestContext context) {
        return (Boolean)context.getFlowScope().get("passwordManagementEnabled", Boolean.class);
    }

    public static void putPrincipal(RequestContext requestContext, Principal authenticationPrincipal) {
        requestContext.getFlowScope().put("principal", (Object)authenticationPrincipal);
    }

    public static void putLogoutRedirectUrl(RequestContext context, String service) {
        context.getFlowScope().put("logoutRedirectUrl", (Object)service);
    }

    public static void putLogoutRedirectUrl(HttpServletRequest request, String service) {
        request.setAttribute("logoutRedirectUrl", (Object)service);
    }

    public static <T> T getLogoutRedirectUrl(HttpServletRequest request, Class<T> clazz) {
        Object value = request.getAttribute("logoutRedirectUrl");
        return value != null ? (T)clazz.cast(value) : null;
    }

    public static <T> T getLogoutRedirectUrl(RequestContext context, Class<T> clazz) {
        return (T)context.getFlowScope().get("logoutRedirectUrl", clazz);
    }

    public static void removeLogoutRedirectUrl(RequestContext context) {
        context.getFlowScope().remove("logoutRedirectUrl");
    }

    public static void putRememberMeAuthenticationEnabled(RequestContext context, Boolean enabled) {
        context.getFlowScope().put("rememberMeAuthenticationEnabled", (Object)enabled);
    }

    public static Boolean isRememberMeAuthenticationEnabled(RequestContext context) {
        return context.getFlowScope().getBoolean("rememberMeAuthenticationEnabled", Boolean.FALSE);
    }

    public static <T> Optional<T> getMultifactorAuthenticationTrustRecord(RequestContext context, Class<T> clazz) {
        return Optional.ofNullable(context.getFlowScope().get("mfaTrustRecord", clazz));
    }

    public static void putMultifactorAuthenticationTrustRecord(RequestContext context, Serializable object) {
        context.getFlowScope().put("mfaTrustRecord", (Object)object);
    }

    public static void putResolvedMultifactorAuthenticationProviders(RequestContext context, Collection<MultifactorAuthenticationProvider> value) {
        Set providerIds = value.stream().map(MultifactorAuthenticationProvider::getId).collect(Collectors.toSet());
        context.getConversationScope().put("resolvedMultifactorAuthenticationProviders", providerIds);
    }

    public static Collection<String> getResolvedMultifactorAuthenticationProviders(RequestContext context) {
        return (Collection)context.getConversationScope().get("resolvedMultifactorAuthenticationProviders", Collection.class);
    }

    public static void putServiceUserInterfaceMetadata(RequestContext requestContext, Serializable mdui) {
        if (mdui != null) {
            requestContext.getFlowScope().put(PARAMETER_SERVICE_UI_METADATA, (Object)mdui);
        }
    }

    public static <T> T getServiceUserInterfaceMetadata(RequestContext requestContext, Class<T> clz) {
        if (requestContext.getFlowScope().contains(PARAMETER_SERVICE_UI_METADATA)) {
            return (T)requestContext.getFlowScope().get(PARAMETER_SERVICE_UI_METADATA, clz);
        }
        return null;
    }

    public static String getServiceRedirectUrl(RequestContext requestContext) {
        return (String)requestContext.getRequestScope().get("url", String.class);
    }

    public static void putServiceRedirectUrl(RequestContext requestContext, String url) {
        requestContext.getRequestScope().put("url", (Object)url);
    }

    public static void putServiceResponseIntoRequestScope(RequestContext requestContext, Response response) {
        requestContext.getRequestScope().put("parameters", (Object)response.getAttributes());
        WebUtils.putServiceRedirectUrl(requestContext, response.getUrl());
    }

    public static void putServiceOriginalUrlIntoRequestScope(RequestContext requestContext, WebApplicationService service) {
        requestContext.getRequestScope().put("originalUrl", (Object)service.getOriginalUrl());
    }

    public static ModelAndView produceUnauthorizedErrorView(Exception ex) {
        UnauthorizedServiceException error = new UnauthorizedServiceException((Throwable)ex, "screen.service.error.message", "");
        return WebUtils.produceErrorView((Throwable)error);
    }

    public static ModelAndView produceErrorView(String view, Throwable e) {
        ModelAndView mv = new ModelAndView(view, CollectionUtils.wrap((String)"rootCauseException", (Object)e));
        mv.setStatus(HttpStatus.BAD_REQUEST);
        LoggingUtils.error((Logger)LOGGER, (Throwable)e);
        return mv;
    }

    public static void produceErrorView(HttpServletRequest request, HttpStatus badRequest, String message) {
        request.setAttribute("status", (Object)HttpStatus.BAD_REQUEST.value());
        request.setAttribute("error", (Object)HttpStatus.BAD_REQUEST.name());
        request.setAttribute("message", (Object)message);
    }

    public static ModelAndView produceErrorView(Throwable e) {
        return WebUtils.produceErrorView("error/casServiceErrorView", e);
    }

    public static Authentication getInProgressAuthentication() {
        RequestContext context = RequestContextHolder.getRequestContext();
        Authentication authentication = Optional.ofNullable(context).map(WebUtils::getAuthentication).orElse(null);
        if (authentication == null) {
            return AuthenticationCredentialsThreadLocalBinder.getInProgressAuthentication();
        }
        return authentication;
    }

    public static void putPasswordlessAuthenticationEnabled(RequestContext requestContext, Boolean value) {
        requestContext.getFlowScope().put("passwordlessAuthenticationEnabled", (Object)value);
    }

    public static void putPasswordlessAuthenticationAccount(RequestContext requestContext, Object account) {
        requestContext.getFlowScope().put("passwordlessAccount", account);
    }

    public static <T> T getPasswordlessAuthenticationAccount(Event event, Class<T> clazz) {
        if (event != null) {
            return (T)event.getAttributes().get("passwordlessAccount", clazz);
        }
        return null;
    }

    public static <T> T getPasswordlessAuthenticationAccount(RequestContext requestContext, Class<T> clazz) {
        Object result = WebUtils.getPasswordlessAuthenticationAccount(requestContext.getCurrentEvent(), clazz);
        if (result == null) {
            result = requestContext.getFlowScope().get("passwordlessAccount", clazz);
        }
        return result;
    }

    public static boolean hasPasswordlessAuthenticationAccount(RequestContext requestContext) {
        return requestContext.getFlowScope().contains("passwordlessAccount");
    }

    public static void putSurrogateAuthenticationRequest(RequestContext context, Boolean value) {
        context.getFlowScope().put(REQUEST_SURROGATE_ACCOUNT_ATTRIBUTE, (Object)value);
    }

    public static boolean hasSurrogateAuthenticationRequest(RequestContext requestContext) {
        return BooleanUtils.toBoolean((Boolean)requestContext.getFlowScope().getBoolean(REQUEST_SURROGATE_ACCOUNT_ATTRIBUTE, Boolean.FALSE));
    }

    public static void removeSurrogateAuthenticationRequest(RequestContext requestContext) {
        requestContext.getFlowScope().remove(REQUEST_SURROGATE_ACCOUNT_ATTRIBUTE);
    }

    public static void putSurrogateAuthenticationAccounts(RequestContext requestContext, List<String> surrogates) {
        requestContext.getFlowScope().put("surrogates", surrogates);
    }

    public static List<String> getSurrogateAuthenticationAccounts(RequestContext requestContext) {
        return (List)requestContext.getFlowScope().get("surrogates", List.class);
    }

    public static void putGraphicalUserAuthenticationEnabled(RequestContext requestContext, Boolean value) {
        requestContext.getFlowScope().put("guaEnabled", (Object)value);
    }

    public static boolean isGraphicalUserAuthenticationEnabled(RequestContext requestContext) {
        return BooleanUtils.isTrue((Boolean)((Boolean)requestContext.getFlowScope().get("guaEnabled", Boolean.class)));
    }

    public static void putGraphicalUserAuthenticationUsername(RequestContext requestContext, String username) {
        requestContext.getFlowScope().put("guaUsername", (Object)username);
    }

    public static boolean containsGraphicalUserAuthenticationUsername(RequestContext requestContext) {
        return requestContext.getFlowScope().contains("guaUsername");
    }

    public static void putGraphicalUserAuthenticationImage(RequestContext requestContext, String image) {
        requestContext.getFlowScope().put("guaUserImage", (Object)image);
    }

    public static boolean containsGraphicalUserAuthenticationImage(RequestContext requestContext) {
        return requestContext.getFlowScope().contains("guaUserImage");
    }

    public static void putDelegatedAuthenticationProviderPrimary(RequestContext context, Object client) {
        context.getFlowScope().put("delegatedAuthenticationProviderPrimary", client);
    }

    public static Object getDelegatedAuthenticationProviderPrimary(RequestContext context) {
        return context.getFlowScope().get("delegatedAuthenticationProviderPrimary");
    }

    public static void putAvailableAuthenticationHandleNames(RequestContext context, Collection<String> availableHandlers) {
        context.getFlowScope().put("availableAuthenticationHandlerNames", availableHandlers);
    }

    public static Collection<String> getAvailableAuthenticationHandleNames(RequestContext context) {
        return (Collection)context.getFlowScope().get("availableAuthenticationHandlerNames", Collection.class);
    }

    public static void putAcceptableUsagePolicyStatusIntoFlowScope(RequestContext context, Object status) {
        context.getFlowScope().put("aupStatus", status);
    }

    public static void putAcceptableUsagePolicyTermsIntoFlowScope(RequestContext context, Object terms) {
        context.getFlowScope().put("aupPolicy", terms);
    }

    public static <T> T getAcceptableUsagePolicyTermsFromFlowScope(RequestContext requestContext, Class<T> clazz) {
        if (requestContext.getFlowScope().contains("aupPolicy")) {
            return (T)requestContext.getFlowScope().get("aupPolicy", clazz);
        }
        return null;
    }

    public static void putCustomLoginFormFields(RequestContext context, Map customLoginFormFields) {
        context.getFlowScope().put("customLoginFormFields", (Object)customLoginFormFields);
    }

    public static void putInitialHttpRequestPostParameters(RequestContext context) {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(context);
        context.getFlashScope().put("httpRequestInitialPostParameters", (Object)request.getParameterMap());
    }

    public static void putExistingSingleSignOnSessionAvailable(RequestContext context, boolean value) {
        context.getFlowScope().put("existingSingleSignOnSessionAvailable", (Object)value);
    }

    public static Boolean isExistingSingleSignOnSessionAvailable(MockRequestContext context) {
        return (Boolean)context.getFlowScope().get("existingSingleSignOnSessionAvailable", Boolean.class);
    }

    public static void putExistingSingleSignOnSessionPrincipal(RequestContext context, Principal value) {
        context.getFlashScope().put("existingSingleSignOnSessionPrincipal", (Object)value);
    }

    public static void putCasLoginFormViewable(RequestContext context, boolean viewable) {
        context.getFlowScope().put("casLoginFormViewable", (Object)viewable);
    }

    public static boolean isCasLoginFormViewable(RequestContext context) {
        return context.getFlowScope().getBoolean("casLoginFormViewable", Boolean.TRUE);
    }

    public static boolean isCasLoginFormSetToViewable(RequestContext context) {
        return context.getFlowScope().getBoolean("casLoginFormViewable", Boolean.FALSE);
    }

    public static String getHttpRequestFullUrl(RequestContext requestContext) {
        return WebUtils.getHttpRequestFullUrl(WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext));
    }

    public static String getHttpRequestFullUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
    }

    public static void createCredential(RequestContext requestContext) {
        WebUtils.removeCredential(requestContext);
        Flow flow = (Flow)requestContext.getActiveFlow();
        FlowVariable var = flow.getVariable(PARAMETER_CREDENTIAL);
        if (var != null) {
            var.create(requestContext);
        }
    }

    public static void putDelegatedAuthenticationProviderConfigurations(RequestContext context, Set<? extends Serializable> urls) {
        context.getFlowScope().put("delegatedAuthenticationProviderConfigurations", urls);
    }

    public static void putDelegatedAuthenticationDynamicProviderSelection(RequestContext context, Boolean result) {
        context.getFlowScope().put("delegatedAuthenticationDynamicProviderSelection", (Object)result);
    }

    public static Boolean isDelegatedAuthenticationDynamicProviderSelection(RequestContext context) {
        return (Boolean)context.getFlowScope().get("delegatedAuthenticationDynamicProviderSelection", Boolean.class, (Object)Boolean.FALSE);
    }

    public static Set<? extends Serializable> getDelegatedAuthenticationProviderConfigurations(RequestContext context) {
        MutableAttributeMap scope = context.getFlowScope();
        if (scope.contains("delegatedAuthenticationProviderConfigurations", Set.class)) {
            return (Set)scope.get("delegatedAuthenticationProviderConfigurations", Set.class);
        }
        return new HashSet(0);
    }

    public static void putOpenIdLocalUserId(RequestContext context, String user) {
        if (StringUtils.isBlank((CharSequence)user)) {
            context.getFlowScope().remove("openIdLocalId");
        } else {
            context.getFlowScope().put("openIdLocalId", (Object)user);
        }
    }

    @Deprecated(since="6.2.0")
    public static String getOpenIdLocalUserId(RequestContext context) {
        return (String)context.getFlowScope().get("openIdLocalId", String.class);
    }

    public static void putMultifactorAuthenticationProviderIdIntoFlowScope(RequestContext context, MultifactorAuthenticationProvider provider) {
        context.getFlowScope().put("mfaProviderId", (Object)provider.getId());
    }

    public static String getMultifactorAuthenticationProviderById(RequestContext context) {
        return (String)context.getFlowScope().get("mfaProviderId", String.class);
    }

    public static void putSelectableMultifactorAuthenticationProviders(RequestContext requestContext, List<String> mfaProviders) {
        requestContext.getViewScope().put("mfaSelectableProviders", mfaProviders);
    }

    public static List<String> getSelectableMultifactorAuthenticationProviders(RequestContext requestContext) {
        return (List)requestContext.getViewScope().get("mfaSelectableProviders", List.class);
    }

    public static void putOneTimeTokenAccount(RequestContext requestContext, OneTimeTokenAccount account) {
        requestContext.getFlowScope().put("registeredDevice", (Object)account);
    }

    public static void putOneTimeTokenAccounts(RequestContext requestContext, Collection accounts) {
        requestContext.getFlowScope().put("registeredDevices", (Object)accounts);
    }

    public static <T extends OneTimeTokenAccount> T getOneTimeTokenAccount(RequestContext requestContext, Class<T> clazz) {
        return (T)((OneTimeTokenAccount)requestContext.getFlowScope().get("registeredDevice", clazz));
    }

    public static void putGoogleAuthenticatorMultipleDeviceRegistrationEnabled(RequestContext requestContext, boolean enabled) {
        requestContext.getFlowScope().put("gauthMultipleDeviceRegistrationEnabled", (Object)enabled);
    }

    public static Boolean isGoogleAuthenticatorMultipleDeviceRegistrationEnabled(RequestContext requestContext) {
        return (Boolean)requestContext.getFlowScope().get("gauthMultipleDeviceRegistrationEnabled", Boolean.class);
    }

    public static void putYubiKeyMultipleDeviceRegistrationEnabled(RequestContext requestContext, boolean enabled) {
        requestContext.getFlowScope().put("yubikeyMultipleDeviceRegistrationEnabled", (Object)enabled);
    }

    public static void putSingleLogoutRequest(HttpServletRequest request, String logoutRequest) {
        request.setAttribute("singleLogoutRequest", (Object)logoutRequest);
    }

    public static String getSingleLogoutRequest(HttpServletRequest request) {
        return (String)request.getAttribute("singleLogoutRequest");
    }

    public static String getDelegatedAuthenticationClientName(RequestContext requestContext) {
        return (String)requestContext.getFlowScope().get("delegatedAuthenticationClientName", String.class);
    }

    public static void putDelegatedAuthenticationClientName(RequestContext requestContext, String clientName) {
        requestContext.getFlowScope().put("delegatedAuthenticationClientName", (Object)clientName);
    }

    public static void putAuthorizedServices(RequestContext requestContext, List<RegisteredService> authorizedServices) {
        requestContext.getFlowScope().put("authorizedServices", authorizedServices);
    }

    public static void putSingleSignOnSessions(RequestContext requestContext, List<? extends Serializable> sessions) {
        requestContext.getFlowScope().put("singleSignOnSessions", sessions);
    }

    public static List<? extends Serializable> getSingleSignOnSessions(RequestContext requestContext) {
        return (List)requestContext.getFlowScope().get("singleSignOnSessions", List.class);
    }

    public static List<RegisteredService> getAuthorizedServices(RequestContext requestContext) {
        return (List)requestContext.getFlowScope().get("authorizedServices", List.class);
    }

    public static void putRecaptchaForgotUsernameEnabled(RequestContext requestContext, GoogleRecaptchaProperties properties) {
        requestContext.getFlowScope().put("recaptchaForgotUsernameEnabled", (Object)properties.isEnabled());
    }

    public static Boolean isRecaptchaForgotUsernameEnabled(RequestContext requestContext) {
        return (Boolean)requestContext.getFlowScope().get("recaptchaForgotUsernameEnabled", Boolean.class);
    }

    public static void putRecaptchaPasswordManagementEnabled(RequestContext requestContext, GoogleRecaptchaProperties recaptcha) {
        requestContext.getFlowScope().put("recaptchaPasswordManagementEnabled", (Object)recaptcha.isEnabled());
    }

    public static Boolean isRecaptchaPasswordManagementEnabled(RequestContext requestContext) {
        return (Boolean)requestContext.getFlowScope().get("recaptchaPasswordManagementEnabled", Boolean.class);
    }

    public static void putSimpleMultifactorAuthenticationToken(RequestContext requestContext, Ticket token) {
        requestContext.getFlowScope().put("simpleMultifactorAuthenticationToken", (Object)token);
    }

    public static void removeSimpleMultifactorAuthenticationToken(RequestContext requestContext) {
        requestContext.getFlowScope().remove("simpleMultifactorAuthenticationToken");
    }

    public static <T extends Ticket> T getSimpleMultifactorAuthenticationToken(RequestContext requestContext, Class<T> clazz) {
        return (T)((Ticket)requestContext.getFlowScope().get("simpleMultifactorAuthenticationToken", clazz));
    }

    public static RegisteredService resolveRegisteredService(RequestContext requestContext, ServicesManager servicesManager, AuthenticationServiceSelectionPlan serviceSelectionStrategy) {
        RegisteredService registeredService = WebUtils.getRegisteredService(requestContext);
        if (registeredService != null) {
            return registeredService;
        }
        WebApplicationService service = WebUtils.getService(requestContext);
        Service serviceToUse = serviceSelectionStrategy.resolveService((Service)service);
        if (serviceToUse != null) {
            return servicesManager.findServiceBy(serviceToUse);
        }
        return null;
    }

    public static void addErrorMessageToContext(RequestContext requestContext, String code, String defaultText, Object[] args) {
        WebUtils.addErrorMessageToContext(requestContext.getMessageContext(), code, defaultText, args);
    }

    public static void addErrorMessageToContext(RequestContext requestContext, String code, String defaultText) {
        WebUtils.addErrorMessageToContext(requestContext.getMessageContext(), code, defaultText, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    public static void addErrorMessageToContext(RequestContext requestContext, String code) {
        WebUtils.addErrorMessageToContext(requestContext.getMessageContext(), code, null, null);
    }

    public static void addErrorMessageToContext(MessageContext messageContext, String code, String defaultText, Object[] args) {
        MessageResolver msg = new MessageBuilder().error().code(code).args(args).defaultText(defaultText).build();
        messageContext.addMessage(msg);
    }

    public static void addInfoMessageToContext(RequestContext requestContext, String code) {
        MessageResolver msg = new MessageBuilder().info().code(code).build();
        requestContext.getMessageContext().addMessage(msg);
    }

    public static void putLogoutPostUrl(RequestContext requestContext, String postUrl) {
        requestContext.getFlowScope().put("logoutPostUrl", (Object)postUrl);
    }

    public static void putLogoutPostData(RequestContext requestContext, Map<String, Object> postData) {
        requestContext.getFlowScope().put("logoutPostData", postData);
    }

    public static String getLogoutPostUrl(RequestContext requestContext) {
        return (String)requestContext.getFlowScope().get("logoutPostUrl", String.class);
    }

    public static Map<String, Object> getLogoutPostData(RequestContext requestContext) {
        return (Map)requestContext.getFlowScope().get("logoutPostData", Map.class);
    }

    public static void putPasswordPolicyPattern(RequestContext requestContext, String policyPattern) {
        MutableAttributeMap flowScope = requestContext.getFlowScope();
        flowScope.put("passwordPolicyPattern", (Object)policyPattern);
    }

    public static String getPasswordPolicyPattern(RequestContext requestContext) {
        MutableAttributeMap flowScope = requestContext.getFlowScope();
        return (String)flowScope.get("passwordPolicyPattern", String.class);
    }

    public static boolean isInterruptAuthenticationFlowFinalized(RequestContext requestContext) {
        return requestContext.getRequestScope().contains("authenticationFlowInterruptFinalized");
    }

    public static void putInterruptAuthenticationFlowFinalized(RequestContext requestContext) {
        requestContext.getRequestScope().put("authenticationFlowInterruptFinalized", (Object)Boolean.TRUE);
    }

    public static void removeInterruptAuthenticationFlowFinalized(RequestContext requestContext) {
        requestContext.getRequestScope().remove("authenticationFlowInterruptFinalized");
    }

    public static Credential getMultifactorAuthenticationParentCredential(RequestContext requestContext) {
        return (Credential)requestContext.getFlowScope().get("parentCredential", Credential.class);
    }

    public static void putWsFederationDelegatedClients(RequestContext context, List<? extends Serializable> clients) {
        context.getFlowScope().put("wsfedUrls", clients);
    }

    public static <T extends Serializable> List<T> getWsFederationDelegatedClients(RequestContext context, Class<T> clazz) {
        return (List)context.getFlowScope().get("wsfedUrls", List.class);
    }

    public static void putMultifactorAuthenticationRegisteredDevices(RequestContext requestContext, List accounts) {
        List list = (List)ObjectUtils.defaultIfNull((Object)WebUtils.getMultifactorAuthenticationRegisteredDevices(requestContext), new ArrayList());
        list.addAll(accounts);
        requestContext.getFlowScope().put("multifactorRegisteredAccounts", (Object)list);
    }

    public static List getMultifactorAuthenticationRegisteredDevices(RequestContext requestContext) {
        return (List)requestContext.getFlowScope().get("multifactorRegisteredAccounts", List.class);
    }

    public static void putDelegatedAuthenticationDisabled(RequestContext requestContext, boolean disabled) {
        requestContext.getFlowScope().put("delegatedAuthenticationDisabled", (Object)disabled);
    }

    public static void putDelegatedClientAuthenticationResolvedCredentials(RequestContext context, List<? extends Serializable> candidateMatches) {
        context.getFlowScope().put("delegatedAuthenticationCredentials", candidateMatches);
    }

    public static <T extends Serializable> List<T> getDelegatedClientAuthenticationResolvedCredentials(RequestContext context, Class<T> clazz) {
        List results = (List)context.getFlowScope().get("delegatedAuthenticationCredentials", List.class);
        return (List)ObjectUtils.defaultIfNull((Object)results, List.of());
    }

    public static <T> T getDelegatedClientAuthenticationCandidateProfile(RequestContext context, Class<T> clazz) {
        return (T)context.getFlashScope().get("delegatedClientAuthenticationCandidateProfile", clazz);
    }

    public static boolean hasDelegatedClientAuthenticationCandidateProfile(RequestContext context) {
        return context.getFlashScope().contains("delegatedClientAuthenticationCandidateProfile");
    }

    public static void putDelegatedClientAuthenticationCandidateProfile(RequestContext context, Serializable profile) {
        context.getFlashScope().put("delegatedClientAuthenticationCandidateProfile", (Object)profile);
    }

    @Generated
    private WebUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

