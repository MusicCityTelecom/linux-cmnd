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

import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.integration.config.AbstractStandardMessageHandlerFactoryBean;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class RouterFactoryBean
extends AbstractStandardMessageHandlerFactoryBean {
    private Map<String, String> channelMappings;
    private MessageChannel defaultOutputChannel;
    private String defaultOutputChannelName;
    private Boolean resolutionRequired;
    private Boolean applySequence;
    private Boolean ignoreSendFailures;

    public void setDefaultOutputChannel(MessageChannel defaultOutputChannel) {
        this.defaultOutputChannel = defaultOutputChannel;
    }

    public void setDefaultOutputChannelName(String defaultOutputChannelName) {
        this.defaultOutputChannelName = defaultOutputChannelName;
    }

    public void setResolutionRequired(Boolean resolutionRequired) {
        this.resolutionRequired = resolutionRequired;
    }

    public void setApplySequence(Boolean applySequence) {
        this.applySequence = applySequence;
    }

    public void setIgnoreSendFailures(Boolean ignoreSendFailures) {
        this.ignoreSendFailures = ignoreSendFailures;
    }

    public void setChannelMappings(Map<String, String> channelMappings) {
        this.channelMappings = channelMappings;
    }

    @Override
    protected MessageHandler createMethodInvokingHandler(Object targetObject, String targetMethodName) {
        Assert.notNull((Object)targetObject, (String)"target object must not be null");
        AbstractMessageRouter router = IntegrationObjectSupport.extractTypeIfPossible(targetObject, AbstractMessageRouter.class);
        if (router == null) {
            if (targetObject instanceof MessageHandler && this.noRouterAttributesProvided() && this.methodIsHandleMessageOrEmpty(targetMethodName)) {
                return (MessageHandler)targetObject;
            }
            router = this.createMethodInvokingRouter(targetObject, targetMethodName);
            this.configureRouter(router);
        } else {
            Assert.isTrue((!StringUtils.hasText((String)targetMethodName) ? 1 : 0) != 0, (String)"target method should not be provided when the target object is an implementation of AbstractMessageRouter");
            this.configureRouter(router);
            if (targetObject instanceof MessageHandler) {
                return (MessageHandler)targetObject;
            }
        }
        return router;
    }

    @Override
    protected MessageHandler createExpressionEvaluatingHandler(Expression expression) {
        return this.configureRouter(new ExpressionEvaluatingRouter(expression));
    }

    protected AbstractMappingMessageRouter createMethodInvokingRouter(Object targetObject, String targetMethodName) {
        return StringUtils.hasText((String)targetMethodName) ? new MethodInvokingRouter(targetObject, targetMethodName) : new MethodInvokingRouter(targetObject);
    }

    protected AbstractMessageRouter configureRouter(AbstractMessageRouter router) {
        if (this.defaultOutputChannel != null) {
            router.setDefaultOutputChannel(this.defaultOutputChannel);
        }
        if (this.defaultOutputChannelName != null) {
            router.setDefaultOutputChannelName(this.defaultOutputChannelName);
        }
        if (this.getSendTimeout() != null) {
            router.setSendTimeout(this.getSendTimeout());
        }
        if (this.applySequence != null) {
            router.setApplySequence(this.applySequence);
        }
        if (this.ignoreSendFailures != null) {
            router.setIgnoreSendFailures(this.ignoreSendFailures);
        }
        if (router instanceof AbstractMappingMessageRouter) {
            this.configureMappingRouter((AbstractMappingMessageRouter)router);
        }
        return router;
    }

    protected void configureMappingRouter(AbstractMappingMessageRouter router) {
        if (this.channelMappings != null) {
            router.setChannelMappings(this.channelMappings);
        }
        if (this.resolutionRequired != null) {
            router.setResolutionRequired(this.resolutionRequired);
        }
    }

    @Override
    protected boolean canBeUsedDirect(AbstractMessageProducingHandler handler) {
        return this.noRouterAttributesProvided();
    }

    protected boolean noRouterAttributesProvided() {
        return this.channelMappings == null && this.defaultOutputChannel == null && this.getSendTimeout() == null && this.resolutionRequired == null && this.applySequence == null && this.ignoreSendFailures == null;
    }

    @Override
    protected Class<? extends MessageHandler> getPreCreationHandlerType() {
        return AbstractMessageRouter.class;
    }
}

