/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.config.AutowireCapableBeanFactory
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.transaction;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.JavaUtils;
import org.springframework.integration.transaction.DefaultTransactionSynchronizationFactory;
import org.springframework.integration.transaction.ExpressionEvaluatingTransactionSynchronizationProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class TransactionSynchronizationFactoryBean
implements FactoryBean<DefaultTransactionSynchronizationFactory>,
BeanFactoryAware {
    private static final String EXPRESSION_OR_CHANNEL_NEEDED = "At least one attribute ('expression' and/or 'messageChannel') must be defined";
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private final AtomicInteger counter = new AtomicInteger();
    private BeanFactory beanFactory;
    private volatile String beforeCommitExpression;
    private volatile String afterCommitExpression;
    private volatile String afterRollbackExpression;
    private volatile MessageChannel beforeCommitChannel;
    private volatile String beforeCommitChannelName;
    private volatile MessageChannel afterCommitChannel;
    private volatile String afterCommitChannelName;
    private volatile MessageChannel afterRollbackChannel;
    private volatile String afterRollbackChannelName;
    private volatile DestinationResolver<MessageChannel> channelResolver;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public TransactionSynchronizationFactoryBean channelResolver(DestinationResolver<MessageChannel> resolver) {
        Assert.notNull(resolver, (String)"'channelResolver' must not be null");
        this.channelResolver = resolver;
        return this;
    }

    public TransactionSynchronizationFactoryBean beforeCommit(String expression) {
        return this.beforeCommit(expression, this.beforeCommitChannel);
    }

    public TransactionSynchronizationFactoryBean beforeCommit(String expression, String messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || StringUtils.hasText((String)messageChannel) ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.beforeCommitExpression = expression;
        this.beforeCommitChannelName = messageChannel;
        this.beforeCommitChannel = null;
        return this;
    }

    public TransactionSynchronizationFactoryBean beforeCommit(MessageChannel messageChannel) {
        return this.beforeCommit(this.beforeCommitExpression, messageChannel);
    }

    public TransactionSynchronizationFactoryBean beforeCommit(String expression, MessageChannel messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || messageChannel != null ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.beforeCommitExpression = expression;
        this.beforeCommitChannel = messageChannel;
        this.beforeCommitChannelName = null;
        return this;
    }

    public TransactionSynchronizationFactoryBean afterCommit(String expression) {
        return this.afterCommit(expression, this.afterCommitChannel);
    }

    public TransactionSynchronizationFactoryBean afterCommit(String expression, String messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || StringUtils.hasText((String)messageChannel) ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.afterCommitExpression = expression;
        this.afterCommitChannelName = messageChannel;
        this.afterCommitChannel = null;
        return this;
    }

    public TransactionSynchronizationFactoryBean afterCommit(MessageChannel messageChannel) {
        return this.afterCommit(this.afterCommitExpression, messageChannel);
    }

    public TransactionSynchronizationFactoryBean afterCommit(String expression, MessageChannel messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || messageChannel != null ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.afterCommitExpression = expression;
        this.afterCommitChannel = messageChannel;
        this.afterCommitChannelName = null;
        return this;
    }

    public TransactionSynchronizationFactoryBean afterRollback(String expression) {
        return this.afterRollback(expression, this.afterRollbackChannel);
    }

    public TransactionSynchronizationFactoryBean afterRollback(String expression, String messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || StringUtils.hasText((String)messageChannel) ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.afterRollbackExpression = expression;
        this.afterRollbackChannelName = messageChannel;
        this.afterRollbackChannel = null;
        return this;
    }

    public TransactionSynchronizationFactoryBean afterRollback(MessageChannel messageChannel) {
        return this.afterRollback(this.afterRollbackExpression, messageChannel);
    }

    public TransactionSynchronizationFactoryBean afterRollback(String expression, MessageChannel messageChannel) {
        Assert.state((StringUtils.hasText((String)expression) || messageChannel != null ? 1 : 0) != 0, (String)EXPRESSION_OR_CHANNEL_NEEDED);
        this.afterRollbackExpression = expression;
        this.afterRollbackChannel = messageChannel;
        this.afterRollbackChannelName = null;
        return this;
    }

    public DefaultTransactionSynchronizationFactory getObject() {
        if (this.channelResolver == null) {
            this.channelResolver = new BeanFactoryMessageChannelDestinationResolver(this.beanFactory);
        }
        if (StringUtils.hasText((String)this.beforeCommitChannelName)) {
            this.beforeCommitChannel = (MessageChannel)this.channelResolver.resolveDestination(this.beforeCommitChannelName);
        }
        if (StringUtils.hasText((String)this.afterCommitChannelName)) {
            this.afterCommitChannel = (MessageChannel)this.channelResolver.resolveDestination(this.afterCommitChannelName);
        }
        if (StringUtils.hasText((String)this.afterRollbackChannelName)) {
            this.afterRollbackChannel = (MessageChannel)this.channelResolver.resolveDestination(this.afterRollbackChannelName);
        }
        ExpressionEvaluatingTransactionSynchronizationProcessor processor = new ExpressionEvaluatingTransactionSynchronizationProcessor();
        JavaUtils.INSTANCE.acceptIfHasText(this.beforeCommitExpression, expression -> processor.setBeforeCommitExpression(PARSER.parseExpression(expression))).acceptIfHasText(this.afterCommitExpression, expression -> processor.setAfterCommitExpression(PARSER.parseExpression(expression))).acceptIfHasText(this.afterRollbackExpression, expression -> processor.setAfterRollbackExpression(PARSER.parseExpression(expression))).acceptIfNotNull(this.beforeCommitChannel, processor::setBeforeCommitChannel).acceptIfNotNull(this.afterCommitChannel, processor::setAfterCommitChannel).acceptIfNotNull(this.afterRollbackChannel, processor::setAfterRollbackChannel);
        if (this.beanFactory instanceof AutowireCapableBeanFactory) {
            ((AutowireCapableBeanFactory)this.beanFactory).initializeBean((Object)processor, this.getClass().getName() + "#" + this.counter.incrementAndGet());
        }
        return new DefaultTransactionSynchronizationFactory(processor);
    }

    public Class<?> getObjectType() {
        return DefaultTransactionSynchronizationFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }
}

