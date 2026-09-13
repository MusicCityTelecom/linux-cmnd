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
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.web.support.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.web.support.filters.AbstractSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddResponseHeadersFilter
extends AbstractSecurityFilter
implements Filter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AddResponseHeadersFilter.class);
    private Map<String, String> headersMap = new LinkedHashMap<String, String>();

    public void init(FilterConfig filterConfig) {
        Enumeration initParamNames = filterConfig.getInitParameterNames();
        while (initParamNames.hasMoreElements()) {
            String paramName = (String)initParamNames.nextElement();
            String paramValue = filterConfig.getInitParameter(paramName);
            this.headersMap.put(paramName, paramValue);
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            for (Map.Entry<String, String> entry : this.headersMap.entrySet()) {
                LOGGER.debug("Adding parameter [{}] with value [{}]", (Object)entry.getKey(), (Object)entry.getValue());
                httpServletResponse.addHeader(entry.getKey(), entry.getValue());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

    @Generated
    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    @Generated
    public Map<String, String> getHeadersMap() {
        return this.headersMap;
    }
}

