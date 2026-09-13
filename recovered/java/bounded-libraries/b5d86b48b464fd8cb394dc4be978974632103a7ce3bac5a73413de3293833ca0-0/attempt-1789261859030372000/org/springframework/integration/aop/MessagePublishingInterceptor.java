/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.LocalVariableTableParameterNameDiscoverer
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.aop.PublisherMetadataSource;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.expression.ExpressionEvalMap;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;

public class MessagePublishingInterceptor
implements MethodInterceptor,
BeanFactoryAware {
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final PublisherMetadataSource metadataSource;
    private DestinationResolver<MessageChannel> channelResolver;
    private BeanFactory beanFactory;
    private MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private boolean messageBuilderFactorySet;
    private String defaultChannelName;

    public MessagePublishingInterceptor(PublisherMetadataSource metadataSource) {
        Assert.notNull((Object)metadataSource, (String)"metadataSource must not be null");
        this.metadataSource = metadataSource;
    }

    public void setDefaultChannelName(String defaultChannelName) {
        this.defaultChannelName = defaultChannelName;
    }

    public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        this.channelResolver = channelResolver;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.messagingTemplate.setBeanFactory(beanFactory);
        if (this.channelResolver == null) {
            this.channelResolver = ChannelResolverUtils.getChannelResolver(this.beanFactory);
        }
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    public final Object invoke(MethodInvocation invocation) throws Throwable {
        StandardEvaluationContext context = ExpressionUtils.createStandardEvaluationContext(this.beanFactory);
        Class targetClass = AopUtils.getTargetClass((Object)invocation.getThis());
        Method method = AopUtils.getMostSpecificMethod((Method)invocation.getMethod(), (Class)targetClass);
        String[] argumentNames = this.resolveArgumentNames(method);
        context.setVariable("method", (Object)method.getName());
        if (invocation.getArguments().length > 0 && argumentNames != null) {
            HashMap<Object, Object> argumentMap = new HashMap<Object, Object>();
            for (int i = 0; i < argumentNames.length && invocation.getArguments().length > i; ++i) {
                Object argValue = invocation.getArguments()[i];
                argumentMap.put(i, argValue);
                argumentMap.put(argumentNames[i], argValue);
            }
            context.setVariable("args", argumentMap);
        }
        try {
            Object returnValue = invocation.proceed();
            context.setVariable("return", returnValue);
            Object object = returnValue;
            return object;
        }
        catch (Throwable t) {
            context.setVariable("exception", (Object)t);
            throw t;
        }
        finally {
            this.publishMessage(method, context);
        }
    }

    private String[] resolveArgumentNames(Method method) {
        return this.parameterNameDiscoverer.getParameterNames(method);
    }

    private void publishMessage(Method method, StandardEvaluationContext context) {
        Object result;
        Expression payloadExpression = this.metadataSource.getExpressionForPayload(method);
        if (payloadExpression == null) {
            payloadExpression = PublisherMetadataSource.RETURN_VALUE_EXPRESSION;
        }
        if ((result = payloadExpression.getValue((EvaluationContext)context)) != null) {
            AbstractIntegrationMessageBuilder<Object> builder = result instanceof Message ? this.getMessageBuilderFactory().fromMessage((Message)result) : this.getMessageBuilderFactory().withPayload(result);
            Map<String, Object> headers = this.evaluateHeaders(method, context);
            if (headers != null) {
                builder.copyHeaders(headers);
            }
            Message<Object> message = builder.build();
            String channelName = this.metadataSource.getChannelName(method);
            MessageChannel channel = null;
            if (channelName != null) {
                Assert.state((this.channelResolver != null ? 1 : 0) != 0, (String)"ChannelResolver is required to resolve channel names.");
                channel = (MessageChannel)this.channelResolver.resolveDestination(channelName);
            }
            if (channel != null) {
                this.messagingTemplate.send(channel, message);
            } else {
                String channelNameToUse = this.defaultChannelName;
                if (channelNameToUse != null && this.messagingTemplate.getDefaultDestination() == null) {
                    Assert.state((this.channelResolver != null ? 1 : 0) != 0, (String)"ChannelResolver is required to resolve channel names.");
                    this.messagingTemplate.setDefaultChannel((MessageChannel)this.channelResolver.resolveDestination(channelNameToUse));
                    this.defaultChannelName = null;
                }
                this.messagingTemplate.send(message);
            }
        }
    }

    private Map<String, Object> evaluateHeaders(Method method, StandardEvaluationContext context) {
        Map<String, Expression> headerExpressionMap = this.metadataSource.getExpressionsForHeaders(method);
        if (headerExpressionMap != null) {
            return ExpressionEvalMap.from(headerExpressionMap).usingEvaluationContext((EvaluationContext)context).build();
        }
        return null;
    }
}

