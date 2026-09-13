/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.savedrequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.savedrequest.SavedRequestAwareWrapper;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class HttpSessionRequestCache
implements RequestCache {
    static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private PortResolver portResolver = new PortResolverImpl();
    private boolean createSessionAllowed = true;
    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;
    private String sessionAttrName = "SPRING_SECURITY_SAVED_REQUEST";

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!this.requestMatcher.matches(request)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Did not save request since it did not match [%s]", (Object)this.requestMatcher));
            }
            return;
        }
        DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, this.portResolver);
        if (this.createSessionAllowed || request.getSession(false) != null) {
            request.getSession().setAttribute(this.sessionAttrName, (Object)savedRequest);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)LogMessage.format((String)"Saved request %s to session", (Object)savedRequest.getRedirectUrl()));
            }
        } else {
            this.logger.trace((Object)"Did not save request since there's no session and createSessionAllowed is false");
        }
    }

    @Override
    public SavedRequest getRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
        HttpSession session = currentRequest.getSession(false);
        return session != null ? (SavedRequest)session.getAttribute(this.sessionAttrName) : null;
    }

    @Override
    public void removeRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
        HttpSession session = currentRequest.getSession(false);
        if (session != null) {
            this.logger.trace((Object)"Removing DefaultSavedRequest from session if present");
            session.removeAttribute(this.sessionAttrName);
        }
    }

    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = this.getRequest(request, response);
        if (saved == null) {
            this.logger.trace((Object)"No saved request");
            return null;
        }
        if (!this.matchesSavedRequest(request, saved)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Did not match request %s to the saved one %s", (Object)UrlUtils.buildRequestUrl(request), (Object)saved));
            }
            return null;
        }
        this.removeRequest(request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)LogMessage.format((String)"Loaded matching saved request %s", (Object)saved.getRedirectUrl()));
        }
        return new SavedRequestAwareWrapper(saved, request);
    }

    private boolean matchesSavedRequest(HttpServletRequest request, SavedRequest savedRequest) {
        if (savedRequest instanceof DefaultSavedRequest) {
            DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest)savedRequest;
            return defaultSavedRequest.doesRequestMatch(request, this.portResolver);
        }
        String currentUrl = UrlUtils.buildFullRequestUrl(request);
        return savedRequest.getRedirectUrl().equals(currentUrl);
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public void setCreateSessionAllowed(boolean createSessionAllowed) {
        this.createSessionAllowed = createSessionAllowed;
    }

    public void setPortResolver(PortResolver portResolver) {
        this.portResolver = portResolver;
    }

    public void setSessionAttrName(String sessionAttrName) {
        this.sessionAttrName = sessionAttrName;
    }
}

