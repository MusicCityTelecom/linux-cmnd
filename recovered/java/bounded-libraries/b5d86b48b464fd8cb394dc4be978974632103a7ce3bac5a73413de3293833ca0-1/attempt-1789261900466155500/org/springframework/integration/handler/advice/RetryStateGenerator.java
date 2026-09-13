/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.retry.RetryState
 */
package org.springframework.integration.handler.advice;

import org.springframework.messaging.Message;
import org.springframework.retry.RetryState;

@FunctionalInterface
public interface RetryStateGenerator {
    public RetryState determineRetryState(Message<?> var1);
}

