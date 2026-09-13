/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.support.InterceptableChannel
 */
package org.springframework.integration.channel.interceptor;

import org.springframework.messaging.support.InterceptableChannel;

public interface VetoCapableInterceptor {
    public boolean shouldIntercept(String var1, InterceptableChannel var2);
}

