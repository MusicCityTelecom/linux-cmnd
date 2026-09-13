/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageProducer
 *  javax.jms.QueueSession
 *  javax.jms.Session
 *  javax.jms.TemporaryQueue
 *  javax.jms.TemporaryTopic
 *  javax.jms.Topic
 *  javax.jms.TopicSession
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.jms.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicSession;
import org.springframework.jms.connection.CachedMessageConsumer;
import org.springframework.jms.connection.CachedMessageProducer;
import org.springframework.jms.connection.SessionProxy;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

public class CachingConnectionFactory
extends SingleConnectionFactory {
    private int sessionCacheSize = 1;
    private boolean cacheProducers = true;
    private boolean cacheConsumers = true;
    private volatile boolean active = true;
    private final ConcurrentMap<Integer, Deque<Session>> cachedSessions = new ConcurrentHashMap<Integer, Deque<Session>>();

    public CachingConnectionFactory() {
        this.setReconnectOnException(true);
    }

    public CachingConnectionFactory(ConnectionFactory targetConnectionFactory) {
        super(targetConnectionFactory);
        this.setReconnectOnException(true);
    }

    public void setSessionCacheSize(int sessionCacheSize) {
        Assert.isTrue((sessionCacheSize >= 1 ? 1 : 0) != 0, (String)"Session cache size must be 1 or higher");
        this.sessionCacheSize = sessionCacheSize;
    }

    public int getSessionCacheSize() {
        return this.sessionCacheSize;
    }

    public void setCacheProducers(boolean cacheProducers) {
        this.cacheProducers = cacheProducers;
    }

    public boolean isCacheProducers() {
        return this.cacheProducers;
    }

    public void setCacheConsumers(boolean cacheConsumers) {
        this.cacheConsumers = cacheConsumers;
    }

    public boolean isCacheConsumers() {
        return this.cacheConsumers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getCachedSessionCount() {
        int count = 0;
        ConcurrentMap<Integer, Deque<Session>> concurrentMap = this.cachedSessions;
        synchronized (concurrentMap) {
            Iterator iterator = this.cachedSessions.values().iterator();
            while (iterator.hasNext()) {
                Deque sessionList;
                Deque deque = sessionList = (Deque)iterator.next();
                synchronized (deque) {
                    count += sessionList.size();
                }
            }
        }
        return count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resetConnection() {
        this.active = false;
        ConcurrentMap<Integer, Deque<Session>> concurrentMap = this.cachedSessions;
        synchronized (concurrentMap) {
            Iterator iterator = this.cachedSessions.values().iterator();
            while (iterator.hasNext()) {
                Deque sessionList;
                Deque deque = sessionList = (Deque)iterator.next();
                synchronized (deque) {
                    for (Session session : sessionList) {
                        try {
                            session.close();
                        }
                        catch (Throwable ex) {
                            this.logger.trace((Object)"Could not close cached JMS Session", ex);
                        }
                    }
                }
            }
            this.cachedSessions.clear();
        }
        super.resetConnection();
        this.active = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Session getSession(Connection con, Integer mode) throws JMSException {
        if (!this.active) {
            return null;
        }
        Deque sessionList = this.cachedSessions.computeIfAbsent(mode, k -> new ArrayDeque());
        Session session = null;
        Deque deque = sessionList;
        synchronized (deque) {
            if (!sessionList.isEmpty()) {
                session = (Session)sessionList.removeFirst();
            }
        }
        if (session != null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)("Found cached JMS Session for mode " + mode + ": " + (session instanceof SessionProxy ? ((SessionProxy)session).getTargetSession() : session)));
            }
        } else {
            Session targetSession = this.createSession(con, mode);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Registering cached JMS Session for mode " + mode + ": " + targetSession));
            }
            session = this.getCachedSessionProxy(targetSession, sessionList);
        }
        return session;
    }

    protected Session getCachedSessionProxy(Session target, Deque<Session> sessionList) {
        ArrayList<Class<TopicSession>> classes = new ArrayList<Class<TopicSession>>(3);
        classes.add(SessionProxy.class);
        if (target instanceof QueueSession) {
            classes.add(QueueSession.class);
        }
        if (target instanceof TopicSession) {
            classes.add(TopicSession.class);
        }
        return (Session)Proxy.newProxyInstance(SessionProxy.class.getClassLoader(), ClassUtils.toClassArray(classes), (InvocationHandler)new CachedSessionInvocationHandler(target, sessionList));
    }

    private static class ConsumerCacheKey
    extends DestinationCacheKey {
        @Nullable
        private final String selector;
        @Nullable
        private final Boolean noLocal;
        @Nullable
        private final String subscription;
        private final boolean durable;

        public ConsumerCacheKey(Destination destination, @Nullable String selector, @Nullable Boolean noLocal, @Nullable String subscription, boolean durable) {
            super(destination);
            this.selector = selector;
            this.noLocal = noLocal;
            this.subscription = subscription;
            this.durable = durable;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ConsumerCacheKey)) {
                return false;
            }
            ConsumerCacheKey otherKey = (ConsumerCacheKey)other;
            return this.destinationEquals(otherKey) && ObjectUtils.nullSafeEquals((Object)this.selector, (Object)otherKey.selector) && ObjectUtils.nullSafeEquals((Object)this.noLocal, (Object)otherKey.noLocal) && ObjectUtils.nullSafeEquals((Object)this.subscription, (Object)otherKey.subscription) && this.durable == otherKey.durable;
        }

        @Override
        public int hashCode() {
            return 31 * super.hashCode() + ObjectUtils.nullSafeHashCode((Object)this.selector);
        }

        @Override
        public String toString() {
            return super.toString() + " [selector=" + this.selector + ", noLocal=" + this.noLocal + ", subscription=" + this.subscription + ", durable=" + this.durable + "]";
        }
    }

    private static class DestinationCacheKey
    implements Comparable<DestinationCacheKey> {
        private final Destination destination;
        @Nullable
        private String destinationString;

        public DestinationCacheKey(Destination destination) {
            Assert.notNull((Object)destination, (String)"Destination must not be null");
            this.destination = destination;
        }

        private String getDestinationString() {
            if (this.destinationString == null) {
                this.destinationString = this.destination.toString();
            }
            return this.destinationString;
        }

        protected boolean destinationEquals(DestinationCacheKey otherKey) {
            return this.destination.getClass() == otherKey.destination.getClass() && (this.destination.equals(otherKey.destination) || this.getDestinationString().equals(otherKey.getDestinationString()));
        }

        public boolean equals(@Nullable Object other) {
            return this == other || other instanceof DestinationCacheKey && this.destinationEquals((DestinationCacheKey)other);
        }

        public int hashCode() {
            return this.destination.getClass().hashCode();
        }

        public String toString() {
            return this.getDestinationString();
        }

        @Override
        public int compareTo(DestinationCacheKey other) {
            return this.getDestinationString().compareTo(other.getDestinationString());
        }
    }

    private class CachedSessionInvocationHandler
    implements InvocationHandler {
        private final Session target;
        private final Deque<Session> sessionList;
        private final Map<DestinationCacheKey, MessageProducer> cachedProducers = new HashMap<DestinationCacheKey, MessageProducer>();
        private final Map<ConsumerCacheKey, MessageConsumer> cachedConsumers = new HashMap<ConsumerCacheKey, MessageConsumer>();
        private boolean transactionOpen = false;

        public CachedSessionInvocationHandler(Session target, Deque<Session> sessionList) {
            this.target = target;
            this.sessionList = sessionList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Nullable
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (methodName.equals("equals")) {
                return proxy == args[0];
            }
            if (methodName.equals("hashCode")) {
                return System.identityHashCode(proxy);
            }
            if (methodName.equals("toString")) {
                return "Cached JMS Session: " + this.target;
            }
            if (methodName.equals("close")) {
                if (CachingConnectionFactory.this.active) {
                    Deque<Session> deque = this.sessionList;
                    synchronized (deque) {
                        if (this.sessionList.size() < CachingConnectionFactory.this.getSessionCacheSize()) {
                            try {
                                this.logicalClose((Session)proxy);
                                return null;
                            }
                            catch (JMSException ex) {
                                CachingConnectionFactory.this.logger.trace((Object)"Logical close of cached JMS Session failed - discarding it", (Throwable)ex);
                            }
                        }
                    }
                }
                this.physicalClose();
                return null;
            }
            if (methodName.equals("getTargetSession")) {
                return this.target;
            }
            if (methodName.equals("commit") || methodName.equals("rollback")) {
                this.transactionOpen = false;
            } else if (methodName.startsWith("create")) {
                Destination dest;
                this.transactionOpen = true;
                if (CachingConnectionFactory.this.isCacheProducers() && (methodName.equals("createProducer") || methodName.equals("createSender") || methodName.equals("createPublisher"))) {
                    dest = (Destination)args[0];
                    if (!(dest instanceof TemporaryQueue) && !(dest instanceof TemporaryTopic)) {
                        return this.getCachedProducer(dest);
                    }
                } else if (CachingConnectionFactory.this.isCacheConsumers()) {
                    if (methodName.equals("createConsumer") || methodName.equals("createReceiver") || methodName.equals("createSubscriber")) {
                        dest = (Destination)args[0];
                        if (dest != null && !(dest instanceof TemporaryQueue) && !(dest instanceof TemporaryTopic)) {
                            return this.getCachedConsumer(dest, args.length > 1 ? (String)args[1] : null, args.length > 2 && (Boolean)args[2] != false, null, false);
                        }
                    } else if (methodName.equals("createDurableConsumer") || methodName.equals("createDurableSubscriber")) {
                        dest = (Destination)args[0];
                        if (dest != null) {
                            return this.getCachedConsumer(dest, args.length > 2 ? (String)args[2] : null, args.length > 3 && (Boolean)args[3] != false, (String)args[1], true);
                        }
                    } else if (methodName.equals("createSharedConsumer")) {
                        dest = (Destination)args[0];
                        if (dest != null) {
                            return this.getCachedConsumer(dest, args.length > 2 ? (String)args[2] : null, null, (String)args[1], false);
                        }
                    } else if (methodName.equals("createSharedDurableConsumer") && (dest = (Destination)args[0]) != null) {
                        return this.getCachedConsumer(dest, args.length > 2 ? (String)args[2] : null, null, (String)args[1], true);
                    }
                }
            }
            try {
                return method.invoke(this.target, args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }

        private MessageProducer getCachedProducer(@Nullable Destination dest) throws JMSException {
            DestinationCacheKey cacheKey = dest != null ? new DestinationCacheKey(dest) : null;
            MessageProducer producer = this.cachedProducers.get(cacheKey);
            if (producer != null) {
                if (CachingConnectionFactory.this.logger.isTraceEnabled()) {
                    CachingConnectionFactory.this.logger.trace((Object)("Found cached JMS MessageProducer for destination [" + dest + "]: " + producer));
                }
            } else {
                producer = this.target.createProducer(dest);
                if (CachingConnectionFactory.this.logger.isDebugEnabled()) {
                    CachingConnectionFactory.this.logger.debug((Object)("Registering cached JMS MessageProducer for destination [" + dest + "]: " + producer));
                }
                this.cachedProducers.put(cacheKey, producer);
            }
            return new CachedMessageProducer(producer);
        }

        private MessageConsumer getCachedConsumer(Destination dest, @Nullable String selector, @Nullable Boolean noLocal, @Nullable String subscription, boolean durable) throws JMSException {
            ConsumerCacheKey cacheKey = new ConsumerCacheKey(dest, selector, noLocal, subscription, durable);
            Object consumer = this.cachedConsumers.get(cacheKey);
            if (consumer != null) {
                if (CachingConnectionFactory.this.logger.isTraceEnabled()) {
                    CachingConnectionFactory.this.logger.trace((Object)("Found cached JMS MessageConsumer for destination [" + dest + "]: " + consumer));
                }
            } else {
                consumer = dest instanceof Topic ? (noLocal == null ? (durable ? this.target.createSharedDurableConsumer((Topic)dest, subscription, selector) : this.target.createSharedConsumer((Topic)dest, subscription, selector)) : (durable ? this.target.createDurableSubscriber((Topic)dest, subscription, selector, noLocal.booleanValue()) : this.target.createConsumer(dest, selector, noLocal.booleanValue()))) : this.target.createConsumer(dest, selector);
                if (CachingConnectionFactory.this.logger.isDebugEnabled()) {
                    CachingConnectionFactory.this.logger.debug((Object)("Registering cached JMS MessageConsumer for destination [" + dest + "]: " + consumer));
                }
                this.cachedConsumers.put(cacheKey, (MessageConsumer)consumer);
            }
            return new CachedMessageConsumer((MessageConsumer)consumer);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void logicalClose(Session proxy) throws JMSException {
            if (this.transactionOpen && this.target.getTransacted()) {
                this.transactionOpen = false;
                this.target.rollback();
            }
            Iterator<Map.Entry<ConsumerCacheKey, MessageConsumer>> it = this.cachedConsumers.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<ConsumerCacheKey, MessageConsumer> entry = it.next();
                if (entry.getKey().subscription == null) continue;
                entry.getValue().close();
                it.remove();
            }
            boolean returned = false;
            Deque<Session> deque = this.sessionList;
            synchronized (deque) {
                if (!this.sessionList.contains(proxy)) {
                    this.sessionList.addLast(proxy);
                    returned = true;
                }
            }
            if (returned && CachingConnectionFactory.this.logger.isTraceEnabled()) {
                CachingConnectionFactory.this.logger.trace((Object)("Returned cached Session: " + this.target));
            }
        }

        private void physicalClose() throws JMSException {
            if (CachingConnectionFactory.this.logger.isDebugEnabled()) {
                CachingConnectionFactory.this.logger.debug((Object)("Closing cached Session: " + this.target));
            }
            try {
                for (MessageProducer producer : this.cachedProducers.values()) {
                    producer.close();
                }
                for (MessageConsumer consumer : this.cachedConsumers.values()) {
                    consumer.close();
                }
            }
            finally {
                this.cachedProducers.clear();
                this.cachedConsumers.clear();
                this.target.close();
            }
        }
    }
}

