/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.EvaluationException
 *  org.springframework.expression.Expression
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.handler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.DelayHandlerManagement;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@ManagedResource
@IntegrationManagedResource
public class DelayHandler
extends AbstractReplyProducingMessageHandler
implements DelayHandlerManagement,
ApplicationListener<ContextRefreshedEvent> {
    public static final int DEFAULT_MAX_ATTEMPTS = 5;
    public static final long DEFAULT_RETRY_DELAY = 1000L;
    private final String messageGroupId;
    private final ConcurrentMap<String, AtomicInteger> deliveries = new ConcurrentHashMap<String, AtomicInteger>();
    private long defaultDelay;
    private Expression delayExpression;
    private boolean ignoreExpressionFailures = true;
    private MessageGroupStore messageStore;
    private List<Advice> delayedAdviceChain;
    private final AtomicBoolean initialized = new AtomicBoolean();
    private MessageHandler releaseHandler = new ReleaseMessageHandler();
    private EvaluationContext evaluationContext;
    private MessageChannel delayedMessageErrorChannel;
    private String delayedMessageErrorChannelName;
    private int maxAttempts = 5;
    private long retryDelay = 1000L;

    public DelayHandler(String messageGroupId) {
        Assert.notNull((Object)messageGroupId, (String)"'messageGroupId' must not be null");
        this.messageGroupId = messageGroupId;
    }

    public DelayHandler(String messageGroupId, TaskScheduler taskScheduler) {
        this(messageGroupId);
        this.setTaskScheduler(taskScheduler);
    }

    public void setDefaultDelay(long defaultDelay) {
        this.defaultDelay = defaultDelay;
    }

    public void setDelayExpression(Expression delayExpression) {
        this.delayExpression = delayExpression;
    }

    public void setDelayExpressionString(String delayExpression) {
        this.delayExpression = EXPRESSION_PARSER.parseExpression(delayExpression);
    }

    public void setIgnoreExpressionFailures(boolean ignoreExpressionFailures) {
        this.ignoreExpressionFailures = ignoreExpressionFailures;
    }

    public void setMessageStore(MessageGroupStore messageStore) {
        Assert.state((messageStore != null ? 1 : 0) != 0, (String)"MessageStore must not be null");
        this.messageStore = messageStore;
    }

    public void setDelayedAdviceChain(List<Advice> delayedAdviceChain) {
        Assert.notNull(delayedAdviceChain, (String)"delayedAdviceChain must not be null");
        this.delayedAdviceChain = delayedAdviceChain;
    }

    public void setDelayedMessageErrorChannel(MessageChannel delayedMessageErrorChannel) {
        this.delayedMessageErrorChannel = delayedMessageErrorChannel;
    }

    public void setDelayedMessageErrorChannelName(String delayedMessageErrorChannelName) {
        this.delayedMessageErrorChannelName = delayedMessageErrorChannelName;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    private MessageChannel getErrorChannel() {
        if (this.delayedMessageErrorChannel != null) {
            return this.delayedMessageErrorChannel;
        }
        DestinationResolver<MessageChannel> channelResolver = this.getChannelResolver();
        if (this.delayedMessageErrorChannelName != null && channelResolver != null) {
            this.delayedMessageErrorChannel = (MessageChannel)channelResolver.resolveDestination(this.delayedMessageErrorChannelName);
        }
        return this.delayedMessageErrorChannel;
    }

    @Override
    public String getComponentType() {
        return "delayer";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.delayer;
    }

    @Override
    protected void doInit() {
        if (this.messageStore == null) {
            this.messageStore = new SimpleMessageStore();
        } else {
            Assert.isInstanceOf(MessageStore.class, (Object)this.messageStore);
        }
        this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
        this.releaseHandler = this.createReleaseMessageTask();
    }

    private MessageHandler createReleaseMessageTask() {
        ReleaseMessageHandler handler = new ReleaseMessageHandler();
        if (!CollectionUtils.isEmpty(this.delayedAdviceChain)) {
            ProxyFactory proxyFactory = new ProxyFactory((Object)handler);
            for (Advice advice : this.delayedAdviceChain) {
                proxyFactory.addAdvice(advice);
            }
            return (MessageHandler)proxyFactory.getProxy(this.getApplicationContext().getClassLoader());
        }
        return handler;
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        long delay;
        boolean delayed = requestMessage.getPayload() instanceof DelayedMessageWrapper;
        if (!delayed && (delay = this.determineDelayForMessage(requestMessage)) > 0L) {
            this.releaseMessageAfterDelay(requestMessage, delay);
            return null;
        }
        return delayed ? ((DelayedMessageWrapper)requestMessage.getPayload()).getOriginal() : requestMessage;
    }

    private long determineDelayForMessage(Message<?> message) {
        if (this.delayExpression != null) {
            return this.determineDelayFromExpression(message);
        }
        return this.defaultDelay;
    }

    private long determineDelayFromExpression(Message<?> message) {
        long delay = this.defaultDelay;
        DelayedMessageWrapper delayedMessageWrapper = null;
        if (message.getPayload() instanceof DelayedMessageWrapper) {
            delayedMessageWrapper = (DelayedMessageWrapper)message.getPayload();
        }
        Throwable delayValueException = null;
        Object delayValue = null;
        try {
            delayValue = this.delayExpression.getValue(this.evaluationContext, delayedMessageWrapper != null ? delayedMessageWrapper.getOriginal() : message);
        }
        catch (EvaluationException e) {
            delayValueException = e;
        }
        if (delayValue instanceof Date) {
            long current = delayedMessageWrapper != null ? delayedMessageWrapper.getRequestDate() : System.currentTimeMillis();
            delay = ((Date)delayValue).getTime() - current;
        } else if (delayValue != null) {
            try {
                delay = Long.parseLong(delayValue.toString());
            }
            catch (NumberFormatException e) {
                delayValueException = e;
            }
        }
        if (delayValueException != null) {
            this.handleDelayValueException((Exception)delayValueException);
        }
        return delay;
    }

    private void handleDelayValueException(Exception delayValueException) {
        if (!this.ignoreExpressionFailures) {
            throw new IllegalStateException("Error occurred during 'delay' value determination", delayValueException);
        }
        this.logger.debug(() -> "Failed to get delay value from 'delayExpression': " + delayValueException.getMessage() + ". Will fall back to default delay: " + this.defaultDelay);
    }

    private void releaseMessageAfterDelay(Message<?> message, long delay) {
        DelayedMessageWrapper messageWrapper;
        Object delayedMessage = message;
        if (message.getPayload() instanceof DelayedMessageWrapper) {
            messageWrapper = (DelayedMessageWrapper)message.getPayload();
        } else {
            messageWrapper = new DelayedMessageWrapper(message, System.currentTimeMillis());
            delayedMessage = this.getMessageBuilderFactory().withPayload(messageWrapper).copyHeaders((Map<String, ?>)message.getHeaders()).build();
            this.messageStore.addMessageToGroup(this.messageGroupId, (Message<?>)delayedMessage);
        }
        final Runnable releaseTask = this.releaseTaskForMessage((Message<?>)delayedMessage);
        final Date startTime = new Date(messageWrapper.getRequestDate() + delay);
        if (TransactionSynchronizationManager.isSynchronizationActive() && TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCommit() {
                    DelayHandler.this.getTaskScheduler().schedule(releaseTask, startTime);
                }
            });
        } else {
            this.getTaskScheduler().schedule(releaseTask, startTime);
        }
    }

    private Runnable releaseTaskForMessage(Message<?> delayedMessage) {
        if (this.messageStore instanceof SimpleMessageStore) {
            return () -> this.releaseMessage(delayedMessage);
        }
        UUID messageId = delayedMessage.getHeaders().getId();
        return () -> {
            Message<?> messageToRelease = this.getMessageById(messageId);
            if (messageToRelease != null) {
                this.releaseMessage(messageToRelease);
            }
        };
    }

    private Message<?> getMessageById(UUID messageId) {
        Message<?> theMessage = ((MessageStore)((Object)this.messageStore)).getMessage(messageId);
        if (theMessage == null) {
            this.logger.debug(() -> "No message in the Message Store for id: " + messageId + ". Likely another instance has already released it.");
            return null;
        }
        return theMessage;
    }

    private void releaseMessage(Message<?> message) {
        block6: {
            String identity = ObjectUtils.getIdentityHexString(message);
            this.deliveries.putIfAbsent(identity, new AtomicInteger());
            try {
                this.releaseHandler.handleMessage(message);
                this.deliveries.remove(identity);
            }
            catch (Exception e) {
                if (this.getErrorChannel() != null) {
                    ErrorMessage errorMessage = new ErrorMessage((Throwable)e, Collections.singletonMap("deliveryAttempt", new AtomicInteger(((AtomicInteger)this.deliveries.get(identity)).get() + 1)), message);
                    try {
                        if (!this.getErrorChannel().send((Message)errorMessage)) {
                            this.logger.debug(() -> "Failed to send error message: " + errorMessage);
                            this.rescheduleForRetry(message, identity);
                        }
                        this.deliveries.remove(identity);
                    }
                    catch (Exception e1) {
                        this.logger.debug((Throwable)e1, () -> "Error flow threw an exception for message: " + message);
                        this.rescheduleForRetry(message, identity);
                    }
                }
                this.logger.debug((Throwable)e, () -> "Release flow threw an exception for message: " + message);
                if (this.rescheduleForRetry(message, identity)) break block6;
                throw e;
            }
        }
    }

    private boolean rescheduleForRetry(Message<?> message, String identity) {
        if (((AtomicInteger)this.deliveries.get(identity)).incrementAndGet() >= this.maxAttempts) {
            this.logger.error(() -> "Discarding; maximum release attempts reached for: " + message);
            this.deliveries.remove(identity);
            return false;
        }
        if (this.retryDelay <= 0L) {
            this.rescheduleNow(message);
        } else {
            this.rescheduleAt(message, new Date(System.currentTimeMillis() + this.retryDelay));
        }
        return true;
    }

    private void rescheduleNow(Message<?> message) {
        this.rescheduleAt(message, new Date());
    }

    protected void rescheduleAt(Message<?> message, Date startTime) {
        Runnable releaseTask = this.releaseTaskForMessage(message);
        this.getTaskScheduler().schedule(releaseTask, startTime);
    }

    private void doReleaseMessage(Message<?> message) {
        if (this.removeDelayedMessageFromMessageStore(message) || ((AtomicInteger)this.deliveries.get(ObjectUtils.getIdentityHexString(message))).get() > 0) {
            if (!(this.messageStore instanceof SimpleMessageStore)) {
                this.messageStore.removeMessagesFromGroup((Object)this.messageGroupId, message);
            }
            this.handleMessageInternal(message);
        } else {
            this.logger.debug(() -> "No message in the Message Store to release: " + message + ". Likely another instance has already released it.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean removeDelayedMessageFromMessageStore(Message<?> message) {
        if (this.messageStore instanceof SimpleMessageStore) {
            String string = this.messageGroupId;
            synchronized (string) {
                Collection<Message<?>> messages = this.messageStore.getMessageGroup(this.messageGroupId).getMessages();
                if (messages.contains(message)) {
                    this.messageStore.removeMessagesFromGroup((Object)this.messageGroupId, message);
                    return true;
                }
                return false;
            }
        }
        return ((MessageStore)((Object)this.messageStore)).removeMessage(message.getHeaders().getId()) != null;
    }

    @Override
    public int getDelayedMessageCount() {
        return this.messageStore.messageGroupSize(this.messageGroupId);
    }

    @Override
    public synchronized void reschedulePersistedMessages() {
        MessageGroup messageGroup = this.messageStore.getMessageGroup(this.messageGroupId);
        try (Stream<Message<?>> messageStream = messageGroup.streamMessages();){
            TaskScheduler taskScheduler = this.getTaskScheduler();
            messageStream.forEach(message -> taskScheduler.schedule(() -> {
                long delay = this.determineDelayForMessage((Message<?>)message);
                if (delay > 0L) {
                    this.releaseMessageAfterDelay((Message<?>)message, delay);
                } else {
                    this.releaseMessage((Message<?>)message);
                }
            }, new Date()));
        }
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().equals(this.getApplicationContext()) && !this.initialized.getAndSet(true)) {
            this.reschedulePersistedMessages();
        }
    }

    private class ReleaseMessageHandler
    implements MessageHandler {
        ReleaseMessageHandler() {
        }

        public void handleMessage(Message<?> message) throws MessagingException {
            DelayHandler.this.doReleaseMessage(message);
        }
    }

    public static final class DelayedMessageWrapper
    implements Serializable {
        private static final long serialVersionUID = -4739802369074947045L;
        private final long requestDate;
        private final Message<?> original;

        DelayedMessageWrapper(Message<?> original, long requestDate) {
            this.original = original;
            this.requestDate = requestDate;
        }

        public long getRequestDate() {
            return this.requestDate;
        }

        public Message<?> getOriginal() {
            return this.original;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            DelayedMessageWrapper that = (DelayedMessageWrapper)o;
            return this.original.equals(that.original);
        }

        public int hashCode() {
            return this.original.hashCode();
        }
    }
}

