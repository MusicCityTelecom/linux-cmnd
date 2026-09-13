/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.AsyncContext
 *  javax.servlet.AsyncListener
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.concurrent.DelegatingSecurityContextRunnable
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.security.web.servletapi;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servletapi.HttpServletRequestFactory;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

final class HttpServlet3RequestFactory
implements HttpServletRequestFactory {
    private Log logger = LogFactory.getLog(this.getClass());
    private final String rolePrefix;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private List<LogoutHandler> logoutHandlers;

    HttpServlet3RequestFactory(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    void setLogoutHandlers(List<LogoutHandler> logoutHandlers) {
        this.logoutHandlers = logoutHandlers;
    }

    void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }

    @Override
    public HttpServletRequest create(HttpServletRequest request, HttpServletResponse response) {
        return new Servlet3SecurityContextHolderAwareRequestWrapper(request, this.rolePrefix, response);
    }

    private static class SecurityContextAsyncContext
    implements AsyncContext {
        private final AsyncContext asyncContext;

        SecurityContextAsyncContext(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        public ServletRequest getRequest() {
            return this.asyncContext.getRequest();
        }

        public ServletResponse getResponse() {
            return this.asyncContext.getResponse();
        }

        public boolean hasOriginalRequestAndResponse() {
            return this.asyncContext.hasOriginalRequestAndResponse();
        }

        public void dispatch() {
            this.asyncContext.dispatch();
        }

        public void dispatch(String path) {
            this.asyncContext.dispatch(path);
        }

        public void dispatch(ServletContext context, String path) {
            this.asyncContext.dispatch(context, path);
        }

        public void complete() {
            this.asyncContext.complete();
        }

        public void start(Runnable run) {
            this.asyncContext.start((Runnable)new DelegatingSecurityContextRunnable(run));
        }

        public void addListener(AsyncListener listener) {
            this.asyncContext.addListener(listener);
        }

        public void addListener(AsyncListener listener, ServletRequest request, ServletResponse response) {
            this.asyncContext.addListener(listener, request, response);
        }

        public <T extends AsyncListener> T createListener(Class<T> clazz) throws ServletException {
            return (T)this.asyncContext.createListener(clazz);
        }

        public long getTimeout() {
            return this.asyncContext.getTimeout();
        }

        public void setTimeout(long timeout) {
            this.asyncContext.setTimeout(timeout);
        }
    }

    private class Servlet3SecurityContextHolderAwareRequestWrapper
    extends SecurityContextHolderAwareRequestWrapper {
        private final HttpServletResponse response;

        Servlet3SecurityContextHolderAwareRequestWrapper(HttpServletRequest request, String rolePrefix, HttpServletResponse response) {
            super(request, HttpServlet3RequestFactory.this.trustResolver, rolePrefix);
            this.response = response;
        }

        public AsyncContext getAsyncContext() {
            AsyncContext asyncContext = super.getAsyncContext();
            if (asyncContext == null) {
                return null;
            }
            return new SecurityContextAsyncContext(asyncContext);
        }

        public AsyncContext startAsync() {
            AsyncContext startAsync = super.startAsync();
            return new SecurityContextAsyncContext(startAsync);
        }

        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            AsyncContext startAsync = super.startAsync(servletRequest, servletResponse);
            return new SecurityContextAsyncContext(startAsync);
        }

        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            AuthenticationEntryPoint entryPoint = HttpServlet3RequestFactory.this.authenticationEntryPoint;
            if (entryPoint == null) {
                HttpServlet3RequestFactory.this.logger.debug((Object)"authenticationEntryPoint is null, so allowing original HttpServletRequest to handle authenticate");
                return super.authenticate(response);
            }
            if (this.isAuthenticated()) {
                return true;
            }
            entryPoint.commence((HttpServletRequest)this, response, (AuthenticationException)((Object)new AuthenticationCredentialsNotFoundException("User is not Authenticated")));
            return false;
        }

        public void login(String username, String password) throws ServletException {
            if (this.isAuthenticated()) {
                throw new ServletException("Cannot perform login for '" + username + "' already authenticated as '" + this.getRemoteUser() + "'");
            }
            AuthenticationManager authManager = HttpServlet3RequestFactory.this.authenticationManager;
            if (authManager == null) {
                HttpServlet3RequestFactory.this.logger.debug((Object)"authenticationManager is null, so allowing original HttpServletRequest to handle login");
                super.login(username, password);
                return;
            }
            Authentication authentication = this.getAuthentication(authManager, username, password);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext((SecurityContext)context);
        }

        private Authentication getAuthentication(AuthenticationManager authManager, String username, String password) throws ServletException {
            try {
                UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.unauthenticated((Object)username, (Object)password);
                Object details = HttpServlet3RequestFactory.this.authenticationDetailsSource.buildDetails((Object)this);
                authentication.setDetails(details);
                return authManager.authenticate((Authentication)authentication);
            }
            catch (AuthenticationException ex) {
                SecurityContextHolder.clearContext();
                throw new ServletException(ex.getMessage(), (Throwable)ex);
            }
        }

        public void logout() throws ServletException {
            List handlers = HttpServlet3RequestFactory.this.logoutHandlers;
            if (CollectionUtils.isEmpty((Collection)handlers)) {
                HttpServlet3RequestFactory.this.logger.debug((Object)"logoutHandlers is null, so allowing original HttpServletRequest to handle logout");
                super.logout();
                return;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for (LogoutHandler handler : handlers) {
                handler.logout((HttpServletRequest)this, this.response, authentication);
            }
        }

        private boolean isAuthenticated() {
            return this.getUserPrincipal() != null;
        }
    }
}

