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
 *  javax.servlet.http.HttpSession
 *  org.springframework.core.Ordered
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.boot.actuate.web.trace.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.TraceableHttpServletRequest;
import org.springframework.boot.actuate.web.trace.servlet.TraceableHttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class HttpTraceFilter
extends OncePerRequestFilter
implements Ordered {
    private int order = 0x7FFFFFF5;
    private final HttpTraceRepository repository;
    private final HttpExchangeTracer tracer;

    public HttpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
        this.repository = repository;
        this.tracer = tracer;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.isRequestValid(request)) {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        TraceableHttpServletRequest traceableRequest = new TraceableHttpServletRequest(request);
        HttpTrace trace = this.tracer.receivedRequest(traceableRequest);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        try {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            status = response.getStatus();
        }
        catch (Throwable throwable) {
            TraceableHttpServletResponse traceableResponse = new TraceableHttpServletResponse((HttpServletResponse)(status != response.getStatus() ? new CustomStatusResponseWrapper(response, status) : response));
            this.tracer.sendingResponse(trace, traceableResponse, () -> ((HttpServletRequest)request).getUserPrincipal(), () -> this.getSessionId(request));
            this.repository.add(trace);
            throw throwable;
        }
        TraceableHttpServletResponse traceableResponse = new TraceableHttpServletResponse((HttpServletResponse)(status != response.getStatus() ? new CustomStatusResponseWrapper(response, status) : response));
        this.tracer.sendingResponse(trace, traceableResponse, () -> ((HttpServletRequest)request).getUserPrincipal(), () -> this.getSessionId(request));
        this.repository.add(trace);
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        }
        catch (URISyntaxException ex) {
            return false;
        }
    }

    private String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? session.getId() : null;
    }

    private static final class CustomStatusResponseWrapper
    extends HttpServletResponseWrapper {
        private final int status;

        private CustomStatusResponseWrapper(HttpServletResponse response, int status) {
            super(response);
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }
    }
}

