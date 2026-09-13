/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Poller {
    public String value() default "";

    public String trigger() default "";

    public String taskExecutor() default "";

    public String maxMessagesPerPoll() default "";

    public String fixedDelay() default "";

    public String fixedRate() default "";

    public String cron() default "";

    public String errorChannel() default "";

    public String receiveTimeout() default "";
}

