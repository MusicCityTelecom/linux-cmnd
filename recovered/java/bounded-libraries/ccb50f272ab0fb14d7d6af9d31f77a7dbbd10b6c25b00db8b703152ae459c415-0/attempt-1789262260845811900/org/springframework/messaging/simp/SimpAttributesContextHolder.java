/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.NamedThreadLocal
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp;

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpAttributes;

public abstract class SimpAttributesContextHolder {
    private static final ThreadLocal<SimpAttributes> attributesHolder = new NamedThreadLocal("SiMP session attributes");

    public static void resetAttributes() {
        attributesHolder.remove();
    }

    public static void setAttributes(@Nullable SimpAttributes attributes) {
        if (attributes != null) {
            attributesHolder.set(attributes);
        } else {
            SimpAttributesContextHolder.resetAttributes();
        }
    }

    public static void setAttributesFromMessage(Message<?> message) {
        SimpAttributesContextHolder.setAttributes(SimpAttributes.fromMessage(message));
    }

    @Nullable
    public static SimpAttributes getAttributes() {
        return attributesHolder.get();
    }

    public static SimpAttributes currentAttributes() throws IllegalStateException {
        SimpAttributes attributes = SimpAttributesContextHolder.getAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No thread-bound SimpAttributes found. Your code is probably not processing a client message and executing in message-handling methods invoked by the SimpAnnotationMethodMessageHandler?");
        }
        return attributes;
    }
}

