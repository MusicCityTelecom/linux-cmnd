/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.listener.adapter;

import org.springframework.jms.JmsException;

public class ReplyFailureException
extends JmsException {
    public ReplyFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

