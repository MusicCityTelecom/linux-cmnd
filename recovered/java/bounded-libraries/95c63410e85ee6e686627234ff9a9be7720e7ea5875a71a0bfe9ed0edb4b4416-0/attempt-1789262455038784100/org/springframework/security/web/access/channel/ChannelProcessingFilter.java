/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.access.ConfigAttribute
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.ChannelDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class ChannelProcessingFilter
extends GenericFilterBean {
    private ChannelDecisionManager channelDecisionManager;
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.securityMetadataSource, (String)"securityMetadataSource must be specified");
        Assert.notNull((Object)this.channelDecisionManager, (String)"channelDecisionManager must be specified");
        Collection attributes = this.securityMetadataSource.getAllConfigAttributes();
        if (attributes == null) {
            this.logger.warn((Object)"Could not validate configuration attributes as the FilterInvocationSecurityMetadataSource did not return any attributes");
            return;
        }
        Set<ConfigAttribute> unsupportedAttributes = this.getUnsupportedAttributes(attributes);
        Assert.isTrue((boolean)unsupportedAttributes.isEmpty(), () -> "Unsupported configuration attributes: " + unsupportedAttributes);
        this.logger.info((Object)"Validated configuration attributes");
    }

    private Set<ConfigAttribute> getUnsupportedAttributes(Collection<ConfigAttribute> attrDefs) {
        HashSet<ConfigAttribute> unsupportedAttributes = new HashSet<ConfigAttribute>();
        for (ConfigAttribute attr : attrDefs) {
            if (this.channelDecisionManager.supports(attr)) continue;
            unsupportedAttributes.add(attr);
        }
        return unsupportedAttributes;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        FilterInvocation filterInvocation = new FilterInvocation((ServletRequest)request, (ServletResponse)response, chain);
        Collection attributes = this.securityMetadataSource.getAttributes(filterInvocation);
        if (attributes != null) {
            this.logger.debug((Object)LogMessage.format((String)"Request: %s; ConfigAttributes: %s", (Object)filterInvocation, (Object)attributes));
            this.channelDecisionManager.decide(filterInvocation, attributes);
            if (filterInvocation.getResponse().isCommitted()) {
                return;
            }
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    protected ChannelDecisionManager getChannelDecisionManager() {
        return this.channelDecisionManager;
    }

    protected FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setChannelDecisionManager(ChannelDecisionManager channelDecisionManager) {
        this.channelDecisionManager = channelDecisionManager;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        this.securityMetadataSource = filterInvocationSecurityMetadataSource;
    }
}

