/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.aopalliance.aop.Advice;
import org.springframework.integration.aggregator.AbstractCorrelatingMessageHandler;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ExpressionEvaluatingCorrelationStrategy;
import org.springframework.integration.aggregator.ExpressionEvaluatingReleaseStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.config.CorrelationStrategyFactoryBean;
import org.springframework.integration.config.ReleaseStrategyFactoryBean;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public abstract class CorrelationHandlerSpec<S extends CorrelationHandlerSpec<S, H>, H extends AbstractCorrelatingMessageHandler>
extends ConsumerEndpointSpec<S, H> {
    private final List<Advice> forceReleaseAdviceChain = new LinkedList<Advice>();

    protected CorrelationHandlerSpec(H messageHandler) {
        super(messageHandler);
        ((AbstractCorrelatingMessageHandler)messageHandler).setForceReleaseAdviceChain(this.forceReleaseAdviceChain);
    }

    public S messageStore(MessageGroupStore messageStore) {
        Assert.notNull((Object)messageStore, (String)"'messageStore' must not be null.");
        ((AbstractCorrelatingMessageHandler)this.handler).setMessageStore(messageStore);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S sendPartialResultOnExpiry(boolean sendPartialResultOnExpiry) {
        ((AbstractCorrelatingMessageHandler)this.handler).setSendPartialResultOnExpiry(sendPartialResultOnExpiry);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S minimumTimeoutForEmptyGroups(long minimumTimeoutForEmptyGroups) {
        ((AbstractCorrelatingMessageHandler)this.handler).setMinimumTimeoutForEmptyGroups(minimumTimeoutForEmptyGroups);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S groupTimeout(long groupTimeout) {
        ((AbstractCorrelatingMessageHandler)this.handler).setGroupTimeoutExpression(new ValueExpression<Long>(groupTimeout));
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S groupTimeoutExpression(String groupTimeoutExpression) {
        Assert.hasText((String)groupTimeoutExpression, (String)"'groupTimeoutExpression' must not be empty string.");
        ((AbstractCorrelatingMessageHandler)this.handler).setGroupTimeoutExpression(PARSER.parseExpression(groupTimeoutExpression));
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S groupTimeout(Function<MessageGroup, ?> groupTimeoutFunction) {
        ((AbstractCorrelatingMessageHandler)this.handler).setGroupTimeoutExpression(new FunctionExpression<MessageGroup>(groupTimeoutFunction));
        return (S)((CorrelationHandlerSpec)this._this());
    }

    @Override
    public S taskScheduler(TaskScheduler taskScheduler) {
        Assert.notNull((Object)taskScheduler, (String)"'taskScheduler' must not be null");
        super.taskScheduler(taskScheduler);
        ((AbstractCorrelatingMessageHandler)this.handler).setTaskScheduler(taskScheduler);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S discardChannel(MessageChannel discardChannel) {
        Assert.notNull((Object)discardChannel, (String)"'discardChannel' must not be null.");
        ((AbstractCorrelatingMessageHandler)this.handler).setDiscardChannel(discardChannel);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S discardChannel(String discardChannelName) {
        Assert.hasText((String)discardChannelName, (String)"'discardChannelName' must not be empty.");
        ((AbstractCorrelatingMessageHandler)this.handler).setDiscardChannelName(discardChannelName);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S processor(Object target) {
        try {
            CorrelationStrategyFactoryBean correlationStrategyFactoryBean = new CorrelationStrategyFactoryBean();
            correlationStrategyFactoryBean.setTarget(target);
            correlationStrategyFactoryBean.afterPropertiesSet();
            ReleaseStrategyFactoryBean releaseStrategyFactoryBean = new ReleaseStrategyFactoryBean();
            releaseStrategyFactoryBean.setTarget(target);
            releaseStrategyFactoryBean.afterPropertiesSet();
            return ((CorrelationHandlerSpec)this.correlationStrategy(correlationStrategyFactoryBean.getObject())).releaseStrategy(releaseStrategyFactoryBean.getObject());
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public S correlationExpression(String correlationExpression) {
        return this.correlationStrategy(new ExpressionEvaluatingCorrelationStrategy(correlationExpression));
    }

    public S correlationStrategy(Object target, String methodName) {
        try {
            CorrelationStrategyFactoryBean correlationStrategyFactoryBean = new CorrelationStrategyFactoryBean();
            correlationStrategyFactoryBean.setTarget(target);
            correlationStrategyFactoryBean.setMethodName(methodName);
            correlationStrategyFactoryBean.afterPropertiesSet();
            return this.correlationStrategy(correlationStrategyFactoryBean.getObject());
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public S correlationStrategy(CorrelationStrategy correlationStrategy) {
        ((AbstractCorrelatingMessageHandler)this.handler).setCorrelationStrategy(correlationStrategy);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S releaseExpression(String releaseExpression) {
        return this.releaseStrategy(new ExpressionEvaluatingReleaseStrategy(releaseExpression));
    }

    public S releaseStrategy(Object target, String methodName) {
        try {
            ReleaseStrategyFactoryBean releaseStrategyFactoryBean = new ReleaseStrategyFactoryBean();
            releaseStrategyFactoryBean.setTarget(target);
            releaseStrategyFactoryBean.setMethodName(methodName);
            releaseStrategyFactoryBean.afterPropertiesSet();
            return this.releaseStrategy(releaseStrategyFactoryBean.getObject());
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public S releaseStrategy(ReleaseStrategy releaseStrategy) {
        ((AbstractCorrelatingMessageHandler)this.handler).setReleaseStrategy(releaseStrategy);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S expireGroupsUponTimeout(boolean expireGroupsUponTimeout) {
        ((AbstractCorrelatingMessageHandler)this.handler).setExpireGroupsUponTimeout(expireGroupsUponTimeout);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S forceReleaseAdvice(Advice ... advice) {
        this.forceReleaseAdviceChain.addAll(Arrays.asList(advice));
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S lockRegistry(LockRegistry lockRegistry) {
        Assert.notNull((Object)lockRegistry, (String)"'lockRegistry' must not be null.");
        ((AbstractCorrelatingMessageHandler)this.handler).setLockRegistry(lockRegistry);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S popSequence(boolean popSequence) {
        ((AbstractCorrelatingMessageHandler)this.handler).setPopSequence(popSequence);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    @Deprecated
    public S setExpireTimeout(long expireTimeout) {
        return this.expireTimeout(expireTimeout);
    }

    public S expireTimeout(long expireTimeout) {
        ((AbstractCorrelatingMessageHandler)this.handler).setExpireTimeout(expireTimeout);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    @Deprecated
    public S setExpireDuration(Duration expireDuration) {
        return this.expireDuration(expireDuration);
    }

    public S expireDuration(Duration expireDuration) {
        ((AbstractCorrelatingMessageHandler)this.handler).setExpireDuration(expireDuration);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S releaseLockBeforeSend(boolean releaseLockBeforeSend) {
        ((AbstractCorrelatingMessageHandler)this.handler).setReleaseLockBeforeSend(releaseLockBeforeSend);
        return (S)((CorrelationHandlerSpec)this._this());
    }

    public S groupConditionSupplier(BiFunction<Message<?>, String, String> conditionSupplier) {
        ((AbstractCorrelatingMessageHandler)this.handler).setGroupConditionSupplier(conditionSupplier);
        return (S)((CorrelationHandlerSpec)this._this());
    }
}

