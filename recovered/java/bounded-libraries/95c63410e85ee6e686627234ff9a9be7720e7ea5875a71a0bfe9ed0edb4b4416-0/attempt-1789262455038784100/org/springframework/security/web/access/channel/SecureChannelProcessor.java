/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.security.access.ConfigAttribute
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.ChannelEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.security.web.access.channel.RetryWithHttpsEntryPoint;
import org.springframework.util.Assert;

public class SecureChannelProcessor
implements InitializingBean,
ChannelProcessor {
    private ChannelEntryPoint entryPoint = new RetryWithHttpsEntryPoint();
    private String secureKeyword = "REQUIRES_SECURE_CHANNEL";

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.secureKeyword, (String)"secureKeyword required");
        Assert.notNull((Object)this.entryPoint, (String)"entryPoint required");
    }

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        Assert.isTrue((invocation != null && config != null ? 1 : 0) != 0, (String)"Nulls cannot be provided");
        for (ConfigAttribute attribute : config) {
            if (!this.supports(attribute) || invocation.getHttpRequest().isSecure()) continue;
            this.entryPoint.commence(invocation.getRequest(), invocation.getResponse());
        }
    }

    public ChannelEntryPoint getEntryPoint() {
        return this.entryPoint;
    }

    public String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setEntryPoint(ChannelEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public void setSecureKeyword(String secureKeyword) {
        this.secureKeyword = secureKeyword;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute != null && attribute.getAttribute() != null && attribute.getAttribute().equals(this.getSecureKeyword());
    }
}

