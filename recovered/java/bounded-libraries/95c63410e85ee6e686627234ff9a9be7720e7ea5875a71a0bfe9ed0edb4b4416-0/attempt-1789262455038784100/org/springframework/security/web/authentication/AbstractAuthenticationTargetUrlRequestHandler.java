/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractAuthenticationTargetUrlRequestHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String targetUrlParameter = null;
    private String defaultTargetUrl = "/";
    private boolean alwaysUseDefaultTargetUrl = false;
    private boolean useReferer = false;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected AbstractAuthenticationTargetUrlRequestHandler() {
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = this.determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            this.logger.debug((Object)LogMessage.format((String)"Did not redirect to %s since response already committed.", (Object)targetUrl));
            return;
        }
        this.redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return this.determineTargetUrl(request, response);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (this.isAlwaysUseDefaultTargetUrl()) {
            return this.defaultTargetUrl;
        }
        String targetUrl = null;
        if (this.targetUrlParameter != null && StringUtils.hasText((String)(targetUrl = request.getParameter(this.targetUrlParameter)))) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Using url %s from request parameter %s", (Object)targetUrl, (Object)this.targetUrlParameter));
            }
            return targetUrl;
        }
        if (this.useReferer && !StringUtils.hasLength((String)targetUrl)) {
            targetUrl = request.getHeader("Referer");
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Using url %s from Referer header", (Object)targetUrl));
            }
        }
        if (!StringUtils.hasText((String)targetUrl)) {
            targetUrl = this.defaultTargetUrl;
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Using default url %s", (Object)targetUrl));
            }
        }
        return targetUrl;
    }

    protected final String getDefaultTargetUrl() {
        return this.defaultTargetUrl;
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(defaultTargetUrl), (String)"defaultTarget must start with '/' or with 'http(s)'");
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
        this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
    }

    protected boolean isAlwaysUseDefaultTargetUrl() {
        return this.alwaysUseDefaultTargetUrl;
    }

    public void setTargetUrlParameter(String targetUrlParameter) {
        if (targetUrlParameter != null) {
            Assert.hasText((String)targetUrlParameter, (String)"targetUrlParameter cannot be empty");
        }
        this.targetUrlParameter = targetUrlParameter;
    }

    protected String getTargetUrlParameter() {
        return this.targetUrlParameter;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }

    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }
}

