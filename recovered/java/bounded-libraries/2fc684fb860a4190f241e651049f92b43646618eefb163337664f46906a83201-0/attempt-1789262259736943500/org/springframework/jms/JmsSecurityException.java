/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSSecurityException
 */
package org.springframework.jms;

import javax.jms.JMSSecurityException;
import org.springframework.jms.JmsException;

public class JmsSecurityException
extends JmsException {
    public JmsSecurityException(JMSSecurityException cause) {
        super((Throwable)cause);
    }
}

