/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.NestedRuntimeException
 */
package org.springframework.retry;

import org.springframework.core.NestedRuntimeException;

public class RetryException
extends NestedRuntimeException {
    public RetryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RetryException(String msg) {
        super(msg);
    }
}

