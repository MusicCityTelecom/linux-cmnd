/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener.adapter;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JmsResponse<T> {
    private final T response;
    private final Object destination;

    protected JmsResponse(T response, Object destination) {
        Assert.notNull(response, (String)"Result must not be null");
        this.response = response;
        this.destination = destination;
    }

    public T getResponse() {
        return this.response;
    }

    @Nullable
    public Destination resolveDestination(DestinationResolver destinationResolver, Session session) throws JMSException {
        if (this.destination instanceof Destination) {
            return (Destination)this.destination;
        }
        if (this.destination instanceof DestinationNameHolder) {
            DestinationNameHolder nameHolder = (DestinationNameHolder)this.destination;
            return destinationResolver.resolveDestinationName(session, nameHolder.destinationName, nameHolder.pubSubDomain);
        }
        return null;
    }

    public String toString() {
        return "JmsResponse [response=" + this.response + ", destination=" + this.destination + ']';
    }

    public static <T> JmsResponse<T> forQueue(T result, String queueName) {
        Assert.notNull((Object)queueName, (String)"Queue name must not be null");
        return new JmsResponse<T>(result, new DestinationNameHolder(queueName, false));
    }

    public static <T> JmsResponse<T> forTopic(T result, String topicName) {
        Assert.notNull((Object)topicName, (String)"Topic name must not be null");
        return new JmsResponse<T>(result, new DestinationNameHolder(topicName, true));
    }

    public static <T> JmsResponse<T> forDestination(T result, Destination destination) {
        Assert.notNull((Object)destination, (String)"Destination must not be null");
        return new JmsResponse<T>(result, destination);
    }

    private static class DestinationNameHolder {
        private final String destinationName;
        private final boolean pubSubDomain;

        public DestinationNameHolder(String destinationName, boolean pubSubDomain) {
            this.destinationName = destinationName;
            this.pubSubDomain = pubSubDomain;
        }

        public String toString() {
            return this.destinationName + "{pubSubDomain=" + this.pubSubDomain + '}';
        }
    }
}

