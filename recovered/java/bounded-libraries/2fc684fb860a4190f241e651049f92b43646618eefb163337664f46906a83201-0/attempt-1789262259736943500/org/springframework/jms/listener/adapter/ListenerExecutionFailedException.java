/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.listener.adapter;

import org.springframework.jms.JmsException;

public class ListenerExecutionFailedException
extends JmsException {
    public ListenerExecutionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

