/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import org.springframework.expression.Expression;
import org.springframework.integration.config.AbstractStandardMessageHandlerFactoryBean;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.integration.splitter.ExpressionEvaluatingSplitter;
import org.springframework.integration.splitter.MethodInvokingSplitter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SplitterFactoryBean
extends AbstractStandardMessageHandlerFactoryBean {
    private Boolean applySequence;
    private String delimiters;
    private MessageChannel discardChannel;
    private String discardChannelName;

    public void setApplySequence(boolean applySequence) {
        this.applySequence = applySequence;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
    }

    public void setDiscardChannelName(String discardChannelName) {
        this.discardChannelName = discardChannelName;
    }

    @Override
    protected MessageHandler createMethodInvokingHandler(Object targetObject, String targetMethodName) {
        Assert.notNull((Object)targetObject, (String)"targetObject must not be null");
        AbstractMessageSplitter splitter = IntegrationObjectSupport.extractTypeIfPossible(targetObject, AbstractMessageSplitter.class);
        if (splitter == null) {
            this.checkForIllegalTarget(targetObject, targetMethodName);
            splitter = this.createMethodInvokingSplitter(targetObject, targetMethodName);
            this.configureSplitter(splitter);
        } else {
            Assert.isTrue((!StringUtils.hasText((String)targetMethodName) ? 1 : 0) != 0, (String)"target method should not be provided when the target object is an implementation of AbstractMessageSplitter");
            this.configureSplitter(splitter);
            if (targetObject instanceof MessageHandler) {
                return (MessageHandler)targetObject;
            }
        }
        return splitter;
    }

    protected AbstractMessageSplitter createMethodInvokingSplitter(Object targetObject, String targetMethodName) {
        return StringUtils.hasText((String)targetMethodName) ? new MethodInvokingSplitter(targetObject, targetMethodName) : new MethodInvokingSplitter(targetObject);
    }

    @Override
    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        return this.configureSplitter(new ExpressionEvaluatingSplitter(expression));
    }

    @Override
    protected MessageHandler createDefaultHandler() {
        return this.configureSplitter(new DefaultMessageSplitter());
    }

    protected AbstractMessageSplitter configureSplitter(AbstractMessageSplitter splitter) {
        this.postProcessReplyProducer(splitter);
        if (this.discardChannel != null) {
            splitter.setDiscardChannel(this.discardChannel);
        } else if (StringUtils.hasText((String)this.discardChannelName)) {
            splitter.setDiscardChannelName(this.discardChannelName);
        }
        return splitter;
    }

    @Override
    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return handler instanceof AbstractMessageSplitter || this.applySequence == null && this.delimiters == null;
    }

    @Override
    protected void postProcessReplyProducer(AbstractMessageProducingHandler handler) {
        super.postProcessReplyProducer(handler);
        if (!(handler instanceof AbstractMessageSplitter)) {
            Assert.isNull((Object)this.applySequence, (String)"Cannot set applySequence if the referenced bean is an AbstractReplyProducingMessageHandler, but not an AbstractMessageSplitter");
            Assert.isNull((Object)this.delimiters, (String)"Cannot set delimiters if the referenced bean is not an an AbstractReplyProducingMessageHandler, but not an AbstractMessageSplitter");
        } else {
            AbstractMessageSplitter splitter = (AbstractMessageSplitter)handler;
            if (this.delimiters != null) {
                Assert.isInstanceOf(DefaultMessageSplitter.class, (Object)splitter, (String)"The 'delimiters' property is only available for a Splitter definition where no 'ref', 'expression', or inner bean has been provided.");
                ((DefaultMessageSplitter)splitter).setDelimiters(this.delimiters);
            }
            if (this.applySequence != null) {
                splitter.setApplySequence(this.applySequence);
            }
        }
    }

    @Override
    protected Class<? extends MessageHandler> getPreCreationHandlerType() {
        return AbstractMessageSplitter.class;
    }
}

