/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpHeaders
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class FilterInvocation {
    static final FilterChain DUMMY_CHAIN = (req, res) -> {
        throw new UnsupportedOperationException("Dummy filter chain");
    };
    private FilterChain chain;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public FilterInvocation(ServletRequest request, ServletResponse response, FilterChain chain) {
        Assert.isTrue((request != null && response != null && chain != null ? 1 : 0) != 0, (String)"Cannot pass null values to constructor");
        this.request = (HttpServletRequest)request;
        this.response = (HttpServletResponse)response;
        this.chain = chain;
    }

    public FilterInvocation(String servletPath, String method) {
        this(null, servletPath, method);
    }

    public FilterInvocation(String contextPath, String servletPath, String method) {
        this(contextPath, servletPath, method, null);
    }

    public FilterInvocation(String contextPath, String servletPath, String method, ServletContext servletContext) {
        this(contextPath, servletPath, null, null, method, servletContext);
    }

    public FilterInvocation(String contextPath, String servletPath, String pathInfo, String query, String method) {
        this(contextPath, servletPath, pathInfo, query, method, null);
    }

    public FilterInvocation(String contextPath, String servletPath, String pathInfo, String query, String method, ServletContext servletContext) {
        DummyRequest request = new DummyRequest();
        contextPath = contextPath != null ? contextPath : "/cp";
        request.setContextPath(contextPath);
        request.setServletPath(servletPath);
        request.setRequestURI(contextPath + servletPath + (pathInfo != null ? pathInfo : ""));
        request.setPathInfo(pathInfo);
        request.setQueryString(query);
        request.setMethod(method);
        request.setServletContext(servletContext);
        this.request = request;
    }

    public FilterChain getChain() {
        return this.chain;
    }

    public String getFullRequestUrl() {
        return UrlUtils.buildFullRequestUrl(this.request);
    }

    public HttpServletRequest getHttpRequest() {
        return this.request;
    }

    public HttpServletResponse getHttpResponse() {
        return this.response;
    }

    public String getRequestUrl() {
        return UrlUtils.buildRequestUrl(this.request);
    }

    public HttpServletRequest getRequest() {
        return this.getHttpRequest();
    }

    public HttpServletResponse getResponse() {
        return this.getHttpResponse();
    }

    public String toString() {
        if (StringUtils.isEmpty((Object)this.request.getMethod())) {
            return "filter invocation [" + this.getRequestUrl() + "]";
        }
        return "filter invocation [" + this.request.getMethod() + " " + this.getRequestUrl() + "]";
    }

    static final class UnsupportedOperationExceptionInvocationHandler
    implements InvocationHandler {
        private static final float JAVA_VERSION = Float.parseFloat(System.getProperty("java.class.version", "52"));

        UnsupportedOperationExceptionInvocationHandler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isDefault()) {
                return this.invokeDefaultMethod(proxy, method, args);
            }
            throw new UnsupportedOperationException(method + " is not supported");
        }

        private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
            if (this.isJdk8OrEarlier()) {
                return this.invokeDefaultMethodForJdk8(proxy, method, args);
            }
            return MethodHandles.lookup().findSpecial(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), new Class[0]), method.getDeclaringClass()).bindTo(proxy).invokeWithArguments(args);
        }

        private Object invokeDefaultMethodForJdk8(Object proxy, Method method, Object[] args) throws Throwable {
            Constructor constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            Class<?> clazz = method.getDeclaringClass();
            return ((MethodHandles.Lookup)constructor.newInstance(clazz)).in(clazz).unreflectSpecial(method, clazz).bindTo(proxy).invokeWithArguments(args);
        }

        private boolean isJdk8OrEarlier() {
            return JAVA_VERSION <= 52.0f;
        }
    }

    static class DummyRequest
    extends HttpServletRequestWrapper {
        private static final HttpServletRequest UNSUPPORTED_REQUEST = (HttpServletRequest)Proxy.newProxyInstance(DummyRequest.class.getClassLoader(), new Class[]{HttpServletRequest.class}, (InvocationHandler)new UnsupportedOperationExceptionInvocationHandler());
        private String requestURI;
        private String contextPath = "";
        private String servletPath;
        private String pathInfo;
        private String queryString;
        private String method;
        private ServletContext servletContext;
        private final HttpHeaders headers = new HttpHeaders();
        private final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();

        DummyRequest() {
            super(UNSUPPORTED_REQUEST);
        }

        public String getCharacterEncoding() {
            return "UTF-8";
        }

        public Object getAttribute(String attributeName) {
            return null;
        }

        void setRequestURI(String requestURI) {
            this.requestURI = requestURI;
        }

        void setPathInfo(String pathInfo) {
            this.pathInfo = pathInfo;
        }

        public String getRequestURI() {
            return this.requestURI;
        }

        void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        public String getContextPath() {
            return this.contextPath;
        }

        void setServletPath(String servletPath) {
            this.servletPath = servletPath;
        }

        public String getServletPath() {
            return this.servletPath;
        }

        void setMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }

        public String getPathInfo() {
            return this.pathInfo;
        }

        public String getQueryString() {
            return this.queryString;
        }

        void setQueryString(String queryString) {
            this.queryString = queryString;
        }

        public String getServerName() {
            return null;
        }

        public String getHeader(String name) {
            return this.headers.getFirst(name);
        }

        public Enumeration<String> getHeaders(String name) {
            return Collections.enumeration(this.headers.get((Object)name));
        }

        public Enumeration<String> getHeaderNames() {
            return Collections.enumeration(this.headers.keySet());
        }

        public int getIntHeader(String name) {
            String value = this.headers.getFirst(name);
            if (value == null) {
                return -1;
            }
            return Integer.parseInt(value);
        }

        void addHeader(String name, String value) {
            this.headers.add(name, value);
        }

        public String getParameter(String name) {
            String[] array = this.parameters.get(name);
            return array != null && array.length > 0 ? array[0] : null;
        }

        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(this.parameters);
        }

        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(this.parameters.keySet());
        }

        public String[] getParameterValues(String name) {
            return this.parameters.get(name);
        }

        void setParameter(String name, String ... values) {
            this.parameters.put(name, values);
        }

        public ServletContext getServletContext() {
            return this.servletContext;
        }

        void setServletContext(ServletContext servletContext) {
            this.servletContext = servletContext;
        }
    }
}

