/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.destination;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.springframework.jms.support.JmsAccessor;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public abstract class JmsDestinationAccessor
extends JmsAccessor {
    public static final long RECEIVE_TIMEOUT_NO_WAIT = -1L;
    public static final long RECEIVE_TIMEOUT_INDEFINITE_WAIT = 0L;
    private DestinationResolver destinationResolver = new DynamicDestinationResolver();
    private boolean pubSubDomain = false;

    public void setDestinationResolver(DestinationResolver destinationResolver) {
        Assert.notNull((Object)destinationResolver, (String)"'destinationResolver' must not be null");
        this.destinationResolver = destinationResolver;
    }

    public DestinationResolver getDestinationResolver() {
        return this.destinationResolver;
    }

    public void setPubSubDomain(boolean pubSubDomain) {
        this.pubSubDomain = pubSubDomain;
    }

    public boolean isPubSubDomain() {
        return this.pubSubDomain;
    }

    protected Destination resolveDestinationName(Session session, String destinationName) throws JMSException {
        return this.getDestinationResolver().resolveDestinationName(session, destinationName, this.isPubSubDomain());
    }

    @Nullable
    protected Message receiveFromConsumer(MessageConsumer consumer, long timeout) throws JMSException {
        if (timeout > 0L) {
            return consumer.receive(timeout);
        }
        if (timeout < 0L) {
            return consumer.receiveNoWait();
        }
        return consumer.receive();
    }
}

