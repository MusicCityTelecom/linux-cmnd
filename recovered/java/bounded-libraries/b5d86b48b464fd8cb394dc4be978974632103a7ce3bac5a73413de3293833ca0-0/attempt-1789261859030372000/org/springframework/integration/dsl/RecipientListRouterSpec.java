/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.dsl;

import org.springframework.expression.Expression;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.AbstractRouterSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.util.ClassUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.StringUtils;

public class RecipientListRouterSpec
extends AbstractRouterSpec<RecipientListRouterSpec, RecipientListRouter> {
    protected RecipientListRouterSpec() {
        super(new RecipientListRouter());
    }

    public RecipientListRouterSpec recipient(String channelName) {
        return this.recipient(channelName, (String)null);
    }

    public RecipientListRouterSpec recipient(String channelName, String expression) {
        if (StringUtils.hasText((String)expression)) {
            return this.recipient(channelName, PARSER.parseExpression(expression));
        }
        ((RecipientListRouter)this.handler).addRecipient(channelName);
        return (RecipientListRouterSpec)this._this();
    }

    public RecipientListRouterSpec recipient(String channelName, Expression expression) {
        ExpressionEvaluatingSelector selector = new ExpressionEvaluatingSelector(expression);
        ((RecipientListRouter)this.handler).addRecipient(channelName, (MessageSelector)selector);
        this.componentsToRegister.put(selector, null);
        return (RecipientListRouterSpec)this._this();
    }

    public RecipientListRouterSpec recipientMessageSelector(String channelName, MessageSelector selector) {
        return this.recipient(channelName, selector);
    }

    public <P> RecipientListRouterSpec recipient(String channelName, GenericSelector<P> selector) {
        MessageSelector messageSelector = this.wrapToMessageSelectorIfNecessary(selector);
        ((RecipientListRouter)this.handler).addRecipient(channelName, messageSelector);
        return (RecipientListRouterSpec)this._this();
    }

    private <P> MessageSelector wrapToMessageSelectorIfNecessary(GenericSelector<P> selector) {
        MessageSelector messageSelector = selector instanceof MessageSelector ? (MessageSelector)selector : new MethodInvokingSelector(selector, ClassUtils.SELECTOR_ACCEPT_METHOD);
        return messageSelector;
    }

    public RecipientListRouterSpec recipient(MessageChannel channel) {
        return this.recipient(channel, (String)null);
    }

    public RecipientListRouterSpec recipient(MessageChannel channel, String expression) {
        return this.recipient(channel, StringUtils.hasText((String)expression) ? PARSER.parseExpression(expression) : null);
    }

    public RecipientListRouterSpec recipient(MessageChannel channel, Expression expression) {
        if (expression != null) {
            ExpressionEvaluatingSelector selector = new ExpressionEvaluatingSelector(expression);
            ((RecipientListRouter)this.handler).addRecipient(channel, (MessageSelector)selector);
            this.componentsToRegister.put(selector, null);
        } else {
            ((RecipientListRouter)this.handler).addRecipient(channel);
        }
        return (RecipientListRouterSpec)this._this();
    }

    public RecipientListRouterSpec recipientMessageSelector(MessageChannel channel, MessageSelector selector) {
        return this.recipient(channel, selector);
    }

    public <P> RecipientListRouterSpec recipient(MessageChannel channel, GenericSelector<P> selector) {
        MessageSelector messageSelector = this.wrapToMessageSelectorIfNecessary(selector);
        ((RecipientListRouter)this.handler).addRecipient(channel, messageSelector);
        return (RecipientListRouterSpec)this._this();
    }

    public RecipientListRouterSpec recipientMessageSelectorFlow(MessageSelector selector, IntegrationFlow subFlow) {
        return this.recipientFlow(selector, subFlow);
    }

    public <P> RecipientListRouterSpec recipientFlow(GenericSelector<P> selector, IntegrationFlow subFlow) {
        MessageChannel channel = this.obtainInputChannelFromFlow(subFlow);
        return this.recipient(channel, selector);
    }

    public RecipientListRouterSpec recipientFlow(IntegrationFlow subFlow) {
        return this.recipientFlow((String)null, subFlow);
    }

    public RecipientListRouterSpec recipientFlow(String expression, IntegrationFlow subFlow) {
        return this.recipientFlow(StringUtils.hasText((String)expression) ? PARSER.parseExpression(expression) : null, subFlow);
    }

    public RecipientListRouterSpec recipientFlow(Expression expression, IntegrationFlow subFlow) {
        MessageChannel channel = this.obtainInputChannelFromFlow(subFlow);
        return this.recipient(channel, expression);
    }
}

