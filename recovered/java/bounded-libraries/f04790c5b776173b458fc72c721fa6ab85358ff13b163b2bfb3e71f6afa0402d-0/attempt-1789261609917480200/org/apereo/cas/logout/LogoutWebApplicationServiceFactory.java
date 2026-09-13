/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.WebApplicationServiceFactory
 *  org.apereo.cas.configuration.model.core.logout.LogoutProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.logout;

import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.principal.WebApplicationServiceFactory;
import org.apereo.cas.configuration.model.core.logout.LogoutProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutWebApplicationServiceFactory
extends WebApplicationServiceFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutWebApplicationServiceFactory.class);
    private final LogoutProperties logoutProperties;

    protected String getRequestedService(HttpServletRequest request) {
        if (request.getRequestURI().endsWith("/logout")) {
            String paramName = this.logoutProperties.getRedirectParameter();
            LOGGER.trace("Using request parameter name [{}] to detect destination service, if any", (Object)paramName);
            String service = request.getParameter(paramName);
            LOGGER.trace("Located target service [{}] for redirection after logout", (Object)service);
            return service;
        }
        return null;
    }

    @Generated
    public LogoutWebApplicationServiceFactory(LogoutProperties logoutProperties) {
        this.logoutProperties = logoutProperties;
    }
}

