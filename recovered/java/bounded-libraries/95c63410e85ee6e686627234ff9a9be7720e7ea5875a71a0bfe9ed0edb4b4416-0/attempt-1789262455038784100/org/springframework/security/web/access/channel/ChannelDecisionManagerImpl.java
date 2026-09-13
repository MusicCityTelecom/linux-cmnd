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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.ChannelDecisionManager;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.util.Assert;

public class ChannelDecisionManagerImpl
implements ChannelDecisionManager,
InitializingBean {
    public static final String ANY_CHANNEL = "ANY_CHANNEL";
    private List<ChannelProcessor> channelProcessors;

    public void afterPropertiesSet() {
        Assert.notEmpty(this.channelProcessors, (String)"A list of ChannelProcessors is required");
    }

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        for (ConfigAttribute attribute : config) {
            if (!ANY_CHANNEL.equals(attribute.getAttribute())) continue;
            return;
        }
        for (ChannelProcessor processor : this.channelProcessors) {
            processor.decide(invocation, config);
            if (!invocation.getResponse().isCommitted()) continue;
            break;
        }
    }

    protected List<ChannelProcessor> getChannelProcessors() {
        return this.channelProcessors;
    }

    public void setChannelProcessors(List<?> channelProcessors) {
        Assert.notEmpty(channelProcessors, (String)"A list of ChannelProcessors is required");
        this.channelProcessors = new ArrayList<ChannelProcessor>(channelProcessors.size());
        for (Object currentObject : channelProcessors) {
            Assert.isInstanceOf(ChannelProcessor.class, currentObject, () -> "ChannelProcessor " + currentObject.getClass().getName() + " must implement ChannelProcessor");
            this.channelProcessors.add((ChannelProcessor)currentObject);
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if (ANY_CHANNEL.equals(attribute.getAttribute())) {
            return true;
        }
        for (ChannelProcessor processor : this.channelProcessors) {
            if (!processor.supports(attribute)) continue;
            return true;
        }
        return false;
    }
}

