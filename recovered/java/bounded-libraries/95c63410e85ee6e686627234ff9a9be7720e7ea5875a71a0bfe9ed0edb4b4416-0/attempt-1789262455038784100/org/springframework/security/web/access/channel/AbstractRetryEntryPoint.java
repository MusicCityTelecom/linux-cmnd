/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.channel;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.channel.ChannelEntryPoint;
import org.springframework.util.Assert;

public abstract class AbstractRetryEntryPoint
implements ChannelEntryPoint {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private PortMapper portMapper = new PortMapperImpl();
    private PortResolver portResolver = new PortResolverImpl();
    private final String scheme;
    private final int standardPort;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public AbstractRetryEntryPoint(String scheme, int standardPort) {
        this.scheme = scheme;
        this.standardPort = standardPort;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String queryString = request.getQueryString();
        String redirectUrl = request.getRequestURI() + (queryString != null ? "?" + queryString : "");
        Integer currentPort = this.portResolver.getServerPort((ServletRequest)request);
        Integer redirectPort = this.getMappedPort(currentPort);
        if (redirectPort != null) {
            boolean includePort = redirectPort != this.standardPort;
            String port = includePort ? ":" + redirectPort : "";
            redirectUrl = this.scheme + request.getServerName() + port + redirectUrl;
        }
        this.logger.debug((Object)LogMessage.format((String)"Redirecting to: %s", (Object)redirectUrl));
        this.redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    protected abstract Integer getMappedPort(Integer var1);

    protected final PortMapper getPortMapper() {
        return this.portMapper;
    }

    public void setPortMapper(PortMapper portMapper) {
        Assert.notNull((Object)portMapper, (String)"portMapper cannot be null");
        this.portMapper = portMapper;
    }

    public void setPortResolver(PortResolver portResolver) {
        Assert.notNull((Object)portResolver, (String)"portResolver cannot be null");
        this.portResolver = portResolver;
    }

    protected final PortResolver getPortResolver() {
        return this.portResolver;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        Assert.notNull((Object)redirectStrategy, (String)"redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }

    protected final RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }
}

