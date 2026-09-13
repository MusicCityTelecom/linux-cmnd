/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpServletResponseWrapper
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.Ordered
 *  org.springframework.util.ClassUtils
 *  org.springframework.web.filter.OncePerRequestFilter
 *  org.springframework.web.util.NestedServletException
 */
package org.springframework.boot.web.servlet.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

public class ErrorPageFilter
implements Filter,
ErrorPageRegistry,
Ordered {
    private static final Log logger = LogFactory.getLog(ErrorPageFilter.class);
    private static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    private static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    private static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    private static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    private static final Set<Class<?>> CLIENT_ABORT_EXCEPTIONS;
    private String global;
    private final Map<Integer, String> statuses = new HashMap<Integer, String>();
    private final Map<Class<?>, String> exceptions = new HashMap();
    private final OncePerRequestFilter delegate = new OncePerRequestFilter(){

        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
            ErrorPageFilter.this.doFilter(request, response, chain);
        }

        protected boolean shouldNotFilterAsyncDispatch() {
            return false;
        }
    };

    public void init(FilterConfig filterConfig) throws ServletException {
        this.delegate.init(filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.delegate.doFilter(request, response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ErrorWrapperResponse wrapped = new ErrorWrapperResponse(response);
        try {
            chain.doFilter((ServletRequest)request, (ServletResponse)wrapped);
            if (wrapped.hasErrorToSend()) {
                this.handleErrorStatus(request, response, wrapped.getStatus(), wrapped.getMessage());
                response.flushBuffer();
            } else if (!request.isAsyncStarted() && !response.isCommitted()) {
                response.flushBuffer();
            }
        }
        catch (Throwable ex) {
            Throwable rootCause;
            Throwable exceptionToHandle = ex;
            if (ex instanceof NestedServletException && (rootCause = ((NestedServletException)ex).getRootCause()) != null) {
                exceptionToHandle = rootCause;
            }
            this.handleException(request, response, wrapped, exceptionToHandle);
            response.flushBuffer();
        }
    }

    private void handleErrorStatus(HttpServletRequest request, HttpServletResponse response, int status, String message) throws ServletException, IOException {
        if (response.isCommitted()) {
            this.handleCommittedResponse(request, null);
            return;
        }
        String errorPath = this.getErrorPath(this.statuses, status);
        if (errorPath == null) {
            response.sendError(status, message);
            return;
        }
        response.setStatus(status);
        this.setErrorAttributes(request, status, message);
        request.getRequestDispatcher(errorPath).forward((ServletRequest)request, (ServletResponse)response);
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, ErrorWrapperResponse wrapped, Throwable ex) throws IOException, ServletException {
        Class<?> type = ex.getClass();
        String errorPath = this.getErrorPath(type);
        if (errorPath == null) {
            this.rethrow(ex);
            return;
        }
        if (response.isCommitted()) {
            this.handleCommittedResponse(request, ex);
            return;
        }
        this.forwardToErrorPage(errorPath, request, (HttpServletResponse)wrapped, ex);
    }

    private void forwardToErrorPage(String path, HttpServletRequest request, HttpServletResponse response, Throwable ex) throws ServletException, IOException {
        if (logger.isErrorEnabled()) {
            String message = "Forwarding to error page from request " + this.getDescription(request) + " due to exception [" + ex.getMessage() + "]";
            logger.error((Object)message, ex);
        }
        this.setErrorAttributes(request, 500, ex.getMessage());
        request.setAttribute(ERROR_EXCEPTION, (Object)ex);
        request.setAttribute(ERROR_EXCEPTION_TYPE, ex.getClass());
        response.reset();
        response.setStatus(500);
        request.getRequestDispatcher(path).forward((ServletRequest)request, (ServletResponse)response);
        request.removeAttribute(ERROR_EXCEPTION);
        request.removeAttribute(ERROR_EXCEPTION_TYPE);
    }

    protected String getDescription(HttpServletRequest request) {
        String pathInfo = request.getPathInfo() != null ? request.getPathInfo() : "";
        return "[" + request.getServletPath() + pathInfo + "]";
    }

    private void handleCommittedResponse(HttpServletRequest request, Throwable ex) {
        if (this.isClientAbortException(ex)) {
            return;
        }
        String message = "Cannot forward to error page for request " + this.getDescription(request) + " as the response has already been committed. As a result, the response may have the wrong status code. If your application is running on WebSphere Application Server you may be able to resolve this problem by setting com.ibm.ws.webcontainer.invokeFlushAfterService to false";
        if (ex == null) {
            logger.error((Object)message);
        } else {
            logger.error((Object)message, ex);
        }
    }

    private boolean isClientAbortException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        for (Class<?> candidate : CLIENT_ABORT_EXCEPTIONS) {
            if (!candidate.isInstance(ex)) continue;
            return true;
        }
        return this.isClientAbortException(ex.getCause());
    }

    private String getErrorPath(Map<Integer, String> map, Integer status) {
        if (map.containsKey(status)) {
            return map.get(status);
        }
        return this.global;
    }

    private String getErrorPath(Class<?> type) {
        while (type != Object.class) {
            String path = this.exceptions.get(type);
            if (path != null) {
                return path;
            }
            type = type.getSuperclass();
        }
        return this.global;
    }

    private void setErrorAttributes(HttpServletRequest request, int status, String message) {
        request.setAttribute(ERROR_STATUS_CODE, (Object)status);
        request.setAttribute(ERROR_MESSAGE, (Object)message);
        request.setAttribute(ERROR_REQUEST_URI, (Object)request.getRequestURI());
    }

    private void rethrow(Throwable ex) throws IOException, ServletException {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        }
        if (ex instanceof Error) {
            throw (Error)ex;
        }
        if (ex instanceof IOException) {
            throw (IOException)ex;
        }
        if (ex instanceof ServletException) {
            throw (ServletException)ex;
        }
        throw new IllegalStateException(ex);
    }

    @Override
    public void addErrorPages(ErrorPage ... errorPages) {
        for (ErrorPage errorPage : errorPages) {
            if (errorPage.isGlobal()) {
                this.global = errorPage.getPath();
                continue;
            }
            if (errorPage.getStatus() != null) {
                this.statuses.put(errorPage.getStatus().value(), errorPage.getPath());
                continue;
            }
            this.exceptions.put(errorPage.getException(), errorPage.getPath());
        }
    }

    public void destroy() {
    }

    public int getOrder() {
        return -2147483647;
    }

    private static void addClassIfPresent(Collection<Class<?>> collection, String className) {
        try {
            collection.add(ClassUtils.forName((String)className, null));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    static {
        HashSet clientAbortExceptions = new HashSet();
        ErrorPageFilter.addClassIfPresent(clientAbortExceptions, "org.apache.catalina.connector.ClientAbortException");
        CLIENT_ABORT_EXCEPTIONS = Collections.unmodifiableSet(clientAbortExceptions);
    }

    private static class ErrorWrapperResponse
    extends HttpServletResponseWrapper {
        private int status;
        private String message;
        private boolean hasErrorToSend = false;

        ErrorWrapperResponse(HttpServletResponse response) {
            super(response);
        }

        public void sendError(int status) throws IOException {
            this.sendError(status, null);
        }

        public void sendError(int status, String message) throws IOException {
            this.status = status;
            this.message = message;
            this.hasErrorToSend = true;
        }

        public int getStatus() {
            if (this.hasErrorToSend) {
                return this.status;
            }
            return super.getStatus();
        }

        public void flushBuffer() throws IOException {
            this.sendErrorIfNecessary();
            super.flushBuffer();
        }

        private void sendErrorIfNecessary() throws IOException {
            if (this.hasErrorToSend && !this.isCommitted()) {
                ((HttpServletResponse)this.getResponse()).sendError(this.status, this.message);
            }
        }

        String getMessage() {
            return this.message;
        }

        boolean hasErrorToSend() {
            return this.hasErrorToSend;
        }

        public PrintWriter getWriter() throws IOException {
            this.sendErrorIfNecessary();
            return super.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            this.sendErrorIfNecessary();
            return super.getOutputStream();
        }
    }
}

