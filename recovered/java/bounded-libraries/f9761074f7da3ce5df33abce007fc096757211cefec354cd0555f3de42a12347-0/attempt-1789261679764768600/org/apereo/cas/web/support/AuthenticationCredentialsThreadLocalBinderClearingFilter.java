/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder
 */
package org.apereo.cas.web.support;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apereo.cas.authentication.AuthenticationCredentialsThreadLocalBinder;

public class AuthenticationCredentialsThreadLocalBinderClearingFilter
implements Filter {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally {
            AuthenticationCredentialsThreadLocalBinder.clear();
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}

