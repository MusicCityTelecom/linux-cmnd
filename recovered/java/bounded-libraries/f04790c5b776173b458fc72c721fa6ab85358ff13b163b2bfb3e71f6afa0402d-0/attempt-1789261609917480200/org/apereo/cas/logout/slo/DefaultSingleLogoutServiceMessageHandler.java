/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.logout.slo.SingleLogoutMessageCreator
 *  org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.http.HttpClient
 */
package org.apereo.cas.logout.slo;

import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.BaseSingleLogoutServiceMessageHandler;
import org.apereo.cas.logout.slo.SingleLogoutMessageCreator;
import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.http.HttpClient;

public class DefaultSingleLogoutServiceMessageHandler
extends BaseSingleLogoutServiceMessageHandler {
    public DefaultSingleLogoutServiceMessageHandler(HttpClient httpClient, SingleLogoutMessageCreator logoutMessageBuilder, ServicesManager servicesManager, SingleLogoutServiceLogoutUrlBuilder singleLogoutServiceLogoutUrlBuilder, boolean asynchronous, AuthenticationServiceSelectionPlan authenticationRequestServiceSelectionStrategies) {
        super(httpClient, logoutMessageBuilder, servicesManager, singleLogoutServiceLogoutUrlBuilder, asynchronous, authenticationRequestServiceSelectionStrategies);
    }

    @Override
    protected boolean supportsInternal(WebApplicationService singleLogoutService, RegisteredService registeredService, SingleLogoutExecutionRequest context) {
        return super.supportsInternal(singleLogoutService, registeredService, context) && registeredService.getFriendlyName().equalsIgnoreCase("CAS Client");
    }
}

