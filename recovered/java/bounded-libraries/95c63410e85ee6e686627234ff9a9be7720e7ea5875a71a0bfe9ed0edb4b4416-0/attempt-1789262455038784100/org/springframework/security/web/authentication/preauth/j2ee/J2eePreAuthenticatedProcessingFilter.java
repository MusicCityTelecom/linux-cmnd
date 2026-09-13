/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.authentication.preauth.j2ee;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class J2eePreAuthenticatedProcessingFilter
extends AbstractPreAuthenticatedProcessingFilter {
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
        String principal = httpRequest.getUserPrincipal() != null ? httpRequest.getUserPrincipal().getName() : null;
        this.logger.debug((Object)LogMessage.format((String)"PreAuthenticated J2EE principal: %s", (Object)principal));
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpRequest) {
        return "N/A";
    }
}

