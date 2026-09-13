/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  org.springframework.core.NestedRuntimeException
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms;

import javax.jms.JMSException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

public abstract class JmsException
extends NestedRuntimeException {
    public JmsException(String msg) {
        super(msg);
    }

    public JmsException(String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }

    public JmsException(@Nullable Throwable cause) {
        super(cause != null ? cause.getMessage() : null, cause);
    }

    @Nullable
    public String getErrorCode() {
        Throwable cause = this.getCause();
        if (cause instanceof JMSException) {
            return ((JMSException)cause).getErrorCode();
        }
        return null;
    }

    @Nullable
    public String getMessage() {
        Exception linkedEx;
        String message = super.getMessage();
        Throwable cause = this.getCause();
        if (cause instanceof JMSException && (linkedEx = ((JMSException)cause).getLinkedException()) != null) {
            String linkedMessage = linkedEx.getMessage();
            String causeMessage = cause.getMessage();
            if (!(linkedMessage == null || causeMessage != null && causeMessage.contains(linkedMessage))) {
                message = message + "; nested exception is " + linkedEx;
            }
        }
        return message;
    }
}

