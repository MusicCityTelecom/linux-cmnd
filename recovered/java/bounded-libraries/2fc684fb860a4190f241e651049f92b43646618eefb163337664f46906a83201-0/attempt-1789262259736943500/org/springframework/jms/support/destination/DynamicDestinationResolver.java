/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Queue
 *  javax.jms.Session
 *  javax.jms.Topic
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support.destination;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class DynamicDestinationResolver
implements DestinationResolver {
    @Override
    public Destination resolveDestinationName(@Nullable Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        Assert.notNull((Object)session, (String)"Session must not be null");
        Assert.notNull((Object)destinationName, (String)"Destination name must not be null");
        if (pubSubDomain) {
            return this.resolveTopic(session, destinationName);
        }
        return this.resolveQueue(session, destinationName);
    }

    protected Topic resolveTopic(Session session, String topicName) throws JMSException {
        return session.createTopic(topicName);
    }

    protected Queue resolveQueue(Session session, String queueName) throws JMSException {
        return session.createQueue(queueName);
    }
}

