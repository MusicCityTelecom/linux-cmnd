/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.logout;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class DelegatingLogoutSuccessHandler
implements LogoutSuccessHandler {
    private final LinkedHashMap<RequestMatcher, LogoutSuccessHandler> matcherToHandler;
    private LogoutSuccessHandler defaultLogoutSuccessHandler;

    public DelegatingLogoutSuccessHandler(LinkedHashMap<RequestMatcher, LogoutSuccessHandler> matcherToHandler) {
        Assert.notEmpty(matcherToHandler, (String)"matcherToHandler cannot be null");
        this.matcherToHandler = matcherToHandler;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        for (Map.Entry<RequestMatcher, LogoutSuccessHandler> entry : this.matcherToHandler.entrySet()) {
            RequestMatcher matcher = entry.getKey();
            if (!matcher.matches(request)) continue;
            LogoutSuccessHandler handler = entry.getValue();
            handler.onLogoutSuccess(request, response, authentication);
            return;
        }
        if (this.defaultLogoutSuccessHandler != null) {
            this.defaultLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
        }
    }

    public void setDefaultLogoutSuccessHandler(LogoutSuccessHandler defaultLogoutSuccessHandler) {
        this.defaultLogoutSuccessHandler = defaultLogoutSuccessHandler;
    }
}

