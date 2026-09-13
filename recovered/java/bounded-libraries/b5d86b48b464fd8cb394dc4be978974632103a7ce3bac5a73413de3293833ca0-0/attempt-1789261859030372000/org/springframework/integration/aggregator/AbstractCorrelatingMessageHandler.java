/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.aggregator;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.function.BiFunction;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.Lifecycle;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.GroupConditionProvider;
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy;
import org.springframework.integration.aggregator.MessageGroupExpiredEvent;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.MessageSequenceComparator;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.aggregator.SequenceSizeReleaseStrategy;
import org.springframework.integration.aggregator.SimpleSequenceSizeReleaseStrategy;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageGroup;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.integration.store.UniqueExpiryCallback;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.util.UUIDConverter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public abstract class AbstractCorrelatingMessageHandler
extends AbstractMessageProducingHandler
implements DiscardingMessageHandler,
ApplicationEventPublisherAware,
ManageableLifecycle {
    private final Comparator<Message<?>> sequenceNumberComparator = new MessageSequenceComparator();
    private final Map<UUID, ScheduledFuture<?>> expireGroupScheduledFutures = new ConcurrentHashMap();
    private MessageGroupProcessor outputProcessor;
    private MessageGroupStore messageStore;
    private CorrelationStrategy correlationStrategy;
    private ReleaseStrategy releaseStrategy;
    private boolean releaseStrategySet;
    private MessageChannel discardChannel;
    private String discardChannelName;
    private boolean sendPartialResultOnExpiry;
    private boolean sequenceAware;
    private LockRegistry lockRegistry = new DefaultLockRegistry();
    private boolean lockRegistrySet = false;
    private long minimumTimeoutForEmptyGroups;
    private boolean releasePartialSequences;
    private Expression groupTimeoutExpression;
    private List<Advice> forceReleaseAdviceChain;
    private long expireTimeout;
    private Duration expireDuration;
    private MessageGroupProcessor forceReleaseProcessor = new ForceReleaseMessageGroupProcessor();
    private EvaluationContext evaluationContext;
    private ApplicationEventPublisher applicationEventPublisher;
    private boolean expireGroupsUponTimeout = true;
    private boolean popSequence = true;
    private boolean releaseLockBeforeSend;
    private volatile boolean running;
    private BiFunction<Message<?>, String, String> groupConditionSupplier;

    public AbstractCorrelatingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store, CorrelationStrategy correlationStrategy, ReleaseStrategy releaseStrategy) {
        Assert.notNull((Object)processor, (String)"'processor' must not be null");
        Assert.notNull((Object)store, (String)"'store' must not be null");
        this.setMessageStore(store);
        this.outputProcessor = processor;
        this.correlationStrategy = correlationStrategy == null ? new HeaderAttributeCorrelationStrategy("correlationId") : correlationStrategy;
        this.releaseStrategy = releaseStrategy == null ? new SimpleSequenceSizeReleaseStrategy() : releaseStrategy;
        this.releaseStrategySet = releaseStrategy != null;
        this.sequenceAware = this.releaseStrategy instanceof SequenceSizeReleaseStrategy;
    }

    public AbstractCorrelatingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store) {
        this(processor, store, null, null);
    }

    public AbstractCorrelatingMessageHandler(MessageGroupProcessor processor) {
        this(processor, new SimpleMessageStore(0), null, null);
    }

    public void setLockRegistry(LockRegistry lockRegistry) {
        Assert.isTrue((!this.lockRegistrySet ? 1 : 0) != 0, (String)"'this.lockRegistry' can not be reset once its been set");
        Assert.notNull((Object)lockRegistry, (String)"'lockRegistry' must not be null");
        this.lockRegistry = lockRegistry;
        this.lockRegistrySet = true;
    }

    public final void setMessageStore(MessageGroupStore store) {
        this.messageStore = store;
        UniqueExpiryCallback expiryCallback = (messageGroupStore, group) -> this.forceReleaseProcessor.processMessageGroup(group);
        store.registerMessageGroupExpiryCallback(expiryCallback);
    }

    public void setCorrelationStrategy(CorrelationStrategy correlationStrategy) {
        Assert.notNull((Object)correlationStrategy, (String)"'correlationStrategy' must not be null");
        this.correlationStrategy = correlationStrategy;
    }

    public void setReleaseStrategy(ReleaseStrategy releaseStrategy) {
        Assert.notNull((Object)releaseStrategy, (String)"'releaseStrategy' must not be null");
        this.releaseStrategy = releaseStrategy;
        this.sequenceAware = this.releaseStrategy instanceof SequenceSizeReleaseStrategy;
        this.releaseStrategySet = true;
    }

    public void setGroupTimeoutExpression(Expression groupTimeoutExpression) {
        this.groupTimeoutExpression = groupTimeoutExpression;
    }

    public void setForceReleaseAdviceChain(List<Advice> forceReleaseAdviceChain) {
        Assert.notNull(forceReleaseAdviceChain, (String)"'forceReleaseAdviceChain' must not be null");
        this.forceReleaseAdviceChain = forceReleaseAdviceChain;
    }

    public void setOutputProcessor(MessageGroupProcessor outputProcessor) {
        Assert.notNull((Object)outputProcessor, (String)"'processor' must not be null");
        this.outputProcessor = outputProcessor;
    }

    public MessageGroupProcessor getOutputProcessor() {
        return this.outputProcessor;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        Assert.notNull((Object)discardChannel, (String)"'discardChannel' cannot be null");
        this.discardChannel = discardChannel;
    }

    public void setDiscardChannelName(String discardChannelName) {
        Assert.hasText((String)discardChannelName, (String)"'discardChannelName' must not be empty");
        this.discardChannelName = discardChannelName;
    }

    public void setSendPartialResultOnExpiry(boolean sendPartialResultOnExpiry) {
        this.sendPartialResultOnExpiry = sendPartialResultOnExpiry;
    }

    public void setMinimumTimeoutForEmptyGroups(long minimumTimeoutForEmptyGroups) {
        this.minimumTimeoutForEmptyGroups = minimumTimeoutForEmptyGroups;
    }

    public void setReleasePartialSequences(boolean releasePartialSequences) {
        if (!this.releaseStrategySet && releasePartialSequences) {
            this.setReleaseStrategy(new SequenceSizeReleaseStrategy());
        }
        this.releasePartialSequences = releasePartialSequences;
    }

    public void setExpireGroupsUponTimeout(boolean expireGroupsUponTimeout) {
        this.expireGroupsUponTimeout = expireGroupsUponTimeout;
    }

    public void setPopSequence(boolean popSequence) {
        this.popSequence = popSequence;
    }

    protected boolean isReleaseLockBeforeSend() {
        return this.releaseLockBeforeSend;
    }

    public void setReleaseLockBeforeSend(boolean releaseLockBeforeSend) {
        this.releaseLockBeforeSend = releaseLockBeforeSend;
    }

    public void setExpireTimeout(long expireTimeout) {
        Assert.isTrue((expireTimeout > 0L ? 1 : 0) != 0, (String)"'expireTimeout' must be more than 0.");
        this.expireTimeout = expireTimeout;
    }

    public void setExpireDurationMillis(long expireDuration) {
        this.setExpireDuration(Duration.ofMillis(expireDuration));
    }

    public void setExpireDuration(@Nullable Duration expireDuration) {
        this.expireDuration = expireDuration;
    }

    public void setGroupConditionSupplier(BiFunction<Message<?>, String, String> conditionSupplier) {
        this.groupConditionSupplier = conditionSupplier;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void onInit() {
        super.onInit();
        Assert.state((this.discardChannelName == null || this.discardChannel == null ? 1 : 0) != 0, (String)"'discardChannelName' and 'discardChannel' are mutually exclusive.");
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            if (this.outputProcessor instanceof BeanFactoryAware) {
                ((BeanFactoryAware)this.outputProcessor).setBeanFactory(beanFactory);
            }
            if (this.correlationStrategy instanceof BeanFactoryAware) {
                ((BeanFactoryAware)this.correlationStrategy).setBeanFactory(beanFactory);
            }
            if (this.releaseStrategy instanceof BeanFactoryAware) {
                ((BeanFactoryAware)this.releaseStrategy).setBeanFactory(beanFactory);
            }
        }
        if (this.releasePartialSequences) {
            Assert.isInstanceOf(SequenceSizeReleaseStrategy.class, (Object)this.releaseStrategy, () -> "Release strategy of type [" + this.releaseStrategy.getClass().getSimpleName() + "] cannot release partial sequences. Use a SequenceSizeReleaseStrategy instead.");
            ((SequenceSizeReleaseStrategy)this.releaseStrategy).setReleasePartialSequences(this.releasePartialSequences);
        }
        if (this.evaluationContext == null) {
            this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
        }
        if (this.sequenceAware) {
            this.logger.warn((CharSequence)"Using a SequenceSizeReleaseStrategy with large groups may not perform well, consider using a SimpleSequenceSizeReleaseStrategy");
        }
        this.lockRegistrySet = true;
        this.forceReleaseProcessor = this.createGroupTimeoutProcessor();
        if (this.releaseStrategy instanceof GroupConditionProvider) {
            this.groupConditionSupplier = ((GroupConditionProvider)((Object)this.releaseStrategy)).getGroupConditionSupplier();
        }
    }

    private MessageGroupProcessor createGroupTimeoutProcessor() {
        ForceReleaseMessageGroupProcessor processor = new ForceReleaseMessageGroupProcessor();
        if (this.groupTimeoutExpression != null && !CollectionUtils.isEmpty(this.forceReleaseAdviceChain)) {
            ProxyFactory proxyFactory = new ProxyFactory((Object)processor);
            this.forceReleaseAdviceChain.forEach(arg_0 -> ((ProxyFactory)proxyFactory).addAdvice(arg_0));
            return (MessageGroupProcessor)proxyFactory.getProxy(this.getApplicationContext().getClassLoader());
        }
        return processor;
    }

    @Override
    public String getComponentType() {
        return "aggregator";
    }

    public MessageGroupStore getMessageStore() {
        return this.messageStore;
    }

    protected Map<UUID, ScheduledFuture<?>> getExpireGroupScheduledFutures() {
        return this.expireGroupScheduledFutures;
    }

    protected CorrelationStrategy getCorrelationStrategy() {
        return this.correlationStrategy;
    }

    protected ReleaseStrategy getReleaseStrategy() {
        return this.releaseStrategy;
    }

    @Nullable
    protected BiFunction<Message<?>, String, String> getGroupConditionSupplier() {
        return this.groupConditionSupplier;
    }

    @Override
    public MessageChannel getDiscardChannel() {
        String channelName = this.discardChannelName;
        if (channelName == null && this.discardChannel == null) {
            channelName = "nullChannel";
        }
        if (channelName != null) {
            try {
                this.discardChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            }
            catch (DestinationResolutionException ex) {
                if (channelName.equals("nullChannel")) {
                    this.discardChannel = new NullChannel();
                }
                throw ex;
            }
            this.discardChannelName = null;
        }
        return this.discardChannel;
    }

    protected String getDiscardChannelName() {
        return this.discardChannelName;
    }

    protected boolean isSendPartialResultOnExpiry() {
        return this.sendPartialResultOnExpiry;
    }

    protected boolean isSequenceAware() {
        return this.sequenceAware;
    }

    protected LockRegistry getLockRegistry() {
        return this.lockRegistry;
    }

    protected boolean isLockRegistrySet() {
        return this.lockRegistrySet;
    }

    protected long getMinimumTimeoutForEmptyGroups() {
        return this.minimumTimeoutForEmptyGroups;
    }

    protected boolean isReleasePartialSequences() {
        return this.releasePartialSequences;
    }

    protected Expression getGroupTimeoutExpression() {
        return this.groupTimeoutExpression;
    }

    protected EvaluationContext getEvaluationContext() {
        return this.evaluationContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void handleMessageInternal(Message<?> message) {
        Object correlationKey = this.correlationStrategy.getCorrelationKey(message);
        Assert.state((correlationKey != null ? 1 : 0) != 0, (String)"Null correlation not allowed.  Maybe the CorrelationStrategy is failing?");
        this.logger.debug(() -> "Handling message with correlationKey [" + correlationKey + "]: " + message);
        UUID groupIdUuid = UUIDConverter.getUUID(correlationKey);
        Lock lock = this.lockRegistry.obtain(groupIdUuid.toString());
        boolean noOutput = true;
        try {
            lock.lockInterruptibly();
            try {
                noOutput = this.processMessageForGroup(message, correlationKey, groupIdUuid, lock);
            }
            finally {
                if (noOutput || !this.releaseLockBeforeSend) {
                    lock.unlock();
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessageHandlingException(message, "Interrupted getting lock in the [" + this + ']', (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean processMessageForGroup(Message<?> message, Object correlationKey, UUID groupIdUuid, Lock lock) {
        boolean noOutput = true;
        this.cancelScheduledFutureIfAny(correlationKey, groupIdUuid, true);
        MessageGroup messageGroup = this.messageStore.getMessageGroup(correlationKey);
        if (this.sequenceAware) {
            messageGroup = new SequenceAwareMessageGroup(messageGroup);
        }
        if (!messageGroup.isComplete() && messageGroup.canAdd(message)) {
            MessageGroup messageGroupToLog = messageGroup;
            this.logger.trace(() -> "Adding message to group [ " + messageGroupToLog + "]");
            messageGroup = this.store(correlationKey, message);
            this.setGroupConditionIfAny(message, messageGroup);
            if (this.releaseStrategy.canRelease(messageGroup)) {
                Collection<Message<?>> completedMessages = null;
                try {
                    noOutput = false;
                    completedMessages = this.completeGroup(message, correlationKey, messageGroup, lock);
                }
                finally {
                    this.afterRelease(messageGroup, completedMessages);
                }
                if (!this.isExpireGroupsUponCompletion() && this.minimumTimeoutForEmptyGroups > 0L) {
                    this.removeEmptyGroupAfterTimeout(groupIdUuid, this.minimumTimeoutForEmptyGroups);
                }
            } else {
                this.scheduleGroupToForceComplete(messageGroup);
            }
        } else {
            noOutput = false;
            this.discardMessage(message, lock);
        }
        return noOutput;
    }

    private void cancelScheduledFutureIfAny(Object correlationKey, UUID groupIdUuid, boolean mayInterruptIfRunning) {
        boolean canceled;
        ScheduledFuture<?> scheduledFuture = this.expireGroupScheduledFutures.remove(groupIdUuid);
        if (scheduledFuture != null && (canceled = scheduledFuture.cancel(mayInterruptIfRunning))) {
            this.logger.debug(() -> "Cancel 'ScheduledFuture' for MessageGroup with Correlation Key [ " + correlationKey + "].");
        }
    }

    private void setGroupConditionIfAny(Message<?> message, MessageGroup messageGroup) {
        if (this.groupConditionSupplier != null) {
            String condition = this.groupConditionSupplier.apply(message, messageGroup.getCondition());
            this.messageStore.setGroupCondition(messageGroup.getGroupId(), condition);
            messageGroup.setCondition(condition);
        }
    }

    protected boolean isExpireGroupsUponCompletion() {
        return false;
    }

    private void removeEmptyGroupAfterTimeout(UUID groupId, long timeout) {
        ScheduledFuture scheduledFuture = this.getTaskScheduler().schedule(() -> {
            Lock lock = this.lockRegistry.obtain(groupId.toString());
            try {
                lock.lockInterruptibly();
                try {
                    boolean removeGroup;
                    this.expireGroupScheduledFutures.remove(groupId);
                    MessageGroup groupNow = this.messageStore.getMessageGroup(groupId);
                    boolean bl = removeGroup = groupNow.size() == 0 && groupNow.getLastModified() <= System.currentTimeMillis() - this.minimumTimeoutForEmptyGroups;
                    if (removeGroup) {
                        this.logger.debug(() -> "Removing empty group: " + groupId);
                        this.remove(groupNow);
                    }
                }
                finally {
                    lock.unlock();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.logger.debug(() -> "Thread was interrupted while trying to obtain lock.Rescheduling empty MessageGroup [ " + groupId + "] for removal.");
                this.removeEmptyGroupAfterTimeout(groupId, timeout);
            }
        }, new Date(System.currentTimeMillis() + timeout));
        this.logger.debug(() -> "Schedule empty MessageGroup [ " + groupId + "] for removal.");
        this.expireGroupScheduledFutures.put(groupId, scheduledFuture);
    }

    private void scheduleGroupToForceComplete(MessageGroup messageGroup) {
        Object groupTimeout = this.obtainGroupTimeout(messageGroup);
        if (groupTimeout != null) {
            Date startTime = null;
            if (groupTimeout instanceof Date) {
                startTime = (Date)groupTimeout;
            } else if ((Long)groupTimeout > 0L) {
                startTime = new Date(System.currentTimeMillis() + (Long)groupTimeout);
            }
            if (startTime != null) {
                Object groupId = messageGroup.getGroupId();
                long timestamp = messageGroup.getTimestamp();
                long lastModified = messageGroup.getLastModified();
                ScheduledFuture scheduledFuture = this.getTaskScheduler().schedule(() -> {
                    try {
                        this.processForceRelease(groupId, timestamp, lastModified);
                    }
                    catch (MessageDeliveryException ex) {
                        this.logger.warn((Throwable)ex, () -> "The MessageGroup [" + groupId + "] is rescheduled by the reason of: ");
                        this.scheduleGroupToForceComplete(groupId);
                    }
                }, startTime);
                this.logger.debug(() -> "Schedule MessageGroup [ " + messageGroup + "] to 'forceComplete'.");
                this.expireGroupScheduledFutures.put(UUIDConverter.getUUID(groupId), scheduledFuture);
            } else {
                this.forceReleaseProcessor.processMessageGroup(messageGroup);
            }
        }
    }

    private void scheduleGroupToForceComplete(Object groupId) {
        MessageGroup messageGroup = this.messageStore.getMessageGroup(groupId);
        this.scheduleGroupToForceComplete(messageGroup);
    }

    private void processForceRelease(Object groupId, long timestamp, long lastModified) {
        MessageGroup messageGroup = this.messageStore.getMessageGroup(groupId);
        if (messageGroup.getTimestamp() == timestamp && messageGroup.getLastModified() == lastModified) {
            this.forceReleaseProcessor.processMessageGroup(messageGroup);
        }
    }

    private void discardMessage(Message<?> message, Lock lock) {
        if (this.releaseLockBeforeSend) {
            lock.unlock();
        }
        this.discardMessage(message);
    }

    private void discardMessage(Message<?> message) {
        MessageChannel messageChannel = this.getDiscardChannel();
        if (messageChannel != null) {
            this.messagingTemplate.send(messageChannel, message);
        }
    }

    protected abstract void afterRelease(MessageGroup var1, Collection<Message<?>> var2);

    protected void afterRelease(MessageGroup group, Collection<Message<?>> completedMessages, boolean timeout) {
        this.afterRelease(group, completedMessages);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void forceComplete(MessageGroup group) {
        Object correlationKey = group.getGroupId();
        UUID groupId = UUIDConverter.getUUID(correlationKey);
        Lock lock = this.lockRegistry.obtain(groupId.toString());
        boolean removeGroup = true;
        boolean noOutput = true;
        try {
            lock.lockInterruptibly();
            try {
                this.cancelScheduledFutureIfAny(correlationKey, groupId, false);
                MessageGroup groupNow = group;
                if (!group.isComplete()) {
                    groupNow = this.messageStore.getMessageGroup(correlationKey);
                }
                long lastModifiedNow = groupNow.getLastModified();
                int groupSize = groupNow.size();
                if (!(groupNow.isComplete() && groupSize != 0 || group.getLastModified() != lastModifiedNow || group.getTimestamp() != groupNow.getTimestamp())) {
                    if (groupSize > 0) {
                        noOutput = false;
                        if (this.releaseStrategy.canRelease(groupNow)) {
                            this.completeGroup(correlationKey, groupNow, lock);
                        } else {
                            this.expireGroup(correlationKey, groupNow, lock);
                        }
                        if (!this.expireGroupsUponTimeout) {
                            this.afterRelease(groupNow, groupNow.getMessages(), true);
                            removeGroup = false;
                        }
                    } else {
                        boolean bl = removeGroup = lastModifiedNow <= System.currentTimeMillis() - this.minimumTimeoutForEmptyGroups;
                        if (removeGroup) {
                            this.logger.debug(() -> "Removing empty group: " + correlationKey);
                        }
                    }
                } else {
                    removeGroup = false;
                    this.logger.debug(() -> "Group expiry candidate (" + correlationKey + ") has changed - it may be reconsidered for a future expiration");
                }
            }
            catch (MessageDeliveryException e) {
                removeGroup = false;
                this.logger.debug(() -> "Group expiry candidate (" + correlationKey + ") has been affected by MessageDeliveryException - it may be reconsidered for a future expiration one more time");
                throw e;
            }
            finally {
                try {
                    if (removeGroup) {
                        this.remove(group);
                    }
                }
                finally {
                    if (noOutput || !this.releaseLockBeforeSend) {
                        lock.unlock();
                    }
                }
            }
        }
        catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            this.logger.debug((CharSequence)"Thread was interrupted while trying to obtain lock");
        }
    }

    protected void remove(MessageGroup group) {
        Object correlationKey = group.getGroupId();
        this.messageStore.removeMessageGroup(correlationKey);
    }

    protected int findLastReleasedSequenceNumber(Object groupId, Collection<Message<?>> partialSequence) {
        Message<?> lastReleasedMessage = Collections.max(partialSequence, this.sequenceNumberComparator);
        return StaticMessageHeaderAccessor.getSequenceNumber(lastReleasedMessage);
    }

    protected MessageGroup store(Object correlationKey, Message<?> message) {
        return this.messageStore.addMessageToGroup(correlationKey, message);
    }

    protected void expireGroup(Object correlationKey, MessageGroup group, Lock lock) {
        this.logger.info(() -> "Expiring MessageGroup with correlationKey[" + correlationKey + "]");
        if (this.sendPartialResultOnExpiry) {
            this.logger.debug(() -> "Prematurely releasing partially complete group with key [" + correlationKey + "] to: " + this.getOutputChannel());
            this.completeGroup(correlationKey, group, lock);
        } else {
            this.logger.debug(() -> "Discarding messages of partially complete group with key [" + correlationKey + "] to: " + (this.discardChannelName != null ? this.discardChannelName : this.discardChannel));
            if (this.releaseLockBeforeSend) {
                lock.unlock();
            }
            group.getMessages().forEach(this::discardMessage);
        }
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent((ApplicationEvent)new MessageGroupExpiredEvent(this, correlationKey, group.size(), new Date(group.getLastModified()), new Date(), !this.sendPartialResultOnExpiry));
        }
    }

    protected void completeGroup(Object correlationKey, MessageGroup group, Lock lock) {
        Message<?> first = null;
        if (group != null) {
            first = group.getOne();
        }
        this.completeGroup(first, correlationKey, group, lock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Collection<Message<?>> completeGroup(Message<?> message, Object correlationKey, MessageGroup group, Lock lock) {
        AbstractIntegrationMessageBuilder result;
        Collection partialSequence = null;
        try {
            this.logger.debug(() -> "Completing group with correlationKey [" + correlationKey + "]");
            result = this.outputProcessor.processMessageGroup(group);
            if (result instanceof Collection) {
                this.verifyResultCollectionConsistsOfMessages((Collection)((Object)result));
                partialSequence = (Collection)((Object)result);
            }
            if (this.popSequence && partialSequence == null) {
                AbstractIntegrationMessageBuilder<Object> messageBuilder = null;
                if (result instanceof AbstractIntegrationMessageBuilder) {
                    messageBuilder = result;
                } else if (!(result instanceof Message)) {
                    messageBuilder = this.getMessageBuilderFactory().withPayload(result).copyHeaders((Map<String, ?>)message.getHeaders());
                } else if (AbstractCorrelatingMessageHandler.compareSequences((Message)result, message)) {
                    messageBuilder = this.getMessageBuilderFactory().fromMessage((Message)result);
                }
                result = messageBuilder != null ? messageBuilder.popSequenceDetails() : result;
            }
        }
        finally {
            if (this.releaseLockBeforeSend) {
                lock.unlock();
            }
        }
        this.sendOutputs(result, message);
        return partialSequence;
    }

    private static boolean compareSequences(Message<?> msg1, Message<?> msg2) {
        Object sequence1 = msg1.getHeaders().get((Object)"sequenceDetails");
        Object sequence2 = msg2.getHeaders().get((Object)"sequenceDetails");
        return ObjectUtils.nullSafeEquals((Object)sequence1, (Object)sequence2);
    }

    protected void verifyResultCollectionConsistsOfMessages(Collection<?> elements) {
        Class commonElementType = CollectionUtils.findCommonElementType(elements);
        Assert.isAssignable(Message.class, (Class)commonElementType, () -> "The expected collection of Messages contains non-Message element: " + commonElementType);
    }

    protected Object obtainGroupTimeout(MessageGroup group) {
        if (this.groupTimeoutExpression != null) {
            Object timeout = this.groupTimeoutExpression.getValue(this.evaluationContext, (Object)group);
            if (timeout instanceof Date) {
                return timeout;
            }
            if (timeout != null) {
                try {
                    return Long.parseLong(timeout.toString());
                }
                catch (NumberFormatException ex) {
                    throw new IllegalStateException("Error evaluating 'groupTimeoutExpression'", ex);
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {
        this.expireGroupScheduledFutures.values().forEach(future -> future.cancel(true));
    }

    @Override
    public void start() {
        if (!this.running) {
            this.running = true;
            if (this.outputProcessor instanceof Lifecycle) {
                ((Lifecycle)this.outputProcessor).start();
            }
            if (this.releaseStrategy instanceof Lifecycle) {
                ((Lifecycle)this.releaseStrategy).start();
            }
            if (this.expireTimeout > 0L) {
                this.purgeOrphanedGroups();
                if (this.expireDuration != null) {
                    this.getTaskScheduler().scheduleWithFixedDelay(this::purgeOrphanedGroups, this.expireDuration);
                }
            }
        }
    }

    @Override
    public void stop() {
        if (this.running) {
            this.running = false;
            if (this.outputProcessor instanceof Lifecycle) {
                ((Lifecycle)this.outputProcessor).stop();
            }
            if (this.releaseStrategy instanceof Lifecycle) {
                ((Lifecycle)this.releaseStrategy).stop();
            }
        }
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    public void purgeOrphanedGroups() {
        Assert.isTrue((this.expireTimeout > 0L ? 1 : 0) != 0, (String)"'expireTimeout' must be more than 0.");
        this.messageStore.expireMessageGroups(this.expireTimeout);
    }

    private class ForceReleaseMessageGroupProcessor
    implements MessageGroupProcessor {
        ForceReleaseMessageGroupProcessor() {
        }

        @Override
        public Object processMessageGroup(MessageGroup group) {
            AbstractCorrelatingMessageHandler.this.forceComplete(group);
            return null;
        }
    }

    protected static class SequenceAwareMessageGroup
    extends SimpleMessageGroup {
        private final SimpleMessageGroup sourceGroup;

        public SequenceAwareMessageGroup(MessageGroup messageGroup) {
            super(messageGroup.getMessages(), null, messageGroup.getGroupId(), messageGroup.getTimestamp(), messageGroup.isComplete(), true);
            this.sourceGroup = messageGroup instanceof SimpleMessageGroup ? (SimpleMessageGroup)messageGroup : null;
        }

        @Override
        public boolean canAdd(Message<?> message) {
            if (this.size() == 0) {
                return true;
            }
            Integer messageSequenceNumber = (Integer)message.getHeaders().get((Object)"sequenceNumber", Integer.class);
            if (messageSequenceNumber != null && messageSequenceNumber > 0) {
                Integer messageSequenceSize = (Integer)message.getHeaders().get((Object)"sequenceSize", Integer.class);
                if (messageSequenceSize == null) {
                    messageSequenceSize = 0;
                }
                return messageSequenceSize.equals(this.getSequenceSize()) && !(this.sourceGroup == null ? this.containsSequenceNumber(this.getMessages(), messageSequenceNumber) : this.sourceGroup.containsSequence(messageSequenceNumber));
            }
            return true;
        }

        private boolean containsSequenceNumber(Collection<Message<?>> messages, Integer messageSequenceNumber) {
            for (Message<?> member : messages) {
                if (!messageSequenceNumber.equals(member.getHeaders().get((Object)"sequenceNumber", Integer.class))) continue;
                return true;
            }
            return false;
        }
    }
}

