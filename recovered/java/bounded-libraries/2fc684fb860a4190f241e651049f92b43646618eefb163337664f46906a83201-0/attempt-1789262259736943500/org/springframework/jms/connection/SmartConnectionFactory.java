/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 */
package org.springframework.jms.connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;

public interface SmartConnectionFactory
extends ConnectionFactory {
    public boolean shouldStop(Connection var1);
}

