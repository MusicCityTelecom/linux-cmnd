/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.NestedRuntimeException
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.core.NestedRuntimeException;

public class StompConversionException
extends NestedRuntimeException {
    public StompConversionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public StompConversionException(String msg) {
        super(msg);
    }
}

