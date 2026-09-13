/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.handler;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

public interface MessagingAdviceBean
extends Ordered {
    @Nullable
    public Class<?> getBeanType();

    public Object resolveBean();

    public boolean isApplicableToBeanType(Class<?> var1);
}

