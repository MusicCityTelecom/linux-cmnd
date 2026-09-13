/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.handler.annotation.MessageMapping
 */
package org.springframework.jms.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Target(value={ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Repeatable(value=JmsListeners.class)
@MessageMapping
public @interface JmsListener {
    public String id() default "";

    public String containerFactory() default "";

    public String destination();

    public String subscription() default "";

    public String selector() default "";

    public String concurrency() default "";
}

