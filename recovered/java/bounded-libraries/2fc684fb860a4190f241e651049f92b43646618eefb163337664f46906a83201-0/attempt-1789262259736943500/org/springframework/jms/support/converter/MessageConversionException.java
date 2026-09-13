/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.support.converter;

import org.springframework.jms.JmsException;
import org.springframework.lang.Nullable;

public class MessageConversionException
extends JmsException {
    public MessageConversionException(String msg) {
        super(msg);
    }

    public MessageConversionException(String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}

