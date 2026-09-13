/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry;

import org.springframework.retry.RetryException;

public class TerminatedRetryException
extends RetryException {
    public TerminatedRetryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TerminatedRetryException(String msg) {
        super(msg);
    }
}

