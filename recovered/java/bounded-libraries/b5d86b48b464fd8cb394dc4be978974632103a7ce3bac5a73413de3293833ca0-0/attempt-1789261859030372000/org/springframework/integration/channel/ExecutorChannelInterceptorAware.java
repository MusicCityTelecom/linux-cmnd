/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.support.InterceptableChannel
 */
package org.springframework.integration.channel;

import org.springframework.messaging.support.InterceptableChannel;

public interface ExecutorChannelInterceptorAware
extends InterceptableChannel {
    public boolean hasExecutorInterceptors();
}

