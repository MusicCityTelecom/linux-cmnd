/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.Assert;

public class RequestHeaderAuthenticationFilter
extends AbstractPreAuthenticatedProcessingFilter {
    private String principalRequestHeader = "SM_USER";
    private String credentialsRequestHeader;
    private boolean exceptionIfHeaderMissing = true;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String principal = request.getHeader(this.principalRequestHeader);
        if (principal == null && this.exceptionIfHeaderMissing) {
            throw new PreAuthenticatedCredentialsNotFoundException(this.principalRequestHeader + " header not found in request.");
        }
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        if (this.credentialsRequestHeader != null) {
            return request.getHeader(this.credentialsRequestHeader);
        }
        return "N/A";
    }

    public void setPrincipalRequestHeader(String principalRequestHeader) {
        Assert.hasText((String)principalRequestHeader, (String)"principalRequestHeader must not be empty or null");
        this.principalRequestHeader = principalRequestHeader;
    }

    public void setCredentialsRequestHeader(String credentialsRequestHeader) {
        Assert.hasText((String)credentialsRequestHeader, (String)"credentialsRequestHeader must not be empty or null");
        this.credentialsRequestHeader = credentialsRequestHeader;
    }

    public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
        this.exceptionIfHeaderMissing = exceptionIfHeaderMissing;
    }
}

