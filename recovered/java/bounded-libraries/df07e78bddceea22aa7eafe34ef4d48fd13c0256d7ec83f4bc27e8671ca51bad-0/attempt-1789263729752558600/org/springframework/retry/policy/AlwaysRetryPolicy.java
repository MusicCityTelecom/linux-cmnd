/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.NeverRetryPolicy;

public class AlwaysRetryPolicy
extends NeverRetryPolicy {
    @Override
    public boolean canRetry(RetryContext context) {
        return true;
    }
}

