/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import org.springframework.retry.RetryException;

public class ExhaustedRetryException
extends RetryException {
    public ExhaustedRetryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ExhaustedRetryException(String msg) {
        super(msg);
    }
}

