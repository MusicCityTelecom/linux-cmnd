/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.common.spi.PrincipalResolver
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package org.apereo.inspektr.audit.spi.support;

import org.apereo.inspektr.common.spi.PrincipalResolver;
import org.aspectj.lang.JoinPoint;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditablePrincipalResolver
implements PrincipalResolver {
    public String resolveFrom(JoinPoint auditableTarget, Object retval) {
        return this.getFromSecurityContext();
    }

    public String resolveFrom(JoinPoint auditableTarget, Exception exception) {
        return this.getFromSecurityContext();
    }

    public String resolve() {
        return this.getFromSecurityContext();
    }

    private String getFromSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return "audit:unknown";
        }
        if (securityContext.getAuthentication() == null) {
            return "audit:unknown";
        }
        String subject = securityContext.getAuthentication().getName();
        if (subject == null) {
            return "audit:unknown";
        }
        return subject;
    }
}

