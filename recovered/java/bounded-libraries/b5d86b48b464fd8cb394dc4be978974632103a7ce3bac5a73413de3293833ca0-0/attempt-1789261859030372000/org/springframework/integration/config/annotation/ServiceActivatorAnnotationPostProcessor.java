/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.handler.ReplyProducingMessageHandlerWrapper;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.util.StringUtils;

public class ServiceActivatorAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<ServiceActivator> {
    public ServiceActivatorAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.messageHandlerAttributes.addAll(Arrays.asList("outputChannel", "requiresReply", "adviceChain"));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        String isAsync;
        String requiresReply;
        AbstractReplyProducingMessageHandler serviceActivator;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            serviceActivator = this.extractTypeIfPossible(target, AbstractReplyProducingMessageHandler.class);
            if (serviceActivator != null) {
                this.checkMessageHandlerAttributes(this.resolveTargetBeanName(method), annotations);
                return (MessageHandler)target;
            }
            if (target instanceof ReactiveMessageHandler) {
                return new ReactiveMessageHandlerAdapter((ReactiveMessageHandler)target);
            }
            if (target instanceof MessageHandler) {
                return new ReplyProducingMessageHandlerWrapper((MessageHandler)target);
            }
            MessageProcessor<?> messageProcessor = this.buildLambdaMessageProcessorForBeanMethod(method, target);
            serviceActivator = messageProcessor != null ? new ServiceActivatingHandler(messageProcessor) : new ServiceActivatingHandler(target);
        } else {
            serviceActivator = new ServiceActivatingHandler(bean, method);
        }
        if (StringUtils.hasText((String)(requiresReply = MessagingAnnotationUtils.resolveAttribute(annotations, "requiresReply", String.class)))) {
            serviceActivator.setRequiresReply(this.resolveAttributeToBoolean(requiresReply));
        }
        if (StringUtils.hasText((String)(isAsync = MessagingAnnotationUtils.resolveAttribute(annotations, "async", String.class)))) {
            serviceActivator.setAsync(this.resolveAttributeToBoolean(isAsync));
        }
        this.setOutputChannelIfPresent(annotations, serviceActivator);
        return serviceActivator;
    }
}

