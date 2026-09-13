/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebFilter
 */
package com.tpvision.smartinstall.filter;

import com.tpvision.smartinstall.pms.PmsUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns={"/pmsservice.jsp", "/services/*"})
public class PmsAccessFilter
implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(PmsAccessFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipaddress = PmsUtils.getLimitNetworkIpAddress();
        LOG.info("servername:{},limit ipaddress:{}", (Object)request.getServerName(), (Object)ipaddress);
        if ("Off".equalsIgnoreCase(ipaddress) || ipaddress.equalsIgnoreCase(request.getServerName())) {
            chain.doFilter(request, response);
        }
    }
}

