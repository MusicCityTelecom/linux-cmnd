/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.LogoutRequestStatus
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.logout.slo.SingleLogoutMessage
 *  org.apereo.cas.logout.slo.SingleLogoutMessageCreator
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder
 *  org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler
 *  org.apereo.cas.logout.slo.SingleLogoutUrl
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.WebBasedRegisteredService
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.http.HttpClient
 *  org.apereo.cas.util.http.HttpMessage
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.logout.slo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.DefaultSingleLogoutRequestContext;
import org.apereo.cas.logout.LogoutHttpMessage;
import org.apereo.cas.logout.LogoutRequestStatus;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutMessage;
import org.apereo.cas.logout.slo.SingleLogoutMessageCreator;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler;
import org.apereo.cas.logout.slo.SingleLogoutUrl;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.WebBasedRegisteredService;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.http.HttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSingleLogoutServiceMessageHandler
implements SingleLogoutServiceMessageHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSingleLogoutServiceMessageHandler.class);
    private final HttpClient httpClient;
    private final SingleLogoutMessageCreator logoutMessageBuilder;
    private final ServicesManager servicesManager;
    private final SingleLogoutServiceLogoutUrlBuilder singleLogoutServiceLogoutUrlBuilder;
    private final boolean asynchronous;
    private final AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies;

    public Collection<SingleLogoutRequestContext> handle(WebApplicationService singleLogoutService, String ticketId, SingleLogoutExecutionRequest context) {
        if (singleLogoutService.isLoggedOutAlready()) {
            LOGGER.debug("Service [{}] is already logged out.", (Object)singleLogoutService);
            return new ArrayList<SingleLogoutRequestContext>(0);
        }
        WebApplicationService selectedService = (WebApplicationService)this.authenticationRequestServiceSelectionStrategies.resolveService((Service)singleLogoutService);
        LOGGER.trace("Processing logout request for service [{}]...", (Object)selectedService);
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)selectedService);
        LOGGER.debug("Service [{}] supports single logout and is found in the registry as [{}]. Proceeding...", (Object)selectedService.getId(), (Object)registeredService.getName());
        Collection logoutUrls = this.singleLogoutServiceLogoutUrlBuilder.determineLogoutUrl(registeredService, selectedService);
        LOGGER.debug("Prepared logout url [{}] for service [{}]", (Object)logoutUrls, (Object)selectedService);
        if (logoutUrls == null || logoutUrls.isEmpty()) {
            LOGGER.debug("Service [{}] does not support logout operations given no logout url could be determined.", (Object)selectedService);
            return new ArrayList<SingleLogoutRequestContext>(0);
        }
        LOGGER.trace("Creating logout request for [{}] and ticket id [{}]", (Object)selectedService, (Object)ticketId);
        return this.createLogoutRequests(ticketId, selectedService, registeredService, logoutUrls, context);
    }

    public boolean supports(SingleLogoutExecutionRequest context, WebApplicationService singleLogoutService) {
        WebApplicationService selectedService = (WebApplicationService)this.authenticationRequestServiceSelectionStrategies.resolveService((Service)singleLogoutService);
        WebBasedRegisteredService registeredService = (WebBasedRegisteredService)this.servicesManager.findServiceBy((Service)selectedService);
        return registeredService != null && registeredService.getAccessStrategy().isServiceAccessAllowed() && registeredService.getLogoutType() != RegisteredServiceLogoutType.NONE && this.supportsInternal(singleLogoutService, (RegisteredService)registeredService, context);
    }

    public boolean performBackChannelLogout(SingleLogoutRequestContext request) {
        try {
            LOGGER.trace("Creating back-channel logout request based on [{}]", (Object)request);
            SingleLogoutMessage logoutRequest = this.createSingleLogoutMessage(request);
            return this.sendSingleLogoutMessage(request, logoutRequest);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return false;
        }
    }

    public SingleLogoutMessage createSingleLogoutMessage(SingleLogoutRequestContext logoutRequest) {
        return this.logoutMessageBuilder.create(logoutRequest);
    }

    protected boolean supportsInternal(WebApplicationService singleLogoutService, RegisteredService registeredService, SingleLogoutExecutionRequest context) {
        return true;
    }

    protected Collection<SingleLogoutRequestContext> createLogoutRequests(String ticketId, WebApplicationService selectedService, RegisteredService registeredService, Collection<SingleLogoutUrl> logoutUrls, SingleLogoutExecutionRequest context) {
        return logoutUrls.stream().map(url -> this.createLogoutRequest(ticketId, selectedService, registeredService, (SingleLogoutUrl)url, context)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected SingleLogoutRequestContext createLogoutRequest(String ticketId, WebApplicationService selectedService, RegisteredService registeredService, SingleLogoutUrl logoutUrl, SingleLogoutExecutionRequest context) {
        Object logoutRequest = ((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)((DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder)DefaultSingleLogoutRequestContext.builder().ticketId(ticketId)).service(selectedService)).logoutUrl((URL)FunctionUtils.doUnchecked(() -> new URL(logoutUrl.getUrl())))).logoutType(logoutUrl.getLogoutType())).registeredService(registeredService)).executionRequest(context)).properties(logoutUrl.getProperties())).build();
        LOGGER.trace("Logout request [{}] created for [{}] and ticket id [{}]", new Object[]{logoutRequest, selectedService, ticketId});
        if (((DefaultSingleLogoutRequestContext)logoutRequest).getLogoutType() == RegisteredServiceLogoutType.BACK_CHANNEL) {
            if (this.performBackChannelLogout((SingleLogoutRequestContext)logoutRequest)) {
                ((DefaultSingleLogoutRequestContext)logoutRequest).setStatus(LogoutRequestStatus.SUCCESS);
            } else {
                ((DefaultSingleLogoutRequestContext)logoutRequest).setStatus(LogoutRequestStatus.FAILURE);
                LOGGER.warn("Logout message is not sent to [{}]; Continuing processing...", (Object)selectedService);
            }
        } else {
            LOGGER.trace("Logout operation is not yet attempted for [{}] given logout type is set to [{}]", (Object)selectedService, (Object)((DefaultSingleLogoutRequestContext)logoutRequest).getLogoutType());
            ((DefaultSingleLogoutRequestContext)logoutRequest).setStatus(LogoutRequestStatus.NOT_ATTEMPTED);
        }
        return logoutRequest;
    }

    protected boolean sendSingleLogoutMessage(SingleLogoutRequestContext request, SingleLogoutMessage logoutMessage) {
        WebApplicationService logoutService = request.getService();
        LOGGER.trace("Preparing logout request for [{}] to [{}]", (Object)logoutService.getId(), (Object)request.getLogoutUrl());
        LogoutHttpMessage msg = this.getLogoutHttpMessageToSend(request, logoutMessage);
        LOGGER.debug("Prepared logout message to send is [{}]. Sending...", (Object)msg);
        boolean result = this.sendMessageToEndpoint(msg, request, logoutMessage);
        logoutService.setLoggedOutAlready(result);
        return result;
    }

    protected boolean sendMessageToEndpoint(LogoutHttpMessage msg, SingleLogoutRequestContext request, SingleLogoutMessage logoutMessage) {
        return this.httpClient.sendMessageToEndPoint((HttpMessage)msg);
    }

    protected LogoutHttpMessage getLogoutHttpMessageToSend(SingleLogoutRequestContext request, SingleLogoutMessage logoutMessage) {
        return new LogoutHttpMessage(request.getLogoutUrl(), logoutMessage.getPayload(), this.asynchronous);
    }

    @Generated
    protected BaseSingleLogoutServiceMessageHandler(HttpClient httpClient, SingleLogoutMessageCreator logoutMessageBuilder, ServicesManager servicesManager, SingleLogoutServiceLogoutUrlBuilder singleLogoutServiceLogoutUrlBuilder, boolean asynchronous, AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies) {
        this.httpClient = httpClient;
        this.logoutMessageBuilder = logoutMessageBuilder;
        this.servicesManager = servicesManager;
        this.singleLogoutServiceLogoutUrlBuilder = singleLogoutServiceLogoutUrlBuilder;
        this.asynchronous = asynchronous;
        this.authenticationRequestServiceSelectionStrategies = authenticationRequestServiceSelectionStrategies;
    }

    @Generated
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    @Generated
    public SingleLogoutMessageCreator getLogoutMessageBuilder() {
        return this.logoutMessageBuilder;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public SingleLogoutServiceLogoutUrlBuilder getSingleLogoutServiceLogoutUrlBuilder() {
        return this.singleLogoutServiceLogoutUrlBuilder;
    }

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public AuthenticationServiceSelectionPlan getAuthenticationRequestServiceSelectionStrategies() {
        return this.authenticationRequestServiceSelectionStrategies;
    }
}

