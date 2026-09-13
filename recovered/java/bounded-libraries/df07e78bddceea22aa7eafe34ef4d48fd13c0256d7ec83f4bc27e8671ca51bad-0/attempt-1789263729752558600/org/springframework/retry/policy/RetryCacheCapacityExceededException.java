/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryException;

public class RetryCacheCapacityExceededException
extends RetryException {
    public RetryCacheCapacityExceededException(String message) {
        super(message);
    }

    public RetryCacheCapacityExceededException(String msg, Throwable nested) {
        super(msg, nested);
    }
}

