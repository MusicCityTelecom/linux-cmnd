/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class UncategorizedJmsException
extends JmsException {
    public UncategorizedJmsException(String msg) {
        super(msg);
    }

    public UncategorizedJmsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UncategorizedJmsException(Throwable cause) {
        super("Uncategorized exception occurred during JMS processing", cause);
    }
}

