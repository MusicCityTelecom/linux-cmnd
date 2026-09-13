/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.XAConnectionFactory
 */
package org.springframework.boot.jms;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;

@FunctionalInterface
public interface XAConnectionFactoryWrapper {
    public ConnectionFactory wrapConnectionFactory(XAConnectionFactory var1) throws Exception;
}

