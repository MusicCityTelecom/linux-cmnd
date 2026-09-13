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
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.context;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecurityContextHolderFilter
extends OncePerRequestFilter {
    private final SecurityContextRepository securityContextRepository;
    private boolean shouldNotFilterErrorDispatch;

    public SecurityContextHolderFilter(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext = this.securityContextRepository.loadContext(request).get();
        try {
            SecurityContextHolder.setContext((SecurityContext)securityContext);
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
        finally {
            SecurityContextHolder.clearContext();
        }
    }

    protected boolean shouldNotFilterErrorDispatch() {
        return this.shouldNotFilterErrorDispatch;
    }

    public void setShouldNotFilterErrorDispatch(boolean shouldNotFilterErrorDispatch) {
        this.shouldNotFilterErrorDispatch = shouldNotFilterErrorDispatch;
    }
}

