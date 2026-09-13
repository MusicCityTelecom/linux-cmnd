/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageProducer
 *  javax.jms.Queue
 *  javax.jms.QueueBrowser
 *  javax.jms.Session
 *  javax.jms.TemporaryQueue
 *  org.springframework.lang.Nullable
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 */
package org.springframework.jms.core;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import org.springframework.jms.JmsException;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.connection.JmsResourceHolder;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jms.support.destination.JmsDestinationAccessor;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class JmsTemplate
extends JmsDestinationAccessor
implements JmsOperations {
    private final JmsTemplateResourceFactory transactionalResourceFactory = new JmsTemplateResourceFactory();
    @Nullable
    private Object defaultDestination;
    @Nullable
    private MessageConverter messageConverter;
    private boolean messageIdEnabled = true;
    private boolean messageTimestampEnabled = true;
    private boolean pubSubNoLocal = false;
    private long receiveTimeout = 0L;
    private long deliveryDelay = -1L;
    private boolean explicitQosEnabled = false;
    private int deliveryMode = 2;
    private int priority = 4;
    private long timeToLive = 0L;

    public JmsTemplate() {
        this.initDefaultStrategies();
    }

    public JmsTemplate(ConnectionFactory connectionFactory) {
        this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    protected void initDefaultStrategies() {
        this.setMessageConverter(new SimpleMessageConverter());
    }

    public void setDefaultDestination(@Nullable Destination destination) {
        this.defaultDestination = destination;
    }

    @Nullable
    public Destination getDefaultDestination() {
        return this.defaultDestination instanceof Destination ? (Destination)this.defaultDestination : null;
    }

    @Nullable
    private Queue getDefaultQueue() {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null && !(defaultDestination instanceof Queue)) {
            throw new IllegalStateException("'defaultDestination' does not correspond to a Queue. Check configuration of JmsTemplate.");
        }
        return (Queue)defaultDestination;
    }

    public void setDefaultDestinationName(@Nullable String destinationName) {
        this.defaultDestination = destinationName;
    }

    @Nullable
    public String getDefaultDestinationName() {
        return this.defaultDestination instanceof String ? (String)this.defaultDestination : null;
    }

    private String getRequiredDefaultDestinationName() throws IllegalStateException {
        String name = this.getDefaultDestinationName();
        if (name == null) {
            throw new IllegalStateException("No 'defaultDestination' or 'defaultDestinationName' specified. Check configuration of JmsTemplate.");
        }
        return name;
    }

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Nullable
    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }

    private MessageConverter getRequiredMessageConverter() throws IllegalStateException {
        MessageConverter converter = this.getMessageConverter();
        if (converter == null) {
            throw new IllegalStateException("No 'messageConverter' specified. Check configuration of JmsTemplate.");
        }
        return converter;
    }

    public void setMessageIdEnabled(boolean messageIdEnabled) {
        this.messageIdEnabled = messageIdEnabled;
    }

    public boolean isMessageIdEnabled() {
        return this.messageIdEnabled;
    }

    public void setMessageTimestampEnabled(boolean messageTimestampEnabled) {
        this.messageTimestampEnabled = messageTimestampEnabled;
    }

    public boolean isMessageTimestampEnabled() {
        return this.messageTimestampEnabled;
    }

    public void setPubSubNoLocal(boolean pubSubNoLocal) {
        this.pubSubNoLocal = pubSubNoLocal;
    }

    public boolean isPubSubNoLocal() {
        return this.pubSubNoLocal;
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public long getReceiveTimeout() {
        return this.receiveTimeout;
    }

    public void setDeliveryDelay(long deliveryDelay) {
        this.deliveryDelay = deliveryDelay;
    }

    public long getDeliveryDelay() {
        return this.deliveryDelay;
    }

    public void setExplicitQosEnabled(boolean explicitQosEnabled) {
        this.explicitQosEnabled = explicitQosEnabled;
    }

    public boolean isExplicitQosEnabled() {
        return this.explicitQosEnabled;
    }

    public void setQosSettings(QosSettings settings) {
        Assert.notNull((Object)settings, (String)"Settings must not be null");
        this.setExplicitQosEnabled(true);
        this.setDeliveryMode(settings.getDeliveryMode());
        this.setPriority(settings.getPriority());
        this.setTimeToLive(settings.getTimeToLive());
    }

    public void setDeliveryPersistent(boolean deliveryPersistent) {
        this.deliveryMode = deliveryPersistent ? 2 : 1;
    }

    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public int getDeliveryMode() {
        return this.deliveryMode;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    @Override
    @Nullable
    public <T> T execute(SessionCallback<T> action) throws JmsException {
        return this.execute(action, false);
    }

    @Nullable
    public <T> T execute(SessionCallback<T> action, boolean startConnection) throws JmsException {
        T t;
        Assert.notNull(action, (String)"Callback object must not be null");
        Connection conToClose = null;
        Session sessionToClose = null;
        try {
            Session sessionToUse = ConnectionFactoryUtils.doGetTransactionalSession(this.obtainConnectionFactory(), this.transactionalResourceFactory, startConnection);
            if (sessionToUse == null) {
                conToClose = this.createConnection();
                sessionToClose = this.createSession(conToClose);
                if (startConnection) {
                    conToClose.start();
                }
                sessionToUse = sessionToClose;
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Executing callback on JMS Session: " + sessionToUse));
            }
            t = action.doInJms(sessionToUse);
        }
        catch (JMSException ex) {
            try {
                throw this.convertJmsAccessException(ex);
            }
            catch (Throwable throwable) {
                JmsUtils.closeSession(sessionToClose);
                ConnectionFactoryUtils.releaseConnection(conToClose, this.getConnectionFactory(), startConnection);
                throw throwable;
            }
        }
        JmsUtils.closeSession(sessionToClose);
        ConnectionFactoryUtils.releaseConnection(conToClose, this.getConnectionFactory(), startConnection);
        return t;
    }

    @Override
    @Nullable
    public <T> T execute(ProducerCallback<T> action) throws JmsException {
        String defaultDestinationName = this.getDefaultDestinationName();
        if (defaultDestinationName != null) {
            return this.execute(defaultDestinationName, action);
        }
        return this.execute(this.getDefaultDestination(), action);
    }

    @Override
    @Nullable
    public <T> T execute(@Nullable Destination destination, ProducerCallback<T> action) throws JmsException {
        Assert.notNull(action, (String)"Callback object must not be null");
        return (T)this.execute((Session session) -> {
            MessageProducer producer = this.createProducer(session, destination);
            try {
                Object t = action.doInJms(session, producer);
                return t;
            }
            finally {
                JmsUtils.closeMessageProducer(producer);
            }
        }, false);
    }

    @Override
    @Nullable
    public <T> T execute(String destinationName, ProducerCallback<T> action) throws JmsException {
        Assert.notNull(action, (String)"Callback object must not be null");
        return (T)this.execute((Session session) -> {
            Destination destination = this.resolveDestinationName(session, destinationName);
            MessageProducer producer = this.createProducer(session, destination);
            try {
                Object t = action.doInJms(session, producer);
                return t;
            }
            finally {
                JmsUtils.closeMessageProducer(producer);
            }
        }, false);
    }

    @Override
    public void send(MessageCreator messageCreator) throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            this.send(defaultDestination, messageCreator);
        } else {
            this.send(this.getRequiredDefaultDestinationName(), messageCreator);
        }
    }

    @Override
    public void send(Destination destination, MessageCreator messageCreator) throws JmsException {
        this.execute((Session session) -> {
            this.doSend(session, destination, messageCreator);
            return null;
        }, false);
    }

    @Override
    public void send(String destinationName, MessageCreator messageCreator) throws JmsException {
        this.execute((Session session) -> {
            Destination destination = this.resolveDestinationName(session, destinationName);
            this.doSend(session, destination, messageCreator);
            return null;
        }, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doSend(Session session, Destination destination, MessageCreator messageCreator) throws JMSException {
        Assert.notNull((Object)messageCreator, (String)"MessageCreator must not be null");
        MessageProducer producer = this.createProducer(session, destination);
        try {
            Message message = messageCreator.createMessage(session);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Sending created message: " + message));
            }
            this.doSend(producer, message);
            if (session.getTransacted() && this.isSessionLocallyTransacted(session)) {
                JmsUtils.commitIfNecessary(session);
            }
        }
        finally {
            JmsUtils.closeMessageProducer(producer);
        }
    }

    protected void doSend(MessageProducer producer, Message message) throws JMSException {
        if (this.deliveryDelay >= 0L) {
            producer.setDeliveryDelay(this.deliveryDelay);
        }
        if (this.isExplicitQosEnabled()) {
            producer.send(message, this.getDeliveryMode(), this.getPriority(), this.getTimeToLive());
        } else {
            producer.send(message);
        }
    }

    @Override
    public void convertAndSend(Object message) throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            this.convertAndSend(defaultDestination, message);
        } else {
            this.convertAndSend(this.getRequiredDefaultDestinationName(), message);
        }
    }

    @Override
    public void convertAndSend(Destination destination, Object message) throws JmsException {
        this.send(destination, (Session session) -> this.getRequiredMessageConverter().toMessage(message, session));
    }

    @Override
    public void convertAndSend(String destinationName, Object message) throws JmsException {
        this.send(destinationName, (Session session) -> this.getRequiredMessageConverter().toMessage(message, session));
    }

    @Override
    public void convertAndSend(Object message, MessagePostProcessor postProcessor) throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            this.convertAndSend(defaultDestination, message, postProcessor);
        } else {
            this.convertAndSend(this.getRequiredDefaultDestinationName(), message, postProcessor);
        }
    }

    @Override
    public void convertAndSend(Destination destination, Object message, MessagePostProcessor postProcessor) throws JmsException {
        this.send(destination, (Session session) -> {
            Message msg = this.getRequiredMessageConverter().toMessage(message, session);
            return postProcessor.postProcessMessage(msg);
        });
    }

    @Override
    public void convertAndSend(String destinationName, Object message, MessagePostProcessor postProcessor) throws JmsException {
        this.send(destinationName, (Session session) -> {
            Message msg = this.getRequiredMessageConverter().toMessage(message, session);
            return postProcessor.postProcessMessage(msg);
        });
    }

    @Override
    @Nullable
    public Message receive() throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            return this.receive(defaultDestination);
        }
        return this.receive(this.getRequiredDefaultDestinationName());
    }

    @Override
    @Nullable
    public Message receive(Destination destination) throws JmsException {
        return this.receiveSelected(destination, null);
    }

    @Override
    @Nullable
    public Message receive(String destinationName) throws JmsException {
        return this.receiveSelected(destinationName, null);
    }

    @Override
    @Nullable
    public Message receiveSelected(String messageSelector) throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            return this.receiveSelected(defaultDestination, messageSelector);
        }
        return this.receiveSelected(this.getRequiredDefaultDestinationName(), messageSelector);
    }

    @Override
    @Nullable
    public Message receiveSelected(Destination destination, @Nullable String messageSelector) throws JmsException {
        return this.execute((Session session) -> this.doReceive(session, destination, messageSelector), true);
    }

    @Override
    @Nullable
    public Message receiveSelected(String destinationName, @Nullable String messageSelector) throws JmsException {
        return this.execute((Session session) -> {
            Destination destination = this.resolveDestinationName(session, destinationName);
            return this.doReceive(session, destination, messageSelector);
        }, true);
    }

    @Nullable
    protected Message doReceive(Session session, Destination destination, @Nullable String messageSelector) throws JMSException {
        return this.doReceive(session, this.createConsumer(session, destination, messageSelector));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    protected Message doReceive(Session session, MessageConsumer consumer) throws JMSException {
        try {
            long timeout = this.getReceiveTimeout();
            ConnectionFactory connectionFactory = this.getConnectionFactory();
            JmsResourceHolder resourceHolder = null;
            if (connectionFactory != null) {
                resourceHolder = (JmsResourceHolder)((Object)TransactionSynchronizationManager.getResource((Object)connectionFactory));
            }
            if (resourceHolder != null && resourceHolder.hasTimeout()) {
                timeout = Math.min(timeout, resourceHolder.getTimeToLiveInMillis());
            }
            Message message = this.receiveFromConsumer(consumer, timeout);
            if (session.getTransacted()) {
                if (this.isSessionLocallyTransacted(session)) {
                    JmsUtils.commitIfNecessary(session);
                }
            } else if (this.isClientAcknowledge(session) && message != null) {
                message.acknowledge();
            }
            Message message2 = message;
            return message2;
        }
        finally {
            JmsUtils.closeMessageConsumer(consumer);
        }
    }

    @Override
    @Nullable
    public Object receiveAndConvert() throws JmsException {
        return this.doConvertFromMessage(this.receive());
    }

    @Override
    @Nullable
    public Object receiveAndConvert(Destination destination) throws JmsException {
        return this.doConvertFromMessage(this.receive(destination));
    }

    @Override
    @Nullable
    public Object receiveAndConvert(String destinationName) throws JmsException {
        return this.doConvertFromMessage(this.receive(destinationName));
    }

    @Override
    @Nullable
    public Object receiveSelectedAndConvert(String messageSelector) throws JmsException {
        return this.doConvertFromMessage(this.receiveSelected(messageSelector));
    }

    @Override
    @Nullable
    public Object receiveSelectedAndConvert(Destination destination, String messageSelector) throws JmsException {
        return this.doConvertFromMessage(this.receiveSelected(destination, messageSelector));
    }

    @Override
    @Nullable
    public Object receiveSelectedAndConvert(String destinationName, String messageSelector) throws JmsException {
        return this.doConvertFromMessage(this.receiveSelected(destinationName, messageSelector));
    }

    @Nullable
    protected Object doConvertFromMessage(@Nullable Message message) {
        if (message != null) {
            try {
                return this.getRequiredMessageConverter().fromMessage(message);
            }
            catch (JMSException ex) {
                throw this.convertJmsAccessException(ex);
            }
        }
        return null;
    }

    @Override
    @Nullable
    public Message sendAndReceive(MessageCreator messageCreator) throws JmsException {
        Destination defaultDestination = this.getDefaultDestination();
        if (defaultDestination != null) {
            return this.sendAndReceive(defaultDestination, messageCreator);
        }
        return this.sendAndReceive(this.getRequiredDefaultDestinationName(), messageCreator);
    }

    @Override
    @Nullable
    public Message sendAndReceive(Destination destination, MessageCreator messageCreator) throws JmsException {
        return this.executeLocal(session -> this.doSendAndReceive(session, destination, messageCreator), true);
    }

    @Override
    @Nullable
    public Message sendAndReceive(String destinationName, MessageCreator messageCreator) throws JmsException {
        return this.executeLocal(session -> {
            Destination destination = this.resolveDestinationName(session, destinationName);
            return this.doSendAndReceive(session, destination, messageCreator);
        }, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    protected Message doSendAndReceive(Session session, Destination destination, MessageCreator messageCreator) throws JMSException {
        Message message;
        Assert.notNull((Object)messageCreator, (String)"MessageCreator must not be null");
        TemporaryQueue responseQueue = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        try {
            Message requestMessage = messageCreator.createMessage(session);
            responseQueue = session.createTemporaryQueue();
            producer = session.createProducer(destination);
            consumer = session.createConsumer((Destination)responseQueue);
            requestMessage.setJMSReplyTo((Destination)responseQueue);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Sending created message: " + requestMessage));
            }
            this.doSend(producer, requestMessage);
            message = this.receiveFromConsumer(consumer, this.getReceiveTimeout());
        }
        catch (Throwable throwable) {
            JmsUtils.closeMessageConsumer(consumer);
            JmsUtils.closeMessageProducer(producer);
            if (responseQueue != null) {
                responseQueue.delete();
            }
            throw throwable;
        }
        JmsUtils.closeMessageConsumer(consumer);
        JmsUtils.closeMessageProducer(producer);
        if (responseQueue != null) {
            responseQueue.delete();
        }
        return message;
    }

    @Nullable
    private <T> T executeLocal(SessionCallback<T> action, boolean startConnection) throws JmsException {
        T t;
        Assert.notNull(action, (String)"Callback object must not be null");
        Connection con = null;
        Session session = null;
        try {
            con = this.createConnection();
            session = con.createSession(false, 1);
            if (startConnection) {
                con.start();
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Executing callback on JMS Session: " + session));
            }
            t = action.doInJms(session);
        }
        catch (JMSException ex) {
            try {
                throw this.convertJmsAccessException(ex);
            }
            catch (Throwable throwable) {
                JmsUtils.closeSession(session);
                ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory(), startConnection);
                throw throwable;
            }
        }
        JmsUtils.closeSession(session);
        ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory(), startConnection);
        return t;
    }

    @Override
    @Nullable
    public <T> T browse(BrowserCallback<T> action) throws JmsException {
        Queue defaultQueue = this.getDefaultQueue();
        if (defaultQueue != null) {
            return this.browse(defaultQueue, action);
        }
        return this.browse(this.getRequiredDefaultDestinationName(), action);
    }

    @Override
    @Nullable
    public <T> T browse(Queue queue, BrowserCallback<T> action) throws JmsException {
        return this.browseSelected(queue, null, action);
    }

    @Override
    @Nullable
    public <T> T browse(String queueName, BrowserCallback<T> action) throws JmsException {
        return this.browseSelected(queueName, null, action);
    }

    @Override
    @Nullable
    public <T> T browseSelected(String messageSelector, BrowserCallback<T> action) throws JmsException {
        Queue defaultQueue = this.getDefaultQueue();
        if (defaultQueue != null) {
            return this.browseSelected(defaultQueue, messageSelector, action);
        }
        return this.browseSelected(this.getRequiredDefaultDestinationName(), messageSelector, action);
    }

    @Override
    @Nullable
    public <T> T browseSelected(Queue queue, @Nullable String messageSelector, BrowserCallback<T> action) throws JmsException {
        Assert.notNull(action, (String)"Callback object must not be null");
        return (T)this.execute((Session session) -> {
            QueueBrowser browser = this.createBrowser(session, queue, messageSelector);
            try {
                Object t = action.doInJms(session, browser);
                return t;
            }
            finally {
                JmsUtils.closeQueueBrowser(browser);
            }
        }, true);
    }

    @Override
    @Nullable
    public <T> T browseSelected(String queueName, @Nullable String messageSelector, BrowserCallback<T> action) throws JmsException {
        Assert.notNull(action, (String)"Callback object must not be null");
        return (T)this.execute((Session session) -> {
            Queue queue = (Queue)this.getDestinationResolver().resolveDestinationName(session, queueName, false);
            QueueBrowser browser = this.createBrowser(session, queue, messageSelector);
            try {
                Object t = action.doInJms(session, browser);
                return t;
            }
            finally {
                JmsUtils.closeQueueBrowser(browser);
            }
        }, true);
    }

    @Nullable
    protected Connection getConnection(JmsResourceHolder holder) {
        return holder.getConnection();
    }

    @Nullable
    protected Session getSession(JmsResourceHolder holder) {
        return holder.getSession();
    }

    protected boolean isSessionLocallyTransacted(Session session) {
        return this.isSessionTransacted() && !ConnectionFactoryUtils.isSessionTransactional(session, this.getConnectionFactory());
    }

    protected MessageProducer createProducer(Session session, @Nullable Destination destination) throws JMSException {
        MessageProducer producer = this.doCreateProducer(session, destination);
        if (!this.isMessageIdEnabled()) {
            producer.setDisableMessageID(true);
        }
        if (!this.isMessageTimestampEnabled()) {
            producer.setDisableMessageTimestamp(true);
        }
        return producer;
    }

    protected MessageProducer doCreateProducer(Session session, @Nullable Destination destination) throws JMSException {
        return session.createProducer(destination);
    }

    protected MessageConsumer createConsumer(Session session, Destination destination, @Nullable String messageSelector) throws JMSException {
        if (this.isPubSubDomain()) {
            return session.createConsumer(destination, messageSelector, this.isPubSubNoLocal());
        }
        return session.createConsumer(destination, messageSelector);
    }

    protected QueueBrowser createBrowser(Session session, Queue queue, @Nullable String messageSelector) throws JMSException {
        return session.createBrowser(queue, messageSelector);
    }

    private class JmsTemplateResourceFactory
    implements ConnectionFactoryUtils.ResourceFactory {
        private JmsTemplateResourceFactory() {
        }

        @Override
        @Nullable
        public Connection getConnection(JmsResourceHolder holder) {
            return JmsTemplate.this.getConnection(holder);
        }

        @Override
        @Nullable
        public Session getSession(JmsResourceHolder holder) {
            return JmsTemplate.this.getSession(holder);
        }

        @Override
        public Connection createConnection() throws JMSException {
            return JmsTemplate.this.createConnection();
        }

        @Override
        public Session createSession(Connection con) throws JMSException {
            return JmsTemplate.this.createSession(con);
        }

        @Override
        public boolean isSynchedLocalTransactionAllowed() {
            return JmsTemplate.this.isSessionTransacted();
        }
    }
}

