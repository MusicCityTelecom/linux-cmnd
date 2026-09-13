/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package org.springframework.security.web.context;

import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

public final class RequestAttributeSecurityContextRepository
implements SecurityContextRepository {
    public static final String DEFAULT_REQUEST_ATTR_NAME = RequestAttributeSecurityContextRepository.class.getName().concat(".SPRING_SECURITY_CONTEXT");
    private final String requestAttributeName;

    public RequestAttributeSecurityContextRepository() {
        this(DEFAULT_REQUEST_ATTR_NAME);
    }

    public RequestAttributeSecurityContextRepository(String requestAttributeName) {
        this.requestAttributeName = requestAttributeName;
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return this.getContext(request) != null;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return this.getContextOrEmpty(requestResponseHolder.getRequest());
    }

    @Override
    public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        return () -> this.getContextOrEmpty(request);
    }

    private SecurityContext getContextOrEmpty(HttpServletRequest request) {
        SecurityContext context = this.getContext(request);
        return context != null ? context : SecurityContextHolder.createEmptyContext();
    }

    private SecurityContext getContext(HttpServletRequest request) {
        return (SecurityContext)request.getAttribute(this.requestAttributeName);
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(this.requestAttributeName, (Object)context);
    }
}

