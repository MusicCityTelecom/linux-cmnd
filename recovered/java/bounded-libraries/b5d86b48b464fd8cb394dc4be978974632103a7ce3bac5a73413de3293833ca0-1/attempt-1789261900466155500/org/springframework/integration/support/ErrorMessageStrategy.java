/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.support.ErrorMessage
 */
package org.springframework.integration.support;

import org.springframework.core.AttributeAccessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.support.ErrorMessage;

@FunctionalInterface
public interface ErrorMessageStrategy {
    public ErrorMessage buildErrorMessage(Throwable var1, @Nullable AttributeAccessor var2);
}

