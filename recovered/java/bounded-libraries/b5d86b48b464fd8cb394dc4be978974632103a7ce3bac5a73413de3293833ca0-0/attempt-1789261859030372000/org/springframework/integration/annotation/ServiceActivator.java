/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Reactive;

@Target(value={ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceActivator {
    public String inputChannel() default "";

    public String outputChannel() default "";

    public String requiresReply() default "";

    public String[] adviceChain() default {};

    public String sendTimeout() default "";

    public String autoStartup() default "";

    public String phase() default "";

    public String async() default "";

    public Poller[] poller() default {};

    public Reactive reactive() default @Reactive(value="\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n");
}

