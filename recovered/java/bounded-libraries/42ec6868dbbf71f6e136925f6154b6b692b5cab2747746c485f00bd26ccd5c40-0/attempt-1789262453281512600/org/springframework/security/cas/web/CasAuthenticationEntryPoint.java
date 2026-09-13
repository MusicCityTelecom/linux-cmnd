/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.jasig.cas.client.util.CommonUtils
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.web.AuthenticationEntryPoint
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class CasAuthenticationEntryPoint
implements AuthenticationEntryPoint,
InitializingBean {
    private ServiceProperties serviceProperties;
    private String loginUrl;
    private boolean encodeServiceUrlWithSessionId = true;

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.loginUrl, (String)"loginUrl must be specified");
        Assert.notNull((Object)this.serviceProperties, (String)"serviceProperties must be specified");
        Assert.notNull((Object)this.serviceProperties.getService(), (String)"serviceProperties.getService() cannot be null.");
    }

    public final void commence(HttpServletRequest servletRequest, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        String urlEncodedService = this.createServiceUrl(servletRequest, response);
        String redirectUrl = this.createRedirectUrl(urlEncodedService);
        this.preCommence(servletRequest, response);
        response.sendRedirect(redirectUrl);
    }

    protected String createServiceUrl(HttpServletRequest request, HttpServletResponse response) {
        return CommonUtils.constructServiceUrl(null, (HttpServletResponse)response, (String)this.serviceProperties.getService(), null, (String)this.serviceProperties.getArtifactParameter(), (boolean)this.encodeServiceUrlWithSessionId);
    }

    protected String createRedirectUrl(String serviceUrl) {
        return CommonUtils.constructRedirectUrl((String)this.loginUrl, (String)this.serviceProperties.getServiceParameter(), (String)serviceUrl, (boolean)this.serviceProperties.isSendRenew(), (boolean)false);
    }

    protected void preCommence(HttpServletRequest request, HttpServletResponse response) {
    }

    public final String getLoginUrl() {
        return this.loginUrl;
    }

    public final ServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public final void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public final void setEncodeServiceUrlWithSessionId(boolean encodeServiceUrlWithSessionId) {
        this.encodeServiceUrlWithSessionId = encodeServiceUrlWithSessionId;
    }

    protected boolean getEncodeServiceUrlWithSessionId() {
        return this.encodeServiceUrlWithSessionId;
    }
}

