/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.support;

import java.util.List;
import org.springframework.messaging.support.ChannelInterceptor;

public interface InterceptableChannel {
    public void setInterceptors(List<ChannelInterceptor> var1);

    public void addInterceptor(ChannelInterceptor var1);

    public void addInterceptor(int var1, ChannelInterceptor var2);

    public List<ChannelInterceptor> getInterceptors();

    public boolean removeInterceptor(ChannelInterceptor var1);

    public ChannelInterceptor removeInterceptor(int var1);
}

