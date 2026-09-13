/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.AsyncContext
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.Transient
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.util.WebUtils
 */
package org.springframework.security.web.context;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

public class HttpSessionSecurityContextRepository
implements SecurityContextRepository {
    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Object contextObject = SecurityContextHolder.createEmptyContext();
    private boolean allowSessionCreation = true;
    private boolean disableUrlRewriting = false;
    private String springSecurityContextKey = "SPRING_SECURITY_CONTEXT";
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpServletResponse response = requestResponseHolder.getResponse();
        HttpSession httpSession = request.getSession(false);
        SecurityContext context = this.readSecurityContextFromSession(httpSession);
        if (context == null) {
            context = this.generateNewContext();
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Created %s", (Object)context));
            }
        }
        if (response != null) {
            SaveToSessionResponseWrapper wrappedResponse = new SaveToSessionResponseWrapper(response, request, httpSession != null, context);
            requestResponseHolder.setResponse((HttpServletResponse)wrappedResponse);
            requestResponseHolder.setRequest((HttpServletRequest)new SaveToSessionRequestWrapper(request, wrappedResponse));
        }
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SaveContextOnUpdateOrErrorResponseWrapper responseWrapper = (SaveContextOnUpdateOrErrorResponseWrapper)((Object)WebUtils.getNativeResponse((ServletResponse)response, SaveContextOnUpdateOrErrorResponseWrapper.class));
        if (responseWrapper == null) {
            boolean httpSessionExists = request.getSession(false) != null;
            SecurityContext initialContext = SecurityContextHolder.createEmptyContext();
            responseWrapper = new SaveToSessionResponseWrapper(response, request, httpSessionExists, initialContext);
        }
        responseWrapper.saveContext(context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        return session.getAttribute(this.springSecurityContextKey) != null;
    }

    private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
        if (httpSession == null) {
            this.logger.trace((Object)"No HttpSession currently exists");
            return null;
        }
        Object contextFromSession = httpSession.getAttribute(this.springSecurityContextKey);
        if (contextFromSession == null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Did not find SecurityContext in HttpSession %s using the SPRING_SECURITY_CONTEXT session attribute", (Object)httpSession.getId()));
            }
            return null;
        }
        if (!(contextFromSession instanceof SecurityContext)) {
            this.logger.warn((Object)LogMessage.format((String)"%s did not contain a SecurityContext but contained: '%s'; are you improperly modifying the HttpSession directly (you should always use SecurityContextHolder) or using the HttpSession attribute reserved for this class?", (Object)this.springSecurityContextKey, (Object)contextFromSession));
            return null;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)LogMessage.format((String)"Retrieved %s from %s", (Object)contextFromSession, (Object)this.springSecurityContextKey));
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)LogMessage.format((String)"Retrieved %s", (Object)contextFromSession));
        }
        return (SecurityContext)contextFromSession;
    }

    protected SecurityContext generateNewContext() {
        return SecurityContextHolder.createEmptyContext();
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    public void setDisableUrlRewriting(boolean disableUrlRewriting) {
        this.disableUrlRewriting = disableUrlRewriting;
    }

    public void setSpringSecurityContextKey(String springSecurityContextKey) {
        Assert.hasText((String)springSecurityContextKey, (String)"springSecurityContextKey cannot be empty");
        this.springSecurityContextKey = springSecurityContextKey;
    }

    private boolean isTransient(Object object) {
        if (object == null) {
            return false;
        }
        return AnnotationUtils.getAnnotation(object.getClass(), Transient.class) != null;
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }

    final class SaveToSessionResponseWrapper
    extends SaveContextOnUpdateOrErrorResponseWrapper {
        private final Log logger;
        private final HttpServletRequest request;
        private final boolean httpSessionExistedAtStartOfRequest;
        private final SecurityContext contextBeforeExecution;
        private final Authentication authBeforeExecution;
        private boolean isSaveContextInvoked;

        SaveToSessionResponseWrapper(HttpServletResponse response, HttpServletRequest request, boolean httpSessionExistedAtStartOfRequest, SecurityContext context) {
            super(response, HttpSessionSecurityContextRepository.this.disableUrlRewriting);
            this.logger = HttpSessionSecurityContextRepository.this.logger;
            this.request = request;
            this.httpSessionExistedAtStartOfRequest = httpSessionExistedAtStartOfRequest;
            this.contextBeforeExecution = context;
            this.authBeforeExecution = context.getAuthentication();
        }

        @Override
        protected void saveContext(SecurityContext context) {
            if (HttpSessionSecurityContextRepository.this.isTransient(context)) {
                return;
            }
            Authentication authentication = context.getAuthentication();
            if (HttpSessionSecurityContextRepository.this.isTransient(authentication)) {
                return;
            }
            HttpSession httpSession = this.request.getSession(false);
            String springSecurityContextKey = HttpSessionSecurityContextRepository.this.springSecurityContextKey;
            if (authentication == null || HttpSessionSecurityContextRepository.this.trustResolver.isAnonymous(authentication)) {
                if (httpSession != null && this.authBeforeExecution != null) {
                    httpSession.removeAttribute(springSecurityContextKey);
                    this.isSaveContextInvoked = true;
                }
                if (this.logger.isDebugEnabled()) {
                    if (authentication == null) {
                        this.logger.debug((Object)"Did not store empty SecurityContext");
                    } else {
                        this.logger.debug((Object)"Did not store anonymous SecurityContext");
                    }
                }
                return;
            }
            HttpSession httpSession2 = httpSession = httpSession != null ? httpSession : this.createNewSessionIfAllowed(context);
            if (httpSession != null && (this.contextChanged(context) || httpSession.getAttribute(springSecurityContextKey) == null)) {
                httpSession.setAttribute(springSecurityContextKey, (Object)context);
                this.isSaveContextInvoked = true;
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)LogMessage.format((String)"Stored %s to HttpSession [%s]", (Object)context, (Object)httpSession));
                }
            }
        }

        private boolean contextChanged(SecurityContext context) {
            return this.isSaveContextInvoked || context != this.contextBeforeExecution || context.getAuthentication() != this.authBeforeExecution;
        }

        private HttpSession createNewSessionIfAllowed(SecurityContext context) {
            if (this.httpSessionExistedAtStartOfRequest) {
                this.logger.debug((Object)"HttpSession is now null, but was not null at start of request; session was invalidated, so do not create a new session");
                return null;
            }
            if (!HttpSessionSecurityContextRepository.this.allowSessionCreation) {
                this.logger.debug((Object)("The HttpSession is currently null, and the " + HttpSessionSecurityContextRepository.class.getSimpleName() + " is prohibited from creating an HttpSession (because the allowSessionCreation property is false) - SecurityContext thus not stored for next request"));
                return null;
            }
            if (HttpSessionSecurityContextRepository.this.contextObject.equals(context)) {
                this.logger.debug((Object)LogMessage.format((String)"HttpSession is null, but SecurityContext has not changed from default empty context %s so not creating HttpSession or storing SecurityContext", (Object)context));
                return null;
            }
            try {
                HttpSession session = this.request.getSession(true);
                this.logger.debug((Object)"Created HttpSession as SecurityContext is non-default");
                return session;
            }
            catch (IllegalStateException ex) {
                this.logger.warn((Object)"Failed to create a session, as response has been committed. Unable to store SecurityContext.");
                return null;
            }
        }
    }

    private static class SaveToSessionRequestWrapper
    extends HttpServletRequestWrapper {
        private final SaveContextOnUpdateOrErrorResponseWrapper response;

        SaveToSessionRequestWrapper(HttpServletRequest request, SaveContextOnUpdateOrErrorResponseWrapper response) {
            super(request);
            this.response = response;
        }

        public AsyncContext startAsync() {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync();
        }

        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync(servletRequest, servletResponse);
        }
    }
}

