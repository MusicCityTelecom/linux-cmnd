/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.security.access.intercept.AbstractSecurityInterceptor
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.web.context.ServletContextAware
 */
package org.springframework.security.web.access;

import java.util.Collection;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

public class DefaultWebInvocationPrivilegeEvaluator
implements WebInvocationPrivilegeEvaluator,
ServletContextAware {
    protected static final Log logger = LogFactory.getLog(DefaultWebInvocationPrivilegeEvaluator.class);
    private final AbstractSecurityInterceptor securityInterceptor;
    private ServletContext servletContext;

    public DefaultWebInvocationPrivilegeEvaluator(AbstractSecurityInterceptor securityInterceptor) {
        Assert.notNull((Object)securityInterceptor, (String)"SecurityInterceptor cannot be null");
        Assert.isTrue((boolean)FilterInvocation.class.equals((Object)securityInterceptor.getSecureObjectClass()), (String)"AbstractSecurityInterceptor does not support FilterInvocations");
        Assert.notNull((Object)securityInterceptor.getAccessDecisionManager(), (String)"AbstractSecurityInterceptor must provide a non-null AccessDecisionManager");
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public boolean isAllowed(String uri, Authentication authentication) {
        return this.isAllowed(null, uri, null, authentication);
    }

    @Override
    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
        Assert.notNull((Object)uri, (String)"uri parameter is required");
        FilterInvocation filterInvocation = new FilterInvocation(contextPath, uri, method, this.servletContext);
        Collection attributes = this.securityInterceptor.obtainSecurityMetadataSource().getAttributes((Object)filterInvocation);
        if (attributes == null) {
            return !this.securityInterceptor.isRejectPublicInvocations();
        }
        if (authentication == null) {
            return false;
        }
        try {
            this.securityInterceptor.getAccessDecisionManager().decide(authentication, (Object)filterInvocation, attributes);
            return true;
        }
        catch (AccessDeniedException ex) {
            logger.debug((Object)LogMessage.format((String)"%s denied for %s", (Object)filterInvocation, (Object)authentication), (Throwable)ex);
            return false;
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

