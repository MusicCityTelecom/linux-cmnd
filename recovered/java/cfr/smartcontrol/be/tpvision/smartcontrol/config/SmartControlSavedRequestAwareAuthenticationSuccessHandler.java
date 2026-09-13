/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class SmartControlSavedRequestAwareAuthenticationSuccessHandler
extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String contextPath;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        int targetUrlLength;
        int lastSlashIndex;
        String lastPart;
        boolean alwaysUseDefaultTargetUrl;
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        String targetUrlParameter = super.getTargetUrlParameter();
        boolean hasTargetUrl = false;
        if (targetUrlParameter != null) {
            String targetUrl = request.getParameter(targetUrlParameter);
            hasTargetUrl = StringUtils.hasText(targetUrl);
        }
        if ((alwaysUseDefaultTargetUrl = super.isAlwaysUseDefaultTargetUrl()) || hasTargetUrl) {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        this.clearAuthenticationAttributes(request);
        String targetUrl = savedRequest.getRedirectUrl();
        if (this.contextPath != null && (lastPart = targetUrl.substring(lastSlashIndex = targetUrl.lastIndexOf(47), targetUrlLength = targetUrl.length())).equals(this.contextPath)) {
            targetUrl = targetUrl + "/";
        }
        this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        RedirectStrategy redirectStrategy = super.getRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}

