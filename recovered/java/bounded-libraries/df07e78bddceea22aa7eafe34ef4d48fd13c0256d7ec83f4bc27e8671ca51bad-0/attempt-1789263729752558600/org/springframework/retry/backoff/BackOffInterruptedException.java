/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.RetryException;

public class BackOffInterruptedException
extends RetryException {
    public BackOffInterruptedException(String msg) {
        super(msg);
    }

    public BackOffInterruptedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

