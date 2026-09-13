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
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 */
package org.springframework.security.web.debug;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.debug.Logger;
import org.springframework.security.web.util.UrlUtils;

public final class DebugFilter
implements Filter {
    static final String ALREADY_FILTERED_ATTR_NAME = DebugFilter.class.getName().concat(".FILTERED");
    private final FilterChainProxy filterChainProxy;
    private final Logger logger = new Logger();

    public DebugFilter(FilterChainProxy filterChainProxy) {
        this.filterChainProxy = filterChainProxy;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("DebugFilter just supports HTTP requests");
        }
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        List<Filter> filters = this.getFilters(request);
        this.logger.info("Request received for " + request.getMethod() + " '" + UrlUtils.buildRequestUrl(request) + "':\n\n" + request + "\n\nservletPath:" + request.getServletPath() + "\npathInfo:" + request.getPathInfo() + "\nheaders: \n" + this.formatHeaders(request) + "\n\n" + this.formatFilters(filters));
        if (request.getAttribute(ALREADY_FILTERED_ATTR_NAME) == null) {
            this.invokeWithWrappedRequest(request, response, filterChain);
        } else {
            this.filterChainProxy.doFilter((ServletRequest)request, (ServletResponse)response, filterChain);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void invokeWithWrappedRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute(ALREADY_FILTERED_ATTR_NAME, (Object)Boolean.TRUE);
        request = new DebugRequestWrapper((HttpServletRequest)request);
        try {
            this.filterChainProxy.doFilter((ServletRequest)request, (ServletResponse)response, filterChain);
        }
        finally {
            request.removeAttribute(ALREADY_FILTERED_ATTR_NAME);
        }
    }

    String formatHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration eHeaderNames = request.getHeaderNames();
        while (eHeaderNames.hasMoreElements()) {
            String headerName = (String)eHeaderNames.nextElement();
            sb.append(headerName);
            sb.append(": ");
            Enumeration eHeaderValues = request.getHeaders(headerName);
            while (eHeaderValues.hasMoreElements()) {
                sb.append((String)eHeaderValues.nextElement());
                if (!eHeaderValues.hasMoreElements()) continue;
                sb.append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    String formatFilters(List<Filter> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("Security filter chain: ");
        if (filters == null) {
            sb.append("no match");
        } else if (filters.isEmpty()) {
            sb.append("[] empty (bypassed by security='none') ");
        } else {
            sb.append("[\n");
            for (Filter f : filters) {
                sb.append("  ").append(f.getClass().getSimpleName()).append("\n");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    private List<Filter> getFilters(HttpServletRequest request) {
        for (SecurityFilterChain chain : this.filterChainProxy.getFilterChains()) {
            if (!chain.matches(request)) continue;
            return chain.getFilters();
        }
        return null;
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public FilterChainProxy getFilterChainProxy() {
        return this.filterChainProxy;
    }

    static class DebugRequestWrapper
    extends HttpServletRequestWrapper {
        private static final Logger logger = new Logger();

        DebugRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        public HttpSession getSession() {
            boolean sessionExists = super.getSession(false) != null;
            HttpSession session = super.getSession();
            if (!sessionExists) {
                logger.info("New HTTP session created: " + session.getId(), true);
            }
            return session;
        }

        public HttpSession getSession(boolean create) {
            if (!create) {
                return super.getSession(create);
            }
            return this.getSession();
        }
    }
}

