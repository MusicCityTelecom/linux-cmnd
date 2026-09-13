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
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.context;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

@Deprecated
public class SecurityContextPersistenceFilter
extends GenericFilterBean {
    static final String FILTER_APPLIED = "__spring_security_scpf_applied";
    private SecurityContextRepository repo;
    private boolean forceEagerSessionCreation = false;

    public SecurityContextPersistenceFilter() {
        this(new HttpSessionSecurityContextRepository());
    }

    public SecurityContextPersistenceFilter(SecurityContextRepository repo) {
        this.repo = repo;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        request.setAttribute(FILTER_APPLIED, (Object)Boolean.TRUE);
        if (this.forceEagerSessionCreation) {
            HttpSession session = request.getSession();
            if (this.logger.isDebugEnabled() && session.isNew()) {
                this.logger.debug((Object)LogMessage.format((String)"Created session %s eagerly", (Object)session.getId()));
            }
        }
        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
        SecurityContext contextBeforeChainExecution = this.repo.loadContext(holder);
        try {
            SecurityContextHolder.setContext((SecurityContext)contextBeforeChainExecution);
            if (contextBeforeChainExecution.getAuthentication() == null) {
                this.logger.debug((Object)"Set SecurityContextHolder to empty SecurityContext");
            } else if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)LogMessage.format((String)"Set SecurityContextHolder to %s", (Object)contextBeforeChainExecution));
            }
            chain.doFilter((ServletRequest)holder.getRequest(), (ServletResponse)holder.getResponse());
        }
        finally {
            SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
            SecurityContextHolder.clearContext();
            this.repo.saveContext(contextAfterChainExecution, holder.getRequest(), holder.getResponse());
            request.removeAttribute(FILTER_APPLIED);
            this.logger.debug((Object)"Cleared SecurityContextHolder to complete request");
        }
    }

    public void setForceEagerSessionCreation(boolean forceEagerSessionCreation) {
        this.forceEagerSessionCreation = forceEagerSessionCreation;
    }
}

