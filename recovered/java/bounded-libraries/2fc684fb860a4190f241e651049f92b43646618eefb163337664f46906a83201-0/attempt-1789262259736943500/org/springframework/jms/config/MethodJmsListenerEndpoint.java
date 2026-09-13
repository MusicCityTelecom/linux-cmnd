/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.EmbeddedValueResolver
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.handler.annotation.SendTo
 *  org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory
 *  org.springframework.messaging.handler.invocation.InvocableHandlerMethod
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.util.StringValueResolver
 */
package org.springframework.jms.config;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.jms.config.AbstractJmsListenerEndpoint;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

public class MethodJmsListenerEndpoint
extends AbstractJmsListenerEndpoint
implements BeanFactoryAware {
    @Nullable
    private Object bean;
    @Nullable
    private Method method;
    @Nullable
    private Method mostSpecificMethod;
    @Nullable
    private MessageHandlerMethodFactory messageHandlerMethodFactory;
    @Nullable
    private StringValueResolver embeddedValueResolver;

    public void setBean(@Nullable Object bean) {
        this.bean = bean;
    }

    @Nullable
    public Object getBean() {
        return this.bean;
    }

    public void setMethod(@Nullable Method method) {
        this.method = method;
    }

    @Nullable
    public Method getMethod() {
        return this.method;
    }

    public void setMostSpecificMethod(@Nullable Method mostSpecificMethod) {
        this.mostSpecificMethod = mostSpecificMethod;
    }

    @Nullable
    public Method getMostSpecificMethod() {
        Object bean;
        if (this.mostSpecificMethod != null) {
            return this.mostSpecificMethod;
        }
        Method method = this.getMethod();
        if (method != null && AopUtils.isAopProxy((Object)(bean = this.getBean()))) {
            Class targetClass = AopProxyUtils.ultimateTargetClass((Object)bean);
            method = AopUtils.getMostSpecificMethod((Method)method, (Class)targetClass);
        }
        return method;
    }

    public void setMessageHandlerMethodFactory(MessageHandlerMethodFactory messageHandlerMethodFactory) {
        this.messageHandlerMethodFactory = messageHandlerMethodFactory;
    }

    public void setEmbeddedValueResolver(@Nullable StringValueResolver embeddedValueResolver) {
        this.embeddedValueResolver = embeddedValueResolver;
    }

    public void setBeanFactory(@Nullable BeanFactory beanFactory) {
        if (this.embeddedValueResolver == null && beanFactory instanceof ConfigurableBeanFactory) {
            this.embeddedValueResolver = new EmbeddedValueResolver((ConfigurableBeanFactory)beanFactory);
        }
    }

    @Override
    protected MessagingMessageListenerAdapter createMessageListener(MessageListenerContainer container) {
        DestinationResolver destinationResolver;
        MessageConverter messageConverter;
        QosSettings responseQosSettings;
        Assert.state((this.messageHandlerMethodFactory != null ? 1 : 0) != 0, (String)"Could not create message listener - MessageHandlerMethodFactory not set");
        MessagingMessageListenerAdapter messageListener = this.createMessageListenerInstance();
        Object bean = this.getBean();
        Method method = this.getMethod();
        Assert.state((bean != null && method != null ? 1 : 0) != 0, (String)"No bean+method set on endpoint");
        InvocableHandlerMethod invocableHandlerMethod = this.messageHandlerMethodFactory.createInvocableHandlerMethod(bean, method);
        messageListener.setHandlerMethod(invocableHandlerMethod);
        String responseDestination = this.getDefaultResponseDestination();
        if (StringUtils.hasText((String)responseDestination)) {
            if (container.isReplyPubSubDomain()) {
                messageListener.setDefaultResponseTopicName(responseDestination);
            } else {
                messageListener.setDefaultResponseQueueName(responseDestination);
            }
        }
        if ((responseQosSettings = container.getReplyQosSettings()) != null) {
            messageListener.setResponseQosSettings(responseQosSettings);
        }
        if ((messageConverter = container.getMessageConverter()) != null) {
            messageListener.setMessageConverter(messageConverter);
        }
        if ((destinationResolver = container.getDestinationResolver()) != null) {
            messageListener.setDestinationResolver(destinationResolver);
        }
        return messageListener;
    }

    protected MessagingMessageListenerAdapter createMessageListenerInstance() {
        return new MessagingMessageListenerAdapter();
    }

    @Nullable
    protected String getDefaultResponseDestination() {
        Method specificMethod = this.getMostSpecificMethod();
        if (specificMethod == null) {
            return null;
        }
        SendTo ann = this.getSendTo(specificMethod);
        if (ann != null) {
            Object[] destinations = ann.value();
            if (destinations.length != 1) {
                throw new IllegalStateException("Invalid @" + SendTo.class.getSimpleName() + " annotation on '" + specificMethod + "' one destination must be set (got " + Arrays.toString(destinations) + ")");
            }
            return this.resolve(destinations[0]);
        }
        return null;
    }

    @Nullable
    private SendTo getSendTo(Method specificMethod) {
        SendTo ann = (SendTo)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)specificMethod, SendTo.class);
        if (ann == null) {
            ann = (SendTo)AnnotatedElementUtils.findMergedAnnotation(specificMethod.getDeclaringClass(), SendTo.class);
        }
        return ann;
    }

    @Nullable
    private String resolve(String value) {
        return this.embeddedValueResolver != null ? this.embeddedValueResolver.resolveStringValue(value) : value;
    }

    @Override
    protected StringBuilder getEndpointDescription() {
        return super.getEndpointDescription().append(" | bean='").append(this.bean).append('\'').append(" | method='").append(this.method).append('\'');
    }
}

