/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.filters.CorsFilter
 */
package com.tpvision.smartinstall.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.CorsFilter;

public class TpvCorsFilter
extends CorsFilter {
    private static final long serialVersionUID = 1L;

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        String path = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "").toLowerCase();
        if (path.startsWith("/api") || path.startsWith("/exapi")) {
            HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
            if (httpRequest.getHeader("Origin") != null) {
                httpResponse.addHeader("Access-Control-Allow-Origin", "*");
                httpResponse.addHeader("Access-Control-Allow-Headers", "*");
                httpResponse.addHeader("Access-Control-Max-Age", "3600");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            super.doFilter(servletRequest, servletResponse, filterChain);
        }
    }
}

