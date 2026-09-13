/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.common.spi.JoinPointArgumentAuditPrincipalIdProvider
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.inspektr.audit.spi.support;

import org.apereo.inspektr.common.spi.JoinPointArgumentAuditPrincipalIdProvider;
import org.aspectj.lang.JoinPoint;
import org.springframework.webflow.execution.RequestContext;

public class SpringWebflowActionExecutionAuditablePrincipalResolver
extends JoinPointArgumentAuditPrincipalIdProvider<RequestContext> {
    private final String attributeName;

    public SpringWebflowActionExecutionAuditablePrincipalResolver(String attributeName) {
        super(0, RequestContext.class);
        this.attributeName = attributeName;
    }

    protected String resolveFrom(RequestContext requestContext, JoinPoint auditTarget, Object returnValue) {
        if (requestContext.getFlashScope().contains(this.attributeName)) {
            return requestContext.getFlashScope().get(this.attributeName).toString();
        }
        if (requestContext.getRequestScope().contains(this.attributeName)) {
            return requestContext.getRequestScope().get(this.attributeName).toString();
        }
        if (requestContext.getFlowScope().contains(this.attributeName)) {
            return requestContext.getFlowScope().get(this.attributeName).toString();
        }
        if (requestContext.getConversationScope().contains(this.attributeName)) {
            return requestContext.getConversationScope().get(this.attributeName).toString();
        }
        if (requestContext.getRequestParameters().contains(this.attributeName)) {
            return requestContext.getRequestParameters().getRequired(this.attributeName);
        }
        return "audit:unknown";
    }
}

