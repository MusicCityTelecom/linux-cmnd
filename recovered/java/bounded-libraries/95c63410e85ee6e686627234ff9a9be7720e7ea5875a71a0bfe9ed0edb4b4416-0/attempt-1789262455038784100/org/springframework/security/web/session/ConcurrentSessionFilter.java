/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.core.session.SessionInformation
 *  org.springframework.security.core.session.SessionRegistry
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.session;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class ConcurrentSessionFilter
extends GenericFilterBean {
    private final SessionRegistry sessionRegistry;
    private String expiredUrl;
    private RedirectStrategy redirectStrategy;
    private LogoutHandler handlers = new CompositeLogoutHandler(new SecurityContextLogoutHandler());
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry) {
        Assert.notNull((Object)sessionRegistry, (String)"SessionRegistry required");
        this.sessionRegistry = sessionRegistry;
        this.sessionInformationExpiredStrategy = new ResponseBodySessionInformationExpiredStrategy();
    }

    @Deprecated
    public ConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
        Assert.notNull((Object)sessionRegistry, (String)"SessionRegistry required");
        Assert.isTrue((expiredUrl == null || UrlUtils.isValidRedirectUrl(expiredUrl) ? 1 : 0) != 0, () -> expiredUrl + " isn't a valid redirect URL");
        this.expiredUrl = expiredUrl;
        this.sessionRegistry = sessionRegistry;
        this.sessionInformationExpiredStrategy = event -> {
            HttpServletRequest request = event.getRequest();
            HttpServletResponse response = event.getResponse();
            SessionInformation info = event.getSessionInformation();
            this.redirectStrategy.sendRedirect(request, response, this.determineExpiredUrl(request, info));
        };
    }

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry, SessionInformationExpiredStrategy sessionInformationExpiredStrategy) {
        Assert.notNull((Object)sessionRegistry, (String)"sessionRegistry required");
        Assert.notNull((Object)sessionInformationExpiredStrategy, (String)"sessionInformationExpiredStrategy cannot be null");
        this.sessionRegistry = sessionRegistry;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.sessionRegistry, (String)"SessionRegistry required");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionInformation info;
        HttpSession session = request.getSession(false);
        if (session != null && (info = this.sessionRegistry.getSessionInformation(session.getId())) != null) {
            if (info.isExpired()) {
                this.logger.debug((Object)LogMessage.of(() -> "Requested session ID " + request.getRequestedSessionId() + " has expired."));
                this.doLogout(request, response);
                this.sessionInformationExpiredStrategy.onExpiredSessionDetected(new SessionInformationExpiredEvent(info, request, response));
                return;
            }
            this.sessionRegistry.refreshLastRequest(info.getSessionId());
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    @Deprecated
    protected String determineExpiredUrl(HttpServletRequest request, SessionInformation info) {
        return this.expiredUrl;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.handlers.logout(request, response, auth);
    }

    public void setLogoutHandlers(LogoutHandler[] handlers) {
        this.handlers = new CompositeLogoutHandler(handlers);
    }

    public void setLogoutHandlers(List<LogoutHandler> handlers) {
        this.handlers = new CompositeLogoutHandler(handlers);
    }

    @Deprecated
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    private static final class ResponseBodySessionInformationExpiredStrategy
    implements SessionInformationExpiredStrategy {
        private ResponseBodySessionInformationExpiredStrategy() {
        }

        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
            HttpServletResponse response = event.getResponse();
            response.getWriter().print("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
            response.flushBuffer();
        }
    }
}

