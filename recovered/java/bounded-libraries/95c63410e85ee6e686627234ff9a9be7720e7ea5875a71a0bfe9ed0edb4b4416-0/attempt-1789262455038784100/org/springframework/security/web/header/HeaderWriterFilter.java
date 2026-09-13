/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.RequestDispatcher
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.header;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.util.OnCommittedResponseWrapper;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class HeaderWriterFilter
extends OncePerRequestFilter {
    private final List<HeaderWriter> headerWriters;
    private boolean shouldWriteHeadersEagerly = false;

    public HeaderWriterFilter(List<HeaderWriter> headerWriters) {
        Assert.notEmpty(headerWriters, (String)"headerWriters cannot be null or empty");
        this.headerWriters = headerWriters;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.shouldWriteHeadersEagerly) {
            this.doHeadersBefore(request, response, filterChain);
        } else {
            this.doHeadersAfter(request, response, filterChain);
        }
    }

    private void doHeadersBefore(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        this.writeHeaders(request, response);
        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doHeadersAfter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HeaderWriterResponse headerWriterResponse = new HeaderWriterResponse(request, response);
        HeaderWriterRequest headerWriterRequest = new HeaderWriterRequest(request, headerWriterResponse);
        try {
            filterChain.doFilter((ServletRequest)headerWriterRequest, (ServletResponse)headerWriterResponse);
        }
        finally {
            headerWriterResponse.writeHeaders();
        }
    }

    void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        for (HeaderWriter writer : this.headerWriters) {
            writer.writeHeaders(request, response);
        }
    }

    public void setShouldWriteHeadersEagerly(boolean shouldWriteHeadersEagerly) {
        this.shouldWriteHeadersEagerly = shouldWriteHeadersEagerly;
    }

    static class HeaderWriterRequestDispatcher
    implements RequestDispatcher {
        private final RequestDispatcher delegate;
        private final HeaderWriterResponse response;

        HeaderWriterRequestDispatcher(RequestDispatcher delegate, HeaderWriterResponse response) {
            this.delegate = delegate;
            this.response = response;
        }

        public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            this.delegate.forward(request, response);
        }

        public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            this.response.onResponseCommitted();
            this.delegate.include(request, response);
        }
    }

    static class HeaderWriterRequest
    extends HttpServletRequestWrapper {
        private final HeaderWriterResponse response;

        HeaderWriterRequest(HttpServletRequest request, HeaderWriterResponse response) {
            super(request);
            this.response = response;
        }

        public RequestDispatcher getRequestDispatcher(String path) {
            return new HeaderWriterRequestDispatcher(super.getRequestDispatcher(path), this.response);
        }
    }

    class HeaderWriterResponse
    extends OnCommittedResponseWrapper {
        private final HttpServletRequest request;

        HeaderWriterResponse(HttpServletRequest request, HttpServletResponse response) {
            super(response);
            this.request = request;
        }

        @Override
        protected void onResponseCommitted() {
            this.writeHeaders();
            this.disableOnResponseCommitted();
        }

        protected void writeHeaders() {
            if (this.isDisableOnResponseCommitted()) {
                return;
            }
            HeaderWriterFilter.this.writeHeaders(this.request, this.getHttpResponse());
        }

        private HttpServletResponse getHttpResponse() {
            return (HttpServletResponse)this.getResponse();
        }
    }
}

