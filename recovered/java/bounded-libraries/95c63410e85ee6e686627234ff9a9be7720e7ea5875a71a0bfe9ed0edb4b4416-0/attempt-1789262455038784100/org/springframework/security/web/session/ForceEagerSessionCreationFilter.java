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
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.session;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.core.log.LogMessage;
import org.springframework.web.filter.OncePerRequestFilter;

public class ForceEagerSessionCreationFilter
extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (this.logger.isDebugEnabled() && session.isNew()) {
            this.logger.debug((Object)LogMessage.format((String)"Created session eagerly", (Object[])new Object[0]));
        }
        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }
}

