/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.channel.interceptor;

import java.util.Arrays;
import org.springframework.core.Ordered;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.Assert;

public class GlobalChannelInterceptorWrapper
implements Ordered {
    private final ChannelInterceptor channelInterceptor;
    private volatile String[] patterns = new String[]{"*"};
    private volatile int order = 0;

    public GlobalChannelInterceptorWrapper(ChannelInterceptor channelInterceptor) {
        Assert.notNull((Object)channelInterceptor, (String)"channelInterceptor must not be null");
        this.channelInterceptor = channelInterceptor;
        if (channelInterceptor instanceof Ordered) {
            this.order = ((Ordered)channelInterceptor).getOrder();
        }
    }

    public ChannelInterceptor getChannelInterceptor() {
        return this.channelInterceptor;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public final int getOrder() {
        return this.order;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = Arrays.copyOf(patterns, patterns.length);
    }

    public String[] getPatterns() {
        return this.patterns;
    }

    public String toString() {
        return this.channelInterceptor.toString();
    }
}

