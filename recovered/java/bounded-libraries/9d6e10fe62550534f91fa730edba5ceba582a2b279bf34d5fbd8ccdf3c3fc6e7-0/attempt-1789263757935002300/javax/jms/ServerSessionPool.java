/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.ServerSession;

public interface ServerSessionPool {
    public ServerSession getServerSession() throws JMSException;
}

