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
 */
package org.apereo.inspektr.common.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;

public class ClientInfoThreadLocalFilter
implements Filter {
    public static final String CONST_IP_ADDRESS_HEADER = "alternativeIpAddressHeader";
    public static final String CONST_SERVER_IP_ADDRESS_HEADER = "alternateServerAddrHeaderName";
    public static final String CONST_USE_SERVER_HOST_ADDRESS = "useServerHostAddress";
    private String alternateLocalAddrHeaderName;
    private boolean useServerHostAddress;
    private String alternateServerAddrHeaderName;

    public void destroy() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            ClientInfo clientInfo = new ClientInfo((HttpServletRequest)request, this.alternateServerAddrHeaderName, this.alternateLocalAddrHeaderName, this.useServerHostAddress);
            ClientInfoHolder.setClientInfo(clientInfo);
            filterChain.doFilter(request, response);
        }
        finally {
            ClientInfoHolder.clear();
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.alternateLocalAddrHeaderName = filterConfig.getInitParameter(CONST_IP_ADDRESS_HEADER);
        this.alternateServerAddrHeaderName = filterConfig.getInitParameter(CONST_SERVER_IP_ADDRESS_HEADER);
        String useServerHostAddr = filterConfig.getInitParameter(CONST_USE_SERVER_HOST_ADDRESS);
        if (useServerHostAddr != null && !useServerHostAddr.isEmpty()) {
            this.useServerHostAddress = Boolean.valueOf(useServerHostAddr);
        }
    }
}

