/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.retry.support.RetryTemplate
 */
package org.springframework.boot.autoconfigure.amqp;

import org.springframework.retry.support.RetryTemplate;

@FunctionalInterface
public interface RabbitRetryTemplateCustomizer {
    public void customize(Target var1, RetryTemplate var2);

    public static enum Target {
        SENDER,
        LISTENER;

    }
}

