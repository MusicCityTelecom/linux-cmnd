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
 *  javax.servlet.http.HttpServletResponseWrapper
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
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.web.filter.OncePerRequestFilter;

public class DisableEncodeUrlFilter
extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter((ServletRequest)request, (ServletResponse)new DisableEncodeUrlResponseWrapper(response));
    }

    private static final class DisableEncodeUrlResponseWrapper
    extends HttpServletResponseWrapper {
        private DisableEncodeUrlResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        public String encodeRedirectUrl(String url) {
            return url;
        }

        public String encodeRedirectURL(String url) {
            return url;
        }

        public String encodeUrl(String url) {
            return url;
        }

        public String encodeURL(String url) {
            return url;
        }
    }
}

