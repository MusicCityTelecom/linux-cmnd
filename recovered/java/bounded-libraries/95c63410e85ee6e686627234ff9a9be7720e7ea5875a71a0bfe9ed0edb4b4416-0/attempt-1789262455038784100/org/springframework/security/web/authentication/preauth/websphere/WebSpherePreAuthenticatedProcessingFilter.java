/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.authentication.preauth.websphere;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.websphere.DefaultWASUsernameAndGroupsExtractor;
import org.springframework.security.web.authentication.preauth.websphere.WASUsernameAndGroupsExtractor;
import org.springframework.security.web.authentication.preauth.websphere.WebSpherePreAuthenticatedWebAuthenticationDetailsSource;

public class WebSpherePreAuthenticatedProcessingFilter
extends AbstractPreAuthenticatedProcessingFilter {
    private final WASUsernameAndGroupsExtractor wasHelper;

    public WebSpherePreAuthenticatedProcessingFilter() {
        this(new DefaultWASUsernameAndGroupsExtractor());
    }

    WebSpherePreAuthenticatedProcessingFilter(WASUsernameAndGroupsExtractor wasHelper) {
        this.wasHelper = wasHelper;
        this.setAuthenticationDetailsSource(new WebSpherePreAuthenticatedWebAuthenticationDetailsSource());
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
        String principal = this.wasHelper.getCurrentUserName();
        this.logger.debug((Object)LogMessage.format((String)"PreAuthenticated WebSphere principal: %s", (Object)principal));
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpRequest) {
        return "N/A";
    }
}

