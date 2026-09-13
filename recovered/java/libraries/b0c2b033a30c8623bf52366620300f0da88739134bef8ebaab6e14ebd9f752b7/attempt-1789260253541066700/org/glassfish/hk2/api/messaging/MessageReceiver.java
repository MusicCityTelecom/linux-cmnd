/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api.messaging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.glassfish.hk2.api.Metadata;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Qualifier
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface MessageReceiver {
    public static final String EVENT_RECEIVER_TYPES = "org.glassfish.hk2.messaging.messageReceiverTypes";

    @Metadata(value="org.glassfish.hk2.messaging.messageReceiverTypes")
    public Class<?>[] value() default {};
}

