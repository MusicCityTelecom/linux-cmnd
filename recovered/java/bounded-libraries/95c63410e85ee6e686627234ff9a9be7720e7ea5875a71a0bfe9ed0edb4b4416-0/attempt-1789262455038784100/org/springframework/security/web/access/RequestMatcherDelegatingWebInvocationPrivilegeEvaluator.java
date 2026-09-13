/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.web.context.ServletContextAware
 */
package org.springframework.security.web.access;

import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

public final class RequestMatcherDelegatingWebInvocationPrivilegeEvaluator
implements WebInvocationPrivilegeEvaluator,
ServletContextAware {
    private final List<RequestMatcherEntry<List<WebInvocationPrivilegeEvaluator>>> delegates;
    private ServletContext servletContext;

    public RequestMatcherDelegatingWebInvocationPrivilegeEvaluator(List<RequestMatcherEntry<List<WebInvocationPrivilegeEvaluator>>> requestMatcherPrivilegeEvaluatorsEntries) {
        Assert.notNull(requestMatcherPrivilegeEvaluatorsEntries, (String)"requestMatcherPrivilegeEvaluators cannot be null");
        for (RequestMatcherEntry<List<WebInvocationPrivilegeEvaluator>> entry : requestMatcherPrivilegeEvaluatorsEntries) {
            Assert.notNull((Object)entry.getRequestMatcher(), (String)"requestMatcher cannot be null");
            Assert.notNull(entry.getEntry(), (String)"webInvocationPrivilegeEvaluators cannot be null");
        }
        this.delegates = requestMatcherPrivilegeEvaluatorsEntries;
    }

    @Override
    public boolean isAllowed(String uri, Authentication authentication) {
        List<WebInvocationPrivilegeEvaluator> privilegeEvaluators = this.getDelegate(null, uri, null);
        if (privilegeEvaluators.isEmpty()) {
            return true;
        }
        for (WebInvocationPrivilegeEvaluator evaluator : privilegeEvaluators) {
            boolean isAllowed = evaluator.isAllowed(uri, authentication);
            if (isAllowed) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
        List<WebInvocationPrivilegeEvaluator> privilegeEvaluators = this.getDelegate(contextPath, uri, method);
        if (privilegeEvaluators.isEmpty()) {
            return true;
        }
        for (WebInvocationPrivilegeEvaluator evaluator : privilegeEvaluators) {
            boolean isAllowed = evaluator.isAllowed(contextPath, uri, method, authentication);
            if (isAllowed) continue;
            return false;
        }
        return true;
    }

    private List<WebInvocationPrivilegeEvaluator> getDelegate(String contextPath, String uri, String method) {
        FilterInvocation filterInvocation = new FilterInvocation(contextPath, uri, method, this.servletContext);
        for (RequestMatcherEntry<List<WebInvocationPrivilegeEvaluator>> delegate : this.delegates) {
            if (!delegate.getRequestMatcher().matches(filterInvocation.getHttpRequest())) continue;
            return delegate.getEntry();
        }
        return Collections.emptyList();
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

