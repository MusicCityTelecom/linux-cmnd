/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultRequestRejectedHandler;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class FilterChainProxy
extends GenericFilterBean {
    private static final Log logger = LogFactory.getLog(FilterChainProxy.class);
    private static final String FILTER_APPLIED = FilterChainProxy.class.getName().concat(".APPLIED");
    private List<SecurityFilterChain> filterChains;
    private FilterChainValidator filterChainValidator = new NullFilterChainValidator();
    private HttpFirewall firewall = new StrictHttpFirewall();
    private RequestRejectedHandler requestRejectedHandler = new DefaultRequestRejectedHandler();
    private ThrowableAnalyzer throwableAnalyzer = new ThrowableAnalyzer();

    public FilterChainProxy() {
    }

    public FilterChainProxy(SecurityFilterChain chain) {
        this(Arrays.asList(chain));
    }

    public FilterChainProxy(List<SecurityFilterChain> filterChains) {
        this.filterChains = filterChains;
    }

    public void afterPropertiesSet() {
        this.filterChainValidator.validate(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean clearContext;
        boolean bl = clearContext = request.getAttribute(FILTER_APPLIED) == null;
        if (!clearContext) {
            this.doFilterInternal(request, response, chain);
            return;
        }
        try {
            request.setAttribute(FILTER_APPLIED, (Object)Boolean.TRUE);
            this.doFilterInternal(request, response, chain);
        }
        catch (Exception ex) {
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
            Throwable requestRejectedException = this.throwableAnalyzer.getFirstThrowableOfType(RequestRejectedException.class, causeChain);
            if (!(requestRejectedException instanceof RequestRejectedException)) {
                throw ex;
            }
            this.requestRejectedHandler.handle((HttpServletRequest)request, (HttpServletResponse)response, (RequestRejectedException)requestRejectedException);
        }
        finally {
            SecurityContextHolder.clearContext();
            request.removeAttribute(FILTER_APPLIED);
        }
    }

    private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FirewalledRequest firewallRequest = this.firewall.getFirewalledRequest((HttpServletRequest)request);
        HttpServletResponse firewallResponse = this.firewall.getFirewalledResponse((HttpServletResponse)response);
        List<Filter> filters = this.getFilters((HttpServletRequest)firewallRequest);
        if (filters == null || filters.size() == 0) {
            if (logger.isTraceEnabled()) {
                logger.trace((Object)LogMessage.of(() -> "No security for " + FilterChainProxy.requestLine((HttpServletRequest)firewallRequest)));
            }
            firewallRequest.reset();
            chain.doFilter((ServletRequest)firewallRequest, (ServletResponse)firewallResponse);
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug((Object)LogMessage.of(() -> "Securing " + FilterChainProxy.requestLine((HttpServletRequest)firewallRequest)));
        }
        VirtualFilterChain virtualFilterChain = new VirtualFilterChain(firewallRequest, chain, filters);
        virtualFilterChain.doFilter((ServletRequest)firewallRequest, (ServletResponse)firewallResponse);
    }

    private List<Filter> getFilters(HttpServletRequest request) {
        int count = 0;
        for (SecurityFilterChain chain : this.filterChains) {
            if (logger.isTraceEnabled()) {
                logger.trace((Object)LogMessage.format((String)"Trying to match request against %s (%d/%d)", (Object)chain, (Object)(++count), (Object)this.filterChains.size()));
            }
            if (!chain.matches(request)) continue;
            return chain.getFilters();
        }
        return null;
    }

    public List<Filter> getFilters(String url) {
        return this.getFilters((HttpServletRequest)this.firewall.getFirewalledRequest(new FilterInvocation(url, "GET").getRequest()));
    }

    public List<SecurityFilterChain> getFilterChains() {
        return Collections.unmodifiableList(this.filterChains);
    }

    public void setFilterChainValidator(FilterChainValidator filterChainValidator) {
        this.filterChainValidator = filterChainValidator;
    }

    public void setFirewall(HttpFirewall firewall) {
        this.firewall = firewall;
    }

    public void setRequestRejectedHandler(RequestRejectedHandler requestRejectedHandler) {
        Assert.notNull((Object)requestRejectedHandler, (String)"requestRejectedHandler may not be null");
        this.requestRejectedHandler = requestRejectedHandler;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FilterChainProxy[");
        sb.append("Filter Chains: ");
        sb.append(this.filterChains);
        sb.append("]");
        return sb.toString();
    }

    private static String requestLine(HttpServletRequest request) {
        return request.getMethod() + " " + UrlUtils.buildRequestUrl(request);
    }

    private static class NullFilterChainValidator
    implements FilterChainValidator {
        private NullFilterChainValidator() {
        }

        @Override
        public void validate(FilterChainProxy filterChainProxy) {
        }
    }

    public static interface FilterChainValidator {
        public void validate(FilterChainProxy var1);
    }

    private static final class VirtualFilterChain
    implements FilterChain {
        private final FilterChain originalChain;
        private final List<Filter> additionalFilters;
        private final FirewalledRequest firewalledRequest;
        private final int size;
        private int currentPosition = 0;

        private VirtualFilterChain(FirewalledRequest firewalledRequest, FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
            this.firewalledRequest = firewalledRequest;
        }

        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (this.currentPosition == this.size) {
                if (logger.isDebugEnabled()) {
                    logger.debug((Object)LogMessage.of(() -> "Secured " + FilterChainProxy.requestLine((HttpServletRequest)this.firewalledRequest)));
                }
                this.firewalledRequest.reset();
                this.originalChain.doFilter(request, response);
                return;
            }
            ++this.currentPosition;
            Filter nextFilter = this.additionalFilters.get(this.currentPosition - 1);
            if (logger.isTraceEnabled()) {
                logger.trace((Object)LogMessage.format((String)"Invoking %s (%d/%d)", (Object)nextFilter.getClass().getSimpleName(), (Object)this.currentPosition, (Object)this.size));
            }
            nextFilter.doFilter(request, response, (FilterChain)this);
        }
    }
}

