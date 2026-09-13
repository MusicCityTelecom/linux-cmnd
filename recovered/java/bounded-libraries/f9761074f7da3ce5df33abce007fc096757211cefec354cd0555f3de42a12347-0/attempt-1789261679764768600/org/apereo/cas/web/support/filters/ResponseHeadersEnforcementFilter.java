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
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.web.support.filters.AbstractSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseHeadersEnforcementFilter
extends AbstractSecurityFilter
implements Filter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHeadersEnforcementFilter.class);
    public static final String INIT_PARAM_ENABLE_CACHE_CONTROL = "enableCacheControl";
    public static final String INIT_PARAM_ENABLE_XCONTENT_OPTIONS = "enableXContentTypeOptions";
    public static final String INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY = "enableStrictTransportSecurity";
    public static final String INIT_PARAM_ENABLE_STRICT_XFRAME_OPTIONS = "enableXFrameOptions";
    public static final String INIT_PARAM_STRICT_XFRAME_OPTIONS = "XFrameOptions";
    public static final String INIT_PARAM_ENABLE_XSS_PROTECTION = "enableXSSProtection";
    public static final String INIT_PARAM_XSS_PROTECTION = "XSSProtection";
    public static final String INIT_PARAM_CONTENT_SECURITY_POLICY = "contentSecurityPolicy";
    public static final String INIT_PARAM_CACHE_CONTROL_STATIC_RESOURCES = "cacheControlStaticResources";
    public static final String INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY_OPTIONS = "enableStrictTransportSecurityOptions";
    private final Object lock = new Object();
    private Pattern cacheControlStaticResourcesPattern;
    private boolean enableCacheControl;
    private String cacheControlHeader = "no-cache, no-store, max-age=0, must-revalidate";
    private boolean enableXContentTypeOptions;
    private String xContentTypeOptionsHeader = "nosniff";
    private boolean enableStrictTransportSecurity;
    private String strictTransportSecurityHeader = "max-age=15768000 ; includeSubDomains";
    private boolean enableXFrameOptions;
    private String xframeOptions = "DENY";
    private boolean enableXSSProtection;
    private String xssProtection = "1; mode=block";
    private String contentSecurityPolicy;

    private static void throwIfUnrecognizedParamName(Enumeration initParamNames) {
        HashSet<String> recognizedParameterNames = new HashSet<String>();
        recognizedParameterNames.add(INIT_PARAM_ENABLE_CACHE_CONTROL);
        recognizedParameterNames.add(INIT_PARAM_ENABLE_XCONTENT_OPTIONS);
        recognizedParameterNames.add(INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY);
        recognizedParameterNames.add(INIT_PARAM_ENABLE_STRICT_XFRAME_OPTIONS);
        recognizedParameterNames.add(INIT_PARAM_STRICT_XFRAME_OPTIONS);
        recognizedParameterNames.add(INIT_PARAM_CONTENT_SECURITY_POLICY);
        recognizedParameterNames.add(INIT_PARAM_ENABLE_XSS_PROTECTION);
        recognizedParameterNames.add(INIT_PARAM_XSS_PROTECTION);
        recognizedParameterNames.add(INIT_PARAM_CACHE_CONTROL_STATIC_RESOURCES);
        recognizedParameterNames.add(INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY_OPTIONS);
        recognizedParameterNames.add("throwOnError");
        while (initParamNames.hasMoreElements()) {
            String initParamName = (String)initParamNames.nextElement();
            if (recognizedParameterNames.contains(initParamName)) continue;
            ResponseHeadersEnforcementFilter.logException((Exception)new ServletException("Unrecognized init parameter [" + initParamName + "]"));
        }
    }

    public void init(FilterConfig filterConfig) {
        String failSafeParam = filterConfig.getInitParameter("throwOnError");
        if (null != failSafeParam) {
            ResponseHeadersEnforcementFilter.setThrowOnErrors(Boolean.parseBoolean(failSafeParam));
        }
        Enumeration initParamNames = filterConfig.getInitParameterNames();
        ResponseHeadersEnforcementFilter.throwIfUnrecognizedParamName(initParamNames);
        String cacheControl = filterConfig.getInitParameter(INIT_PARAM_ENABLE_CACHE_CONTROL);
        String contentTypeOpts = filterConfig.getInitParameter(INIT_PARAM_ENABLE_XCONTENT_OPTIONS);
        String stsEnabled = filterConfig.getInitParameter(INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY);
        String xframeOpts = filterConfig.getInitParameter(INIT_PARAM_ENABLE_STRICT_XFRAME_OPTIONS);
        String xssOpts = filterConfig.getInitParameter(INIT_PARAM_ENABLE_XSS_PROTECTION);
        String cacheControlStaticResources = filterConfig.getInitParameter(INIT_PARAM_CACHE_CONTROL_STATIC_RESOURCES);
        String hstsOptions = filterConfig.getInitParameter(INIT_PARAM_ENABLE_STRICT_TRANSPORT_SECURITY_OPTIONS);
        this.cacheControlStaticResourcesPattern = Pattern.compile("^.+\\.(" + cacheControlStaticResources + ")$", 2);
        this.enableCacheControl = Boolean.parseBoolean(cacheControl);
        this.enableXContentTypeOptions = Boolean.parseBoolean(contentTypeOpts);
        this.enableStrictTransportSecurity = Boolean.parseBoolean(stsEnabled);
        this.enableXFrameOptions = Boolean.parseBoolean(xframeOpts);
        this.xframeOptions = filterConfig.getInitParameter(INIT_PARAM_STRICT_XFRAME_OPTIONS);
        if (this.xframeOptions == null || this.xframeOptions.isEmpty()) {
            this.xframeOptions = "DENY";
        }
        this.enableXSSProtection = Boolean.parseBoolean(xssOpts);
        this.xssProtection = filterConfig.getInitParameter(INIT_PARAM_XSS_PROTECTION);
        if (this.xssProtection == null || this.xssProtection.isEmpty()) {
            this.xssProtection = "1; mode=block";
        }
        this.contentSecurityPolicy = filterConfig.getInitParameter(INIT_PARAM_CONTENT_SECURITY_POLICY);
        if (StringUtils.isNotBlank((CharSequence)hstsOptions)) {
            this.strictTransportSecurityHeader = hstsOptions;
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (servletResponse instanceof HttpServletResponse) {
                HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
                HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
                Optional<Object> result = this.prepareFilterBeforeExecution(httpServletResponse, httpServletRequest);
                this.decideInsertCacheControlHeader(httpServletResponse, httpServletRequest, result);
                this.decideInsertStrictTransportSecurityHeader(httpServletResponse, httpServletRequest, result);
                this.decideInsertXContentTypeOptionsHeader(httpServletResponse, httpServletRequest, result);
                this.decideInsertXFrameOptionsHeader(httpServletResponse, httpServletRequest, result);
                this.decideInsertXSSProtectionHeader(httpServletResponse, httpServletRequest, result);
                this.decideInsertContentSecurityPolicyHeader(httpServletResponse, httpServletRequest, result);
            }
        }
        catch (Exception e) {
            ResponseHeadersEnforcementFilter.logException((Exception)new ServletException(this.getClass().getSimpleName() + " is blocking this request. Examine the cause in this stack trace to understand why.", (Throwable)e));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

    protected Optional<Object> prepareFilterBeforeExecution(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        return Optional.empty();
    }

    protected void decideInsertContentSecurityPolicyHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (this.contentSecurityPolicy == null) {
            return;
        }
        this.insertContentSecurityPolicyHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertContentSecurityPolicyHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertContentSecurityPolicyHeader(httpServletResponse, httpServletRequest, this.contentSecurityPolicy);
    }

    protected void insertContentSecurityPolicyHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String contentSecurityPolicy) {
        String uri = httpServletRequest.getRequestURI();
        httpServletResponse.addHeader("Content-Security-Policy", contentSecurityPolicy);
        LOGGER.trace("Adding Content-Security-Policy response header [{}] for [{}]", (Object)contentSecurityPolicy, (Object)uri);
    }

    protected void decideInsertXSSProtectionHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (!this.enableXSSProtection) {
            return;
        }
        this.insertXSSProtectionHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertXSSProtectionHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertXSSProtectionHeader(httpServletResponse, httpServletRequest, this.xssProtection);
    }

    protected void insertXSSProtectionHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String value) {
        String uri = httpServletRequest.getRequestURI();
        httpServletResponse.addHeader("X-XSS-Protection", value);
        LOGGER.trace("Adding X-XSS Protection [{}] response headers for [{}]", (Object)value, (Object)uri);
    }

    protected void decideInsertXFrameOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (!this.enableXFrameOptions) {
            return;
        }
        this.insertXFrameOptionsHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertXFrameOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertXFrameOptionsHeader(httpServletResponse, httpServletRequest, this.xframeOptions);
    }

    protected void insertXFrameOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String value) {
        String uri = httpServletRequest.getRequestURI();
        httpServletResponse.addHeader("X-Frame-Options", value);
        LOGGER.trace("Adding X-Frame Options [{}] response headers for [{}]", (Object)value, (Object)uri);
    }

    protected void decideInsertXContentTypeOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (!this.enableXContentTypeOptions) {
            return;
        }
        this.insertXContentTypeOptionsHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertXContentTypeOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertXContentTypeOptionsHeader(httpServletResponse, httpServletRequest, this.xContentTypeOptionsHeader);
    }

    protected void insertXContentTypeOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String value) {
        String uri = httpServletRequest.getRequestURI();
        httpServletResponse.addHeader("X-Content-Type-Options", value);
        LOGGER.trace("Adding X-Content Type response headers [{}] for [{}]", (Object)value, (Object)uri);
    }

    protected void decideInsertCacheControlHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (!this.enableCacheControl) {
            return;
        }
        this.insertCacheControlHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertCacheControlHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertCacheControlHeader(httpServletResponse, httpServletRequest, this.cacheControlHeader);
    }

    protected void insertCacheControlHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String value) {
        String uri = httpServletRequest.getRequestURI();
        if (!this.cacheControlStaticResourcesPattern.matcher(uri).matches()) {
            httpServletResponse.addHeader("Cache-Control", value);
            httpServletResponse.addHeader("Pragma", "no-cache");
            httpServletResponse.addIntHeader("Expires", 0);
            LOGGER.trace("Adding Cache Control response headers for [{}]", (Object)uri);
        }
    }

    protected void decideInsertStrictTransportSecurityHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        if (!this.enableStrictTransportSecurity) {
            return;
        }
        this.insertStrictTransportSecurityHeader(httpServletResponse, httpServletRequest);
    }

    protected void insertStrictTransportSecurityHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        this.insertStrictTransportSecurityHeader(httpServletResponse, httpServletRequest, this.strictTransportSecurityHeader);
    }

    protected void insertStrictTransportSecurityHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String strictTransportSecurityHeader) {
        if (httpServletRequest.isSecure()) {
            String uri = httpServletRequest.getRequestURI();
            httpServletResponse.addHeader("Strict-Transport-Security", strictTransportSecurityHeader);
            LOGGER.trace("Adding HSTS response headers for [{}]", (Object)uri);
        }
    }

    @Generated
    public void setCacheControlStaticResourcesPattern(Pattern cacheControlStaticResourcesPattern) {
        this.cacheControlStaticResourcesPattern = cacheControlStaticResourcesPattern;
    }

    @Generated
    public void setEnableCacheControl(boolean enableCacheControl) {
        this.enableCacheControl = enableCacheControl;
    }

    @Generated
    public void setCacheControlHeader(String cacheControlHeader) {
        this.cacheControlHeader = cacheControlHeader;
    }

    @Generated
    public void setEnableXContentTypeOptions(boolean enableXContentTypeOptions) {
        this.enableXContentTypeOptions = enableXContentTypeOptions;
    }

    @Generated
    public void setXContentTypeOptionsHeader(String xContentTypeOptionsHeader) {
        this.xContentTypeOptionsHeader = xContentTypeOptionsHeader;
    }

    @Generated
    public void setEnableStrictTransportSecurity(boolean enableStrictTransportSecurity) {
        this.enableStrictTransportSecurity = enableStrictTransportSecurity;
    }

    @Generated
    public void setStrictTransportSecurityHeader(String strictTransportSecurityHeader) {
        this.strictTransportSecurityHeader = strictTransportSecurityHeader;
    }

    @Generated
    public void setEnableXFrameOptions(boolean enableXFrameOptions) {
        this.enableXFrameOptions = enableXFrameOptions;
    }

    @Generated
    public void setXframeOptions(String xframeOptions) {
        this.xframeOptions = xframeOptions;
    }

    @Generated
    public void setEnableXSSProtection(boolean enableXSSProtection) {
        this.enableXSSProtection = enableXSSProtection;
    }

    @Generated
    public void setXssProtection(String xssProtection) {
        this.xssProtection = xssProtection;
    }

    @Generated
    public void setContentSecurityPolicy(String contentSecurityPolicy) {
        this.contentSecurityPolicy = contentSecurityPolicy;
    }

    @Generated
    public Object getLock() {
        return this.lock;
    }

    @Generated
    public Pattern getCacheControlStaticResourcesPattern() {
        return this.cacheControlStaticResourcesPattern;
    }

    @Generated
    public boolean isEnableCacheControl() {
        return this.enableCacheControl;
    }

    @Generated
    public String getCacheControlHeader() {
        return this.cacheControlHeader;
    }

    @Generated
    public boolean isEnableXContentTypeOptions() {
        return this.enableXContentTypeOptions;
    }

    @Generated
    public String getXContentTypeOptionsHeader() {
        return this.xContentTypeOptionsHeader;
    }

    @Generated
    public boolean isEnableStrictTransportSecurity() {
        return this.enableStrictTransportSecurity;
    }

    @Generated
    public String getStrictTransportSecurityHeader() {
        return this.strictTransportSecurityHeader;
    }

    @Generated
    public boolean isEnableXFrameOptions() {
        return this.enableXFrameOptions;
    }

    @Generated
    public String getXframeOptions() {
        return this.xframeOptions;
    }

    @Generated
    public boolean isEnableXSSProtection() {
        return this.enableXSSProtection;
    }

    @Generated
    public String getXssProtection() {
        return this.xssProtection;
    }

    @Generated
    public String getContentSecurityPolicy() {
        return this.contentSecurityPolicy;
    }
}

