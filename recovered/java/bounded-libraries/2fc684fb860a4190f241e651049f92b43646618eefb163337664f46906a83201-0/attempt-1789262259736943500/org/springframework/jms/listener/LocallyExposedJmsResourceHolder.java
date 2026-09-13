/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Session
 */
package org.springframework.jms.listener;

import javax.jms.Session;
import org.springframework.jms.connection.JmsResourceHolder;

class LocallyExposedJmsResourceHolder
extends JmsResourceHolder {
    public LocallyExposedJmsResourceHolder(Session session) {
        super(session);
    }
}

