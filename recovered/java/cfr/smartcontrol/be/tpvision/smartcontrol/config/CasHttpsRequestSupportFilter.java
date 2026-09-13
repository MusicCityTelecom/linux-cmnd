/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

public class CasHttpsRequestSupportFilter
extends GenericFilterBean {
    private ServiceProperties serviceProperties;
    private CasAuthenticationEntryPoint casAuthenticationEntryPoint;

    public CasHttpsRequestSupportFilter(ServiceProperties serviceProperties, CasAuthenticationEntryPoint casAuthenticationEntryPoint) {
        this.serviceProperties = serviceProperties;
        this.casAuthenticationEntryPoint = casAuthenticationEntryPoint;
    }

    private String getServerUrlByRequest(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    private String fixDomainUrl(String requestDomainAddress, String fixUrl) {
        if (fixUrl.startsWith(requestDomainAddress)) {
            return fixUrl;
        }
        if (fixUrl.startsWith("http")) {
            int singleSlashIndex = StringUtils.indexOf((CharSequence)fixUrl, "/", StringUtils.indexOf((CharSequence)fixUrl, "//") + "//".length());
            if (singleSlashIndex == -1) {
                return requestDomainAddress;
            }
            return requestDomainAddress + fixUrl.substring(singleSlashIndex);
        }
        return requestDomainAddress + fixUrl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestDomainAdress = this.getServerUrlByRequest(httpRequest);
        this.serviceProperties.setService(this.fixDomainUrl(requestDomainAdress, this.serviceProperties.getService()));
        this.casAuthenticationEntryPoint.setLoginUrl(this.fixDomainUrl(requestDomainAdress, this.casAuthenticationEntryPoint.getLoginUrl()));
        chain.doFilter(request, response);
    }
}

