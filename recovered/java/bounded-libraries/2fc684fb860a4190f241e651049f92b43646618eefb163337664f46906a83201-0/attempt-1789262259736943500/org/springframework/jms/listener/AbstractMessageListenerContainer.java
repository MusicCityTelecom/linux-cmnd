/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.Destination
 *  javax.jms.ExceptionListener
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageListener
 *  javax.jms.Queue
 *  javax.jms.Session
 *  javax.jms.Topic
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.jms.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.SubscriptionNameProvider;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

public abstract class AbstractMessageListenerContainer
extends AbstractJmsListeningContainer
implements MessageListenerContainer {
    @Nullable
    private volatile Object destination;
    @Nullable
    private volatile String messageSelector;
    @Nullable
    private volatile Object messageListener;
    private boolean subscriptionDurable = false;
    private boolean subscriptionShared = false;
    @Nullable
    private String subscriptionName;
    @Nullable
    private Boolean replyPubSubDomain;
    @Nullable
    private QosSettings replyQosSettings;
    private boolean pubSubNoLocal = false;
    @Nullable
    private MessageConverter messageConverter;
    @Nullable
    private ExceptionListener exceptionListener;
    @Nullable
    private ErrorHandler errorHandler;
    private boolean exposeListenerSession = true;
    private boolean acceptMessagesWhileStopping = false;

    public abstract void setConcurrency(String var1);

    public void setDestination(@Nullable Destination destination) {
        this.destination = destination;
        if (destination instanceof Topic && !(destination instanceof Queue)) {
            this.setPubSubDomain(true);
        }
    }

    @Nullable
    public Destination getDestination() {
        return this.destination instanceof Destination ? (Destination)this.destination : null;
    }

    public void setDestinationName(@Nullable String destinationName) {
        this.destination = destinationName;
    }

    @Nullable
    public String getDestinationName() {
        return this.destination instanceof String ? (String)this.destination : null;
    }

    protected String getDestinationDescription() {
        Object destination = this.destination;
        return destination != null ? destination.toString() : "";
    }

    public void setMessageSelector(@Nullable String messageSelector) {
        this.messageSelector = messageSelector;
    }

    @Nullable
    public String getMessageSelector() {
        return this.messageSelector;
    }

    public void setMessageListener(@Nullable Object messageListener) {
        this.checkMessageListener(messageListener);
        this.messageListener = messageListener;
        if (messageListener != null && this.subscriptionName == null) {
            this.subscriptionName = this.getDefaultSubscriptionName(messageListener);
        }
    }

    @Nullable
    public Object getMessageListener() {
        return this.messageListener;
    }

    protected void checkMessageListener(@Nullable Object messageListener) {
        if (messageListener != null && !(messageListener instanceof MessageListener) && !(messageListener instanceof SessionAwareMessageListener)) {
            throw new IllegalArgumentException("Message listener needs to be of type [" + MessageListener.class.getName() + "] or [" + SessionAwareMessageListener.class.getName() + "]");
        }
    }

    protected String getDefaultSubscriptionName(Object messageListener) {
        if (messageListener instanceof SubscriptionNameProvider) {
            return ((SubscriptionNameProvider)messageListener).getSubscriptionName();
        }
        return messageListener.getClass().getName();
    }

    public void setSubscriptionDurable(boolean subscriptionDurable) {
        this.subscriptionDurable = subscriptionDurable;
        if (subscriptionDurable) {
            this.setPubSubDomain(true);
        }
    }

    public boolean isSubscriptionDurable() {
        return this.subscriptionDurable;
    }

    public void setSubscriptionShared(boolean subscriptionShared) {
        this.subscriptionShared = subscriptionShared;
        if (subscriptionShared) {
            this.setPubSubDomain(true);
        }
    }

    public boolean isSubscriptionShared() {
        return this.subscriptionShared;
    }

    public void setSubscriptionName(@Nullable String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    @Nullable
    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public void setDurableSubscriptionName(@Nullable String durableSubscriptionName) {
        this.subscriptionName = durableSubscriptionName;
        this.subscriptionDurable = durableSubscriptionName != null;
    }

    @Nullable
    public String getDurableSubscriptionName() {
        return this.subscriptionDurable ? this.subscriptionName : null;
    }

    public void setPubSubNoLocal(boolean pubSubNoLocal) {
        this.pubSubNoLocal = pubSubNoLocal;
    }

    public boolean isPubSubNoLocal() {
        return this.pubSubNoLocal;
    }

    public void setReplyPubSubDomain(boolean replyPubSubDomain) {
        this.replyPubSubDomain = replyPubSubDomain;
    }

    @Override
    public boolean isReplyPubSubDomain() {
        if (this.replyPubSubDomain != null) {
            return this.replyPubSubDomain;
        }
        return this.isPubSubDomain();
    }

    public void setReplyQosSettings(@Nullable QosSettings replyQosSettings) {
        this.replyQosSettings = replyQosSettings;
    }

    @Override
    @Nullable
    public QosSettings getReplyQosSettings() {
        return this.replyQosSettings;
    }

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    @Nullable
    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }

    public void setExceptionListener(@Nullable ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    @Nullable
    public ExceptionListener getExceptionListener() {
        return this.exceptionListener;
    }

    public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Nullable
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public void setExposeListenerSession(boolean exposeListenerSession) {
        this.exposeListenerSession = exposeListenerSession;
    }

    public boolean isExposeListenerSession() {
        return this.exposeListenerSession;
    }

    public void setAcceptMessagesWhileStopping(boolean acceptMessagesWhileStopping) {
        this.acceptMessagesWhileStopping = acceptMessagesWhileStopping;
    }

    public boolean isAcceptMessagesWhileStopping() {
        return this.acceptMessagesWhileStopping;
    }

    @Override
    protected void validateConfiguration() {
        if (this.destination == null) {
            throw new IllegalArgumentException("Property 'destination' or 'destinationName' is required");
        }
    }

    @Override
    public void setupMessageListener(Object messageListener) {
        this.setMessageListener(messageListener);
    }

    protected void executeListener(Session session, Message message) {
        try {
            this.doExecuteListener(session, message);
        }
        catch (Throwable ex) {
            this.handleListenerException(ex);
        }
    }

    protected void doExecuteListener(Session session, Message message) throws JMSException {
        if (!this.isAcceptMessagesWhileStopping() && !this.isRunning()) {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn((Object)("Rejecting received message because of the listener container having been stopped in the meantime: " + message));
            }
            this.rollbackIfNecessary(session);
            throw new MessageRejectedWhileStoppingException();
        }
        try {
            this.invokeListener(session, message);
        }
        catch (Error | RuntimeException | JMSException ex) {
            this.rollbackOnExceptionIfNecessary(session, ex);
            throw ex;
        }
        this.commitIfNecessary(session, message);
    }

    protected void invokeListener(Session session, Message message) throws JMSException {
        Object listener = this.getMessageListener();
        if (listener instanceof SessionAwareMessageListener) {
            this.doInvokeListener((SessionAwareMessageListener)listener, session, message);
        } else if (listener instanceof MessageListener) {
            this.doInvokeListener((MessageListener)listener, message);
        } else {
            if (listener != null) {
                throw new IllegalArgumentException("Only MessageListener and SessionAwareMessageListener supported: " + listener);
            }
            throw new IllegalStateException("No message listener specified - see property 'messageListener'");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doInvokeListener(SessionAwareMessageListener listener, Session session, Message message) throws JMSException {
        Connection conToClose = null;
        Session sessionToClose = null;
        try {
            Session sessionToUse = session;
            if (!this.isExposeListenerSession()) {
                conToClose = this.createConnection();
                sessionToUse = sessionToClose = this.createSession(conToClose);
            }
            listener.onMessage(message, sessionToUse);
            if (sessionToUse != session && sessionToUse.getTransacted() && this.isSessionLocallyTransacted(sessionToUse)) {
                JmsUtils.commitIfNecessary(sessionToUse);
            }
        }
        catch (Throwable throwable) {
            JmsUtils.closeSession(sessionToClose);
            JmsUtils.closeConnection(conToClose);
            throw throwable;
        }
        JmsUtils.closeSession(sessionToClose);
        JmsUtils.closeConnection(conToClose);
    }

    protected void doInvokeListener(MessageListener listener, Message message) throws JMSException {
        listener.onMessage(message);
    }

    protected void commitIfNecessary(Session session, @Nullable Message message) throws JMSException {
        if (session.getTransacted()) {
            if (this.isSessionLocallyTransacted(session)) {
                JmsUtils.commitIfNecessary(session);
            }
        } else if (message != null && this.isClientAcknowledge(session)) {
            message.acknowledge();
        }
    }

    protected void rollbackIfNecessary(Session session) throws JMSException {
        if (session.getTransacted()) {
            if (this.isSessionLocallyTransacted(session)) {
                JmsUtils.rollbackIfNecessary(session);
            }
        } else if (this.isClientAcknowledge(session)) {
            session.recover();
        }
    }

    protected void rollbackOnExceptionIfNecessary(Session session, Throwable ex) throws JMSException {
        try {
            if (session.getTransacted()) {
                if (this.isSessionLocallyTransacted(session)) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug((Object)"Initiating transaction rollback on application exception", ex);
                    }
                    JmsUtils.rollbackIfNecessary(session);
                }
            } else if (this.isClientAcknowledge(session)) {
                session.recover();
            }
        }
        catch (IllegalStateException ex2) {
            this.logger.debug((Object)"Could not roll back because Session already closed", (Throwable)ex2);
        }
        catch (Error | RuntimeException | JMSException ex2) {
            this.logger.error((Object)"Application exception overridden by rollback error", ex);
            throw ex2;
        }
    }

    protected boolean isSessionLocallyTransacted(Session session) {
        return this.isSessionTransacted();
    }

    protected MessageConsumer createConsumer(Session session, Destination destination) throws JMSException {
        if (this.isPubSubDomain() && destination instanceof Topic) {
            if (this.isSubscriptionShared()) {
                return this.isSubscriptionDurable() ? session.createSharedDurableConsumer((Topic)destination, this.getSubscriptionName(), this.getMessageSelector()) : session.createSharedConsumer((Topic)destination, this.getSubscriptionName(), this.getMessageSelector());
            }
            if (this.isSubscriptionDurable()) {
                return session.createDurableSubscriber((Topic)destination, this.getSubscriptionName(), this.getMessageSelector(), this.isPubSubNoLocal());
            }
            return session.createConsumer(destination, this.getMessageSelector(), this.isPubSubNoLocal());
        }
        return session.createConsumer(destination, this.getMessageSelector());
    }

    protected void handleListenerException(Throwable ex) {
        if (ex instanceof MessageRejectedWhileStoppingException) {
            return;
        }
        if (ex instanceof JMSException) {
            this.invokeExceptionListener((JMSException)ex);
        }
        if (this.isActive()) {
            this.invokeErrorHandler(ex);
        } else {
            this.logger.debug((Object)"Listener exception after container shutdown", ex);
        }
    }

    protected void invokeExceptionListener(JMSException ex) {
        ExceptionListener exceptionListener = this.getExceptionListener();
        if (exceptionListener != null) {
            exceptionListener.onException(ex);
        }
    }

    protected void invokeErrorHandler(Throwable ex) {
        ErrorHandler errorHandler = this.getErrorHandler();
        if (errorHandler != null) {
            errorHandler.handleError(ex);
        } else {
            this.logger.warn((Object)"Execution of JMS message listener failed, and no ErrorHandler has been set.", ex);
        }
    }

    private static class MessageRejectedWhileStoppingException
    extends RuntimeException {
        private MessageRejectedWhileStoppingException() {
        }
    }
}

