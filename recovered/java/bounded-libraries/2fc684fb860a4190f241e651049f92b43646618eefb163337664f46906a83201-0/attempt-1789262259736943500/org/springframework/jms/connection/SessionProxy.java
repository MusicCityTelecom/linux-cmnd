/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Session
 */
package org.springframework.jms.connection;

import javax.jms.Session;

public interface SessionProxy
extends Session {
    public Session getTargetSession();
}

