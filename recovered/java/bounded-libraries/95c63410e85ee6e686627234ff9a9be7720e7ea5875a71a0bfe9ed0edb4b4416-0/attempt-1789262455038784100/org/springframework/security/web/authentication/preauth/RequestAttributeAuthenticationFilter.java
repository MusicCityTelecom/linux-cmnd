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

public class RequestAttributeAuthenticationFilter
extends AbstractPreAuthenticatedProcessingFilter {
    private String principalEnvironmentVariable = "REMOTE_USER";
    private String credentialsEnvironmentVariable;
    private boolean exceptionIfVariableMissing = true;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String principal = (String)request.getAttribute(this.principalEnvironmentVariable);
        if (principal == null && this.exceptionIfVariableMissing) {
            throw new PreAuthenticatedCredentialsNotFoundException(this.principalEnvironmentVariable + " variable not found in request.");
        }
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        if (this.credentialsEnvironmentVariable != null) {
            return request.getAttribute(this.credentialsEnvironmentVariable);
        }
        return "N/A";
    }

    public void setPrincipalEnvironmentVariable(String principalEnvironmentVariable) {
        Assert.hasText((String)principalEnvironmentVariable, (String)"principalEnvironmentVariable must not be empty or null");
        this.principalEnvironmentVariable = principalEnvironmentVariable;
    }

    public void setCredentialsEnvironmentVariable(String credentialsEnvironmentVariable) {
        Assert.hasText((String)credentialsEnvironmentVariable, (String)"credentialsEnvironmentVariable must not be empty or null");
        this.credentialsEnvironmentVariable = credentialsEnvironmentVariable;
    }

    public void setExceptionIfVariableMissing(boolean exceptionIfVariableMissing) {
        this.exceptionIfVariableMissing = exceptionIfVariableMissing;
    }
}

