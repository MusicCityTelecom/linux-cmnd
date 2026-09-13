/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.messaging.MessageHandler
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
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.MethodInvokingTransformer;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.MessageHandler;

public class TransformerAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<org.springframework.integration.annotation.Transformer> {
    public TransformerAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.messageHandlerAttributes.addAll(Arrays.asList("outputChannel", "adviceChain"));
    }

    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        Transformer transformer;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            transformer = this.extractTypeIfPossible(target, Transformer.class);
            if (transformer == null) {
                if (this.extractTypeIfPossible(target, AbstractReplyProducingMessageHandler.class) != null) {
                    this.checkMessageHandlerAttributes(this.resolveTargetBeanName(method), annotations);
                    return (MessageHandler)target;
                }
                MessageProcessor<?> messageProcessor = this.buildLambdaMessageProcessorForBeanMethod(method, target);
                transformer = messageProcessor != null ? new MethodInvokingTransformer((Object)messageProcessor) : new MethodInvokingTransformer(target);
            }
        } else {
            transformer = new MethodInvokingTransformer(bean, method);
        }
        MessageTransformingHandler handler = new MessageTransformingHandler(transformer);
        this.setOutputChannelIfPresent(annotations, handler);
        return handler;
    }
}

