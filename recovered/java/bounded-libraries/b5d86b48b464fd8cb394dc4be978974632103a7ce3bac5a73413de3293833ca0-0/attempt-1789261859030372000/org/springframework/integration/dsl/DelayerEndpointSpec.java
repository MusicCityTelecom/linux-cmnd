/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import org.aopalliance.aop.Advice;
import org.springframework.expression.Expression;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.transaction.TransactionInterceptorBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.Assert;

public class DelayerEndpointSpec
extends ConsumerEndpointSpec<DelayerEndpointSpec, DelayHandler> {
    private final List<Advice> delayedAdvice = new LinkedList<Advice>();

    protected DelayerEndpointSpec(DelayHandler delayHandler) {
        super(delayHandler);
        Assert.notNull((Object)delayHandler, (String)"'delayHandler' must not be null.");
        ((DelayHandler)this.handler).setDelayedAdviceChain(this.delayedAdvice);
    }

    public DelayerEndpointSpec defaultDelay(long defaultDelay) {
        ((DelayHandler)this.handler).setDefaultDelay(defaultDelay);
        return (DelayerEndpointSpec)this._this();
    }

    public DelayerEndpointSpec ignoreExpressionFailures(boolean ignoreExpressionFailures) {
        ((DelayHandler)this.handler).setIgnoreExpressionFailures(ignoreExpressionFailures);
        return (DelayerEndpointSpec)this._this();
    }

    public DelayerEndpointSpec messageStore(MessageGroupStore messageStore) {
        ((DelayHandler)this.handler).setMessageStore(messageStore);
        return (DelayerEndpointSpec)this._this();
    }

    public DelayerEndpointSpec delayedAdvice(Advice ... advice) {
        this.delayedAdvice.addAll(Arrays.asList(advice));
        return (DelayerEndpointSpec)this._this();
    }

    public DelayerEndpointSpec delayExpression(Expression delayExpression) {
        ((DelayHandler)this.handler).setDelayExpression(delayExpression);
        return this;
    }

    public DelayerEndpointSpec delayExpression(String delayExpression) {
        ((DelayHandler)this.handler).setDelayExpression(PARSER.parseExpression(delayExpression));
        return this;
    }

    public DelayerEndpointSpec delayedMessageErrorChannel(MessageChannel channel) {
        ((DelayHandler)this.handler).setDelayedMessageErrorChannel(channel);
        return this;
    }

    public DelayerEndpointSpec delayedMessageErrorChannel(String channel) {
        ((DelayHandler)this.handler).setDelayedMessageErrorChannelName(channel);
        return this;
    }

    public DelayerEndpointSpec maxAttempts(int maxAttempts) {
        ((DelayHandler)this.handler).setMaxAttempts(maxAttempts);
        return this;
    }

    public DelayerEndpointSpec retryDelay(long retryDelay) {
        ((DelayHandler)this.handler).setRetryDelay(retryDelay);
        return this;
    }

    public DelayerEndpointSpec transactionalRelease() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptorBuilder().build();
        this.componentsToRegister.put(transactionInterceptor, null);
        return this.delayedAdvice(new Advice[]{transactionInterceptor});
    }

    public DelayerEndpointSpec transactionalRelease(TransactionInterceptor transactionInterceptor) {
        return this.delayedAdvice(new Advice[]{transactionInterceptor});
    }

    public DelayerEndpointSpec transactionalRelease(TransactionManager transactionManager) {
        return this.transactionalRelease(new TransactionInterceptorBuilder().transactionManager(transactionManager).build());
    }

    public <P> DelayerEndpointSpec delayFunction(Function<Message<P>, Object> delayFunction) {
        ((DelayHandler)this.handler).setDelayExpression(new FunctionExpression<Message<P>>(delayFunction));
        return this;
    }
}

